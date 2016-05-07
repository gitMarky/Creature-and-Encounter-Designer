package project.thirteenthage.creatures.mechanics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
		BasicXmlFile template = new BasicXmlFile(file);
		_name = template.getRoot().getChildText("name");
		_attack = Integer.parseInt(template.getRoot().getChildText("bonus"));
		_defense = template.getRoot().getChildText("defense");
		_damage = Double.parseDouble(template.getRoot().getChild("damage").getAttributeValue("factor"));
		_damageDesc = template.getRoot().getChild("damage").getAttributeValue("description");
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
}
