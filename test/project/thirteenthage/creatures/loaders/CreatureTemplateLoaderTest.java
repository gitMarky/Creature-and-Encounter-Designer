package project.thirteenthage.creatures.loaders;

import static org.junit.Assert.*;

import org.junit.Test;

public class CreatureTemplateLoaderTest
{
	@Test
	public void test()
	{
		CreatureTemplateLoader instance = CreatureTemplateLoader.getInstance();
		assertNotNull(instance);
		assertTrue("The instances should be the same", instance == CreatureTemplateLoader.getInstance());
		
		assertEquals(1, instance.getTemplates().size());
		
		assertNotNull(instance.get("creature_dire_rat"));
	}
}