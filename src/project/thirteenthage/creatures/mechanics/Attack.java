package project.thirteenthage.creatures.mechanics;

import java.util.ArrayList;
import java.util.List;

import project.thirteenthage.creatures.interfaces.ITrigger;
import project.thirteenthage.creatures.internal.interfaces.IAttack;
import project.thirteenthage.creatures.internal.interfaces.ICreature;

public class Attack implements IAttack
{
	private IAttack _template;
	private final int _attackBase;
	private final double _damageBase;
	
	private final List<ITrigger> _triggers = new ArrayList<ITrigger>();


	public Attack(final IAttack template, final int attack, final double damage)
	{
		_template = template;
		_attackBase = attack;
		_damageBase = damage;

		_triggers.addAll(template.getTriggers());
	}
	
	public Attack(final ICreature creature, final IAttack template, final int attack, final double damage)
	{
		_template = template;
		_attackBase = attack;
		_damageBase = damage;
		
		for (final ITrigger source : template.getTriggers())
		{
			final ITrigger trigger = new Trigger(source, creature, this);
			_triggers.add(trigger);
		}
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


	@Override
	public List<ITrigger> getTriggers()
	{
		return _triggers;
	}
}
