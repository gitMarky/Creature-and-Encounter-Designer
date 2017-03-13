package legacy.project.thirteenthage.creatures.internal.interfaces;

public interface ISpecial extends INamedItem, IDisplayableInGui
{
	String getDescription();


	/**
	 * Gets an addition to the level adjustment.
	 * 
	 * @return the modifier.
	 */
	double getLevelAdjustment();
}
