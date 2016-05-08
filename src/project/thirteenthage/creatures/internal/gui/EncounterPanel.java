package project.thirteenthage.creatures.internal.gui;

import javax.swing.JPanel;

import project.thirteenthage.creatures.internal.interfaces.ICreature;

/**
 * This panel should be able to contain several creatures that were selected for
 * an encounter. It should also contain a encounter difficulty summary.
 */
@SuppressWarnings("serial")
class EncounterPanel extends JPanel
{

	public void addCreature(ICreature creature)
	{
		// Nothing
		System.out.println("Adding " + creature.getName() + " to encounter");
	}
}