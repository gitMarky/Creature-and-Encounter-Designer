package project.thirteenthage.creatures.loaders;

import static org.junit.Assert.*;

import org.junit.Test;

public class AttackTemplateLoaderTest
{

	@Test
	public void test()
	{
		final AttackTemplateLoader instance = AttackTemplateLoader.getInstance();
		assertNotNull(instance);
		assertTrue("The instances should be the same", instance == AttackTemplateLoader.getInstance());

		assertEquals(16, instance.getTemplates().size());

		assertNotNull(instance.get("attack_bite_infected"));
	}
}
