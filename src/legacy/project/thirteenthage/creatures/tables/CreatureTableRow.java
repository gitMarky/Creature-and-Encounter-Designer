package legacy.project.thirteenthage.creatures.tables;

/**
 * Creatue table row.
 * 
 * Fear threshold not included, because this is always the normal size monster
 * HP/3; Attack bonus not included because this is always level +5. PD bonus not
 * included because this is always level +14. MD bonus not included because this
 * is always level +10.
 */
public class CreatureTableRow
{
	private final double _strikeDamage;
	private final double _hp;
	private final double _fear;


	public CreatureTableRow(final double strikeDamage, final double hp, final double fear)
	{
		_strikeDamage = strikeDamage;
		_hp = hp;
		_fear = fear;
	}


	public double getHP()
	{
		return _hp;
	}


	public double getStrikeDamage()
	{
		return _strikeDamage;
	}


	public double getFearThreshold()
	{
		return _fear;
	}
}
