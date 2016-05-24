package project.thirteenthage.creatures.player.comparison;

public class CreatureAttack extends AbstractEntityAttack
{
	public CreatureAttack(final int attackBonus, final double strikeDamage)
	{
		super(attackBonus, strikeDamage);
	}

	
	@Override
	protected double getMissDamage()
	{
		return 0;
	}
}
