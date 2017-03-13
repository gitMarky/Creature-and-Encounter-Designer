package legacy.project.thirteenthage.creatures.mechanics;

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
public final class LevelAdjustmentAlternate extends LevelAdjustmentBase
{
	LevelAdjustmentAlternate()
	{
		// hide public constructor, this is a utility class
	}


	@Override
	public double valueOfAttack(final int attack)
	{
		// a boost of +6 is worth one level
		final double value = attack / 6.0;
		return value * 0.6;
	}


	@Override
	public double valueOfDefense(final int defense)
	{
		// a boost of +6 is worth one level
		final double value = defense / 6.0;
		return value * 0.6;
	}


	@Override
	public double valueOfHP(final double hp)
	{
		final double value;
		if (hp < 1.0)
		{
			value = -polynomialForHPandDamage(1.0 / hp);
		}
		else
		{
			value = polynomialForHPandDamage(hp);
		}
		return value;

	}


	@Override
	public double valueOfDamage(final double damage)
	{
		final double value;
		if (damage < 1.0)
		{
			value = -polynomialForHPandDamage(1.0 / damage);
		}
		else
		{
			value = polynomialForHPandDamage(damage);
		}
		return value;
	}


	/**
	 * Polynomial for calculating the damage value.
	 * 
	 * @param x
	 *            the input.
	 * @return the value.
	 *         <p>
	 *         An input of 2.0 returns 1.0, resulting in a +1 level upgrade on
	 *         double HP, as specified by the core rule book. This is also
	 *         consistent with the large creature, which doubles damage and HP
	 *         for a total of +2 levels (which is double strength according to
	 *         the difficulty table).
	 *         </p>
	 *         <p>
	 *         A value of 3.0 returns 1.5. This is consistent for the huge
	 *         creature, which triples HP and damage and results in a +3
	 *         difficulty according to the difficulty table).
	 */
	final double polynomialForHPandDamage(final double x)
	{
		final double a = -0.25;
		final double b = 1.75;
		final double c = -1.5;
		return a * x * x + b * x + c;
	}
}
