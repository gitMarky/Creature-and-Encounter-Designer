package legacy.project.thirteenthage.creatures.mechanics;

import java.util.List;

import legacy.project.thirteenthage.creatures.internal.gui.CreatureGui;
import legacy.project.thirteenthage.creatures.internal.interfaces.IAttack;
import legacy.project.thirteenthage.creatures.internal.interfaces.ILevelAdjustment;
import legacy.project.thirteenthage.creatures.internal.interfaces.ISpecial;

/**
 * <p>
 * Adds a modifier to the level of a creature, based on how much it deviates
 * from the default stats of such a creature.
 * </p>
 * <p>
 * This modifier is added to the level of the creature, for the purpose of
 * calculating the difficulty.
 * </p>
 */
public final class LevelAdjustment
{
	private static ILevelAdjustment _original = new LevelAdjustmentOriginal();
	private static ILevelAdjustment _alternate = new LevelAdjustmentAlternate();

	static boolean _calculateOriginalValue = false;


	private LevelAdjustment()
	{
		// hide public constructor, this is a utility class
	}


	public static double getLevelAdjustmentFine(final int attack, final int ac, final int pd, final int md, final double hp, final double damage, final List<IAttack> attacks, final List<ISpecial> specials, final List<ISpecial> nastier)
	{
		return getInstance().getLevelAdjustmentFine(attack, ac, pd, md, hp, damage, attacks, specials, nastier);
	}


	public static int getLevelAdjustment(final int attack, final int ac, final int pd, final int md, final double hp, final double damage, final List<IAttack> attacks, final List<ISpecial> specials, final List<ISpecial> nastier)
	{
		return getInstance().getLevelAdjustment(attack, ac, pd, md, hp, damage, attacks, specials, nastier);
	}


	public static void setUseOriginalCalculation(final boolean use)
	{
		_calculateOriginalValue = use;

		if (CreatureGui.GUI != null)
		{
			CreatureGui.GUI.getCreaturePanel().updateView();
			CreatureGui.GUI.getEncounterPanel().updateView();
		}
	}


	public static boolean useOriginalCalculation()
	{
		return _calculateOriginalValue;
	}


	private static ILevelAdjustment getInstance()
	{
		if (useOriginalCalculation())
		{
			return _original;
		}
		else
		{
			return _alternate;
		}
	}
}
