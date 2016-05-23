package project.thirteenthage.creatures.player.comparison;

public class CreatureAttack
{
	final int _attack;
	final int _damage;
	
	public CreatureAttack(final int attackBonus, final int strikeDamage)
	{
		_attack = attackBonus;
		_damage = strikeDamage;
	}
	
	public double expectedDamage(int defense)
	{
		int targetNumber = defense - getAttackBonus();
		
		int dummy = 1;
		
		double hitChance = getHitChance(targetNumber);
		
		double fumbleChance = 0.05;
		double critChance = 0.05;
		
		hitChance = Math.min(Math.max(0, hitChance - critChance), (1 - fumbleChance - critChance));
		
		double missChance = 1 - hitChance - fumbleChance - critChance;
		
		double expectedDamage = 0 * fumbleChance
				              + 0 * missChance
				              + getDamage() * hitChance
				              + getDamage() * critChance * 2.0;
		
		return expectedDamage;
	}
	
	private double getHitChance(int targetNumber)
	{
		// the chance to roll greater than, or equal to the target number
		return (21.0 - targetNumber) / 20.0;
	}

	private int getAttackBonus()
	{
		return _attack;
	}
	
	private int getDamage()
	{
		return _damage;
	}
}
