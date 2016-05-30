package project.thirteenthage.creatures.mechanics.analysis;

import project.thirteenthage.creatures.internal.conversions.Conversions;
import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.player.PlayerCharacter;
import project.thirteenthage.creatures.player.comparison.PlayerAttack;

public class CombatPlayer extends AbstractCombattant
{
	public CombatPlayer(ICreature creature)
	{
		super(creature);
	}


	@Override
	public int getDamage(final ICreature target, int escalationDie, final AnalysisMode mode)
	{
		int attackBonus = ((PlayerCharacter) getCreature()).getAttackBonus();
		double damage = getCreature().getStrikeDamage();
		
		final boolean withPowers = mode == AnalysisMode.AVERAGE;
		damage *= (withPowers ? 1.5 : 1.0); // multiply with 1.5 for // powers
		
		PlayerAttack attack = new PlayerAttack(attackBonus + escalationDie, damage, getCreature().getLevel());

		return Conversions.round(attack.expectedDamage(target.getAC()));
	}
}
