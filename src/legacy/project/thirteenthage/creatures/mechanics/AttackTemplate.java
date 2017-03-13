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
	private String _name = "Melee attack";
	private final int _attack;
	private final String _defense;
	private final double _damage;
	private String _damageDesc = "damage";
	private final List<ITrigger> _triggers = new ArrayList<ITrigger>();
	private double _levelAdjustment = 0.0;


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
		_attack = Integer.parseInt(template.getRoot().getChildText("bonus"));
		_defense = template.getRoot().getChildText("defense");
		_damage = Double.parseDouble(template.getRoot().getChild("damage").getAttributeValue("factor"));
		_damageDesc = template.getRoot().getChild("damage").getAttributeValue("description");

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


	@Override
	public String getName()
	{
		return _name;
	}


	@Override
	public int getAttackBonus()
	{
		return _attack;
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
}
