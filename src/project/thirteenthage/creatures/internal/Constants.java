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

	public static final int MIN_STAT_MODIFIER = -6;

	public static final int MAX_STAT_MODIFIER = +6;

	public static final int MIN_HP_MODIFIER = -50;

	public static final int MAX_HP_MODIFIER = 100;

	public static final String TEMPLATE_CREATURE_NAME = "creature";

	public static final String TEMPLATE_CREATURE_OF_TYPE = "creature of this type";

	public static final double TEMPLATE_CREATURE_DAMAGE = 100.0;
}
