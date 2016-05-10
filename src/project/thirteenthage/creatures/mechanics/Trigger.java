package project.thirteenthage.creatures.mechanics;

import project.thirteenthage.creatures.interfaces.ITrigger;
import project.thirteenthage.creatures.internal.Html;
import project.thirteenthage.creatures.internal.TextFormatter;
import project.thirteenthage.creatures.internal.interfaces.ICreature;

public class Trigger implements ITrigger
{
	private final String _name;
	private String _description;


	public Trigger(final String name, final String description)
	{
		_name = name;
		_description = description;
	}


	public Trigger(final ITrigger trigger, final ICreature creature)
	{
		_name = trigger.getName();

		_description = TextFormatter.parse(trigger.getDescription(), TextFormatter.PLACEHOLDER_NAME, creature.getName().toLowerCase());
		_description = TextFormatter.parse(_description, TextFormatter.PLACEHOLDER_DAMAGE, creature.getStrikeDamage());
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
		return Html.BEGIN_ITALIC + _name + Html.END_ITALIC + ": " + _description;
	}
}
