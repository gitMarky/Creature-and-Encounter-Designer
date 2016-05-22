package project.thirteenthage.creatures.mechanics;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import project.thirteenthage.creatures.tables.CreatureTableRow;
import project.thirteenthage.creatures.tables.CreatureTables;

public class LevelAdjustmentAlternateTest
{	
	private LevelAdjustmentAlternate _object = new LevelAdjustmentAlternate();
	
	@Test
	public void testUpgradeInTable()
	{
		testCreatureTable(CreatureTables.normal(), "normal");
		testCreatureTable(CreatureTables.large(), "large");
		testCreatureTable(CreatureTables.huge(), "huge");
	}
	
	
	@Test
	public void testUpgradeToTable()
	{
		testTableUpgrade(CreatureTables.normal(), CreatureTables.large(), 2, "normal", "large");
		testTableUpgrade(CreatureTables.normal(), CreatureTables.huge(), 3, "normal", "huge");
		testTableUpgrade(CreatureTables.large(), CreatureTables.huge(), 1, "large", "huge");
	}


	private void testCreatureTable(final List<CreatureTableRow> table, final String tableName)
	{
		// go through all creature tables. The update from one row to the other must be exactly one level adjustment
		// since the table represents individual levels
		for (int i = 1; i < table.size(); ++i)
		{
			CreatureTableRow base = table.get(i - 1);
			CreatureTableRow upgrade = table.get(i);
			
			final double hpUp = upgrade.getHP() / base.getHP();
			final double damageUp = upgrade.getStrikeDamage() / base.getStrikeDamage();
			final double hpDown = base.getHP() / upgrade.getHP();
			final double damageDown = base.getStrikeDamage() / upgrade.getStrikeDamage();
			
			
			System.out.println("Calculating fine adjustment");
			
			final double fineUp =  _object.getLevelAdjustmentFine(0, 0, 0, 0, hpUp, damageUp);
			final double fineDown =  _object.getLevelAdjustmentFine(0, 0, 0, 0, hpDown, damageDown);
			
			System.out.println("Upgrade: " + fineUp + " / downgrade: " + fineDown);

			System.out.println("Calculating rough adjustment");

			
			safeAssertEquals("Upgrade of " + tableName + " creature by one level " + (i-1) + " to " + i, 1, _object.getLevelAdjustment(1, 1, 1, 1, hpUp, damageUp));
			safeAssertEquals("Downgrade of " + tableName + " creature by one level " + i + " to " + (i - 1), -1, _object.getLevelAdjustment(-1, -1, -1, -1, hpDown, damageDown));
		}
	}

	
	private void testTableUpgrade(List<CreatureTableRow> normal, List<CreatureTableRow> large, int level, String nameNormal, final String nameLarge)
	{
		// attack stats stay the same, only damage and hp scale
		for (int i = 0; i < normal.size(); ++i)
		{
			CreatureTableRow base = normal.get(i);
			CreatureTableRow upgrade = large.get(i);
			
			final double hpUp = upgrade.getHP() / base.getHP();
			final double damageUp = upgrade.getStrikeDamage() / base.getStrikeDamage();
			final double hpDown = base.getHP() / upgrade.getHP();
			final double damageDown = base.getStrikeDamage() / upgrade.getStrikeDamage();
			
//			System.out.println("Calculating fine adjustment");
			
			final double fineUp =  _object.getLevelAdjustmentFine(0, 0, 0, 0, hpUp, damageUp);
			final double fineDown =  _object.getLevelAdjustmentFine(0, 0, 0, 0, hpDown, damageDown);

//			System.out.println("Calculating rough adjustment");
			
			assertEquals("Upgrade of creature (" + nameNormal + "->" + nameLarge + ": " + i + "): " + String.format("%+.2f", fineUp), level, _object.getLevelAdjustment(0, 0, 0, 0, hpUp, damageUp));
			assertEquals("Downgrade of creature (" + nameLarge + "->" + nameNormal + ": " + i + "): " + String.format("%+.2f", fineDown), -level, _object.getLevelAdjustment(0, 0, 0, 0, hpDown, damageDown));
		}
	}

	
	private void safeAssertEquals(final String message, final int expected, final int actual)
	{
//		try
//		{
			System.out.println(message + ": Expected = " + expected + "/" + actual);
			assertEquals(message, expected, actual);
//		}
//		catch (final AssertionError e)
//		{
//			System.out.println(e.getMessage());
//		}
	}
}
