package project.thirteenthage.creatures.internal.interfaces;

import javax.swing.JLabel;

public interface IDisplayableInGui
{

	/**
	 * Converts the information of this attack to a text as it would be
	 * displayed in a {@link JLabel}.
	 * 
	 * @return the string.
	 */
	public abstract String toGuiText();

}