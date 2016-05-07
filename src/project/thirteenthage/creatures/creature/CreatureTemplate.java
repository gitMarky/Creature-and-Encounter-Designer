package project.thirteenthage.creatures.creature;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;

import project.thirteenthage.creatures.internal.BasicXmlFile;
import project.thirteenthage.creatures.internal.interfaces.IAttack;
import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.internal.interfaces.ICreatureTemplate;
import project.thirteenthage.creatures.internal.interfaces.ISpecial;
import project.thirteenthage.creatures.mechanics.AttackTemplate;


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
	public CreatureSize getSize()
	{
		return CreatureSize.fromString(getRoot().getChildText("size"));
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
	public int getAC()
	{
		return Integer.parseInt(getRoot().getChild("modifiers").getChildText("ac"));
	}

	@Override
	public int getPD()
	{
		return Integer.parseInt(getRoot().getChild("modifiers").getChildText("pd"));
	}

	@Override
	public int getMD()
	{
		return Integer.parseInt(getRoot().getChild("modifiers").getChildText("md"));
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
		List<IAttack> attacks = new ArrayList<IAttack>();
		
		for (final Element attack : getRoot().getChild("attacks").getChildren())
		{
			String id = attack.getAttributeValue("id");
			
			AttackTemplate template = new AttackTemplate(new File("resources/attacks/" + id + ".xml"));
			attacks.add(template);
		}
		
		return attacks;
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

	@Override
	public ICreature toCreature()
	{
		CreatureBuilder builder = new CreatureBuilder();
		builder.name(getName())
		       .size(getSize())
		       .level(getLevel())
		       .addInitiative(getInitiative())
		       .addAC(getAC())
		       .addPD(getPD())
		       .addMD(getMD())
		       .scaleHP(getHP());
		
		final Creature creature;
		if (getLabels().contains("Mook"))
		{
			creature = builder.buildMook();
		}
		else
		{
			creature = builder.buildCreature();
		}
		
		creature.setAttacks(getAttacks());
		
		return creature;
	}
}
