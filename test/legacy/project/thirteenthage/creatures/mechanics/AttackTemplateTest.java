package legacy.project.thirteenthage.creatures.mechanics;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import static project.thirteenthage.creatures.TestConstants.*;

public class AttackTemplateTest
{
	@Test
	public void testInfectedBite()
	{
		final double exact = 1e-8;
		final File xml = new File("resources/attacks/bite/attack_bite_infected.xml");
		System.out.println(xml.getAbsolutePath());

		final AttackTemplate attack = new AttackTemplate(xml);
		assertEquals("Infected bite", attack.getName());
		assertEquals(-1, attack.getAttackBonus());
		assertEquals(1.0, attack.getDamageFactor(), exact);
		assertEquals("AC", attack.getDefense());
		assertEquals("ongoing damage", attack.getDescription());
		assertEquals(0, attack.getLevelAdjustment(), DOUBLE_FUZZY_1E_3);
	}

	@Test
	public void testClawChimera()
	{
		final double exact = 1e-8;
		final File xml = new File("resources/attacks/claw/attack_claw_chimera.xml");

		final AttackTemplate attack = new AttackTemplate(xml);
		assertEquals("Fangs, claws, and horns", attack.getName());
		assertEquals(0, attack.getAttackBonus());
		assertEquals(0.25, attack.getDamageFactor(), exact);
		assertEquals("AC", attack.getDefense());
		assertEquals("damage", attack.getDescription());
		assertEquals(0.5, attack.getLevelAdjustment(), DOUBLE_FUZZY_1E_3);
	}
}
