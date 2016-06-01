package project.thirteenthage.creatures.mechanics.analysis;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import project.thirteenthage.creatures.internal.gui.views.CreatureEncounterPanel;
import project.thirteenthage.creatures.internal.interfaces.ICreature;

/**
 * Represents an encounter.
 */
public class Encounter
{
	private final Map<ICreature, Integer> _amount = new HashMap<ICreature, Integer>();
	private int _level = 1;
	private int _players = 1;


	public Encounter(final Map<ICreature, CreatureEncounterPanel> creatures)
	{
		if (creatures == null)
		{
			throw new IllegalArgumentException("Parameter 'creatures' must not be null.");
		}

		for (final Entry<ICreature, CreatureEncounterPanel> entry : creatures.entrySet())
		{
			addCreature(entry.getKey(), entry.getValue().getAmount());
		}
	}


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


	public void setPlayerLevel(final int level)
	{
		if (level < 1)
		{
			throw new IllegalArgumentException("Player level has to be at least 1, got " + level);
		}

		_level = level;
	}


	public int getPlayerLevel()
	{
		return _level;
	}


	public void setPlayerAmount(final int amount)
	{
		if (amount < 1)
		{
			throw new IllegalArgumentException("Player amount has to be at least 1, got " + amount);
		}

		_players = amount;
	}


	public int getPlayerAmount()
	{
		return _players;
	}


	public Map<ICreature, Integer> getOpposition()
	{
		return _amount;
	}
}
