package project.thirteenthage.creatures.mechanics;

import project.thirteenthage.creatures.internal.conversions.Conversions;
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
public abstract class LevelAdjustmentBase implements ILevelAdjustment
{
	@Override
	public double getLevelAdjustmentFine(final int attack, final int ac, final int pd, final int md, final double hp, final double damage)
	{
		double adjustment = 0.0;

		adjustment += valueOfAttack(attack);
		adjustment += valueOfDefense(ac);
		adjustment += valueOfDefense(pd);
		adjustment += valueOfDefense(md);
		adjustment += valueOfHP(hp);
		adjustment += valueOfDamage(damage);

		return adjustment;
	}


	@Override
	public int getLevelAdjustment(final int attack, final int ac, final int pd, final int md, final double hp, final double damage)
	{
		return Conversions.round(getLevelAdjustmentFine(attack, ac, pd, md, hp, damage));
	}

}