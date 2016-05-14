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
import project.thirteenthage.creatures.loaders.AttackTemplateLoader;
import project.thirteenthage.creatures.loaders.SpecialTemplateLoader;

/**
 * Defines a creature, implementation of {@link ICreature}.
 */
public class CreatureTemplate extends BasicXmlFile implements ICreatureTemplate
{
	public CreatureTemplate(final File file)
	{
		super(file);
	}


	public CreatureTemplate(BasicXmlFile file)
	{
		this(file.getFile());
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
	public int getModifierAttack()
	{
		return Integer.parseInt(getRoot().getChild("modifiers").getChildText("attack"));
	}


	@Override
	public int getModifierAC()
	{
		return Integer.parseInt(getRoot().getChild("modifiers").getChildText("ac"));
	}


	@Override
	public int getModifierPD()
	{
		return Integer.parseInt(getRoot().getChild("modifiers").getChildText("pd"));
	}


	@Override
	public int getModifierMD()
	{
		return Integer.parseInt(getRoot().getChild("modifiers").getChildText("md"));
	}


	@Override
	public double getModifierHP()
	{
		return Double.parseDouble(getRoot().getChild("modifiers").getChildText("hp"));
	}


	@Override
	public int getModifierInitiative()
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

			IAttack template = AttackTemplateLoader.getInstance().get(id);
			if (template != null) attacks.add(template);
		}

		return attacks;
	}


	@Override
	public List<ISpecial> getSpecials()
	{
		return readSpecials("specials");
	}


	@Override
	public List<ISpecial> getNastierSpecials()
	{
		return readSpecials("nastier");
	}


	@Override
	public ICreature toCreature()
	{
		CreatureBuilder builder = new CreatureBuilder();
		builder.name(getName()).size(getSize()).level(getLevel()).addInitiative(getModifierInitiative()).addAC(getModifierAC()).addPD(getModifierPD()).addMD(getModifierMD()).scaleHP(getModifierHP());

		final Creature creature;
		if (getLabels().contains("Mook"))
		{
			creature = builder.buildMook();
		} else
		{
			creature = builder.buildCreature();
		}

		creature.setAttacks(getAttacks());
		creature.getLabels().addAll(getLabels());
		creature.setSpecials(getSpecials());
		creature.setNastierSpecials(getNastierSpecials());
		creature.setTemplate(this);

		return creature;
	}
	
	
	private List<ISpecial> readSpecials(final String node)
	{
		List<ISpecial> specials = new ArrayList<ISpecial>();

		for (final Element special : getRoot().getChild(node).getChildren())
		{
			String id = special.getAttributeValue("id");

			ISpecial template = SpecialTemplateLoader.getInstance().get(id);
			if (template != null) specials.add(template);
		}
		
		return specials;
	}
}
