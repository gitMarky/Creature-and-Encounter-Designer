package project.thirteenthage.creatures.mechanics;

import project.thirteenthage.creatures.interfaces.ITrigger;
import project.thirteenthage.creatures.internal.Constants;
import project.thirteenthage.creatures.internal.conversions.HtmlDescriptions;
import project.thirteenthage.creatures.internal.interfaces.ICreature;

public class Trigger implements ITrigger
{
	private final String _name;
	private String _description;
	private final boolean _isTemplate;


	public Trigger(final String name, final String description)
	{
		_name = name;
		_description = description;
		_isTemplate = true;
	}


	public Trigger(final ITrigger trigger, final ICreature creature)
	{
		_name = trigger.getName();

		_isTemplate = false;
		_description = HtmlDescriptions.getTriggerDescription(trigger, creature.getName(), creature.getStrikeDamage(), false);
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
			return HtmlDescriptions.getTriggerDescription(this, Constants.TEMPLATE_CREATURE_NAME, Constants.TEMPLATE_CREATURE_DAMAGE, true);
		}
		else
		{
			return _description;
		}
	}
}
