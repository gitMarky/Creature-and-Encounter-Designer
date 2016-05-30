package project.thirteenthage.creatures.mechanics.analysis;

import java.util.List;

import project.thirteenthage.creatures.internal.ApplicationLogger;

/**
 * Represents one combat encounter.
 */
public class Combat
{
	private final List<ICombattant> _players;
	private final List<ICombattant> _monsters;
	private boolean _isResolved = false;
	private int _round = 0;
	
	private AnalysisMode _mode = AnalysisMode.AVERAGE;


	public Combat(List<ICombattant> players, List<ICombattant> monsters)
	{
		_players = players;
		_monsters = monsters;
	}


	public void resolve()
	{
		assertNotResolved();

		initializeCombattants(_players, _mode == AnalysisMode.MONSTER_SURVIVAL);
		initializeCombattants(_monsters, _mode == AnalysisMode.PLAYER_SURVIVAL);

		_round = 0;
		while(!_isResolved)
		{
			_round += 1;
			resolveRound(_round);
		}
	}
	
	
	public int getLastRound()
	{
		return _round;
	}
	
	
	public String getReport()
	{
		return null;
	}


	private void initializeCombattants(final List<ICombattant> creatures, final boolean invulnerable)
	{
		for (final ICombattant creature : creatures)
		{
			creature.initialize();
			if (invulnerable)
			{
				creature.setInvulnerable();
			}
		}
	}


	private void resolveRound(final int round)
	{
		ApplicationLogger.getLogger().info(">> Starting combat round " + round);;
		
		// give the players a little edge, to account for recoveries, special
		// items
		// and other stuff that is not accounted for in the average player
		// calculation

		for (int player = 0; player < _players.size(); ++player)
		{
			resolveAttack(_players.get(player), _monsters, player, escalationDie(round));
		}
		
		// let the monsters attack

		for (int monster = 0; monster < _monsters.size(); ++monster)
		{
			resolveAttack(_monsters.get(monster), _players, monster, escalationDie(round));
		}
		
		_isResolved = isPartyEliminated(_players) || isPartyEliminated(_monsters);
	}


	private void resolveAttack(ICombattant attacker, List<ICombattant> targets, int index, final int escalationDie)
	{
		// the dead do not attack
		if (!attacker.isAlive()) return;
		
		ICombattant target = null;
		for (int i = 0; i < targets.size(); ++i)
		{
			ICombattant candidate = targets.get((index + i) % targets.size());
			
			if (candidate.isAlive())
			{
				target = candidate;
				break;
			}
		}
		
		// do nothing if there is no target
		if (target == null) return;
		
		int damage = attacker.getDamage(target.getCreature(), escalationDie, _mode);
		ApplicationLogger.getLogger().info(attacker.getName() + " hits " + target.getName() + " for " + damage + " damage");
		target.takeDamage(damage);
	}


	private boolean isPartyEliminated(List<ICombattant> party)
	{
		for (final ICombattant member : party)
		{
			if (member.isAlive()) return false;
		}
		
		return true;
	}


	private int escalationDie(int round)
	{
		return Math.min(Math.max(round, 0), 6);
	}	


	private void assertNotResolved()
	{
		if (_isResolved) throw new IllegalStateException("The combat was resolved already.");
	}


	public void setMode(AnalysisMode mode)
	{
		_mode = mode;
	}
}
