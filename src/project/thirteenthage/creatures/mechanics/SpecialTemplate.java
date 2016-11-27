package project.thirteenthage.creatures.mechanics;

import java.io.File;

import project.marky.library.xml.BasicXmlFile;
import project.thirteenthage.creatures.internal.Constants;
import project.thirteenthage.creatures.internal.conversions.HtmlDescriptions;
import project.thirteenthage.creatures.internal.interfaces.ISpecial;

public class SpecialTemplate implements ISpecial
{
	private String _name = "Special";
	private String _description = "Description";


	public SpecialTemplate(final File file)
	{
		this(new BasicXmlFile(file));
	}


	public SpecialTemplate(final BasicXmlFile template)
	{
		if (template == null)
		{
			throw new IllegalArgumentException("Parameter 'template' must not be null.");
		}

		_name = template.getRoot().getChildText("name");
		_description = template.getRoot().getChildText("description");
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
		return HtmlDescriptions.getSpecialDescription(this, Constants.TEMPLATE_CREATURE_OF_TYPE);
	}


	@Override
	public String toString()
	{
		return getName();
	}
}
