package project.thirteenthage.creatures.mechanics;

import project.thirteenthage.creatures.internal.Html;
import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.internal.interfaces.ISpecial;

public class Special implements ISpecial
{
	private ISpecial _template;


	public Special(final ICreature creature, final ISpecial template)
	{
		_template = template;
	}


	@Override
	public String getName()
	{
		return _template.getName();
	}


	@Override
	public String getDescription()
	{
		return _template.getDescription();
	}


	@Override
	public String toHtmlText()
	{
		return Html.BEGIN_ITALIC + getName() + Html.END_ITALIC + ": " + getDescription();
	}
}
