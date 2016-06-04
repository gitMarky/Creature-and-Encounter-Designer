package project.thirteenthage.creatures.internal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import project.thirteenthage.creatures.internal.gui.views.CreatureEncounterPanel;
import project.thirteenthage.creatures.internal.interfaces.ICreature;


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
		content.append(tab(tabDepth) + Html.BEGIN_DIV);
		content.append(tab(tabDepth) + "<table style=\"border-color:rgb(136,136,136);border-width:1px;border-collapse:collapse;width:100%\" border=\"1\" bordercolor=\"#888888\" cellpadding=\"7\" cellspacing=\"0\">" + Constants.NEWLINE);
		content.append(tab(tabDepth) + "<thead>" + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + "<tr>" + Constants.NEWLINE);
		content.append(tab(tabDepth + 2) + "<td style=\"text-align:center;width:10%;background-color:rgb(100,150,255)\">" + Constants.NEWLINE);

		parseCreatureInfo(creature, tabDepth + 3);
		
		content.append(tab(tabDepth + 2) + "</td>" + Constants.NEWLINE);
		content.append(tab(tabDepth + 2) + "<td>" + Constants.NEWLINE);
		
		parseCreatureAttacks(creature, tabDepth + 3);
		
		content.append(tab(tabDepth + 2) + "</td>" + Constants.NEWLINE);
		content.append(tab(tabDepth + 2) + "<td style=\"text-align:center;width:10%;background-color:rgb(100,150,255)\">" + Constants.NEWLINE);

		parseCreatureStats(creature, tabDepth + 3);
		
		content.append(tab(tabDepth + 2) + "</td>" + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + "</tr>" + Constants.NEWLINE);
		content.append(tab(tabDepth) + "</thead>" + Constants.NEWLINE);
		content.append(tab(tabDepth) + "</table>" + Constants.NEWLINE);
		
		printDamageTracks(amount, tabDepth);
		
		content.append(tab(tabDepth) + Html.END_DIV);
	}

	
	private void parseCreatureInfo(ICreature creature, int tabDepth)
	{
		content.append(tab(tabDepth) + "<table style=\"color:rgb(255,255,255)\">" + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + "<tr><td>Normal</td></tr>" + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + "<tr><td>2nd level</td></tr>" + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + "<tr><td>Wrecker</td></tr>" + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + "<tr><td>Devil</td></tr>" + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + "<tr><td>Baatezu</td></tr>" + Constants.NEWLINE);
		content.append(tab(tabDepth) + "</table>" + Constants.NEWLINE);
	}

	
	private void parseCreatureAttacks(ICreature creature, int tabDepth)
	{
		content.append(tab(tabDepth) + "<p><b>Initiative +6</b></p>" + Constants.NEWLINE);
		content.append(tab(tabDepth) + "<p><b>Furious claws and fangs</b> +7 AC / 6 damage<br><i>Natural 11+</i>: The abishai can make another melee attack as a free action, but no more than three per round.</p>" + Constants.NEWLINE);
		content.append(tab(tabDepth) + "<p><b>Poison Tail</b>+7 AC / 5 damage, and 5 ongoing acid damage. Once per battle.</p>" + Constants.NEWLINE);
		content.append(tab(tabDepth) + "<p><b>Dive</b> +9 AC / 8 damage</p>" + Constants.NEWLINE);
		content.append(tab(tabDepth) + "<p><i>Flight</i></p>" + Constants.NEWLINE);
		content.append(tab(tabDepth) + "<p><i>Baatezu Regeneration</i>: The abishai regenerates 1 hp per level at the start of each round, up to 3 times. An attack with holy water removes one regeneration.</p>" + Constants.NEWLINE);
		content.append(tab(tabDepth) + "<p><u>Nastier Specials</u></p>" + Constants.NEWLINE);
		content.append(tab(tabDepth) + "<p><i>Scaly Hide</i>: The abishai has resist damage 12+ against attacks targeting AC.</p>" + Constants.NEWLINE);
	}
	
	
	private void parseCreatureStats(ICreature creature, int tabDepth)
	{
		content.append(tab(tabDepth) + "<table style=\"color:rgb(255,255,255)\">" + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + "<tr><td>AC</td><td>18</td></tr>" + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + "<tr><td>PD</td><td>16</td></tr>" + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + "<tr><td>MD</td><td>12</td></tr>" + Constants.NEWLINE);
		content.append(tab(tabDepth + 1) + "<tr><td>HP</td><td>36</td></tr>" + Constants.NEWLINE);
		content.append(tab(tabDepth) + "</table>" + Constants.NEWLINE);
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
