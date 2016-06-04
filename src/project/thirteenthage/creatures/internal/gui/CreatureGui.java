package project.thirteenthage.creatures.internal.gui;

import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;

import project.thirteenthage.creatures.interfaces.IView;
import project.thirteenthage.creatures.internal.ApplicationLogger;
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
	private final JFileChooser _fileChooserXml = new JFileChooser();
	private final JFileChooser _fileChooserHtml = new JFileChooser();


	public static void main(final String[] args)
	{
		ApplicationLogger.getLogger().info(Constants.HLINE);
		ApplicationLogger.getLogger().info("Initializing");
		ApplicationLogger.getLogger().info(Constants.HLINE);
		
		GUI = new CreatureGui();
		
		ApplicationLogger.getLogger().info(Constants.HLINE);
		ApplicationLogger.getLogger().info("Setup");
		ApplicationLogger.getLogger().info(Constants.HLINE);

		GUI.getMenuSelectionPanel().onCreatureSelected();
		
		ApplicationLogger.getLogger().info(Constants.HLINE);
		ApplicationLogger.getLogger().info("Preparation phase is over, from here on the user takes over.");
		ApplicationLogger.getLogger().info(Constants.HLINE);
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

		final FileFilter fileFilterXml = new javax.swing.filechooser.FileFilter()
		{
			@Override
			public boolean accept(final File file)
			{
				if (file == null)
				{
					throw new IllegalArgumentException("Parameter 'file' must not be null.");
				}

				return file.isDirectory() || file.getName().toLowerCase().endsWith(LoaderHelper.EXTENSION_XML);
			}


			@Override
			public String getDescription()
			{
				return "xml files";
			}
		};

		_fileChooserXml.setCurrentDirectory(Constants.RESOURCES);
		_fileChooserXml.setFileFilter(fileFilterXml);

		final FileFilter fileFilterHtml = new javax.swing.filechooser.FileFilter()
		{
			@Override
			public boolean accept(final File file)
			{
				if (file == null)
				{
					throw new IllegalArgumentException("Parameter 'file' must not be null.");
				}
				
				return file.isDirectory() || file.getName().toLowerCase().endsWith(LoaderHelper.EXTENSION_HTML);
			}


			@Override
			public String getDescription()
			{
				return "html files";
			}
		};
		
		_fileChooserHtml.setCurrentDirectory(Constants.RESOURCES);
		_fileChooserHtml.setFileFilter(fileFilterHtml);

		frame.add(panel);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		_frame = frame;
	}


	@Override
	public void updateView()
	{
		ApplicationLogger.getLogger().info("Update view");
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
	 * Getter for a xml file chooser.
	 */
	public JFileChooser getFileChooser()
	{
		return _fileChooserXml;
	}


	/**
	 * Getter for a html file chooser.
	 */
	public JFileChooser getHtmlFileChooser()
	{
		return _fileChooserHtml;
	}
}
