package project.thirteenthage.creatures.player.comparison;

import project.thirteenthage.creatures.internal.conversions.Conversions;
import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.player.PlayerCharacter;

public class PlayerVersusCreature
{
	private final PlayerCharacter _player;
	private final ICreature _monster;
	
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
	
	
	public double getPlayerDamage()
	{
		int attackBonus = _player.getAttackBonus();
		double damage = getPlayerStrikeDamage();
		PlayerAttack attack = new PlayerAttack(attackBonus, damage, _player.getLevel());
		
		return attack.expectedDamage(_monster.getAC());
	}
	
	public double getPlayerStrikeDamage()
	{
		// assume an average of D8 damage, with +3 damage
		
		double base = 4.5;
		double dice =  base * _player.getLevel();
		double mod = Math.max(1, (_player.getLevel() + 1) / 3);
		System.out.println("mod: " + mod + " / level: " + _player.getLevel());
		
		return dice + _player.getDamageModifier() * mod;
	}
	
	
	public double getExpectedSurvivalTime()
	{
		return _player.getHP() / getMonsterDamage();
	}
	
	
	public double getExpectedKillingTime()
	{
		return _monster.getHP() / getPlayerDamage();
	}
}
