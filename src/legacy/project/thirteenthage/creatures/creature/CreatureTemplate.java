package legacy.project.thirteenthage.creatures.creature;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import legacy.project.thirteenthage.creatures.internal.interfaces.IAttack;
import legacy.project.thirteenthage.creatures.internal.interfaces.ISpecial;
import legacy.project.thirteenthage.creatures.loaders.AttackTemplateLoader;
import legacy.project.thirteenthage.creatures.loaders.SpecialTemplateLoader;

import org.jdom2.Element;

import project.marky.library.xml.BasicXmlFile;

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
	static final String ELEMENT_MODIFIERS_DAMAGE = "damage";
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


	public CreatureTemplate(final File file, final String id)
	{
		this(new BasicXmlFile(file), id);
	}


	public CreatureTemplate(final BasicXmlFile file, final String id)
	{
		if (file == null)
		{
			throw new IllegalArgumentException("Parameter 'file' must not be null.");
		}

		_template = file;

		parse();
		setId(id);
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
		setDamage(parseModifierDamage());
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
		final List<String> labels = new ArrayList<String>();
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


	public double parseModifierDamage()
	{
		try
		{
			return Double.parseDouble(_template.getRoot().getChild(ELEMENT_MODIFIERS).getChildText(ELEMENT_MODIFIERS_DAMAGE));
		}
		catch (final NullPointerException e)
		{
			return 1.0; // default value if the element is not present.
		}
	}


	public int parseModifierInitiative()
	{
		return Integer.parseInt(_template.getRoot().getChild(ELEMENT_MODIFIERS).getChildText(ELEMENT_MODIFIERS_INI));
	}


	public List<IAttack> parseAttacks()
	{
		final List<IAttack> attacks = new ArrayList<IAttack>();

		for (final Element attack : _template.getRoot().getChild(ELEMENT_ATTACKS).getChildren())
		{
			final String id = attack.getAttributeValue(ATTRIBUTE_ID);

			final IAttack template = AttackTemplateLoader.getInstance().get(id);
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
		if (node == null)
		{
			throw new IllegalArgumentException("Parameter 'node' must not be null.");
		}

		final List<ISpecial> specials = new ArrayList<ISpecial>();

		for (final Element special : _template.getRoot().getChild(node).getChildren())
		{
			final String id = special.getAttributeValue(ATTRIBUTE_ID);

			final ISpecial template = SpecialTemplateLoader.getInstance().get(id);
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
		if (file == null)
		{
			throw new IllegalArgumentException("Parameter 'file' must not be null.");
		}

		return saveToFile();
	}
}
