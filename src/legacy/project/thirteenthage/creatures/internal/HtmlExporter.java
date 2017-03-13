package legacy.project.thirteenthage.creatures.internal;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import legacy.project.thirteenthage.creatures.internal.conversions.Conversions;
import legacy.project.thirteenthage.creatures.internal.gui.StyleConstants;
import legacy.project.thirteenthage.creatures.internal.gui.views.CreatureEncounterPanel;
import legacy.project.thirteenthage.creatures.internal.interfaces.IAttack;
import legacy.project.thirteenthage.creatures.internal.interfaces.ICreature;
import legacy.project.thirteenthage.creatures.internal.interfaces.ISpecial;
import legacy.project.thirteenthage.creatures.loaders.LoaderHelper;
import project.library.marky.html.Html;
import project.library.marky.logger.ApplicationLogger;


/**
 * Exports an encounter to a printable html page.
 */
public class HtmlExporter
{
	private static final String TAB = "    ";

	private final StringBuilder content = new StringBuilder();
	private final File _targetFile;


	public HtmlExporter(final Map<ICreature, CreatureEncounterPanel> creatures, File targetFile)
	{
		targetFile = LoaderHelper.forceExtension(targetFile, LoaderHelper.EXTENSION_HTML);

		_targetFile = targetFile;
		convertAndParse(creatures);
	}


	public HtmlExporter(final File targetFile, final Map<ICreature, Integer> creatures)
	{
		_targetFile = targetFile;
		parse(creatures);
	}



	private void convertAndParse(final Map<ICreature, CreatureEncounterPanel> creatures)
	{
		final Map<ICreature, Integer> converted = new HashMap<ICreature, Integer>();

		for (final Entry<ICreature, CreatureEncounterPanel> entry : creatures.entrySet())
		{
			converted.put(entry.getKey(), entry.getValue().getAmount());
		}

		parse(converted);
	}


	private void parse(final Map<ICreature, Integer> creatures)
	{
		content.append(Html.BEGIN + Constants.NEWLINE);
		content.append(tab(1) + Html.BEGIN_BODY + Constants.NEWLINE);

		content.append(tab(2) + "<head><title>Encounter</title></head>" + Constants.NEWLINE);

		for (final Entry<ICreature, Integer> entry : creatures.entrySet())
		{
			parseCreature(entry.getKey(), entry.getValue(), 2);
		}

		content.append(tab(1) + Html.END_BODY + Constants.NEWLINE);
		content.append(Html.END);
	}

	private void parseCreature(final ICreature creature, final int amount, final int tabDepth)
	{
		content.append(tab(tabDepth) + "<h4 style=\"" + styleBackgrounDark() + ";" + styleFontColor(new Color(255, 255, 255)) + ";margin-top:30px;margin-bottom:3px;padding:5px\">" + creature.getName() + "</h4>" + Constants.NEWLINE);
		content.append(tab(tabDepth) + Html.BEGIN_DIV);
		content.append(tab(tabDepth) + "<table style=\"" + styleBorder() + "\" " + visibleBorder() + " cellpadding=\"7\" cellspacing=\"0\">" + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + Html.BEGIN_TR + Constants.NEWLINE);
		content.append(tab(tabDepth + 2) + "<td style=\"" + styleSidebar() + ";" + styleBackgroundLight() + "\">" + Constants.NEWLINE);

		parseCreatureInfo(creature, tabDepth + 3);

		content.append(tab(tabDepth + 2) + Html.END_TD + Constants.NEWLINE);
		content.append(tab(tabDepth + 2) + Html.BEGIN_TD + Constants.NEWLINE);

		parseCreatureAttacks(creature, tabDepth + 3);

		content.append(tab(tabDepth + 2) + Html.END_TD + Constants.NEWLINE);
		content.append(tab(tabDepth + 2) + "<td style=\"" + styleSidebar() + ";" + styleBackgroundLight() + "\">" + Constants.NEWLINE);

		parseCreatureStats(creature, tabDepth + 3);

		content.append(tab(tabDepth + 2) + Html.END_TD + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + Html.END_TR + Constants.NEWLINE);
		content.append(tab(tabDepth) + Html.END_TABLE + Constants.NEWLINE);

		printDamageTracks(creature, amount, tabDepth);

		content.append(tab(tabDepth) + Html.END_DIV);
	}

	private void parseCreatureInfo(final ICreature creature, final int tabDepth)
	{
		content.append(tab(tabDepth) + Html.BEGIN_TABLE + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + Html.tableRow(creature.getSize().toGuiText()) + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + Html.tableRow(LevelHelper.getLevelText(creature.getLevel())) + Constants.NEWLINE);

		for (final String label : creature.getLabels())
		{
			content.append(tab(tabDepth + 1) + Html.tableRow(label) + Constants.NEWLINE);
		}

		content.append(tab(tabDepth) + Html.END_TABLE + Constants.NEWLINE);
	}


	private void parseCreatureAttacks(final ICreature creature, final int tabDepth)
	{
		content.append(tab(tabDepth) + Html.BEGIN_PARAGRAPH + Html.BEGIN_BOLD + "Initiative " + String.format("%+d", creature.getInitiative()) + Html.END_BOLD + Html.END_PARAGRAPH + Constants.NEWLINE);

		for (final IAttack attack : creature.getAttacks())
		{
			content.append(tab(tabDepth) + Html.BEGIN_PARAGRAPH + attack.toGuiText() + Html.END_PARAGRAPH + Constants.NEWLINE);
		}

		for (final ISpecial special : creature.getSpecials())
		{
			content.append(tab(tabDepth) + Html.BEGIN_PARAGRAPH + special.toGuiText() + Html.END_PARAGRAPH + Constants.NEWLINE);
		}

		if (!creature.getNastierSpecials().isEmpty())
		{
			content.append(tab(tabDepth) + "<p><u>Nastier Specials</u></p>" + Constants.NEWLINE);
		}

		for (final ISpecial special : creature.getNastierSpecials())
		{
			content.append(tab(tabDepth) + Html.BEGIN_PARAGRAPH + special.toGuiText() + Html.END_PARAGRAPH + Constants.NEWLINE);
		}
	}


	private void parseCreatureStats(final ICreature creature, final int tabDepth)
	{
		content.append(tab(tabDepth) + Html.BEGIN_TABLE + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + Html.tableRow("AC", "" + creature.getAC()) + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + Html.tableRow("PD", "" + creature.getPD()) + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + Html.tableRow("MD", "" + creature.getMD()) + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + Html.tableRow("HP", "" + Conversions.round(creature.getHP())) + Constants.NEWLINE);
		content.append(tab(tabDepth) + Html.END_TABLE + Constants.NEWLINE);
	}


	private void printDamageTracks(final ICreature creature, final int amount, final int tabDepth)
	{
		final String cell = "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp";
		final int hp = Conversions.round(creature.getHP());

		final String endTable = tab(tabDepth) + Html.END_TD + Html.END_TR + Constants.NEWLINE
				+ tab(tabDepth) + Html.END_TABLE + Constants.NEWLINE;
		for (int i = 1; i <= amount; ++i)
		{
			boolean createNewTable = false;
			if (creature.isMook())
			{
				final int number = (i-1) % Constants.MAX_MOOK_POOL_SIZE;

				if (number == 0) createNewTable = true;
			}
			else
			{
				createNewTable = true;
			}


			if (createNewTable)
			{
				if (i > 1) content.append(endTable);

				content.append(tab(tabDepth) + "<table style=\"" + styleBorder(rgb(StyleConstants.BACKGROUND_LIGHT), 3) + "\" " + visibleBorder() + "\">" + Constants.NEWLINE);
				content.append(tab(tabDepth) + Html.BEGIN_TR + Html.BEGIN_TD + Constants.NEWLINE);
			}

			printDamageTrack(creature, tabDepth + (creature.isMook() ? 1 : 0), cell, hp, i);
		}

		content.append(endTable);
	}


	private void printDamageTrack(final ICreature creature, final int tabDepth, final String cell, final int hp, final int i)
	{
		content.append(tab(tabDepth) + "<table style=\"" + styleBorder() + "\" " + visibleBorder() + "\">" + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + Html.BEGIN_TR + "<th rowspan=\"3\">" + creature.getName() + " #" + i + "</th><td colspan=\"10\">HP: " + hp + Html.END_TD + Html.END_TR + Constants.NEWLINE);

		final String ongoingDamage = "Ongoing damage";
		final String confused = "Confused";
		final String dazed = "Dazed";
		final String fear = "Fear";
		final String hampered = "Hampered";
		final String helpless = "Helpless";
		final String stuck = "Stuck";
		final String stunned = "Stunned";
		final String vulnerable = "Vulnerable";
		final String weakened = "Weakened";
		content.append(tab(tabDepth + 1) + Html.tableRow(ongoingDamage, cell, confused, cell, dazed, cell, fear, cell, hampered, cell) + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + Html.tableRow(helpless, cell, stuck, cell, stunned, cell, vulnerable, cell, weakened, cell) + Constants.NEWLINE);
		content.append(tab(tabDepth) + Html.END_TABLE + Constants.NEWLINE);
	}

	private String visibleBorder()
	{
		return visibleBorder(1);
	}

	private String visibleBorder(final int width)
	{
		return "border=\"" + width + "\" bordercolor=\"#888888\"";
	}


	private String styleBorder()
	{
		return styleBorder(rgb(136, 136, 136), 1);
	}

	private String styleBorder(final String rgb, final int width)
	{
		return "border-color:" + rgb + ";border-width:" + width + "px;border-collapse:collapse;width:100%";
	}



	private String styleFontColor(final Color color)
	{
		return "color:" + rgb(color);
	}


	private String styleBackgrounDark()
	{
		return styleBackgroundColor(StyleConstants.BACKGROUND_DARK);
	}


	private String styleSidebar()
	{
		return "text-align:center;width:10%";
	}


	private String styleBackgroundLight()
	{
		return styleBackgroundColor(StyleConstants.BACKGROUND_LIGHT);
	}

	private String styleBackgroundColor(final Color color)
	{
		return "background-color:" + rgb(color);
	}


	private static String rgb(final Color color)
	{
		return rgb(color.getRed(), color.getGreen(), color.getBlue());
	}


	private static String rgb(final int r, final int g, final int b)
	{
		return "rgb(" + r + ", " + g + ", " + b + ")";
	}


	public void saveToFile()
	{
		saveToFile(_targetFile);
	}


	private void saveToFile(final File targetFile)
	{
		FileWriter fileWriter = null;
		try
		{
			fileWriter = new FileWriter(targetFile);

			fileWriter.write(content.toString());
		}
		catch (final IOException e)
		{
			ApplicationLogger.getLogger().throwing("", "", e);
		}
		finally
		{
			if (fileWriter != null)
			{
				try
				{
					fileWriter.close();
				}
				catch (final IOException e)
				{
					ApplicationLogger.getLogger().throwing("", "", e);
				}
			}
		}
	}


	private String tab(final int depth)
	{
		String tabs = "";
		for (int i = 0; i < depth; ++i)
		{
			tabs += TAB;
		}
		return tabs;
	}
}
