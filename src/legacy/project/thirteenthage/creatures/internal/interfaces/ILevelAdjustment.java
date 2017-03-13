package legacy.project.thirteenthage.creatures.internal.interfaces;

import java.util.List;

public interface ILevelAdjustment
{
	double valueOfAttack(final int attack);


	double valueOfDefense(final int defense);


	double valueOfHP(final double hp);


	double valueOfDamage(final double damage);


	double getLevelAdjustmentFine(final int attack, final int ac, final int pd, final int md, final double hp, final double damage, final List<IAttack> attacks, final List<ISpecial> specials, final List<ISpecial> nastier);


	int getLevelAdjustment(final int attack, final int ac, final int pd, final int md, final double hp, final double damage, final List<IAttack> attacks, final List<ISpecial> specials, final List<ISpecial> nastier);
}
