package project.thirteenthage.creatures.creature;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;

import project.thirteenthage.creatures.internal.BasicXmlFile;
import project.thirteenthage.creatures.internal.interfaces.IAttack;
import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.internal.interfaces.ISpecial;
import project.thirteenthage.creatures.loaders.AttackTemplateLoader;
import project.thirteenthage.creatures.loaders.SpecialTemplateLoader;

/**
 * Defines a creature, implementation of {@link ICreature}.
 */
public class CreatureTemplate extends AbstractCreatureTemplate
{
	private final BasicXmlFile _template;
	
	public CreatureTemplate(final File file)
	{
		this(new BasicXmlFile(file));
	}


	public CreatureTemplate(BasicXmlFile file)
	{
		_template = file;
	}


	@Override
	public String getName()
	{
		return _template.getRoot().getChildText("name");
	}


	@Override
	public int getLevel()
	{
		return Integer.parseInt(_template.getRoot().getChildText("level"));
	}


	@Override
	public CreatureSize getSize()
	{
		return CreatureSize.fromString(_template.getRoot().getChildText("size"));
	}


	@Override
	public List<String> getLabels()
	{
		List<String> labels = new ArrayList<String>();
		for (final Element label : _template.getRoot().getChild("labels").getChildren())
		{
			labels.add(label.getText());
		}
		return labels;
	}


	@Override
	public int getModifierAttack()
	{
		return Integer.parseInt(_template.getRoot().getChild("modifiers").getChildText("attack"));
	}


	@Override
	public int getModifierAC()
	{
		return Integer.parseInt(_template.getRoot().getChild("modifiers").getChildText("ac"));
	}


	@Override
	public int getModifierPD()
	{
		return Integer.parseInt(_template.getRoot().getChild("modifiers").getChildText("pd"));
	}


	@Override
	public int getModifierMD()
	{
		return Integer.parseInt(_template.getRoot().getChild("modifiers").getChildText("md"));
	}


	@Override
	public double getModifierHP()
	{
		return Double.parseDouble(_template.getRoot().getChild("modifiers").getChildText("hp"));
	}


	@Override
	public int getModifierInitiative()
	{
		return Integer.parseInt(_template.getRoot().getChild("modifiers").getChildText("ini"));
	}


	@Override
	public List<IAttack> getAttacks()
	{
		List<IAttack> attacks = new ArrayList<IAttack>();

		for (final Element attack : _template.getRoot().getChild("attacks").getChildren())
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


	private List<ISpecial> readSpecials(final String node)
	{
		List<ISpecial> specials = new ArrayList<ISpecial>();

		for (final Element special : _template.getRoot().getChild(node).getChildren())
		{
			String id = special.getAttributeValue("id");

			ISpecial template = SpecialTemplateLoader.getInstance().get(id);
			if (template != null) specials.add(template);
		}
		
		return specials;
	}
}
