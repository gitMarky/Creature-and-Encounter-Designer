package legacy.project.thirteenthage.creatures.internal.gui.views;

import javax.swing.JLabel;

import project.library.marky.html.Html;
import legacy.project.thirteenthage.creatures.internal.gui.StyleConstants;
import legacy.project.thirteenthage.creatures.internal.interfaces.IAttack;

@SuppressWarnings("serial")
public class AttackViewLabel extends JLabel
{
	public AttackViewLabel(final IAttack attack)
	{
		super();
		setBorder(StyleConstants.DEFAULT_EMPTY_BORDER);
		displayAttack(attack);
	}


	private void displayAttack(final IAttack attack)
	{
		if (attack == null)
		{
			throw new IllegalArgumentException("Parameter 'attack' must not be null.");
		}

		final StringBuilder guiText = new StringBuilder(Html.BEGIN);
		guiText.append(attack.toGuiText());
		guiText.append(Html.END);
		setText(guiText.toString());
	}
}
