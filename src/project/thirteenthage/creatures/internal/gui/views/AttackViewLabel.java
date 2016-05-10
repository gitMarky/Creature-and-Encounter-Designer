package project.thirteenthage.creatures.internal.gui.views;

import javax.swing.JLabel;

import project.thirteenthage.creatures.interfaces.ITrigger;
import project.thirteenthage.creatures.internal.Html;
import project.thirteenthage.creatures.internal.gui.StyleConstants;
import project.thirteenthage.creatures.internal.interfaces.IAttack;

@SuppressWarnings("serial")
public class AttackViewLabel extends JLabel
{
	AttackViewLabel(final IAttack attack)
	{
		super();
		displayAttack(attack);
		setBorder(StyleConstants.DEFAULT_EMPTY_BORDER);
	}


	private void displayAttack(final IAttack attack)
	{
		final StringBuilder guiText = new StringBuilder(Html.BEGIN);
		guiText.append(attack.toHtmlText());

		for (final ITrigger trigger : attack.getTriggers())
		{
			guiText.append(Html.LINE_BREAK);
			guiText.append(trigger.toHtmlText());
		}

		guiText.append(Html.END);
		setText(guiText.toString());
	}
}
