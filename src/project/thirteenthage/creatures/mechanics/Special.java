package project.thirteenthage.creatures.mechanics;

import project.thirteenthage.creatures.internal.conversions.HtmlDescriptions;
import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.internal.interfaces.ISpecial;

public class Special implements ISpecial
{
	private final String _name;
	private final String _description;
	private final String _creatureName;


	public Special(final ICreature creature, final ISpecial template)
	{
		if (creature == null)
		{
			throw new IllegalArgumentException("Parameter 'creature' must not be null.");
		}
		if (template == null)
		{
			throw new IllegalArgumentException("Parameter 'template' must not be null.");
		}

		_name = template.getName();
		_description = template.getDescription();
		_creatureName = creature.getName();
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
		return HtmlDescriptions.getSpecialDescription(this, _creatureName);
	}
}
