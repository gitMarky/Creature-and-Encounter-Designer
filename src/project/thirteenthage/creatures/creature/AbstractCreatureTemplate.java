package project.thirteenthage.creatures.creature;

import java.util.ArrayList;
import java.util.List;

import project.thirteenthage.creatures.interfaces.ICreatureBuilder;
import project.thirteenthage.creatures.internal.interfaces.IAttack;
import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.internal.interfaces.ICreatureTemplate;
import project.thirteenthage.creatures.internal.interfaces.ISpecial;
import project.thirteenthage.creatures.module.creature.Creature;
import project.thirteenthage.creatures.module.creature.CreatureBuilder;

public abstract class AbstractCreatureTemplate implements ICreatureTemplate
{
	private String _name = "New creature";
	private int _level = 1;
	private CreatureSize _size = CreatureSize.NORMAL;
	private final List<String> _labels = new ArrayList<String>();
	private int _attack = 0;
	private int _ac = 0;
	private int _pd = 0;
	private int _md = 0;
	private double _hp = 1.0;
	private double _damage = 1.0;
	private int _ini = 0;
	private BetterDefense _betterDefense = BetterDefense.PD;
	private final List<IAttack> _attacks = new ArrayList<IAttack>();
	private final List<ISpecial> _specials = new ArrayList<ISpecial>();
	private final List<ISpecial> _nastier = new ArrayList<ISpecial>();
	private String _id = null;
	

	@Override
	public String getName()
	{
		return _name;
	}


	@Override
	public int getLevel()
	{
		return _level;
	}


	@Override
	public CreatureSize getSize()
	{
		return _size;
	}


	@Override
	public List<String> getLabels()
	{
		return _labels;
	}


	@Override
	public BetterDefense getBetterDefense()
	{
		return _betterDefense;
	}


	@Override
	public int getModifierAttack()
	{
		return _attack;
	}


	@Override
	public int getModifierAC()
	{
		return _ac;
	}


	@Override
	public int getModifierPD()
	{
		return _pd;
	}


	@Override
	public int getModifierMD()
	{
		return _md;
	}


	@Override
	public double getModifierHP()
	{
		return _hp;
	}


	@Override
	public double getModifierDamage()
	{
		return _damage;
	}


	@Override
	public int getModifierInitiative()
	{
		return _ini;
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
		return _nastier;
	}
	
	
	@Override
	public String getId()
	{
		return _id;
	}


	public void setName(final String name)
	{
		if (name == null)
		{
			throw new IllegalArgumentException("Parameter 'name' must not be null.");
		}

		_name = name;
	}


	public void setLevel(final int level)
	{
		_level = level;
	}


	public void setSize(final CreatureSize size)
	{
		if (size == null)
		{
			throw new IllegalArgumentException("Parameter 'size' must not be null.");
		}

		_size = size;
	}


	public void setAttack(final int attack)
	{
		_attack = attack;
	}


	public void setAC(final int ac)
	{
		_ac = ac;
	}


	public void setPD(final int pd)
	{
		_pd = pd;
	}


	public void setMD(final int md)
	{
		_md = md;
	}


	public void setHP(final double hp)
	{
		_hp = hp;
	}


	public void setDamage(final double damage)
	{
		_damage = damage;
	}


	public void setInitiative(final int ini)
	{
		_ini = ini;
	}


	public void setBetterDefense(final BetterDefense defense)
	{
		if (defense == null)
		{
			throw new IllegalArgumentException("Parameter 'defense' must not be null.");
		}

		_betterDefense = defense;
	}
	
	
	public void setId(final String id)
	{
		_id = id;
	}


	@Override
	public ICreature toCreature()
	{
		final ICreatureBuilder builder = new CreatureBuilder();
		builder.name(getName())
		       .size(getSize())
		       .level(getLevel())
		       .addInitiative(getModifierInitiative())
		       .addAttack(getModifierAttack())
		       .addAC(getModifierAC())
		       .addPD(getModifierPD())
		       .addMD(getModifierMD())
		       .scaleHP(getModifierHP())
		       .scaleDamage(getModifierDamage());

		if (getBetterDefense() == BetterDefense.MD) builder.betterDefenseIsMD();

		final Creature creature;
		if (getLabels().contains("Mook"))
		{
			creature = builder.buildMook();
		}
		else
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

}