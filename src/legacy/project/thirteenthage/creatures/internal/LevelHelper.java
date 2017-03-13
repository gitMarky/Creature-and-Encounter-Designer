package legacy.project.thirteenthage.creatures.internal;

public final class LevelHelper
{
	private LevelHelper()
	{
		
	}
	
	
	public static String getLevelText(final int level)
	{
		return ith(level) + " level";
	}


	private static String ith(final int level)
	{
		if (level == 11 || level == 12 || level == 13) return level + "th";

		switch (level % 10)
		{
			case 1:
				return level + "st";
			case 2:
				return level + "nd";
			case 3:
				return level + "rd";
			default:
				return level + "th";
		}
	}
}
