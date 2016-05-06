package project.thirteenthage.creatures.internal.creature;

import project.thirteenthage.creatures.tables.CreatureTableRow;
import project.thirteenthage.creatures.tables.CreatureTables;

public class CreatureBuilder
{
	private String _name = "New creature";
	private int _level = 0;
	private CreatureSize _size = CreatureSize.NORMAL;
	private boolean _betterDefenseIsPD = true;
	
	private int _modifierAC = 0;
	private int _modifierPD = 0;
	private int _modifierMD = 0;
	private int _modifierInitiative = 0;
	private double _factorHP = 1.0;
	private double _factorDamage = 1.0;
	
	
	public CreatureBuilder name(final String name)
	{
		_name = name;
		return this;
	}
	
	public CreatureBuilder level(final int level)
	{
		_level = level;
		return this;
	}
	
	public CreatureBuilder size(final CreatureSize size)
	{
		_size = size;
		return this;
	}
	
	public CreatureBuilder addAC(final int amount)
	{
		_modifierAC += amount;
		return this;
	}
	
	public CreatureBuilder addPD(final int amount)
	{
		_modifierPD += amount;
		return this;
	}
	
	public CreatureBuilder addMD(final int amount)
	{
		_modifierMD += amount;
		return this;
	}
	
	public CreatureBuilder addInitiative(final int amount)
	{
		_modifierInitiative += amount;
		return this;
	}
	
	public CreatureBuilder scaleHP(final double factor)
	{
		_factorHP *= factor;
		return this;
	}
	
	public CreatureBuilder scaleDamage(final double factor)
	{
		_factorDamage *= factor;
		return this;
	}
	
	public CreatureBuilder betterDefenseIsMD()
	{
		_betterDefenseIsPD = false;
		return this;
	}
	
	public Creature buildCreature()
	{
		Creature creature = new Creature();
		
		creature.setName(_name);
		creature.setSize(_size);
		creature.setLevel(_level);
		
		creature.setAC(creature.getLevel() + 16 + _modifierAC);
		
		if (_betterDefenseIsPD)
		{
			creature.setPD(creature.getLevel() + 14 + _modifierPD);
			creature.setMD(creature.getLevel() + 10 + _modifierMD);
		}
		else
		{
			creature.setPD(creature.getLevel() + 10 + _modifierPD);
			creature.setMD(creature.getLevel() + 14 + _modifierMD);
		}
		
		creature.setInitiative(creature.getLevel() + _modifierInitiative);
		
		final CreatureTableRow sizeInfo = CreatureTables.bySize(_size).get(_level);
		
		creature.setHP(sizeInfo.getHP() * _factorHP);
		creature.setFearThreshold(sizeInfo.getFearThreshold() * _factorHP);
		creature.setStrikeDamage(sizeInfo.getStrikeDamage() * _factorDamage);

		return creature;
	}
	
	public Creature buildMook()
	{
		Creature mook = buildCreature();
		
		mook.setFearThreshold(0); // people are not afraid of mooks
		mook.setHP(mook.getHP() * 0.25);
		mook.setStrikeDamage(getMookStrikeDamageFactor(mook.getStrikeDamage()));
		
		return mook;
	}

	private double getMookStrikeDamageFactor(final double strikeDamage)
	{
		switch(_level)
		{
			case  0: return  3.0 * strikeDamage /   4.0;
			case  1: return  4.0 * strikeDamage /   5.0;
			case  2: return  5.0 * strikeDamage /   7.0;
			case  3: return  6.0 * strikeDamage /  10.0;
			case  4: return  7.0 * strikeDamage /  14.0;
			case  5: return  9.0 * strikeDamage /  18.0;
			case  6: return 12.0 * strikeDamage /  21.0;
			case  7: return 18.0 * strikeDamage /  28.0;
			case  8: return 23.0 * strikeDamage /  38.0;
			case  9: return 31.0 * strikeDamage /  50.0;
			case 10: return 37.0 * strikeDamage /  58.0;
			case 11: return 46.0 * strikeDamage /  70.0;
			case 12: return 60.0 * strikeDamage /  90.0;
			case 13: return 74.0 * strikeDamage / 110.0;
			case 14:
		    default: return 90.0 * strikeDamage / 135.0;
		}
	}
}
