package project.thirteenthage.creatures.internal.gui;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import project.thirteenthage.creatures.interfaces.IView;
import project.thirteenthage.creatures.loaders.AttackTemplateLoader;
import project.thirteenthage.creatures.loaders.CreatureTemplateLoader;


/**
 * This is the main application.
 */
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
        panel.setBorder(StyleConstants.DEFAULT_EMPTY_BORDER);


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


	/**
	 * The selection panel. Allows selecting a creature.
	 * @return the panel.
	 */
	public MenuSelectionPanel getMenuSelectionPanel()
	{
		return _menuSelectionPanel;
	}

	
	/**
	 * Getter for the panel that displays the currently selected creature.
	 * @return
	 */
	public CreaturePanel getCreaturePanel()
	{
		return _creaturePanel;
	}
}
