package project.thirteenthage.creatures.mechanics;

import java.util.ArrayList;
import java.util.List;

import project.thirteenthage.creatures.interfaces.ITrigger;
import project.thirteenthage.creatures.internal.Constants;
import project.thirteenthage.creatures.internal.conversions.HtmlDescriptions;
import project.thirteenthage.creatures.internal.interfaces.IAttack;
import project.thirteenthage.creatures.internal.interfaces.ICreature;

public class Attack implements IAttack
{
	private final IAttack _template;
	private final int _attackBase;
	private final double _damageBase;
	private final ICreature _creature;

	private final List<ITrigger> _triggers = new ArrayList<ITrigger>();


	public Attack(final IAttack template, final int attack, final double damage)
	{
		_template = template;
		_attackBase = attack;
		_damageBase = damage;
		_creature = null;

		_triggers.addAll(template.getTriggers());
	}


	public Attack(final ICreature creature, final IAttack template, final int attack, final double damage)
	{
		_template = template;
		_attackBase = attack;
		_damageBase = damage;
		_creature = creature;

		for (final ITrigger source : template.getTriggers())
		{
			final ITrigger trigger = new Trigger(source, creature);
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
	public String toHtmlText()
	{
		if (_creature == null)
		{
			return HtmlDescriptions.getAttackDescription(this, Constants.TEMPLATE_CREATURE_NAME, Constants.TEMPLATE_CREATURE_DAMAGE, true);
		}
		else
		{
			return HtmlDescriptions.getAttackDescription(this, _creature.getName(), _creature.getStrikeDamage(), false);
		}
	}


	@Override
	public List<ITrigger> getTriggers()
	{
		return _triggers;
	}
}
