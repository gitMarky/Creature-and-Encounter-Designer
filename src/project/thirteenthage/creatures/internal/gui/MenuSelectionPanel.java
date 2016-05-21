package project.thirteenthage.creatures.internal.gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import project.thirteenthage.creatures.interfaces.IView;
import project.thirteenthage.creatures.loaders.CreatureLoader;
import project.thirteenthage.creatures.loaders.CreatureTemplateLoader;

/**
 * This panel contains a combo box that lets you select the loaded creatures.
 */
@SuppressWarnings("serial")
class MenuSelectionPanel extends JPanel implements IView
{
	private final JComboBox<MenuSelectionItem> _creatureList;


	MenuSelectionPanel()
	{
		_creatureList = new JComboBox<MenuSelectionItem>();
		_creatureList.addItemListener(new MenuSelectionListener());

		updateSelectionList();

		this.add(_creatureList);
	}


	@Override
	public void updateView()
	{
		updateSelectionList();
	}


	private void updateSelectionList()
	{
		_creatureList.removeAllItems();
		for (final String item : CreatureTemplateLoader.getInstance().getTemplates().keySet())
		{
			final MenuSelectionItem menuSelectionItem = new MenuSelectionItem(item);
			_creatureList.addItem(menuSelectionItem);
		}
	}


	void onCreatureSelected()
	{
		final MenuSelectionItem selectedItem = (MenuSelectionItem) _creatureList.getSelectedItem();

		if (CreatureGui.GUI != null && selectedItem != null)
		{
			CreatureGui.GUI.getCreaturePanel().displayCreature(CreatureLoader.getInstance().getCreatures().get(selectedItem._id));
			CreatureGui.GUI.updateView();
		}
	}

	/**
	 * Maps the creature template to the creature id, so that the selection list
	 * can display the creature name (which may not be unique).
	 */
	private class MenuSelectionItem
	{
		private final String _id;


		private MenuSelectionItem(final String id)
		{
			if (id == null)
			{
				throw new IllegalArgumentException("Parameter 'id' must not be null.");
			}
			
			_id = id;
		}


		@Override
		public String toString()
		{
			return CreatureTemplateLoader.getInstance().get(_id).getName();
		}
	}

	/**
	 * Listener so that detects when a new item is selected.
	 */
	private class MenuSelectionListener implements ItemListener
	{
		@Override
		public void itemStateChanged(final ItemEvent e)
		{
			if (e == null)
			{
				throw new IllegalArgumentException("Parameter 'e' must not be null.");
			}
			
			if (e.getStateChange() == ItemEvent.SELECTED)
			{
				onCreatureSelected();
			}
		}
	}
}