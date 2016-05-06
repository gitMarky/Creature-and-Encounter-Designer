package project.thirteenthage.creatures.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;

import project.thirteenthage.creatures.internal.interfaces.IAttack;
import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.internal.interfaces.ICreatureTemplate;
import project.thirteenthage.creatures.internal.interfaces.ISpecial;


/**
 * Defines a creature, implementation of {@link ICreature}.
 */
public class CreatureTemplate extends BasicXmlFile implements ICreatureTemplate
{
	public CreatureTemplate(final File file)
	{
		super(file);
	}

	@Override
	public String getName()
	{
		return getRoot().getChildText("name");
	}

	@Override
	public int getLevel()
	{
		return Integer.parseInt(getRoot().getChildText("level"));
	}

	@Override
	public List<String> getLabels()
	{
		List<String> labels = new ArrayList<String>();
		for (final Element label : getRoot().getChild("labels").getChildren())
		{
			labels.add(label.getText());
		}
		return labels;
	}

	@Override
	public double getAC()
	{
		return Double.parseDouble(getRoot().getChild("modifiers").getChildText("ac"));
	}

	@Override
	public double getPD()
	{
		return Double.parseDouble(getRoot().getChild("modifiers").getChildText("pd"));
	}

	@Override
	public double getMD()
	{
		return Double.parseDouble(getRoot().getChild("modifiers").getChildText("md"));
	}

	@Override
	public double getHP()
	{
		return Double.parseDouble(getRoot().getChild("modifiers").getChildText("hp"));
	}

	@Override
	public int getInitiative()
	{
		return Integer.parseInt(getRoot().getChild("modifiers").getChildText("ini"));
	}

	@Override
	public List<IAttack> getAttacks()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ISpecial> getSpecials()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ISpecial> getNastierSpecials()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
