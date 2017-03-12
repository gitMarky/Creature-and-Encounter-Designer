package project.thirteenthage.creatures.values;

import org.junit.Test;

import project.thirteenthage.creatures.creature.CreatureSize;

public class TemplateTestAnimal extends TemplateTest
{
	@Test
	public void testDireRat()
	{
		load("resources/creatures/animal/creature_dire_rat.xml");

		descriptor("Dire Rat", 1, CreatureSize.NORMAL, "Mook", "Beast");
		initiative(2);

		AC(15);
		PD(15);
		MD(10);
		HP(6);

		testAttack(0, "Infected bite", 5, "AC", 4.0);
	}
}
