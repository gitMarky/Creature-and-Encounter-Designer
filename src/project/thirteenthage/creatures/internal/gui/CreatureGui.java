package project.thirteenthage.creatures.internal.gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import project.thirteenthage.creatures.interfaces.IView;
import project.thirteenthage.creatures.internal.gui.views.CreatureViewPanel;
import project.thirteenthage.creatures.internal.interfaces.ICreatureTemplate;
import project.thirteenthage.creatures.loaders.AttackTemplateLoader;
import project.thirteenthage.creatures.loaders.CreatureTemplateLoader;

public class CreatureGui implements IView
{
	private static CreatureGui GUI;
	
	private final JFrame _frame;
	private final MenuSelectionPanel _menuSelectionPanel;
	private final CreaturePanel _creaturePanel;
	
	public static void main(String[] args)
	{
		GUI = new CreatureGui();
		GUI.getMenuSelectionPanel().onCreatureSelected();
	}
	
	

	private CreatureGui()
	{
		AttackTemplateLoader.getInstance();
		CreatureTemplateLoader.getInstance();
		
		final JFrame frame = new JFrame();
		final JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		_menuSelectionPanel = new MenuSelectionPanel();
		_creaturePanel = new CreaturePanel();
		panel.add(_menuSelectionPanel);
		panel.add(_creaturePanel);
		panel.add(new EncounterPanel());

		frame.add(panel);
		//frame.pack();
		//frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		_frame = frame;
	}
	


	@Override
	public void updateView()
	{
		//SwingUtilities.updateComponentTreeUI(_frame);
		//_frame.doLayout();
		//_frame.revalidate();
		_frame.pack();
		_frame.setVisible(true);
	}
	
	public MenuSelectionPanel getMenuSelectionPanel(){ return _menuSelectionPanel;}
	public CreaturePanel getCreaturePanel(){ return _creaturePanel;};
	
	@SuppressWarnings("serial")
	private class MenuSelectionPanel extends JPanel
	{
		private final JComboBox<MenuSelectionItem> _creatureList;
		
		private MenuSelectionPanel()
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

		private void onCreatureSelected()
		{
			MenuSelectionItem selectedItem = (MenuSelectionItem) _creatureList.getSelectedItem();

			if (GUI != null && selectedItem != null)
			{
				GUI.getCreaturePanel().displayCreature(CreatureTemplateLoader.getInstance().get(selectedItem._id));
				GUI.updateView();
			}
		}
	}
	
	@SuppressWarnings("serial") class CreaturePanel extends JPanel
	{
		private CreatureViewPanel _panel = null;
		
		private CreaturePanel()
		{
			super();
		}
		

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
	
	@SuppressWarnings("serial")
	private class EncounterPanel extends JPanel
	{
	}
}
