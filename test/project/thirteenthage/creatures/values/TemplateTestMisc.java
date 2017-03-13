package project.thirteenthage.creatures.values;

import org.junit.Test;

import legacy.project.thirteenthage.creatures.creature.CreatureSize;

public class TemplateTestMisc extends TemplateTest
{
	@Override
	protected void load(final String name)
	{
		super.load(String.format("resources/creatures/%s.xml", name));
	}

	@Test
	public void testAnkheg()
	{
		load("creature_ankheg");

		descriptor("Ankheg", 2, CreatureSize.LARGE, "Troop", "Beast");
		initiative(8);

		testAttack(0, 7, "AC", 8);
		testAttack(1, 7, "PD", 5);

		AC(19);
		PD(17);
		MD(11);
		HP(60);
	}


	@Test
	public void testBulette()
	{
		load("creature_bulette");

		descriptor("Bulette", 5, CreatureSize.LARGE, "Wrecker", "Beast");
		initiative(7);

		testAttack(0, 12, "AC", 15);

		AC(22);
		PD(19);
		MD(14);
		HP(170);
	}


	@Test
	public void testChimera()
	{
		load("creature_chimera");

		descriptor("Chimera", 9, CreatureSize.LARGE, "Wrecker", "Beast");
		initiative(15);

		testAttack(0, 14, "AC", 25);

		AC(24);
		PD(20);
		MD(16);
		HP(320);
	}
}
