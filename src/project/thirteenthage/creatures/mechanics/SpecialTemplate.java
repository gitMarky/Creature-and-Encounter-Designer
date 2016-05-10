package project.thirteenthage.creatures.mechanics;

import java.io.File;

import project.thirteenthage.creatures.internal.BasicXmlFile;
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
	public String toHtmlText()
	{
		throw new IllegalStateException("Not implemented");
	}
}
