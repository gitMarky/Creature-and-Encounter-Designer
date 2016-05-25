package project.thirteenthage.creatures.mechanics.analysis;

import java.util.Map.Entry;

import project.thirteenthage.creatures.creature.CreatureSize;
import project.thirteenthage.creatures.internal.interfaces.ICreature;

/**
 * Calculates the encounter difficulty.
 */
public class EncounterDifficulty
{
	private final Encounter _encounter;

	public EncounterDifficulty(final Encounter encounter)
	{
		_encounter = encounter;
	}

	
	public double getEncounterDifficulty()
	{
		double difficulty = 0;

		for (final Entry<ICreature, Integer> entry : _encounter.getOpposition().entrySet())
		{
			difficulty += entry.getValue() * getCreatureDifficulty(entry.getKey());
		}

		return difficulty;
	}


	private double getCreatureDifficulty(final ICreature creature)
	{
		if (creature == null)
		{
			throw new IllegalArgumentException("Parameter 'creature' must not be null.");
		}

		int levelDifference = creature.getLevel() - _encounter.getPlayerLevel();

		if (_encounter.getPlayerLevel() >= 5) levelDifference -= 1; // monsters for champion battles
		// are fair if
		// level is one higher
		if (_encounter.getPlayerLevel() >= 8) levelDifference -= 2; // same goes for epic battles,
		// with 2 levels
		// instead

		return getDifficultyTable(levelDifference, creature.getSize(), creature.isMook());
	}


	private double getDifficultyTable(final int levelDifference, final CreatureSize size, final boolean mook)
	{
		if (size == null)
		{
			throw new IllegalArgumentException("Parameter 'size' must not be null.");
		}

		final int row = Math.min(Math.max(levelDifference + 2, -1), 7);

		double difficulty = 1.0;
		switch (row)
		{
			case -1:
				difficulty = 0.5 / _encounter.getPlayerLevel(); // add a default for lower levels.
				break;
			case 0:
				difficulty = 0.5;
				break;
			case 1:
				difficulty = 0.7;
				break;
			case 2:
				difficulty = 1.0;
				break;
			case 3:
				difficulty = 1.5;
				break;
			case 4:
				difficulty = 2.0;
				break;
			case 5:
				difficulty = 3.0;
				break;
			case 6:
				difficulty = 4.0;
				break;
			case 7:
				difficulty = 6.0;
				break;
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
