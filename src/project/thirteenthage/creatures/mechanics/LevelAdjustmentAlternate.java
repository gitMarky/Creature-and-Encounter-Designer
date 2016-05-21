package project.thirteenthage.creatures.mechanics;

import project.thirteenthage.creatures.internal.interfaces.ILevelAdjustment;

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
public final class LevelAdjustmentAlternate implements ILevelAdjustment
{
	LevelAdjustmentAlternate()
	{
		// hide public constructor, this is a utility class
	}


	public double valueOfAttack(final int attack)
	{
		return 0.0;
	}


	public double valueOfDefense(final int defense)
	{
		return 0.0;
	}


	public double valueOfHP(final double hp)
	{
		return 0.0;
	}

}
