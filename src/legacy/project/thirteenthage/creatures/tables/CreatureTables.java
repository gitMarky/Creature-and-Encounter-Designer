package legacy.project.thirteenthage.creatures.tables;

import java.util.ArrayList;
import java.util.List;

import legacy.project.thirteenthage.creatures.creature.CreatureSize;

public class CreatureTables
{
	private static final List<CreatureTableRow> TABLE_NORMAL = new ArrayList<CreatureTableRow>();
	private static final List<CreatureTableRow> TABLE_LARGE = new ArrayList<CreatureTableRow>();
	private static final List<CreatureTableRow> TABLE_HUGE = new ArrayList<CreatureTableRow>();


	public static List<CreatureTableRow> bySize(final CreatureSize size)
	{
		if (size == null)
		{
			throw new IllegalArgumentException("Parameter 'size' must not be null.");
		}

		switch (size)
		{
			case NORMAL:
				return normal();
			case LARGE:
				return large();
			case HUGE:
				return huge();
			default:
				throw new IllegalArgumentException("Unsupported size: " + size);
		}
	}


	public static List<CreatureTableRow> normal()
	{
		if (TABLE_NORMAL.isEmpty())
		{
			TABLE_NORMAL.add(0, new CreatureTableRow(4, 20, 7));
			TABLE_NORMAL.add(1, new CreatureTableRow(5, 27, 9));
			TABLE_NORMAL.add(2, new CreatureTableRow(7, 36, 12));
			TABLE_NORMAL.add(3, new CreatureTableRow(10, 45, 15));
			TABLE_NORMAL.add(4, new CreatureTableRow(14, 54, 18));
			TABLE_NORMAL.add(5, new CreatureTableRow(18, 72, 24));
			TABLE_NORMAL.add(6, new CreatureTableRow(21, 90, 30));
			TABLE_NORMAL.add(7, new CreatureTableRow(28, 108, 36));
			TABLE_NORMAL.add(8, new CreatureTableRow(38, 144, 48));
			TABLE_NORMAL.add(9, new CreatureTableRow(50, 180, 60));
			TABLE_NORMAL.add(10, new CreatureTableRow(58, 216, 72));
			TABLE_NORMAL.add(11, new CreatureTableRow(70, 288, 96));
			TABLE_NORMAL.add(12, new CreatureTableRow(90, 360, 120));
			TABLE_NORMAL.add(13, new CreatureTableRow(110, 432, 144));
			TABLE_NORMAL.add(14, new CreatureTableRow(135, 576, 192));
			TABLE_NORMAL.add(15, new CreatureTableRow(170, 720, 240));
			TABLE_NORMAL.add(16, new CreatureTableRow(220, 920, 306));
			TABLE_NORMAL.add(17, new CreatureTableRow(270, 1152, 384));
			TABLE_NORMAL.add(18, new CreatureTableRow(340, 1440, 480));
			TABLE_NORMAL.add(19, new CreatureTableRow(430, 1840, 615));
			TABLE_NORMAL.add(20, new CreatureTableRow(540, 2300, 765));
		}
		return TABLE_NORMAL;
	}


	public static List<CreatureTableRow> large()
	{
		if (TABLE_LARGE.isEmpty())
		{
			TABLE_LARGE.add(0, new CreatureTableRow(9, 41, 7));
			TABLE_LARGE.add(1, new CreatureTableRow(10, 54, 9));
			TABLE_LARGE.add(2, new CreatureTableRow(14, 72, 12));
			TABLE_LARGE.add(3, new CreatureTableRow(21, 90, 15));
			TABLE_LARGE.add(4, new CreatureTableRow(28, 108, 18));
			TABLE_LARGE.add(5, new CreatureTableRow(36, 144, 24));
			TABLE_LARGE.add(6, new CreatureTableRow(42, 180, 30));
			TABLE_LARGE.add(7, new CreatureTableRow(56, 216, 36));
			TABLE_LARGE.add(8, new CreatureTableRow(76, 288, 48));
			TABLE_LARGE.add(9, new CreatureTableRow(100, 360, 60));
			TABLE_LARGE.add(10, new CreatureTableRow(116, 432, 72));
			TABLE_LARGE.add(11, new CreatureTableRow(140, 576, 96));
			TABLE_LARGE.add(12, new CreatureTableRow(180, 720, 120));
			TABLE_LARGE.add(13, new CreatureTableRow(220, 864, 144));
			TABLE_LARGE.add(14, new CreatureTableRow(270, 1152, 192));
			TABLE_LARGE.add(15, new CreatureTableRow(340, 1440, 240));
			TABLE_LARGE.add(16, new CreatureTableRow(430, 1840, 306));
			TABLE_LARGE.add(17, new CreatureTableRow(540, 2300, 384));
			TABLE_LARGE.add(18, new CreatureTableRow(680, 2900, 480));
			TABLE_LARGE.add(19, new CreatureTableRow(870, 3700, 615));
			TABLE_LARGE.add(20, new CreatureTableRow(1100, 4600, 765));
		}
		return TABLE_LARGE;
	}


	public static List<CreatureTableRow> huge()
	{
		if (TABLE_HUGE.isEmpty())
		{
			TABLE_HUGE.add(0, new CreatureTableRow(12, 60, 7));
			TABLE_HUGE.add(1, new CreatureTableRow(15, 81, 9));
			TABLE_HUGE.add(2, new CreatureTableRow(21, 108, 12));
			TABLE_HUGE.add(3, new CreatureTableRow(30, 135, 15));
			TABLE_HUGE.add(4, new CreatureTableRow(42, 162, 18));
			TABLE_HUGE.add(5, new CreatureTableRow(54, 216, 24));
			TABLE_HUGE.add(6, new CreatureTableRow(63, 270, 30));
			TABLE_HUGE.add(7, new CreatureTableRow(84, 324, 36));
			TABLE_HUGE.add(8, new CreatureTableRow(114, 432, 48));
			TABLE_HUGE.add(9, new CreatureTableRow(150, 540, 60));
			TABLE_HUGE.add(10, new CreatureTableRow(174, 648, 72));
			TABLE_HUGE.add(11, new CreatureTableRow(210, 864, 96));
			TABLE_HUGE.add(12, new CreatureTableRow(270, 1080, 120));
			TABLE_HUGE.add(13, new CreatureTableRow(330, 1296, 144));
			TABLE_HUGE.add(14, new CreatureTableRow(405, 1728, 192));
			TABLE_HUGE.add(15, new CreatureTableRow(506, 2160, 240));
			TABLE_HUGE.add(16, new CreatureTableRow(648, 2764, 306));
			TABLE_HUGE.add(17, new CreatureTableRow(810, 3456, 384));
			TABLE_HUGE.add(18, new CreatureTableRow(1012, 4320, 480));
			TABLE_HUGE.add(19, new CreatureTableRow(1296, 5530, 615));
			TABLE_HUGE.add(20, new CreatureTableRow(1620, 6900, 765));
		}
		return TABLE_HUGE;
	}
}
