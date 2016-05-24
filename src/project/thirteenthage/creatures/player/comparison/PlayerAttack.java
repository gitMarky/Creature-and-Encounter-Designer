package project.thirteenthage.creatures.player.comparison;

public class PlayerAttack extends AbstractEntityAttack
{
	final int _level;
	
	public PlayerAttack(final int attackBonus, final double strikeDamage, final int level)
	{
		super(attackBonus, strikeDamage);
		_level = level;
	}
	
	
	@Override
	protected double getMissDamage()
	{
		return _level;
	}
}
