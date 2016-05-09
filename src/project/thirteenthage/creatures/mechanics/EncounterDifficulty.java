package project.thirteenthage.creatures.mechanics;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import project.thirteenthage.creatures.creature.CreatureSize;
import project.thirteenthage.creatures.internal.interfaces.ICreature;

/**
 * Calculates the encounter difficulty.
 */
public class EncounterDifficulty
{
	private Map<ICreature, Integer> _amount = new HashMap<ICreature, Integer>();
	private int _level = 1;


	public void addCreature(final ICreature creature, final int amount)
	{
		if (creature == null)
		{
			throw new IllegalArgumentException("Creature must not be null");
		}
		if (amount < 1)
		{
			throw new IllegalArgumentException("You have to add at least one creature, added " + amount);
		}

		_amount.put(creature, amount);
	}


	public void setPlayerLevel(int level)
	{
		if (level < 1)
		{
			throw new IllegalArgumentException("Player level has to be at least 1, got " + level);
		}

		_level = level;
	}
	
	
	public double getEncounterDifficulty()
	{
		double difficulty = 0;
		
		for (final Entry<ICreature, Integer> entry : _amount.entrySet())
		{
			difficulty += entry.getValue() * getCreatureDifficulty(entry.getKey());
		}
		
		return difficulty;
	}


	private double getCreatureDifficulty(final ICreature creature)
	{
		int levelDifference = creature.getLevel() - _level;
		
		if (_level >= 5) levelDifference -= 1; // monsters for champion battles are fair if level is one higher
		if (_level >= 8) levelDifference -= 2; // same goes for epic battles, with 2 levels instead

		return getDifficultyTable(levelDifference, creature.getSize(), creature.isMook());
	}


	private double getDifficultyTable(int levelDifference, CreatureSize size, boolean mook)
	{
		int row = Math.max(Math.min(levelDifference + 2, 0), 7);

		double difficulty = 1.0;
		switch (row)
		{
			case 0: difficulty = 0.5; break;
			case 1: difficulty = 0.7; break;
			case 2: difficulty = 1.5; break;
			case 3: difficulty = 2.0; break;
			case 4: difficulty = 3.0; break;
			case 5: difficulty = 4.0; break;
			case 6: difficulty = 6.0; break;
			case 7: difficulty = 9.0; break;
		}

		
		if (size == CreatureSize.LARGE)
		{
			difficulty *= 2.0;
		}
		else if (size == CreatureSize.HUGE)
		{
			difficulty *= 3.0;
		}
		
		if (mook)
		{
			difficulty *= 0.2;
		}
		
		return difficulty;
	}
}
