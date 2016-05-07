package project.thirteenthage.creatures.internal.interfaces;

import javax.swing.JLabel;

public interface IAttack extends INamedItem
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


	/**
	 * Converts the information of this attack to a text as it would be
	 * displayed in a {@link JLabel}.
	 * 
	 * @return the string.
	 */
	String toGuiText();
}
