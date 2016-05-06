package project.thirteenthage.creatures.tables;

/**
 * Creatue table row.
 * 
 * Fear threshold not included, because this is always the normal size monster HP/3;
 * Attack bonus not included because this is always level +5.
 * PD bonus not included because this is always level +14.
 * MD bonus not included because this is always level +10.
 */
public class CreatureTableRow
{
	private final double _strikeDamage;
	private final double _hp;
	private final int _ac;
	private final int _defenseBetter;
	private final int _defenseLesser;
	private final double _fear;
	
	public CreatureTableRow(double strikeDamage, double hp, int ac, int defenseBetter, int defenseLesser, final double fear)
	{
		_strikeDamage = strikeDamage;
		_hp = hp;
		_ac = ac;
		_defenseBetter = defenseBetter;
		_defenseLesser = defenseLesser;
		_fear = fear;
	}

	public double getHP()
	{
		return _hp;
	}

	public int getAC()
	{
		return _ac;
	}

	public int getDefenseBetter()
	{
		return _defenseBetter;
	}

	public int getDefenseLesser()
	{
		return _defenseLesser;
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
