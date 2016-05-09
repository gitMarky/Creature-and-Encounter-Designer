package project.thirteenthage.creatures.mechanics;

import project.thirteenthage.creatures.interfaces.ITrigger;
import project.thirteenthage.creatures.internal.Html;

public class Trigger implements ITrigger
{
	private final String _name;
	private final String _description;


	public Trigger(final String name, final String description)
	{
		_name = name;
		_description = description;
	}


	@Override
	public String getName()
	{
		return _name;
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
