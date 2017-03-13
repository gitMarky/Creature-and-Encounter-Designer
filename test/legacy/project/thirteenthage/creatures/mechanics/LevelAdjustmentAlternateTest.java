package legacy.project.thirteenthage.creatures.mechanics;

import static org.junit.Assert.assertEquals;
import static project.thirteenthage.creatures.TestConstants.DOUBLE_EXACT_1E_8;
import static project.thirteenthage.creatures.TestConstants.DOUBLE_FUZZY_1E_3;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import legacy.project.thirteenthage.creatures.internal.interfaces.IAttack;
import legacy.project.thirteenthage.creatures.internal.interfaces.ISpecial;
import legacy.project.thirteenthage.creatures.tables.CreatureTableRow;
import legacy.project.thirteenthage.creatures.tables.CreatureTables;

public class LevelAdjustmentAlternateTest
{
	private final LevelAdjustmentAlternate _object = new LevelAdjustmentAlternate();
	private final List<IAttack> _emptyAttacks = new ArrayList<IAttack>();
	private final List<ISpecial> _emptySpecials = new ArrayList<ISpecial>();


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
		// go through all creature tables. The update from one row to the other
		// must be exactly one level adjustment
		// since the table represents individual levels
		for (int i = 1; i < table.size(); ++i)
		{
			final CreatureTableRow base = table.get(i - 1);
			final CreatureTableRow upgrade = table.get(i);

			final double hpUp = upgrade.getHP() / base.getHP();
			final double damageUp = upgrade.getStrikeDamage() / base.getStrikeDamage();
			final double hpDown = base.getHP() / upgrade.getHP();
			final double damageDown = base.getStrikeDamage() / upgrade.getStrikeDamage();

			assertEquals("Upgrade of " + tableName + " creature by one level " + (i - 1) + " to " + i, 1, _object.getLevelAdjustment(1, 1, 1, 1, hpUp, damageUp, _emptyAttacks, _emptySpecials, _emptySpecials));
			assertEquals("Downgrade of " + tableName + " creature by one level " + i + " to " + (i - 1), -1, _object.getLevelAdjustment(-1, -1, -1, -1, hpDown, damageDown, _emptyAttacks, _emptySpecials, _emptySpecials));
		}
	}


	private void testTableUpgrade(final List<CreatureTableRow> normal, final List<CreatureTableRow> large, final int level, final String nameNormal, final String nameLarge)
	{
		// attack stats stay the same, only damage and hp scale
		for (int i = 0; i < normal.size(); ++i)
		{
			final CreatureTableRow base = normal.get(i);
			final CreatureTableRow upgrade = large.get(i);

			final double hpUp = upgrade.getHP() / base.getHP();
			final double damageUp = upgrade.getStrikeDamage() / base.getStrikeDamage();
			final double hpDown = base.getHP() / upgrade.getHP();
			final double damageDown = base.getStrikeDamage() / upgrade.getStrikeDamage();

			// System.out.println("Calculating fine adjustment");

			final double fineUp = _object.getLevelAdjustmentFine(0, 0, 0, 0, hpUp, damageUp, _emptyAttacks, _emptySpecials, _emptySpecials);
			final double fineDown = _object.getLevelAdjustmentFine(0, 0, 0, 0, hpDown, damageDown, _emptyAttacks, _emptySpecials, _emptySpecials);

			// System.out.println("Calculating rough adjustment");

			assertEquals("Upgrade of creature (" + nameNormal + "->" + nameLarge + ": " + i + "): " + String.format("%+.2f", fineUp), level, _object.getLevelAdjustment(0, 0, 0, 0, hpUp, damageUp, _emptyAttacks, _emptySpecials, _emptySpecials));
			assertEquals("Downgrade of creature (" + nameLarge + "->" + nameNormal + ": " + i + "): " + String.format("%+.2f", fineDown), -level, _object.getLevelAdjustment(0, 0, 0, 0, hpDown, damageDown, _emptyAttacks, _emptySpecials, _emptySpecials));
		}
	}


	private void safeAssertEquals(final String message, final int expected, final int actual)
	{
		try
		{
			System.out.println(message + ": Expected = " + expected + "/" + actual);
			assertEquals(message, expected, actual);
		}
		catch (final AssertionError e)
		{
			System.out.println(e.getMessage());
		}
	}


	@Test
	public void testNoChange()
	{
		final double actual = _object.getLevelAdjustmentFine(0, 0, 0, 0, 1.0, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials);
		assertEquals(0.0, actual, DOUBLE_EXACT_1E_8);
	}


	/**
	 * Tests the "increase attack only" example from the core rules.
	 */
	@Test
	public void testOnlyAttack()
	{
		assertEquals(1, _object.getLevelAdjustment(6, 0, 0, 0, 1.0, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials));

		final double actual = _object.getLevelAdjustmentFine(6, 0, 0, 0, 1.0, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials);
		assertEquals(0.6, actual, DOUBLE_EXACT_1E_8);
	}


	/**
	 * Tests the "increase AC only" example from the core rules.
	 */
	@Test
	public void testOnlyAC()
	{
		assertEquals(1, _object.getLevelAdjustment(0, 6, 0, 0, 1.0, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials));

		final double actual = _object.getLevelAdjustmentFine(0, 6, 0, 0, 1.0, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials);
		assertEquals(0.6, actual, DOUBLE_EXACT_1E_8);
	}


	/**
	 * Tests the "increase HP only" example from the core rules.
	 */
	@Test
	public void testOnlyHP()
	{
		final int rough = _object.getLevelAdjustment(0, 0, 0, 0, 2.0, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials);
		assertEquals(1, rough);

		final double actual = _object.getLevelAdjustmentFine(0, 0, 0, 0, 2.0, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials);
		assertEquals(1.0, actual, DOUBLE_EXACT_1E_8);
	}


	/**
	 * Tests the "scrapper" example from the core rules/13 true ways.
	 */
	@Test
	public void testScrapper()
	{
		assertEquals(0, _object.getLevelAdjustment(3, 0, 0, 0, 0.7, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials));
		assertEquals(0, _object.getLevelAdjustment(2, 0, 0, 0, 0.8, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials));
		assertEquals(0, _object.getLevelAdjustment(1, 0, 0, 0, 0.9, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials));

		double actual = _object.getLevelAdjustmentFine(3, 0, 0, 0, 0.7, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials);
		assertEquals(-0.1897, actual, DOUBLE_FUZZY_1E_3);

		actual = _object.getLevelAdjustmentFine(2, 0, 0, 0, 0.8, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials);
		assertEquals(-0.0968, actual, DOUBLE_FUZZY_1E_3);

		actual = _object.getLevelAdjustmentFine(1, 0, 0, 0, 0.9, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials);
		assertEquals(-0.0358, actual, DOUBLE_FUZZY_1E_3);
	}


	/**
	 * Tests the "offensive" example from the core rules/13 true ways.
	 */
	@Test
	public void testOffensive()
	{
		assertEquals(0, _object.getLevelAdjustment(3, -1, -1, -1, 1.0, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials));
		assertEquals(0, _object.getLevelAdjustment(2, -1, -1, 0, 1.0, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials));
		assertEquals(0, _object.getLevelAdjustment(1, -1, 0, 0, 1.0, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials));

		double actual = _object.getLevelAdjustmentFine(3, -3, -3, -3, 1.0, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials);
		assertEquals(-0.6, actual, DOUBLE_FUZZY_1E_3);

		actual = _object.getLevelAdjustmentFine(2, -2, -2, -2, 1.0, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials);
		assertEquals(-0.3999, actual, DOUBLE_FUZZY_1E_3);

		actual = _object.getLevelAdjustmentFine(1, -1, -1, -1, 1.0, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials);
		assertEquals(-0.1999, actual, DOUBLE_FUZZY_1E_3);
	}


	/**
	 * Tests the "oaf" example from the core rules/13 true ways.
	 */
	@Test
	public void testOaf()
	{
		assertEquals(0, _object.getLevelAdjustment(-3, +3, 0, 0, 1.0, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials));
		assertEquals(0, _object.getLevelAdjustment(-2, +2, 0, 0, 1.0, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials));
		assertEquals(0, _object.getLevelAdjustment(-1, +1, 0, 0, 1.0, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials));

		double actual = _object.getLevelAdjustmentFine(-3, +3, 0, 0, 1.0, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials);
		assertEquals(0.0, actual, DOUBLE_FUZZY_1E_3);

		actual = _object.getLevelAdjustmentFine(-2, +2, 0, 0, 1.0, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials);
		assertEquals(0.0, actual, DOUBLE_FUZZY_1E_3);

		actual = _object.getLevelAdjustmentFine(-1, +1, 0, 0, 1.0, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials);
		assertEquals(0.0, actual, DOUBLE_FUZZY_1E_3);
	}


	/**
	 * Tests the "defensive" example from the core rules/13 true ways.
	 */
	@Test
	public void testDefensive()
	{
		assertEquals(0, _object.getLevelAdjustment(0, +3, 0, 0, 0.7, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials));
		assertEquals(0, _object.getLevelAdjustment(0, +1, 0, 0, 0.9, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials));
		assertEquals(0, _object.getLevelAdjustment(0, +2, 0, 0, 0.8, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials));

		double actual = _object.getLevelAdjustmentFine(0, +3, 0, 0, 0.7, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials);
		assertEquals(-0.1897, actual, DOUBLE_FUZZY_1E_3);

		actual = _object.getLevelAdjustmentFine(0, +2, 0, 0, 0.8, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials);
		assertEquals(-0.0968, actual, DOUBLE_FUZZY_1E_3);

		actual = _object.getLevelAdjustmentFine(0, +1, 0, 0, 0.9, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials);
		assertEquals(-0.0358, actual, DOUBLE_FUZZY_1E_3);
	}


	/**
	 * Tests the "lunk" example from the core rules/13 true ways.
	 */
	@Test
	public void testLunk()
	{
		int rough = _object.getLevelAdjustment(0, -3, -3, -3, 1.4, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials);
		assertEquals(0, rough);

		rough = _object.getLevelAdjustment(0, -2, -2, -2, 1.3, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials);
		assertEquals(0, rough);

		rough = _object.getLevelAdjustment(0, -1, -1, -1, 1.15, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials);
		assertEquals(0, rough);

		// fine

		double actual = _object.getLevelAdjustmentFine(0, -3, -3, -3, 1.4, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials);
		assertEquals(-0.4400, actual, DOUBLE_FUZZY_1E_3);

		actual = _object.getLevelAdjustmentFine(0, -2, -2, -2, 1.3, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials);
		assertEquals(-0.2475, actual, DOUBLE_FUZZY_1E_3);

		actual = _object.getLevelAdjustmentFine(0, -1, -1, -1, 1.15, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials);
		assertEquals(-0.1181, actual, DOUBLE_FUZZY_1E_3);
	}


	/**
	 * Tests the "brittle" example from the core rules/13 true ways.
	 */
	@Test
	public void testBrittle()
	{
		testDefensive(); // this is actually the same thing...
	}


	/**
	 * The normal level up should be reflected in this, too.
	 */
	@Test
	public void testNormalLevelUp()
	{
		assertEquals(0.6968, _object.getLevelAdjustmentFine(1, 1, 1, 1, 1.25, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials), DOUBLE_FUZZY_1E_3);
		assertEquals(1, _object.getLevelAdjustment(1, 1, 1, 1, 1.25, 1.0, _emptyAttacks, _emptySpecials, _emptySpecials));
	}


	@Test
	public void compareLargeCreatureAndLevelUp()
	{
		final int level = 7;
		final int levelUp = 10;
		final CreatureTableRow base = CreatureTables.normal().get(level);
		final CreatureTableRow large = CreatureTables.large().get(level);
		final CreatureTableRow leveled = CreatureTables.normal().get(levelUp);

		final int adjustmentLarge = _object.getLevelAdjustment(0, 0, 0, 0, large.getHP() / base.getHP(), large.getStrikeDamage() / base.getStrikeDamage(), _emptyAttacks, _emptySpecials, _emptySpecials);
		final int adjustmentLeveled = _object.getLevelAdjustment(levelUp - level, levelUp - level, levelUp - level, levelUp - level, leveled.getHP() / base.getHP(), leveled.getStrikeDamage() / base.getStrikeDamage(), _emptyAttacks, _emptySpecials, _emptySpecials);

		final double adjustmentRelative = _object.getLevelAdjustment(levelUp - level, levelUp - level, levelUp - level, levelUp - level, leveled.getHP() / large.getHP(), leveled.getStrikeDamage() / large.getStrikeDamage(), _emptyAttacks, _emptySpecials, _emptySpecials);

		assertEquals(2, adjustmentLarge);
		assertEquals(3, adjustmentLeveled);

		// the upgrade from "large" to "leveled up normal monster" should be the
		// difference between the two.
		assertEquals(1.0, adjustmentRelative, DOUBLE_EXACT_1E_8);
	}


	@Test
	public void compareHugeCreatureAndLevelUp()
	{
		final int level = 7;
		final int levelUp = 10;
		final CreatureTableRow base = CreatureTables.normal().get(level);
		final CreatureTableRow huge = CreatureTables.huge().get(level);
		final CreatureTableRow leveled = CreatureTables.normal().get(levelUp);

		final int adjustmentLarge = _object.getLevelAdjustment(0, 0, 0, 0, huge.getHP() / base.getHP(), huge.getStrikeDamage() / base.getStrikeDamage(), _emptyAttacks, _emptySpecials, _emptySpecials);
		final int adjustmentLeveled = _object.getLevelAdjustment(levelUp - level, levelUp - level, levelUp - level, levelUp - level, leveled.getHP() / base.getHP(), leveled.getStrikeDamage() / base.getStrikeDamage(), _emptyAttacks, _emptySpecials, _emptySpecials);

		final double adjustmentRelative = _object.getLevelAdjustment(levelUp - level, levelUp - level, levelUp - level, levelUp - level, leveled.getHP() / huge.getHP(), leveled.getStrikeDamage() / huge.getStrikeDamage(), _emptyAttacks, _emptySpecials, _emptySpecials);

		assertEquals(3, adjustmentLarge);
		assertEquals(3, adjustmentLeveled);

		// the upgrade from "huge" to "leveled up normal monster" should be the
		// difference between the two.
		assertEquals(0.0, adjustmentRelative, DOUBLE_EXACT_1E_8);
	}
}
