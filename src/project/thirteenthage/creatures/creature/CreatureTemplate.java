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
		
		parse();
	}
	
	private void parse()
	{
		setName(parseName());
		setLevel(parseLevel());
		setSize(parseSize());
		getLabels().addAll(parseLabels());
		setAttack(parseModifierAttack());
		setAC(parseModifierAC());
		setPD(parseModifierPD());
		setMD(parseModifierMD());
		setHP(parseModifierHP());
		setInitiative(parseModifierInitiative());
		getAttacks().addAll(parseAttacks());
		getSpecials().addAll(parseSpecials());
		getNastierSpecials().addAll(parseNastierSpecials());
	}


	public String parseName()
	{
		return _template.getRoot().getChildText("name");
	}


	public int parseLevel()
	{
		return Integer.parseInt(_template.getRoot().getChildText("level"));
	}


	public CreatureSize parseSize()
	{
		return CreatureSize.fromString(_template.getRoot().getChildText("size"));
	}


	public List<String> parseLabels()
	{
		List<String> labels = new ArrayList<String>();
		for (final Element label : _template.getRoot().getChild("labels").getChildren())
		{
			labels.add(label.getText());
		}
		return labels;
	}


	public BetterDefense parseBetterDefense()
	{
		if ("true".equals(_template.getRoot().getChild("modifiers").getChild("md").getAttributeValue("better")))
		{
			return BetterDefense.MD;
		}

		return BetterDefense.PD;
	}


	public int parseModifierAttack()
	{
		return Integer.parseInt(_template.getRoot().getChild("modifiers").getChildText("attack"));
	}


	public int parseModifierAC()
	{
		return Integer.parseInt(_template.getRoot().getChild("modifiers").getChildText("ac"));
	}


	public int parseModifierPD()
	{
		return Integer.parseInt(_template.getRoot().getChild("modifiers").getChildText("pd"));
	}


	public int parseModifierMD()
	{
		return Integer.parseInt(_template.getRoot().getChild("modifiers").getChildText("md"));
	}


	public double parseModifierHP()
	{
		return Double.parseDouble(_template.getRoot().getChild("modifiers").getChildText("hp"));
	}


	public int parseModifierInitiative()
	{
		return Integer.parseInt(_template.getRoot().getChild("modifiers").getChildText("ini"));
	}


	public List<IAttack> parseAttacks()
	{
		List<IAttack> attacks = new ArrayList<IAttack>();

		for (final Element attack : _template.getRoot().getChild("attacks").getChildren())
		{
			String id = attack.getAttributeValue("id");

			IAttack template = AttackTemplateLoader.getInstance().get(id);
			if (template != null)
				attacks.add(template);
		}

		return attacks;
	}


	public List<ISpecial> parseSpecials()
	{
		return readSpecials("specials");
	}


	public List<ISpecial> parseNastierSpecials()
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
			if (template != null)
				specials.add(template);
		}

		return specials;
	}
}
