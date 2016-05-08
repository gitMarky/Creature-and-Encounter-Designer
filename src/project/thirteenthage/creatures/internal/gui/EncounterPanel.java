package project.thirteenthage.creatures.internal.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import project.thirteenthage.creatures.interfaces.IView;
import project.thirteenthage.creatures.internal.gui.views.CreatureViewPanel;
import project.thirteenthage.creatures.internal.interfaces.ICreature;

/**
 * This panel should be able to contain several creatures that were selected for
 * an encounter. It should also contain a encounter difficulty summary.
 */
@SuppressWarnings("serial")
class EncounterPanel extends JPanel implements IView, ActionListener
{
	private JPanel _buttonPanel = new JPanel();
	private JPanel _creatureListPanel = new JPanel();
	private JLabel _creatureListEmpty = new JLabel("No creatures were added to the encounter yet");
	
	private JButton _clearButton = new JButton("Clear");

	private Map<ICreature, CreatureViewPanel> _creatures = new HashMap<ICreature, CreatureViewPanel>();


	EncounterPanel()
	{
		// set up creature panel
		_creatureListPanel.setBorder(BorderFactory.createTitledBorder("Creatures"));
		_creatureListPanel.setLayout(new BoxLayout(_creatureListPanel, BoxLayout.Y_AXIS));
		_creatureListPanel.setAutoscrolls(true);

		clearCreatures();
		
		// set up buttons
		_clearButton.addActionListener(this);
		_buttonPanel.add(_clearButton);

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(_buttonPanel);
		this.add(_creatureListPanel);
		this.setBorder(BorderFactory.createTitledBorder("Encounter"));
	}
	
	
	private void clearCreatures()
	{
		_creatureListPanel.removeAll();
		_creatures.clear();
		
		_creatureListEmpty.setVisible(true);
		_creatureListPanel.add(_creatureListEmpty);
	}


	public void addCreature(ICreature creature)
	{
		if (!_creatures.containsKey(creature))
		{
			final CreatureViewPanel panel = new CreatureViewPanel(creature);
			_creatures.put(creature, panel);
			_creatureListPanel.add(panel);
			_creatureListEmpty.setVisible(false);
			updateView();
		}
	}


	@Override
	public void updateView()
	{
		CreatureGui.GUI.updateView();
	}


	@Override
	public void actionPerformed(ActionEvent event)
	{
		if (event.getSource() == _clearButton)
		{
			clearCreatures();
			updateView();
		}
	}
}