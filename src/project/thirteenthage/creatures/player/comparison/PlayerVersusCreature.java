package project.thirteenthage.creatures.player.comparison;

import project.thirteenthage.creatures.internal.conversions.Conversions;
import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.player.PlayerCharacter;

public class PlayerVersusCreature
{
	private final PlayerCharacter _player;
	private final ICreature _monster;
	
	private boolean _withPowers = true;
	
	public PlayerVersusCreature(final PlayerCharacter player, final ICreature monster)
	{
		_player = player;
		_monster = monster;
	}
	
	
	public double getMonsterDamage()
	{
		int attackBonus = _monster.getAttacks().get(0).getAttackBonus();
		int damage = Conversions.round(_monster.getAttacks().get(0).getDamageFactor());
		CreatureAttack attack = new CreatureAttack(attackBonus, damage);
		
		return attack.expectedDamage(_player.getAC());
	}
	
	
	public double getPlayerDamage(int round)
	{
		int attackBonus = _player.getAttackBonus();
		double damage = getPlayerStrikeDamage();
		PlayerAttack attack = new PlayerAttack(attackBonus + escalationDie(round), damage, _player.getLevel());
		
		return attack.expectedDamage(_monster.getAC());
	}
	
	private int escalationDie(int round)
	{
		return Math.min(Math.max(round, 0), 6);
	}


	public double getPlayerStrikeDamage()
	{
		// assume an average of D8 damage, with +3 damage
		
		double base = 4.5;
		double dice =  base * _player.getLevel();
		double mod = Math.max(1, (_player.getLevel() + 1) / 3);
		
		double damage = dice + _player.getDamageModifier() * mod;
		return damage * (_withPowers ? 1.5 : 1.0); // multiply with 1.5 for powers
	}
	
	
	public double getExpectedSurvivalTime()
	{
		double hp = _player.getHP();
		int round;
		for (round = 0; hp > 0; ++round)
		{
			hp -= getMonsterDamage();
			if (hp <= 0)
			{
//				System.out.println("Player dead in round " + round);
				break;
			}
		}
		
		return round;
	}	
	
	
	public double getExpectedKillingTime()
	{
		double hp = _monster.getHP();
		int round;
		for (round = 0; hp > 0; ++round)
		{
			hp -= getPlayerDamage(round);
			if (hp <= 0)
			{
//				System.out.println("Monster dead in round " + round);
				break;
			}
		}
		return round;
	}
}
