package legacy.project.thirteenthage.creatures.module.analysis;

import legacy.project.thirteenthage.creatures.internal.conversions.Conversions;
import legacy.project.thirteenthage.creatures.internal.interfaces.ICreature;
import legacy.project.thirteenthage.creatures.player.comparison.CreatureAttack;

class CombatMonster extends AbstractCombattant
{
	public CombatMonster(final ICreature creature)
	{
		super(creature);
	}


	@Override
	public int getDamage(final ICreature target, final int escalationDie, final AnalysisMode mode)
	{
		final int attackBonus = getCreature().getAttacks().get(0).getAttackBonus();
		final int damage = Conversions.round(getCreature().getAttacks().get(0).getDamageFactor());
		final CreatureAttack attack = new CreatureAttack(attackBonus, damage);

		return Conversions.round(attack.expectedDamage(target.getAC()));
	}
}
