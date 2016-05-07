package project.thirteenthage.creatures.mechanics;

import project.thirteenthage.creatures.internal.interfaces.IAttack;

public class Attack implements IAttack
{
	private IAttack _template;
	private final int _attackBase;
	private final double _damageBase;


	public Attack(final IAttack template, final int attack, final double damage)
	{
		_template = template;
		_attackBase = attack;
		_damageBase = damage;
	}


	@Override
	public String getName()
	{
		return _template.getName();
	}


	@Override
	public int getAttackBonus()
	{
		return _attackBase + _template.getAttackBonus();
	}


	@Override
	public double getDamageFactor()
	{
		return _damageBase * _template.getDamageFactor();
	}


	@Override
	public String getDefense()
	{
		return _template.getDefense();
	}


	@Override
	public String getDescription()
	{
		return _template.getDescription();
	}


	@Override
	public String toGuiText()
	{
		return getName() + " +" + getAttackBonus() + " vs. " + getDefense() + " - " + (int) getDamageFactor() + " " + getDescription();
	}
}
