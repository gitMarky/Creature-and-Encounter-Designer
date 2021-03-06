package legacy.project.thirteenthage.creatures.mechanics;

import java.util.ArrayList;
import java.util.List;

import legacy.project.thirteenthage.creatures.interfaces.ITrigger;
import legacy.project.thirteenthage.creatures.internal.Constants;
import legacy.project.thirteenthage.creatures.internal.conversions.HtmlDescriptions;
import legacy.project.thirteenthage.creatures.internal.interfaces.IAttack;
import legacy.project.thirteenthage.creatures.internal.interfaces.ICreature;

public class Attack implements IAttack
{
	private final IAttack _template;
	private final int _attackBase;
	private final double _damageBase;
	private final ICreature _creature;
	private final double _levelAdjustment;
	private final String _info;

	private final List<ITrigger> _triggers = new ArrayList<ITrigger>();


	public Attack(final IAttack template, final int attack, final double damage)
	{
		if (template == null)
		{
			throw new IllegalArgumentException("Parameter 'template' must not be null.");
		}

		_template = template;
		_attackBase = attack;
		_damageBase = damage;
		_creature = null;
		_info = template.getInfo();

		_triggers.addAll(template.getTriggers());
		_levelAdjustment = template.getLevelAdjustment();
	}


	public Attack(final ICreature creature, final IAttack template, final int attack, final double damage)
	{
		if (template == null)
		{
			throw new IllegalArgumentException("Parameter 'template' must not be null.");
		}
		if (creature == null)
		{
			throw new IllegalArgumentException("Parameter 'creature' must not be null.");
		}

		_template = template;
		_attackBase = attack;
		_damageBase = damage;
		_creature = creature;
		_info = template.getInfo();

		for (final ITrigger source : template.getTriggers())
		{
			final ITrigger trigger = new Trigger(source, creature);
			_triggers.add(trigger);
		}
		_levelAdjustment = template.getLevelAdjustment();
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
		if (_creature == null)
		{
			return HtmlDescriptions.getAttackDescription(this, Constants.TEMPLATE_CREATURE_NAME, Constants.TEMPLATE_CREATURE_DAMAGE, 1, true);
		}
		else
		{
			return HtmlDescriptions.getAttackDescription(this, _creature.getName(), _creature.getStrikeDamage(), _creature.getLevel(), false);
		}
	}


	@Override
	public List<ITrigger> getTriggers()
	{
		return _triggers;
	}


	@Override
	public double getLevelAdjustment()
	{
		return _levelAdjustment;
	}


	@Override
	public String getInfo()
	{
		return _info;
	}
}
