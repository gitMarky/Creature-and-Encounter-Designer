package project.thirteenthage.creatures.mechanics;

import project.thirteenthage.creatures.internal.Html;
import project.thirteenthage.creatures.internal.TextFormatter;
import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.internal.interfaces.ISpecial;

public class Special implements ISpecial
{
	private final String _name;
	private final String _description;

	public Special(final ICreature creature, final ISpecial template)
	{
		_name = template.getName();
		_description = TextFormatter.parse(template.getDescription(), TextFormatter.PLACEHOLDER_NAME, creature.getName());
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
	public String toHtmlText()
	{
		return Html.BEGIN_ITALIC + getName() + Html.END_ITALIC + ": " + getDescription();
	}
}
