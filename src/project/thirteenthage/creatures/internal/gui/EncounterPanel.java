package project.thirteenthage.creatures.internal.gui;

import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import project.thirteenthage.creatures.interfaces.IView;
import project.thirteenthage.creatures.internal.gui.views.CreatureViewPanel;
import project.thirteenthage.creatures.internal.interfaces.ICreature;

/**
 * This panel should be able to contain several creatures that were selected for
 * an encounter. It should also contain a encounter difficulty summary.
 */
@SuppressWarnings("serial")
class EncounterPanel extends JPanel implements IView
{
	private JPanel _buttonPanel = new JPanel();
	private JPanel _creatureListPanel = new JPanel();
	
	private Map<ICreature, CreatureViewPanel> _creatures = new HashMap<ICreature, CreatureViewPanel>();

	EncounterPanel()
	{
		_creatureListPanel.setBorder(BorderFactory.createTitledBorder(""));
		_creatureListPanel.setLayout(new BoxLayout(_creatureListPanel, BoxLayout.Y_AXIS));
		_creatureListPanel.setAutoscrolls(true);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(_buttonPanel);
		this.add(_creatureListPanel);
		this.setBorder(BorderFactory.createTitledBorder("Encounter"));
	}

	public void addCreature(ICreature creature)
	{
		// Nothing
		System.out.println("Adding " + creature.getName() + " to encounter");
		
		// action
		if (!_creatures.containsKey(creature))
		{
			final CreatureViewPanel panel = new CreatureViewPanel(creature);
			_creatures.put(creature, panel);
			_creatureListPanel.add(panel);
			updateView();
		}
	}

	@Override
	public void updateView()
	{
		CreatureGui.GUI.updateView();
	}
}