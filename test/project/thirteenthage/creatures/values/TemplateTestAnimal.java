package project.thirteenthage.creatures.values;

import org.junit.Test;

import project.thirteenthage.creatures.creature.CreatureSize;

public class TemplateTestAnimal extends TemplateTest
{
	@Override
	protected void load(final String name)
	{
		super.load(String.format("resources/creatures/animal/%s.xml", name));
	}

	@Test
	public void testDireRat()
	{
		load("creature_dire_rat");

		descriptor("Dire Rat", 1, CreatureSize.NORMAL, "Mook", "Beast");
		initiative(2);

		AC(15);
		PD(15);
		MD(10);
		HP(6);

		testAttack(0, 5, "AC", 4.0);
	}


	@Test
	public void testGiantAnt()
	{
		load("creature_giant_ant");

		descriptor("Giant Ant", 0, CreatureSize.NORMAL, "Troop", "Beast");
		initiative(0);

		AC(14);
		PD(13);
		MD(9);
		HP(20);

		testAttack(0, 5, "AC", 3.0);
	}


	@Test
	public void testGiantScorpion()
	{
		load("creature_giant_scorpion");

		descriptor("Giant Scorpion", 1, CreatureSize.NORMAL, "Wrecker", "Beast");
		initiative(6);

		AC(16);
		PD(15);
		MD(10);
		HP(22);

		testAttack(0, 6, "PD", 1.0);
		testAttack(1, 6, "AC", 3.0);
	}
}
