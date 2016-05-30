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
	public int getDamage(final ICreature target, int escalationDie)
	{
		int attackBonus = ((PlayerCharacter) getCreature()).getAttackBonus();
		double damage = getCreature().getStrikeDamage();
		PlayerAttack attack = new PlayerAttack(attackBonus + escalationDie, damage, getCreature().getLevel());

		return Conversions.round(attack.expectedDamage(target.getAC()));
	}
}
