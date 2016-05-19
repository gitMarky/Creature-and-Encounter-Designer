package project.thirteenthage.creatures.mechanics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Element;

import project.thirteenthage.creatures.interfaces.ITrigger;
import project.thirteenthage.creatures.internal.BasicXmlFile;
import project.thirteenthage.creatures.internal.interfaces.IAttack;

public class AttackTemplate implements IAttack
{
	private String _name = "Melee attack";
	private final int _attack;
	private final String _defense;
	private final double _damage;
	private String _damageDesc = "damage";
	private final List<ITrigger> _triggers = new ArrayList<ITrigger>();


	public AttackTemplate(final File file)
	{
		this(new BasicXmlFile(file));
	}


	public AttackTemplate(final BasicXmlFile template)
	{
		_name = template.getRoot().getChildText("name");
		_attack = Integer.parseInt(template.getRoot().getChildText("bonus"));
		_defense = template.getRoot().getChildText("defense");
		_damage = Double.parseDouble(template.getRoot().getChild("damage").getAttributeValue("factor"));
		_damageDesc = template.getRoot().getChild("damage").getAttributeValue("description");

		for (final Element element : template.getRoot().getChild("triggers").getChildren())
		{
			String name = element.getAttributeValue("name");
			String description = element.getAttributeValue("description");

			if (name.isEmpty() || description.isEmpty())
				continue;

			final Trigger trigger = new Trigger(name, description);
			_triggers.add(trigger);
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
		throw new IllegalStateException("Not implemented");
	}


	@Override
	public String toHtmlText()
	{
		throw new IllegalStateException("Not implemented");
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
}
