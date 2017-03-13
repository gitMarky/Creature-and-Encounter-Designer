package project.thirteenthage.creatures.module.creature;

import project.thirteenthage.creatures.creature.CreatureSize;
import project.thirteenthage.creatures.interfaces.ICreatureBuilder;
import project.thirteenthage.creatures.tables.CreatureTableRow;
import project.thirteenthage.creatures.tables.CreatureTables;

public class CreatureBuilder implements ICreatureBuilder
{
	private String _name = "New creature";
	private int _level = 0;
	private CreatureSize _size = CreatureSize.NORMAL;
	private boolean _betterDefenseIsPD = true;

	private int _modifierAttack = 0;
	private int _modifierAC = 0;
	private int _modifierPD = 0;
	private int _modifierMD = 0;
	private int _modifierInitiative = 0;
	private double _factorHP = 1.0;
	private double _factorDamage = 1.0;


	/**
	 * Constructor for a new creature, usually from a template.
	 */
	public CreatureBuilder()
	{

	}


	@Override
	public ICreatureBuilder name(final String name)
	{
		if (name == null)
		{
			throw new IllegalArgumentException("Parameter 'name' must not be null.");
		}

		_name = name;
		return this;
	}


	@Override
	public ICreatureBuilder level(final int level)
	{
		_level = level;
		return this;
	}


	@Override
	public ICreatureBuilder size(final CreatureSize size)
	{
		if (size == null)
		{
			throw new IllegalArgumentException("Parameter 'size' must not be null.");
		}

		_size = size;
		return this;
	}


	@Override
	public ICreatureBuilder addAttack(final int amount)
	{
		_modifierAttack += amount;
		return this;
	}


	@Override
	public ICreatureBuilder addAC(final int amount)
	{
		_modifierAC += amount;
		return this;
	}


	@Override
	public ICreatureBuilder addPD(final int amount)
	{
		_modifierPD += amount;
		return this;
	}


	@Override
	public ICreatureBuilder addMD(final int amount)
	{
		_modifierMD += amount;
		return this;
	}


	@Override
	public ICreatureBuilder addInitiative(final int amount)
	{
		_modifierInitiative += amount;
		return this;
	}


	@Override
	public ICreatureBuilder scaleHP(final double factor)
	{
		_factorHP *= factor;
		return this;
	}


	@Override
	public ICreatureBuilder scaleDamage(final double factor)
	{
		_factorDamage *= factor;
		return this;
	}


	@Override
	public ICreatureBuilder betterDefenseIsMD()
	{
		_betterDefenseIsPD = false;
		return this;
	}


	@Override
	public Creature buildCreature()
	{
		final Creature creature = new Creature();

		creature.setName(_name);
		creature.setSize(_size);
		creature.setLevel(_level);

		creature.setAttackModifier(_modifierAttack);

		creature.setAC(_level + 16 + _modifierAC);

		if (_betterDefenseIsPD)
		{
			creature.setPD(_level + 14 + _modifierPD);
			creature.setMD(_level + 10 + _modifierMD);
		}
		else
		{
			creature.setPD(_level + 10 + _modifierPD);
			creature.setMD(_level + 14 + _modifierMD);
		}

		creature.setInitiative(_level + _modifierInitiative);

		final CreatureTableRow sizeInfo = CreatureTables.bySize(_size).get(_level);

		creature.setHP(sizeInfo.getHP() * _factorHP);
		creature.setFearThreshold(sizeInfo.getFearThreshold() * _factorHP);
		creature.setStrikeDamage(sizeInfo.getStrikeDamage() * _factorDamage);

		return creature;
	}


	@Override
	public Creature buildMook()
	{
		final Creature mook = buildCreature();

		mook.setFearThreshold(0); // people are not afraid of mooks
		mook.setHP(mook.getHP() * 0.25);
		mook.setStrikeDamage(getMookStrikeDamageFactor(mook.getStrikeDamage()));
		mook.setMook(true);

		return mook;
	}


	@Override
	public Creature build(final boolean isMook)
	{
		if (isMook)
			return buildMook();
		else
			return buildCreature();
	}


	private double getMookStrikeDamageFactor(final double strikeDamage)
	{
		switch (_level)
		{
			case 0:
				return 3.0 * strikeDamage / 4.0;
			case 1:
				return 4.0 * strikeDamage / 5.0;
			case 2:
				return 5.0 * strikeDamage / 7.0;
			case 3:
				return 6.0 * strikeDamage / 10.0;
			case 4:
				return 7.0 * strikeDamage / 14.0;
			case 5:
				return 9.0 * strikeDamage / 18.0;
			case 6:
				return 12.0 * strikeDamage / 21.0;
			case 7:
				return 18.0 * strikeDamage / 28.0;
			case 8:
				return 23.0 * strikeDamage / 38.0;
			case 9:
				return 31.0 * strikeDamage / 50.0;
			case 10:
				return 37.0 * strikeDamage / 58.0;
			case 11:
				return 46.0 * strikeDamage / 70.0;
			case 12:
				return 60.0 * strikeDamage / 90.0;
			case 13:
				return 74.0 * strikeDamage / 110.0;
			case 14:
			default:
				return 90.0 * strikeDamage / 135.0;
		}
	}
}
