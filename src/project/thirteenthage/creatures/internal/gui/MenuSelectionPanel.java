package project.thirteenthage.creatures.internal.gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import project.thirteenthage.creatures.loaders.CreatureTemplateLoader;

@SuppressWarnings("serial")
class MenuSelectionPanel extends JPanel
{
	private final JComboBox<MenuSelectionItem> _creatureList;


	MenuSelectionPanel()
	{
		_creatureList = new JComboBox<MenuSelectionItem>();
		_creatureList.addItemListener(new MenuSelectionListener());

		for (final String item : CreatureTemplateLoader.getInstance().getTemplates().keySet())
		{
			MenuSelectionItem menuSelectionItem = new MenuSelectionItem(item);
			_creatureList.addItem(menuSelectionItem);
		}

		this.add(_creatureList);
	}

	private class MenuSelectionItem
	{
		private final String _id;


		private MenuSelectionItem(final String id)
		{
			_id = id;
		}


		@Override
		public String toString()
		{
			return CreatureTemplateLoader.getInstance().get(_id).getName();
		}
	}

	private class MenuSelectionListener implements ItemListener
	{

		@Override
		public void itemStateChanged(ItemEvent e)
		{
			if (e.getStateChange() == ItemEvent.SELECTED)
			{
				onCreatureSelected();
			}
		}
	}


	void onCreatureSelected()
	{
		MenuSelectionItem selectedItem = (MenuSelectionItem) _creatureList.getSelectedItem();

		if (CreatureGui.GUI != null && selectedItem != null)
		{
			CreatureGui.GUI.getCreaturePanel().displayCreature(CreatureTemplateLoader.getInstance().get(selectedItem._id));
			CreatureGui.GUI.updateView();
		}
	}
}