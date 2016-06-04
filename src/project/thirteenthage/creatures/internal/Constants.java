package project.thirteenthage.creatures.internal;

import java.io.File;

public class Constants
{
	public static File RESOURCES = new File("resources");

	public static String NEWLINE = "\n";

	public static final int MIN_LEVEL = 0;

	public static final int MAX_LEVEL = 20;

	public static final int MIN_PLAYER_LEVEL = 1;

	public static final int MAX_PLAYER_LEVEL = 10;

	public static final int MIN_PLAYER_AMOUNT = 1;

	public static final int MAX_PLAYER_AMOUNT = 10;

	public static final int MIN_STAT_MODIFIER = -6;

	public static final int MAX_STAT_MODIFIER = +6;

	public static final int MIN_HP_MODIFIER = -50;

	public static final int MAX_HP_MODIFIER = 200;

	public static final String TEMPLATE_CREATURE_NAME = "creature";

	public static final String TEMPLATE_CREATURE_OF_TYPE = "creature of this type";

	public static final double TEMPLATE_CREATURE_DAMAGE = 100.0;

	public static final int MIN_INITIATIVE = -1;

	public static final int MAX_INITIATIVE = +9;

	public static final int MAX_MOOK_POOL_SIZE = 5;
	
	public static final String HLINE = "==========================================================";
}
