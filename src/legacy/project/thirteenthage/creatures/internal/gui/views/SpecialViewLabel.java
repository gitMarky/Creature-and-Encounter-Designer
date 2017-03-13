package legacy.project.thirteenthage.creatures.internal.gui.views;

import javax.swing.JLabel;

import project.library.marky.html.Html;
import legacy.project.thirteenthage.creatures.internal.gui.StyleConstants;
import legacy.project.thirteenthage.creatures.internal.interfaces.ISpecial;

@SuppressWarnings("serial")
public class SpecialViewLabel extends JLabel
{
	SpecialViewLabel(final ISpecial special)
	{
		super();
		setBorder(StyleConstants.DEFAULT_EMPTY_BORDER);
		displaySpecial(special);
	}


	private void displaySpecial(final ISpecial special)
	{
		if (special == null)
		{
			throw new IllegalArgumentException("Parameter 'special' must not be null.");
		}

		final StringBuilder guiText = new StringBuilder(Html.BEGIN);
		guiText.append(special.toGuiText());
		guiText.append(Html.END);
		setText(guiText.toString());
	}
}
