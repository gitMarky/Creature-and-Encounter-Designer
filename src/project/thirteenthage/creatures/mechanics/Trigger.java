package project.thirteenthage.creatures.mechanics;

import project.thirteenthage.creatures.interfaces.ITrigger;
import project.thirteenthage.creatures.internal.Constants;
import project.thirteenthage.creatures.internal.conversions.HtmlDescriptions;
import project.thirteenthage.creatures.internal.interfaces.ICreature;

public class Trigger implements ITrigger
{
	private final String _name;
	private final String _description;
	private final boolean _isTemplate;


	public Trigger(final String name, final String description)
	{
		if (name == null)
		{
			throw new IllegalArgumentException("Parameter 'name' must not be null.");
		}
		if (description == null)
		{
			throw new IllegalArgumentException("Parameter 'description' must not be null.");
		}

		_name = name;
		_description = description;
		_isTemplate = true;
	}


	public Trigger(final ITrigger trigger, final ICreature creature)
	{
		if (trigger == null)
		{
			throw new IllegalArgumentException("Parameter 'trigger' must not be null.");
		}
		if (creature == null)
		{
			throw new IllegalArgumentException("Parameter 'creature' must not be null.");
		}

		_name = trigger.getName();

		_isTemplate = false;
		_description = HtmlDescriptions.getTriggerDescription(trigger, creature.getName(), creature.getStrikeDamage(), creature.getLevel(), false);
	}


	@Override
	public String getName()
	{
		return _name;
	}


	@Override
	public String getDescription()
	{
		return _description;
	}


	@Override
	public String toGuiText()
	{
		return _name + ": " + _description;
	}


	@Override
	public String toHtmlText()
	{
		if (_isTemplate)
		{
			return HtmlDescriptions.getTriggerDescription(this, Constants.TEMPLATE_CREATURE_NAME, Constants.TEMPLATE_CREATURE_DAMAGE, 1, true);
		}
		else
		{
			return _description;
		}
	}
}
