package project.thirteenthage.creatures.internal;

public enum CreatureSize
{
	NORMAL,
	LARGE,
	HUGE;
	
	public static CreatureSize fromString(final String name)
	{
		for (final CreatureSize size : CreatureSize.values())
		{
			if (size.name().equalsIgnoreCase(name))
			{
				return size;
			}
		}
		
		throw new IllegalArgumentException("No enum for " + name);
	}
}
