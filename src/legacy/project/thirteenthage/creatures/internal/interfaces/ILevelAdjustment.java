package legacy.project.thirteenthage.creatures.internal.interfaces;

public interface ILevelAdjustment
{
	double valueOfAttack(final int attack);


	double valueOfDefense(final int defense);


	double valueOfHP(final double hp);


	double valueOfDamage(final double damage);


	double getLevelAdjustmentFine(final int attack, final int ac, final int pd, final int md, final double hp, final double damage);


	int getLevelAdjustment(final int attack, final int ac, final int pd, final int md, final double hp, final double damage);
}
