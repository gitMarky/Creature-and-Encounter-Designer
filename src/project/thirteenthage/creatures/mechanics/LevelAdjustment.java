package project.thirteenthage.creatures.mechanics;

import project.thirteenthage.creatures.internal.conversions.Conversions;
import project.thirteenthage.creatures.internal.gui.CreatureGui;
import project.thirteenthage.creatures.internal.interfaces.ILevelAdjustment;

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

	private static boolean _calculateOriginalValue = true;


	private LevelAdjustment()
	{
		// hide public constructor, this is a utility class
	}


	public static double getLevelAdjustmentFine(final int attack, final int ac, final int pd, final int md, final double hp)
	{
		double adjustment = 0.0;

		adjustment += getInstance().valueOfAttack(attack);
		adjustment += getInstance().valueOfDefense(ac);
		adjustment += getInstance().valueOfDefense(pd);
		adjustment += getInstance().valueOfDefense(md);
		adjustment += getInstance().valueOfHP(hp);

		return adjustment;
	}


	public static int getLevelAdjustment(final int attack, final int ac, final int pd, final int md, final double hp)
	{
		return Conversions.round(getLevelAdjustmentFine(attack, ac, pd, md, hp));
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


	private static boolean useOriginalCalculation()
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
