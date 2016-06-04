package project.thirteenthage.creatures.internal;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import project.thirteenthage.creatures.internal.gui.views.CreatureEncounterPanel;
import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.loaders.CreatureLoader;
import project.thirteenthage.creatures.loaders.CreatureTemplateLoader;

public class HtmlExporterTest
{

	@Test
	public void test()
	{
		CreatureLoader.getInstance().load(CreatureTemplateLoader.getInstance());
		
		final File targetFile = new File("export.html");
		
		Map<ICreature, Integer> creatures = new HashMap<ICreature, Integer>();

		final ICreature enemy = CreatureLoader.getInstance().getCreatures().get("creature_human_thug");
		
		creatures.put(enemy, 2);
		
		HtmlExporter exporter = new HtmlExporter(targetFile, creatures);
		
		exporter.saveToFile();
	}

}
