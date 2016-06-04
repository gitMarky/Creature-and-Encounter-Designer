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
		content.append(tab(tabDepth) + "<table style=\"" + "border-color:rgb(136,136,136);border-width:1px;border-collapse:collapse;width:100%" + "\" border=\"1\" bordercolor=\"#888888\" cellpadding=\"7\" cellspacing=\"0\">" + Constants.NEWLINE);
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
		
		printDamageTracks(amount, tabDepth);
		
		content.append(tab(tabDepth) + Html.END_DIV);
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

	
	private void parseCreatureInfo(ICreature creature, int tabDepth)
	{
		content.append(tab(tabDepth) + "<table style=\"" + styleFontWhite() + "\">" + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + "<tr><td>" + creature.getSize().toGuiText() + "</td></tr>" + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + "<tr><td>" + LevelHelper.getLevelText(creature.getLevel()) + "</td></tr>" + Constants.NEWLINE);
		
		for (final String label : creature.getLabels())
		{
			content.append(tab(tabDepth + 1) + "<tr><td>" + label + "</td></tr>" + Constants.NEWLINE);			
		}
		
		content.append(tab(tabDepth) + "</table>" + Constants.NEWLINE);
	}

	
	private void parseCreatureAttacks(ICreature creature, int tabDepth)
	{
		content.append(tab(tabDepth) + "<p><b>Initiative " + String.format("%+d", creature.getInitiative()) + "</b></p>" + Constants.NEWLINE);
		
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
		content.append(tab(tabDepth + 1) + "<tr><td>AC</td><td>" + creature.getAC() + "</td></tr>" + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + "<tr><td>PD</td><td>" + creature.getPD() + "</td></tr>" + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + "<tr><td>MD</td><td>" + creature.getMD() + "</td></tr>" + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + "<tr><td>HP</td><td>" + Conversions.round(creature.getHP()) + "</td></tr>" + Constants.NEWLINE);
		content.append(tab(tabDepth) + "</table>" + Constants.NEWLINE);
	}


	private String styleFontWhite()
	{
		return "color:rgb(255,255,255)";
	}


	private void printDamageTracks(int amount, int tabDepth)
	{
		// TODO Auto-generated method stub	
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
