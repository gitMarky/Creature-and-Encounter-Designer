package project.thirteenthage.creatures.mechanics;

import static org.junit.Assert.*;

import org.junit.Test;

import static project.thirteenthage.creatures.TestConstants.*;

public class LevelAdjustmentTest
{	
	@Test
	public void testNoChange()
	{
		double actual = LevelAdjustment.getLevelAdjustmentFine(0, 0, 0, 0, 1.0);
		assertEquals(0.0, actual, DOUBLE_EXACT_1E_8);
	}


	/**
	 * Tests the "increase attack only" example from the core rules.
	 */
	@Test
	public void testOnlyAttack()
	{
		double actual = LevelAdjustment.getLevelAdjustmentFine(6, 0, 0, 0, 1.0);
		assertEquals(1.0, actual, DOUBLE_EXACT_1E_8);
	}


	/**
	 * Tests the "increase AC only" example from the core rules.
	 */
	@Test
	public void testOnlyAC()
	{
		double actual = LevelAdjustment.getLevelAdjustmentFine(0, 6, 0, 0, 1.0);
		assertEquals(1.0, actual, DOUBLE_EXACT_1E_8);
	}


	/**
	 * Tests the "increase HP only" example from the core rules.
	 */
	@Test
	public void testOnlyHP()
	{
		int rough = LevelAdjustment.getLevelAdjustment(0, 0, 0, 0, 2.0);
		assertEquals(1, rough);

		double actual = LevelAdjustment.getLevelAdjustmentFine(0, 0, 0, 0, 2.0);
		assertEquals(1.11111111, actual, DOUBLE_EXACT_1E_8);
	}

	
	/**
	 * Tests the "scrapper" example from the core rules/13 true ways.
	 */
	@Test
	public void testScrapper()
	{
		double actual = LevelAdjustment.getLevelAdjustmentFine(3, 0, 0, 0, 0.7);
		assertEquals(0.1666, actual, DOUBLE_FUZZY_1E_3);

		actual = LevelAdjustment.getLevelAdjustmentFine(2, 0, 0, 0, 0.8);
		assertEquals(0.1111, actual, DOUBLE_FUZZY_1E_3);

		actual = LevelAdjustment.getLevelAdjustmentFine(1, 0, 0, 0, 0.9);
		assertEquals(0.0555, actual, DOUBLE_FUZZY_1E_3);
	}


	/**
	 * Tests the "offensive" example from the core rules/13 true ways.
	 */
	@Test
	public void testOffensive()
	{
		double actual = LevelAdjustment.getLevelAdjustmentFine(3, -3, -3, -3, 1.0);
		assertEquals(0.0, actual, DOUBLE_FUZZY_1E_3);

		actual = LevelAdjustment.getLevelAdjustmentFine(2, -2, -2, -2, 1.0);
		assertEquals(0.0, actual, DOUBLE_FUZZY_1E_3);

		actual = LevelAdjustment.getLevelAdjustmentFine(1, -1, -1, -1, 1.0);
		assertEquals(0.0, actual, DOUBLE_FUZZY_1E_3);
	}


	/**
	 * Tests the "oaf" example from the core rules/13 true ways.
	 */
	@Test
	public void testOaf()
	{
		double actual = LevelAdjustment.getLevelAdjustmentFine(-3, +3, 0, 0, 1.0);
		assertEquals(0.0, actual, DOUBLE_FUZZY_1E_3);

		actual = LevelAdjustment.getLevelAdjustmentFine(-2, +2, 0, 0, 1.0);
		assertEquals(0.0, actual, DOUBLE_FUZZY_1E_3);

		actual = LevelAdjustment.getLevelAdjustmentFine(-1, +1, 0, 0, 1.0);
		assertEquals(0.0, actual, DOUBLE_FUZZY_1E_3);
	}


	/**
	 * Tests the "defensive" example from the core rules/13 true ways.
	 */
	@Test
	public void testDefensive()
	{
		double actual = LevelAdjustment.getLevelAdjustmentFine(0, +3, 0, 0, 0.7);
		assertEquals(0.1666, actual, DOUBLE_FUZZY_1E_3);

		actual = LevelAdjustment.getLevelAdjustmentFine(0, +2, 0, 0, 0.8);
		assertEquals(0.1111, actual, DOUBLE_FUZZY_1E_3);

		actual = LevelAdjustment.getLevelAdjustmentFine(0, +1, 0, 0, 0.9);
		assertEquals(0.0555, actual, DOUBLE_FUZZY_1E_3);
	}


	/**
	 * Tests the "lunk" example from the core rules/13 true ways.
	 */
	@Test
	public void testLunk()
	{
		int rough = LevelAdjustment.getLevelAdjustment(0, -3, -3, -3, 1.4);
		assertEquals(0, rough);

		rough = LevelAdjustment.getLevelAdjustment(0, -2, -2, -2, 1.3);
		assertEquals(0, rough);

		rough = LevelAdjustment.getLevelAdjustment(0, -1, -1, -1, 1.15);
		assertEquals(0, rough);

		// fine
		
		double actual = LevelAdjustment.getLevelAdjustmentFine(0, -3, -3, -3, 1.4);
		assertEquals(-0.0555, actual, DOUBLE_FUZZY_1E_3);

		actual = LevelAdjustment.getLevelAdjustmentFine(0, -2, -2, -2, 1.3);
		assertEquals(0.0, actual, DOUBLE_FUZZY_1E_3);

		actual = LevelAdjustment.getLevelAdjustmentFine(0, -1, -1, -1, 1.15);
		assertEquals(0.0, actual, DOUBLE_FUZZY_1E_3);
	}


	/**
	 * Tests the "brittle" example from the core rules/13 true ways.
	 */
	@Test
	public void testBrittle()
	{
		testDefensive(); // this is actually the same thing...
	}
}
