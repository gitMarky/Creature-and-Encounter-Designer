package project.thirteenthage.creatures.internal.creature;

import java.util.ArrayList;
import java.util.List;

import project.thirteenthage.creatures.internal.interfaces.IAttack;
import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.internal.interfaces.ISpecial;
import project.thirteenthage.creatures.mechanics.Attack;

/**
 * Defines a creature, implementation of {@link ICreature}.
 */
public class Creature implements ICreature
{
	private String _name = "New creature";
	private int _level;
	private List<String> _labels;
	private int _ac;
	private int _pd;
	private int _md;
	private double _hp;
	private double _damage;
	private double _fearThreshold;
	private int _ini;
	private List<IAttack> _attacks = new ArrayList<IAttack>();
	private List<ISpecial> _specials = new ArrayList<ISpecial>();
	private List<ISpecial> _nastierSpecials =  new ArrayList<ISpecial>();
	private CreatureSize _size;

	@Override
	public String getName()
	{
		return _name;
	}
	
	@Override
	public CreatureSize getSize()
	{
		return _size;
	}

	@Override
	public int getLevel()
	{
		return _level;
	}

	@Override
	public List<String> getLabels()
	{
		return _labels;
	}

	@Override
	public int getAC()
	{
		return _ac;
	}

	@Override
	public int getPD()
	{
		return _pd;
	}

	@Override
	public int getMD()
	{
		return _md;
	}

	@Override
	public double getHP()
	{
		return _hp;
	}

	@Override
	public int getInitiative()
	{
		return _ini;
	}
	
	@Override
	public double getStrikeDamage()
	{
		return _damage;
	}
	
	@Override
	public double getFearThreshold()
	{
		return _fearThreshold;
	}

	@Override
	public List<IAttack> getAttacks()
	{
		return _attacks;
	}

	@Override
	public List<ISpecial> getSpecials()
	{
		return _specials;
	}

	@Override
	public List<ISpecial> getNastierSpecials()
	{
		return _nastierSpecials;
	}

	void setName(final String name)
	{
		_name = name;
	}

	void setLevel(final int level)
	{
		_level = level;
	}

	void setAC(final int ac)
	{
		_ac = ac;
	}

	void setPD(final int pd)
	{
		_pd = pd;
	}

	void setMD(final int md)
	{
		_md = md;
	}

	void setHP(final double hp)
	{
		_hp = hp;
	}

	void setInitiative(final int ini)
	{
		_ini = ini;
	}

	void setSize(final CreatureSize size)
	{
		_size = size;
	}

	void setFearThreshold(final double threshold)
	{
		_fearThreshold = threshold;
	}
	
	void setStrikeDamage(final double damage)
	{
		_damage = damage;
	}

	public void setAttacks(List<IAttack> attacks)
	{
		_attacks.clear();
		// create the actual attacks from templates.
		final int bonus = getLevel() + 5;
		for (final IAttack template : attacks)
		{
			final Attack attack = new Attack(template, bonus, getStrikeDamage());
			_attacks.add(attack);
		}
	}
}