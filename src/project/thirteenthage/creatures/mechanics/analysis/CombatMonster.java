package project.thirteenthage.creatures.mechanics.analysis;

import project.thirteenthage.creatures.internal.conversions.Conversions;
import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.player.comparison.CreatureAttack;

class CombatMonster extends AbstractCombattant
{
	public CombatMonster(ICreature creature)
	{
		super(creature);
	}

	@Override
	public int getDamage(final ICreature target, final int escalationDie)
	{
		int attackBonus = getCreature().getAttacks().get(0).getAttackBonus();
		int damage = Conversions.round(getCreature().getAttacks().get(0).getDamageFactor());
		CreatureAttack attack = new CreatureAttack(attackBonus, damage);
		
		return Conversions.round(attack.expectedDamage(target.getAC()));
	}
}
