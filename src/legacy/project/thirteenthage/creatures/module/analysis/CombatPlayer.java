package legacy.project.thirteenthage.creatures.module.analysis;

import legacy.project.thirteenthage.creatures.internal.conversions.Conversions;
import legacy.project.thirteenthage.creatures.internal.interfaces.ICreature;
import legacy.project.thirteenthage.creatures.player.PlayerCharacter;
import legacy.project.thirteenthage.creatures.player.comparison.PlayerAttack;

public class CombatPlayer extends AbstractCombattant
{
	public CombatPlayer(final ICreature creature)
	{
		super(creature);
	}


	@Override
	public int getDamage(final ICreature target, final int escalationDie, final AnalysisMode mode)
	{
		final int attackBonus = ((PlayerCharacter) getCreature()).getAttackBonus();
		double damage = getCreature().getStrikeDamage();

		final boolean withPowers = mode == AnalysisMode.AVERAGE;
		damage *= withPowers ? 1.5 : 1.0; // multiply with 1.5 for // powers

		final PlayerAttack attack = new PlayerAttack(attackBonus + escalationDie, damage, getCreature().getLevel());

		return Conversions.round(attack.expectedDamage(target.getAC()));
	}
}
