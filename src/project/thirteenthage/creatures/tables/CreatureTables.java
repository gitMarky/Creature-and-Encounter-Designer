package project.thirteenthage.creatures.tables;

import java.util.ArrayList;
import java.util.List;

import project.thirteenthage.creatures.internal.CreatureSize;

public class CreatureTables
{
	private static final List<CreatureTableRow> TABLE_NORMAL = new ArrayList<CreatureTableRow>();
	private static final List<CreatureTableRow> TABLE_LARGE = new ArrayList<CreatureTableRow>();
	private static final List<CreatureTableRow> TABLE_HUGE = new ArrayList<CreatureTableRow>();

	public static List<CreatureTableRow> bySize(final CreatureSize size)
	{
		switch (size)
		{
			case NORMAL: return normal();
			case LARGE: return large();
			case HUGE: return huge();
			default: 
				throw new IllegalArgumentException("Unsupported size: " + size);
		}
	}
	
	public static List<CreatureTableRow> normal()
	{
		return TABLE_NORMAL;
	}
	
	public static List<CreatureTableRow> large()
	{
		return TABLE_LARGE;
	}
	
	public static List<CreatureTableRow> huge()
	{
		return TABLE_HUGE;
	}
}
