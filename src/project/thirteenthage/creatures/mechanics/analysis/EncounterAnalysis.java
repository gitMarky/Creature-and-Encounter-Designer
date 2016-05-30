package project.thirteenthage.creatures.mechanics.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.player.AveragePlayerCharacter;
import project.thirteenthage.creatures.player.PlayerCharacter;


/**
 * Calculates the encounter difficulty.
 * 
 * Criteria:
 * - player side max survival
 * - player side min survival
 * - monster side max survival
 * - monster side min survival
 * - expected survival
 * - player characters / monsters hp left
 */
public class EncounterAnalysis
{
	private final Encounter _encounter;

	public EncounterAnalysis(final Encounter encounter)
	{
		_encounter = encounter;
	}
	
	
	public void analyze()
	{
		final List<PlayerCharacter> players = initializePlayers();
		
		Map<ICreature, Integer> monsters = _encounter.getOpposition();

		final List<ICombattant> players2 = new ArrayList<ICombattant>();
		final List<ICombattant> monsters2 = new ArrayList<ICombattant>();
		
		for (final PlayerCharacter player : players)
		{
			players2.add(new CombatPlayer(player));
		}
		
		for (Entry<ICreature, Integer> entry : monsters.entrySet())
		{
			for (int i = 0; i < entry.getValue(); ++i)
			{
				monsters2.add(new CombatMonster(entry.getKey()));
			}
		}

		final Combat combat = new Combat(players2, monsters2);
		combat.setMode(AnalysisMode.AVERAGE);
		combat.resolve();

		final Combat player_survival = new Combat(players2, monsters2);
		player_survival.setMode(AnalysisMode.PLAYER_SURVIVAL);
		player_survival.resolve();
	
		final Combat monster_survival = new Combat(players2, monsters2);
		monster_survival.setMode(AnalysisMode.MONSTER_SURVIVAL);
		monster_survival.resolve();
	}


	private List<PlayerCharacter> initializePlayers()
	{
		final List<PlayerCharacter> players = new ArrayList<PlayerCharacter>();
		
		for (int i = 0; i < _encounter.getPlayerAmount(); ++i)
		{
			players.add(new AveragePlayerCharacter());
		}
		
		return players;
	}
}
