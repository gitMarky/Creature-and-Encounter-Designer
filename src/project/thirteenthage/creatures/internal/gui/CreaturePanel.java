package project.thirteenthage.creatures.internal.gui;

import javax.swing.JPanel;

import project.thirteenthage.creatures.internal.gui.views.CreatureViewPanel;
import project.thirteenthage.creatures.internal.interfaces.ICreatureTemplate;


/**
 * The creature panel should display the currently selected creature.
 * For this purpose it hosts a {@link CreatureViewPanel}.
 */
@SuppressWarnings("serial")
class CreaturePanel extends JPanel
{
	private CreatureViewPanel _panel = null;


	CreaturePanel()
	{
		super();
	}


	/**
	 * Displays a creature in this panel.
	 * @param creature the creature.
	 */
	public void displayCreature(ICreatureTemplate creature)
	{
		if (_panel != null)
		{
			this.remove(_panel);
		}

		_panel = new CreatureViewPanel(creature.toCreature());
		this.add(_panel);
	}
}