package project.thirteenthage.creatures.internal.gui;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import project.thirteenthage.creatures.interfaces.IView;
import project.thirteenthage.creatures.loaders.AttackTemplateLoader;
import project.thirteenthage.creatures.loaders.CreatureTemplateLoader;

public class CreatureGui implements IView
{
	static CreatureGui GUI;

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
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		_frame = frame;
	}


	@Override
	public void updateView()
	{
		_frame.pack();
		_frame.setVisible(true);
	}


	public MenuSelectionPanel getMenuSelectionPanel()
	{
		return _menuSelectionPanel;
	}


	public CreaturePanel getCreaturePanel()
	{
		return _creaturePanel;
	}
}
