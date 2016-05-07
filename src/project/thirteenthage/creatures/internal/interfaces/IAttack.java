package project.thirteenthage.creatures.internal.interfaces;


public interface IAttack extends INamedItem, IDisplayableInGui
{
	/**
	 * Gets the attack bonus that modifies the attack.
	 * 
	 * @return the bonus.
	 */
	int getAttackBonus();


	/**
	 * Multiplier for the damage.
	 * 
	 * @return the multiplier.
	 */
	double getDamageFactor();


	/**
	 * Gets the name of the defense.
	 * 
	 * @return the name.
	 */
	String getDefense();


	/**
	 * Gets the description of the attack.
	 * 
	 * @return the description.
	 */
	String getDescription();
}
