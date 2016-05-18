package project.thirteenthage.creatures.internal.gui;

import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;

import project.thirteenthage.creatures.interfaces.IView;
import project.thirteenthage.creatures.internal.Constants;
import project.thirteenthage.creatures.loaders.AttackTemplateLoader;
import project.thirteenthage.creatures.loaders.CreatureLoader;
import project.thirteenthage.creatures.loaders.CreatureTemplateLoader;
import project.thirteenthage.creatures.loaders.LoaderHelper;
import project.thirteenthage.creatures.loaders.SpecialTemplateLoader;

/**
 * This is the main application.
 */
public class CreatureGui implements IView
{
	public static CreatureGui GUI;

	private final JFrame _frame;
	private final MenuSelectionPanel _menuSelectionPanel;
	private final CreaturePanel _creaturePanel;
	private final EncounterPanel _encounterPanel;
	private final JFileChooser _fileChooser = new JFileChooser();


	public static void main(String[] args)
	{
		GUI = new CreatureGui();
		GUI.getMenuSelectionPanel().onCreatureSelected();
	}


	private CreatureGui()
	{
		AttackTemplateLoader.getInstance();
		CreatureTemplateLoader.getInstance();
		CreatureLoader.getInstance().load(CreatureTemplateLoader.getInstance());
		SpecialTemplateLoader.getInstance();
		
		final JFrame frame = new JFrame();
		final JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(StyleConstants.DEFAULT_EMPTY_BORDER);

		_menuSelectionPanel = new MenuSelectionPanel();
		_creaturePanel = new CreaturePanel();
		_encounterPanel = new EncounterPanel();
		panel.add(_menuSelectionPanel);
		panel.add(_creaturePanel);
		panel.add(_encounterPanel);
		
		final FileFilter fileFilter = new javax.swing.filechooser.FileFilter()
		{
			@Override
			public boolean accept(File file)
			{
				return file.isDirectory() || file.getName().toLowerCase().endsWith(LoaderHelper.EXTENSION_XML);
			}

			@Override
			public String getDescription()
			{
				return "xml files";
			}
		};
		
		_fileChooser.setCurrentDirectory(Constants.RESOURCES);
		_fileChooser.setFileFilter(fileFilter);

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
	 * 
	 * @return the panel.
	 */
	public MenuSelectionPanel getMenuSelectionPanel()
	{
		return _menuSelectionPanel;
	}


	/**
	 * Getter for the panel that displays the currently selected creature.
	 * 
	 * @return
	 */
	public CreaturePanel getCreaturePanel()
	{
		return _creaturePanel;
	}


	/**
	 * Getter for the panels that displays all the creatures.
	 */
	public EncounterPanel getEncounterPanel()
	{
		return _encounterPanel;
	}
	
	
	/**
	 * Getter for a file chooser.
	 */
	public JFileChooser getFileChooser()
	{
		return _fileChooser;
	}
}
