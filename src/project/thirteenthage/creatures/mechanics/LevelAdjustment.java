package project.thirteenthage.creatures.mechanics;

import project.thirteenthage.creatures.internal.conversions.Conversions;

/**
 * <p>
 * Adds a modifier to the level of a creature, based on how much it deviates
 * from the default stats of such a creature.
 * </p>
 * <p>
 * This modifier is added to the level of the creature, for the purpose of
 * calculating the difficulty.
 * </p>
 */
public final class LevelAdjustment
{
	private LevelAdjustment()
	{
		// hide public constructor, this is a utility class
	}


	public static double getLevelAdjustmentFine(final int attack, final int ac, final int pd, final int md, final double hp)
	{
		double adjustment = 0.0;

		adjustment += valueOfAttack(attack);
		adjustment += valueOfDefense(ac);
		adjustment += valueOfDefense(pd);
		adjustment += valueOfDefense(md);
		adjustment += valueOfHP(hp);

		return adjustment;
	}


	private static double valueOfAttack(final int attack)
	{
		// a boost of +6 is worth one level
		final double value = attack / 6.0;
		return value;
	}


	private static double valueOfDefense(final int defense)
	{
		// a boost of +6 is worth one level
		double value = defense / 6.0;
		// -1 to all defenses is worth the same as a +1 attack (see offensive
		// example)
		if (defense < 0) value /= 3.0;
		return value;
	}


	private static double valueOfHP(final double hp)
	{
		double value = hp - 1.0; // offset first
		value /= 0.15; // seems to go in 15% steps
		value /= 6.0; // 6 x0.15 = 0.9, is about the same as twice the hp
		return value;
	}


	public static int getLevelAdjustment(final int attack, final int ac, final int pd, final int md, final double hp)
	{
		return Conversions.round(getLevelAdjustmentFine(attack, ac, pd, md, hp));
	}
}
