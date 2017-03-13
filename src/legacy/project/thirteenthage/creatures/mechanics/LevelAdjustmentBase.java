package legacy.project.thirteenthage.creatures.mechanics;

import java.util.List;

import legacy.project.thirteenthage.creatures.internal.conversions.Conversions;
import legacy.project.thirteenthage.creatures.internal.interfaces.IAttack;
import legacy.project.thirteenthage.creatures.internal.interfaces.ILevelAdjustment;
import legacy.project.thirteenthage.creatures.internal.interfaces.ISpecial;

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
	public double getLevelAdjustmentFine(final int attack, final int ac, final int pd, final int md, final double hp, final double damage, final List<IAttack> attacks, final List<ISpecial> specials, final List<ISpecial> nastier)
	{
		double adjustment = 0.0;

		adjustment += valueOfAttack(attack);
		adjustment += valueOfDefense(ac);
		adjustment += valueOfDefense(pd);
		adjustment += valueOfDefense(md);
		adjustment += valueOfHP(hp);
		adjustment += valueOfDamage(damage);

		for (final IAttack adjust : attacks)
		{
			adjustment += adjust.getLevelAdjustment();
		}
		for (final ISpecial adjust : specials)
		{
			adjustment += adjust.getLevelAdjustment();
		}
		for (final ISpecial adjust : nastier)
		{
			adjustment += adjust.getLevelAdjustment();
		}

		return adjustment;
	}


	@Override
	public int getLevelAdjustment(final int attack, final int ac, final int pd, final int md, final double hp, final double damage, final List<IAttack> attacks, final List<ISpecial> specials, final List<ISpecial> nastier)
	{
		return Conversions.round(getLevelAdjustmentFine(attack, ac, pd, md, hp, damage, attacks, specials, nastier));
	}

}