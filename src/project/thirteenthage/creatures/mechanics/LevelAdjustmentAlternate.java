package project.thirteenthage.creatures.mechanics;


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


	public double valueOfAttack(final int attack)
	{
		// a boost of +6 is worth one level
		final double value = attack / 6.0;
		return value;
	}


	public double valueOfDefense(final int defense)
	{
		// a boost of +6 is worth one level
		double value = defense / 6.0;
		return value;
	}


	public double valueOfHP(final double hp)
	{
		final double value;
		if (hp < 1.0)
		{
			value = -polynomial( 1.0 / hp);
		}
		else
		{
			value = polynomial(hp);
		}
		
//		double value = hp - 1.0; // offset first		
//		if (value < 0) value = 0.5 / value; // invert negative steps
//		value /= 0.15; // seems to go in 15% steps
//		value /= 6.0; // 6 x0.15 = 0.9, is about the same as twice the hp
//		System.out.println("** value of hp (" + String.format("%+.2f", hp) + ") " + String.format("%+.2f", value));
		return value;

	}

	
	public double valueOfDamage(final double damage)
	{
		final double value;
		if (damage < 1.0)
		{
			value = -polynomial( 1.0 / damage);
		}
		else
		{
			value = polynomial(damage);
		}
		
//		double value = damage - 1.0; // offset first
//		if (value < 0) value = 0.5 / value; // invert negative steps
//		value /= 0.15; // seems to go in 15% steps
//		value /= 6.0; // 6 x0.15 = 0.9, is about the same as twice the hp
//		System.out.println("** value of damage (" + String.format("%+.2f", damage) + ") " + String.format("%+.2f", value));
		return value;
	}
	
	final double polynomial(final double x)
	{
		final double a = -0.25;
		final double b = 1.75;
		final double c = -1.5;
		return a * x * x + b * x + c; 
	}
}
