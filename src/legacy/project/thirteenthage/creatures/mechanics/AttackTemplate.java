package legacy.project.thirteenthage.creatures.mechanics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;

import project.marky.library.xml.BasicXmlFile;
import legacy.project.thirteenthage.creatures.interfaces.ITrigger;
import legacy.project.thirteenthage.creatures.internal.conversions.HtmlDescriptions;
import legacy.project.thirteenthage.creatures.internal.interfaces.IAttack;

public class AttackTemplate implements IAttack
{
	private final int _attackBonus;
	private final int _amount;
	private final double _damage;
	private double _levelAdjustment = 0.0;

	private String _name = "Melee attack";
	private String _damageDesc = "damage";

	private final String _defense;
	private String _info = "";

	private final List<ITrigger> _triggers = new ArrayList<ITrigger>();

	public AttackTemplate(final File file)
	{
		this(new BasicXmlFile(file));
	}


	public AttackTemplate(final BasicXmlFile template)
	{
		if (template == null)
		{
			throw new IllegalArgumentException("Parameter 'template' must not be null.");
		}

		_name = template.getRoot().getChildText("name");
		_attackBonus = Integer.parseInt(template.getRoot().getChildText("bonus"));
		_defense = template.getRoot().getChildText("defense");
		_damage = parseDamage(template); //Double.parseDouble(template.getRoot().getChild("damage").getAttributeValue("factor"));
		_damageDesc = parseDamageDescription(template); //template.getRoot().getChild("damage").getAttributeValue("description");

		// get attack amount
		final String amount = getChildText(template, "amount");
		if (amount.isEmpty())
		{
			_amount = 1;
		}
		else
		{
			_amount = Integer.parseInt(amount);
		}

		// get info
		_info = formatInfo(_amount, getChildText(template, "info"));

		for (final Element element : template.getRoot().getChild("triggers").getChildren())
		{
			final String name = element.getAttributeValue("name");
			final String description = element.getAttributeValue("description");

			if (name.isEmpty() || description.isEmpty()) continue;

			final Trigger trigger = new Trigger(name, description);
			_triggers.add(trigger);
		}

		if (template.getRoot().getChild("level") != null)
		{
			_levelAdjustment = Double.parseDouble(template.getRoot().getChildText("level"));
		}
	}


	private double parseDamage(final BasicXmlFile template)
	{
		double damage = Double.NaN;

		damage = parseDouble(template.getRoot().getChild("damage").getAttributeValue("factor"));

		if (Double.isNaN(damage))
		{
			damage = parseDouble(template.getRoot().getChild("damage").getChildText("factor"));
		}

		if (Double.isNaN(damage))
		{
			return 0;
		}
		else
		{
			return damage;
		}
	}


	private double parseDouble(final String value)
	{
		if (value == null || value.isEmpty())
		{
			return Double.NaN;
		}

		try
		{
			return Double.parseDouble(value);
		}
		catch (final IllegalArgumentException e)
		{
			return Double.NaN;
		}
	}


	private String parseDamageDescription(final BasicXmlFile template)
	{
		String desc = template.getRoot().getChild("damage").getAttributeValue("description");

		if (desc == null)
		{
			desc = template.getRoot().getChild("damage").getChildText("description");
		}

		if (desc == null)
		{
			return "damage";
		}
		else
		{
			return desc;
		}
	}


	private String formatInfo(final int amount, final String text)
	{
		final StringBuilder info = new StringBuilder();

		final boolean hasMultipleAttacks = amount > 1;
		final boolean hasInfo = !text.isEmpty();

		if (hasMultipleAttacks || hasInfo)
		{
			info.append(" (");
		}

		if (hasMultipleAttacks)
		{
			info.append(amount).append(" attacks");
		}

		if (hasInfo)
		{
			info.append(text);
		}

		if (hasMultipleAttacks || hasInfo)
		{
			info.append(")");
		}
		return info.toString();
	}


	@Override
	public String getName()
	{
		return _name;
	}


	@Override
	public int getAttackBonus()
	{
		return _attackBonus;
	}


	@Override
	public double getDamageFactor()
	{
		return _damage;
	}


	@Override
	public String getDefense()
	{
		return _defense;
	}


	@Override
	public String getDescription()
	{
		return _damageDesc;
	}


	@Override
	public String toGuiText()
	{
		return HtmlDescriptions.getAttackDescription(this, "creature", 100, 1, true);
	}


	@Override
	public List<ITrigger> getTriggers()
	{
		return _triggers;
	}


	@Override
	public String toString()
	{
		return getName();
	}


	@Override
	public double getLevelAdjustment()
	{
		return _levelAdjustment;
	}


	@Override
	public String getInfo()
	{
		return _info;
	}

	private String getChildText(final BasicXmlFile template, final String name)
	{
		if (template.getRoot().getChild(name) != null)
		{
			return template.getRoot().getChildText(name);
		}
		else
		{
			return "";
		}
	}
}
