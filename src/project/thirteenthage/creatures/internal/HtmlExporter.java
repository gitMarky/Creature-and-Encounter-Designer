package project.thirteenthage.creatures.internal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import project.thirteenthage.creatures.internal.conversions.Conversions;
import project.thirteenthage.creatures.internal.gui.views.CreatureEncounterPanel;
import project.thirteenthage.creatures.internal.interfaces.IAttack;
import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.internal.interfaces.ISpecial;


/**
 * Exports an encounter to a printable html page.
 */
public class HtmlExporter
{
	private static final String TAB = "    ";
	
	private final StringBuilder content = new StringBuilder();
	private final File _targetFile;

	
	public HtmlExporter(Map<ICreature, CreatureEncounterPanel> creatures, File targetFile)
	{
		_targetFile = targetFile;
		convertAndParse(creatures);
	}
	
	
	public HtmlExporter(File targetFile, Map<ICreature, Integer> creatures)
	{
		_targetFile = targetFile;
		parse(creatures);
	}
	

	
	private void convertAndParse(Map<ICreature, CreatureEncounterPanel> creatures)
	{
		final Map<ICreature, Integer> converted = new HashMap<ICreature, Integer>();
		
		for (final Entry<ICreature, CreatureEncounterPanel> entry : creatures.entrySet())
		{
			converted.put(entry.getKey(), entry.getValue().getAmount());
		}
		
		parse(converted);
	}
	

	private void parse(Map<ICreature, Integer> creatures)
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
	
	private void parseCreature(ICreature creature, int amount, int tabDepth)
	{	
		content.append(tab(tabDepth) + "<h4 style=\"" + styleBackgrounDark() + ";" + styleFontWhite() + ";margin-top:30px;margin-bottom:3px;padding:5px\">" + creature.getName() + "</h4>" + Constants.NEWLINE);
		content.append(tab(tabDepth) + Html.BEGIN_DIV);
		content.append(tab(tabDepth) + "<table style=\"" + styleBorder() + "\" " + visibleBorder() + " cellpadding=\"7\" cellspacing=\"0\">" + Constants.NEWLINE);
		content.append(tab(tabDepth) + "<thead>" + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + "<tr>" + Constants.NEWLINE);
		content.append(tab(tabDepth + 2) + "<td style=\"" + styleSidebar() + ";" + styleBackgroundLight() + "\">" + Constants.NEWLINE);

		parseCreatureInfo(creature, tabDepth + 3);
		
		content.append(tab(tabDepth + 2) + "</td>" + Constants.NEWLINE);
		content.append(tab(tabDepth + 2) + "<td>" + Constants.NEWLINE);
		
		parseCreatureAttacks(creature, tabDepth + 3);
		
		content.append(tab(tabDepth + 2) + "</td>" + Constants.NEWLINE);
		content.append(tab(tabDepth + 2) + "<td style=\"" + styleSidebar() + ";" + styleBackgroundLight() + "\">" + Constants.NEWLINE);

		parseCreatureStats(creature, tabDepth + 3);
		
		content.append(tab(tabDepth + 2) + "</td>" + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + "</tr>" + Constants.NEWLINE);
		content.append(tab(tabDepth) + "</thead>" + Constants.NEWLINE);
		content.append(tab(tabDepth) + "</table>" + Constants.NEWLINE);
		
		printDamageTracks(creature, amount, tabDepth);
		
		content.append(tab(tabDepth) + Html.END_DIV);
	}
	
	private void parseCreatureInfo(ICreature creature, int tabDepth)
	{
		content.append(tab(tabDepth) + "<table style=\"" + styleFontWhite() + "\">" + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + Html.tableRow(creature.getSize().toGuiText()) + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + Html.tableRow(LevelHelper.getLevelText(creature.getLevel())) + Constants.NEWLINE);
		
		for (final String label : creature.getLabels())
		{
			content.append(tab(tabDepth + 1) + Html.tableRow(label) + Constants.NEWLINE);			
		}
		
		content.append(tab(tabDepth) + "</table>" + Constants.NEWLINE);
	}


	private void parseCreatureAttacks(ICreature creature, int tabDepth)
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
	
	
	private void parseCreatureStats(ICreature creature, int tabDepth)
	{
		content.append(tab(tabDepth) + "<table style=\"" + styleFontWhite() + "\">" + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + Html.tableRow("AC", "" + creature.getAC()) + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + Html.tableRow("PD", "" + creature.getPD()) + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + Html.tableRow("MD", "" + creature.getMD()) + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + Html.tableRow("HP", "" + Conversions.round(creature.getHP())) + Constants.NEWLINE);
		content.append(tab(tabDepth) + "</table>" + Constants.NEWLINE);
	}
	

	private void printDamageTracks(ICreature creature, int amount, int tabDepth)
	{
		final String cell = "&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp";
		final int hp = Conversions.round(creature.getHP());

		String endTable = tab(tabDepth) + "</td></tr>" + Constants.NEWLINE
				        + tab(tabDepth) + "</table>" + Constants.NEWLINE;
		for (int i = 1; i <= amount; ++i)
		{
			if (creature.isMook())
			{
				int number = (i-1) % Constants.MAX_MOOK_POOL_SIZE;
				
				if (number == 0)
				{
					ApplicationLogger.getLogger().info("Exported mook pool");
					if (i > 1) content.append(endTable);

					content.append(tab(tabDepth) + "<table style=\"" + styleBorder() + "\" " + visibleBorder() + "\">" + Constants.NEWLINE);
					content.append(tab(tabDepth) + "<tr><td>" + Constants.NEWLINE);
				}
			}
			
			printDamageTrack(creature, tabDepth + (creature.isMook() ? 1 : 0), cell, hp, i);
		}
		
		if (creature.isMook()) content.append(endTable);
	}


	private void printDamageTrack(ICreature creature, int tabDepth, final String cell, final int hp, int i)
	{
		content.append(tab(tabDepth) + "<table style=\"" + styleBorder() + "\" " + visibleBorder() + "\">" + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + "<tr><th rowspan=\"3\">" + creature.getName() + " #" + i + "</th><td colspan=\"10\">HP: " + hp + "</td></tr>" + Constants.NEWLINE);

		String ongoingDamage = "Ongoing damage";
		String confused = "Confused";
		String dazed = "Dazed";
		String fear = "Fear";
		String hampered = "Hampered";
		String helpless = "Helpless";
		String stuck = "Stuck";
		String stunned = "Stunned";
		String vulnerable = "Vulnerable";
		String weakened = "Weakened";
		content.append(tab(tabDepth + 1) + Html.tableRow(ongoingDamage, cell, confused, cell, dazed, cell, fear, cell, hampered, cell) + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + Html.tableRow(helpless, cell, stuck, cell, stunned, cell, vulnerable, cell, weakened, cell) + Constants.NEWLINE);
		content.append(tab(tabDepth) + "</table>" + Constants.NEWLINE);
	}


	private String visibleBorder()
	{
		return "border=\"1\" bordercolor=\"#888888\"";
	}


	private String styleBorder()
	{
		return "border-color:rgb(136,136,136);border-width:1px;border-collapse:collapse;width:100%";
	}

	
	private String styleFontWhite()
	{
		return "color:rgb(255,255,255)";
	}


	private String styleBackgrounDark()
	{
		return "background-color:rgb(0,51,153)";
	}


	private String styleSidebar()
	{
		return "text-align:center;width:10%";
	}


	private String styleBackgroundLight()
	{
		return "background-color:rgb(100,150,255)";
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
		catch (IOException e)
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
				catch (IOException e)
				{
					ApplicationLogger.getLogger().throwing("", "", e);
				}
			}
		}
	}
	

	private String tab(int depth)
	{
		String tabs = "";
		for (int i = 0; i < depth; ++i)
		{
			tabs += TAB;
		}
		return tabs;
	}
}
