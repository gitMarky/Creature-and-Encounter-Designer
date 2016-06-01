package project.thirteenthage.creatures.mechanics.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.player.AveragePlayerCharacter;
import project.thirteenthage.creatures.player.PlayerCharacter;

/**
 * Calculates the encounter difficulty.
 * 
 * Criteria: - player side max survival - player side min survival - monster
 * side max survival - monster side min survival - expected survival - player
 * characters / monsters hp left
 */
public class EncounterAnalysis
{
	private final Encounter _encounter;
	private int _combatRoundsAverage = 0;
	private int _combatRoundsPlayerSurvival = 0;
	private int _combatRoundsMonsterSurvival = 0;
	private List<String> _combatHPaveragePlayers = new ArrayList<String>();
	private List<String> _combatHPaverageMonsters = new ArrayList<String>();

	private final Map<String, MookPool> _mookPool = new HashMap<String, MookPool>();


	public EncounterAnalysis(final Encounter encounter)
	{
		_encounter = encounter;
	}


	public void analyze()
	{
		final List<PlayerCharacter> players = initializePlayers();

		final Map<ICreature, Integer> monsters = _encounter.getOpposition();

		final List<ICombattant> players2 = new ArrayList<ICombattant>();
		final List<ICombattant> monsters2 = new ArrayList<ICombattant>();

		for (final PlayerCharacter player : players)
		{
			players2.add(new CombatPlayer(player));
		}

		for (final Entry<ICreature, Integer> entry : monsters.entrySet())
		{
			for (int i = 0; i < entry.getValue(); ++i)
			{
				final ICreature creature = entry.getKey();

				final CombatMonster monster = new CombatMonster(creature);
				monsters2.add(monster);

				if (creature.isMook())
				{
					final String id = creature.getTemplate().getName();
					MookPool pool = _mookPool.get(id);

					if (pool == null || !pool.canAddMook())
					{
						pool = new MookPool();
						_mookPool.put(id, pool);
					}

					pool.addMook(monster);
				}
			}
		}

		final Combat combat = new Combat(players2, monsters2);
		combat.setMode(AnalysisMode.AVERAGE);
		combat.resolve();

		_combatRoundsAverage = combat.getLastRound();
		_combatHPaveragePlayers = getPartyHP(combat.getPlayers());
		_combatHPaverageMonsters = getPartyHP(combat.getMonsters());

		final Combat player_survival = new Combat(players2, monsters2);
		player_survival.setMode(AnalysisMode.PLAYER_SURVIVAL);
		player_survival.resolve();

		final Combat monster_survival = new Combat(players2, monsters2);
		monster_survival.setMode(AnalysisMode.MONSTER_SURVIVAL);
		monster_survival.resolve();

		_combatRoundsPlayerSurvival = player_survival.getLastRound();
		_combatRoundsMonsterSurvival = monster_survival.getLastRound();
	}


	private List<PlayerCharacter> initializePlayers()
	{
		final List<PlayerCharacter> players = new ArrayList<PlayerCharacter>();

		for (int i = 0; i < _encounter.getPlayerAmount(); ++i)
		{
			final AveragePlayerCharacter player = new AveragePlayerCharacter();
			player.setLevel(_encounter.getPlayerLevel());
			players.add(player);
		}

		return players;
	}


	public int getAverageCombatRounds()
	{
		return _combatRoundsAverage;
	}


	public int getPlayerSurvivalRounds()
	{
		return _combatRoundsPlayerSurvival;
	}


	public int getMonsterSurvivalRounds()
	{
		return _combatRoundsMonsterSurvival;
	}


	public List<String> getAverageCombatPlayerHP()
	{
		return _combatHPaveragePlayers;
	}


	public List<String> getAverageCombatMonsterHP()
	{
		return _combatHPaverageMonsters;
	}


	private List<String> getPartyHP(final List<ICombattant> party)
	{
		final List<String> list = new ArrayList<String>();
		for (final ICombattant player : party)
		{
			list.add("* " + player.getName() + ": " + player.getHP() + " HP");
		}
		return list;
	}
}
