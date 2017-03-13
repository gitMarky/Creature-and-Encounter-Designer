package legacy.project.thirteenthage.creatures.internal;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import project.thirteenthage.creatures.loaders.CreatureLoader;
import project.thirteenthage.creatures.loaders.CreatureTemplateLoader;
import legacy.project.thirteenthage.creatures.internal.interfaces.ICreature;

public class HtmlExporterTest
{

	@Test
	public void test()
	{
		CreatureLoader.getInstance().load(CreatureTemplateLoader.getInstance());
		
		final File targetFile = new File("export.html");
		
		Map<ICreature, Integer> creatures = new HashMap<ICreature, Integer>();

		final ICreature thug = CreatureLoader.getInstance().getCreatures().get("creature_human_thug");
		final ICreature rat = CreatureLoader.getInstance().getCreatures().get("creature_dire_rat");
		
		creatures.put(thug, 2);
		creatures.put(rat, 7);
		
		HtmlExporter exporter = new HtmlExporter(targetFile, creatures);
		
		exporter.saveToFile();
	}

}
