package project.thirteenthage.creatures.mechanics;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class AttackTemplateTest
{
	@Test
	public void testInfectedBite()
	{
		final double exact = 1e-8;
		final File xml = new File("resources/attacks/attack_infected_bite.xml");
		System.out.println(xml.getAbsolutePath());

		final AttackTemplate attack = new AttackTemplate(xml);
		assertEquals("Infected bite", attack.getName());
		assertEquals(-1, attack.getAttackBonus());
		assertEquals(1.0, attack.getDamageFactor(), exact);
		assertEquals("AC", attack.getDefense());
		assertEquals("ongoing damage", attack.getDescription());
	}
}
