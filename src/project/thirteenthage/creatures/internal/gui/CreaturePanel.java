package project.thirteenthage.creatures.internal.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import project.thirteenthage.creatures.internal.gui.views.CreatureViewPanel;
import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.internal.interfaces.ICreatureTemplate;
//property change stuff


/**
 * The creature panel should display the currently selected creature.
 * For this purpose it hosts a {@link CreatureViewPanel}.
 */
@SuppressWarnings("serial")
class CreaturePanel extends JPanel implements ActionListener
{
	private CreatureViewPanel _panel = null;
	private final JButton _addButton = new JButton("Add to encounter");
	private ICreature _selectedCreature = null;


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

		_selectedCreature = creature.toCreature();
		_panel = new CreatureViewPanel(_selectedCreature);
		JPanel innerPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
		innerPanel.add(_panel);
		innerPanel.add(buttonPanel);
		
		buttonPanel.add(_addButton);
		_addButton.addActionListener(this);
		
		this.add(innerPanel);
		this.setBorder(BorderFactory.createTitledBorder("Selected creature"));
	}

	
	@Override
	public void actionPerformed(ActionEvent event)
	{
		if (event.getSource() == _addButton)
		{
			transferCreatureToEncounter(_selectedCreature);
		}
	}

	private void transferCreatureToEncounter(ICreature creature)
	{
		CreatureGui.GUI.getEncounterPanel().addCreature(creature);
	}
}