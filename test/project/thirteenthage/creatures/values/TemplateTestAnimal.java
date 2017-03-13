package project.thirteenthage.creatures.values;

import org.junit.Test;

import legacy.project.thirteenthage.creatures.creature.CreatureSize;

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

		testAttack(0, 5, "AC", 4);
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

		testAttack(0, 5, "AC", 3);
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

		testAttack(0, 6, "PD", 1);
		testAttack(1, 6, "AC", 3);
	}


	@Test
	public void testHuntingSpider()
	{
		load("creature_hunting_spider");

		descriptor("Hunting Spider", 2, CreatureSize.NORMAL, "Wrecker", "Beast");
		initiative(6);

		AC(17);
		PD(14);
		MD(11);
		HP(34);

		testAttack(0, 6, "AC", 8);
	}


	@Test
	public void testGiantWebSpider()
	{
		load("creature_giant_web_spider");

		descriptor("Giant Web Spider", 2, CreatureSize.LARGE, "Blocker", "Beast");
		initiative(4);

		testAttack(0, 7, "AC", 5);
		testAttack(1, 7, "PD (up to 2 nearby enemies in a group)", 3);

		AC(17);
		PD(16);
		MD(12);
		HP(68);
	}


	@Test
	public void testWolf()
	{
		load("creature_wolf");

		descriptor("Wolf", 1, CreatureSize.NORMAL, "Troop", "Beast");
		initiative(4);

		testAttack(0, 5, "AC", 5);

		AC(17);
		PD(15);
		MD(11);
		HP(28);
	}


	@Test
	public void testBear()
	{
		load("creature_bear");

		descriptor("Bear", 2, CreatureSize.NORMAL, "Troop", "Beast");
		initiative(4);

		testAttack(0, 7, "AC", 6);

		AC(17);
		PD(16);
		MD(12);
		HP(45);
	}


	@Test
	public void testDireWolf()
	{
		load("creature_dire_wolf");

		descriptor("Dire Wolf", 3, CreatureSize.LARGE, "Troop", "Beast");
		initiative(6);

		testAttack(0, 8, "AC", 18);

		AC(18);
		PD(17);
		MD(13);
		HP(80);
	}


	@Test
	public void testDireBear()
	{
		load("creature_dire_bear");

		descriptor("Dire Bear", 4, CreatureSize.LARGE, "Troop", "Beast");
		initiative(7);

		testAttack(0, 8, "AC", 24);

		AC(19);
		PD(19);
		MD(14);
		HP(130);
	}
}
