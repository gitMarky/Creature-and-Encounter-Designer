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
	public static final String ROOT_ELEMENT = "creature";
	static final String ATTRIBUTE_VALUE_TRUE = "true";
	static final String ATTRIBUTE_BETTER = "better";
	static final String ATTRIBUTE_ID = "id";
	static final String ELEMENT_NASTIER = "nastier";
	static final String ELEMENT_SPECIAL = "special";
	static final String ELEMENT_SPECIALS = "specials";
	static final String ELEMENT_ATTACK = "attack";
	static final String ELEMENT_ATTACKS = "attacks";
	static final String ELEMENT_MODIFIERS_INI = "ini";
	static final String ELEMENT_MODIFIERS_HP = "hp";
	static final String ELEMENT_MODIFIERS_MD = "md";
	static final String ELEMENT_MODIFIERS_PD = "pd";
	static final String ELEMENT_MODIFIERS_AC = "ac";
	static final String ELEMENT_MODIFIERS_ATTACK = "attack";
	static final String ELEMENT_MODIFIERS = "modifiers";
	static final String ELEMENT_LABEL = "label";
	static final String ELEMENT_LABELS = "labels";
	static final String ELEMENT_SIZE = "size";
	static final String ELEMENT_LEVEL = "level";
	static final String ELEMENT_NAME = "name";
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
		return _template.getRoot().getChildText(ELEMENT_NAME);
	}


	public int parseLevel()
	{
		return Integer.parseInt(_template.getRoot().getChildText(ELEMENT_LEVEL));
	}


	public CreatureSize parseSize()
	{
		return CreatureSize.fromString(_template.getRoot().getChildText(ELEMENT_SIZE));
	}


	public List<String> parseLabels()
	{
		List<String> labels = new ArrayList<String>();
		for (final Element label : _template.getRoot().getChild(ELEMENT_LABELS).getChildren())
		{
			labels.add(label.getText());
		}
		return labels;
	}


	public BetterDefense parseBetterDefense()
	{
		if (ATTRIBUTE_VALUE_TRUE.equals(_template.getRoot().getChild(ELEMENT_MODIFIERS).getChild(ELEMENT_MODIFIERS_MD).getAttributeValue(ATTRIBUTE_BETTER)))
		{
			return BetterDefense.MD;
		}

		return BetterDefense.PD;
	}


	public int parseModifierAttack()
	{
		return Integer.parseInt(_template.getRoot().getChild(ELEMENT_MODIFIERS).getChildText(ELEMENT_MODIFIERS_ATTACK));
	}


	public int parseModifierAC()
	{
		return Integer.parseInt(_template.getRoot().getChild(ELEMENT_MODIFIERS).getChildText(ELEMENT_MODIFIERS_AC));
	}


	public int parseModifierPD()
	{
		return Integer.parseInt(_template.getRoot().getChild(ELEMENT_MODIFIERS).getChildText(ELEMENT_MODIFIERS_PD));
	}


	public int parseModifierMD()
	{
		return Integer.parseInt(_template.getRoot().getChild(ELEMENT_MODIFIERS).getChildText(ELEMENT_MODIFIERS_MD));
	}


	public double parseModifierHP()
	{
		return Double.parseDouble(_template.getRoot().getChild(ELEMENT_MODIFIERS).getChildText(ELEMENT_MODIFIERS_HP));
	}


	public int parseModifierInitiative()
	{
		return Integer.parseInt(_template.getRoot().getChild(ELEMENT_MODIFIERS).getChildText(ELEMENT_MODIFIERS_INI));
	}


	public List<IAttack> parseAttacks()
	{
		List<IAttack> attacks = new ArrayList<IAttack>();

		for (final Element attack : _template.getRoot().getChild(ELEMENT_ATTACKS).getChildren())
		{
			String id = attack.getAttributeValue(ATTRIBUTE_ID);

			IAttack template = AttackTemplateLoader.getInstance().get(id);
			if (template != null) attacks.add(template);
		}

		return attacks;
	}


	public List<ISpecial> parseSpecials()
	{
		return readSpecials(ELEMENT_SPECIALS);
	}


	public List<ISpecial> parseNastierSpecials()
	{
		return readSpecials(ELEMENT_NASTIER);
	}


	private List<ISpecial> readSpecials(final String node)
	{
		List<ISpecial> specials = new ArrayList<ISpecial>();

		for (final Element special : _template.getRoot().getChild(node).getChildren())
		{
			String id = special.getAttributeValue(ATTRIBUTE_ID);

			ISpecial template = SpecialTemplateLoader.getInstance().get(id);
			if (template != null) specials.add(template);
		}

		return specials;
	}


	@Override
	public File saveToFile()
	{
		throw new IllegalStateException("Operation not intended");
	}


	@Override
	public File saveToFile(final File file)
	{
		return saveToFile();
	}
}
