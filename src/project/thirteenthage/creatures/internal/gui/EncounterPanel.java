package project.thirteenthage.creatures.internal.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jdom2.Document;
import org.jdom2.Element;

import project.library.marky.logger.ApplicationLogger;
import project.marky.library.xml.BasicXmlFile;
import project.thirteenthage.creatures.interfaces.IView;
import project.thirteenthage.creatures.internal.Constants;
import project.thirteenthage.creatures.internal.HtmlExporter;
import project.thirteenthage.creatures.internal.gui.views.AmountChoicePanel;
import project.thirteenthage.creatures.internal.gui.views.CreatureEncounterPanel;
import project.thirteenthage.creatures.internal.gui.views.EncounterAnalysisView;
import project.thirteenthage.creatures.internal.gui.views.EncounterDifficultyView;
import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.loaders.CreatureLoader;
import project.thirteenthage.creatures.loaders.LoaderHelper;
import project.thirteenthage.creatures.mechanics.analysis.Encounter;
import project.thirteenthage.creatures.mechanics.analysis.EncounterAnalysis;
import project.thirteenthage.creatures.mechanics.analysis.EncounterDifficulty;

/**
 * This panel should be able to contain several creatures that were selected for
 * an encounter. It should also contain a encounter difficulty summary.
 */
@SuppressWarnings("serial")
public class EncounterPanel extends JPanel implements IView, ActionListener
{
	private static final String TOOLTIP_SAVEBUTTON_OK = "Saves the encounter as an .xml file, so that it can be loaded again later.";
	private static final String TOOLTIP_SAVEBUTTON_DISABLED = "Saving is disabled, because the encounter has no creatures";
	private static final String TOOLTIP_SAVEBUTTON_UNSAVED = "Saving is disabled, because the encounter has creatures that were not saved to a template yet. In case that you save the template you will still have to build the encounter again.";
	
	
	private final JPanel _buttonPanel = new JPanel();
	private final JPanel _creatureListPanel = new JPanel();
	private final JLabel _creatureListEmpty = new JLabel("No creatures were added to the encounter yet");
	private final EncounterDifficultyView _difficultyLabel = new EncounterDifficultyView();
	private final AmountChoicePanel _playerLevel = new AmountChoicePanel("Player level");
	private final AmountChoicePanel _playerAmount = new AmountChoicePanel("Player amount");
	private final EncounterAnalysisView _analysisLabel = new EncounterAnalysisView();

	private final JButton _clearButton = new JButton("Clear");
	private final JButton _saveButton = new JButton("Save as");
	private final JButton _loadButton = new JButton("Load");
	private final JButton _exportButton = new JButton("Export to html");

	private final Map<ICreature, CreatureEncounterPanel> _creatures = new HashMap<ICreature, CreatureEncounterPanel>();
	private EncounterDifficulty _difficulty = null;


	EncounterPanel()
	{
		// set up creature panel
		_creatureListPanel.setBorder(BorderFactory.createTitledBorder("Creatures"));
		_creatureListPanel.setLayout(new BoxLayout(_creatureListPanel, BoxLayout.Y_AXIS));
		_creatureListPanel.setAutoscrolls(true);

		_analysisLabel.setBorder(BorderFactory.createTitledBorder("Analysis"));

		clearCreatures();

		// set up buttons
		_clearButton.addActionListener(this);

		final JPanel innerButtonPanel = new JPanel();
		innerButtonPanel.setLayout(new BoxLayout(innerButtonPanel, BoxLayout.Y_AXIS));

		innerButtonPanel.add(_loadButton);
		innerButtonPanel.add(_clearButton);
		innerButtonPanel.add(_saveButton);
		innerButtonPanel.add(_exportButton);
		innerButtonPanel.add(_difficultyLabel);
		innerButtonPanel.add(_playerLevel);
		innerButtonPanel.add(_playerAmount);

		_loadButton.addActionListener(this);
		_saveButton.addActionListener(this);
		_exportButton.addActionListener(this);

		final JScrollPane analysisScrollBar = new JScrollPane(_analysisLabel);

		analysisScrollBar.setPreferredSize(new Dimension(StyleConstants.CREATURE_EDIT_PANEL_WIDTH, StyleConstants.CREATURE_VIEW_PANEL_HEIGHT));

		_buttonPanel.setLayout(new BoxLayout(_buttonPanel, BoxLayout.X_AXIS));
		_buttonPanel.add(innerButtonPanel);
		_buttonPanel.add(analysisScrollBar);

		_playerLevel.setUpdateView(this);
		_playerLevel.setBounds(Constants.MIN_PLAYER_LEVEL, Constants.MAX_PLAYER_LEVEL);

		_playerAmount.setUpdateView(this);
		_playerAmount.setBounds(Constants.MIN_PLAYER_AMOUNT, Constants.MAX_PLAYER_AMOUNT);

		final JScrollPane creatureScrollBar = new JScrollPane(_creatureListPanel);

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(_buttonPanel);
		this.add(creatureScrollBar);
		this.setBorder(BorderFactory.createTitledBorder("Encounter"));
	}


	private void clearCreatures()
	{
		_creatureListPanel.removeAll();
		_creatures.clear();

		_creatureListEmpty.setVisible(true);
		_creatureListPanel.add(_creatureListEmpty);
		updateView();
	}


	public void addCreature(final ICreature creature)
	{
		if (creature == null)
		{
			throw new IllegalArgumentException("Parameter 'creature' must not be null.");
		}

		if (!_creatures.containsKey(creature))
		{
			final CreatureEncounterPanel panel = new CreatureEncounterPanel(creature);
			_creatures.put(creature, panel);
			_creatureListPanel.add(panel);
			_creatureListEmpty.setVisible(false);
			updateView();
		}
	}


	public void removeCreature(final ICreature creature)
	{
		if (creature == null)
		{
			throw new IllegalArgumentException("Parameter 'creature' must not be null.");
		}

		if (_creatures.containsKey(creature))
		{
			_creatureListPanel.remove(_creatures.get(creature));
			_creatures.remove(creature);

			if (_creatures.isEmpty()) _creatureListEmpty.setVisible(true);
			updateView();
		}
	}


	@Override
	public void updateView()
	{
		ApplicationLogger.getLogger().info("Update view");

		for (final CreatureEncounterPanel panel : _creatures.values())
		{
			panel.updateView();
		}

		if (_creatures.isEmpty())
		{
			_difficultyLabel.displayDifficulty(Double.NaN);
			_analysisLabel.displayNothing();
			_clearButton.setEnabled(false);
			_saveButton.setEnabled(false);
			_exportButton.setEnabled(false);
			
			_saveButton.setToolTipText(TOOLTIP_SAVEBUTTON_DISABLED);
		}
		else
		{
			final Encounter encounter = new Encounter(_creatures);
			encounter.setPlayerLevel(_playerLevel.getAmount());
			encounter.setPlayerAmount(_playerAmount.getAmount());
			_difficulty = new EncounterDifficulty(encounter);
			_difficultyLabel.displayDifficulty(_difficulty.getEncounterDifficulty());

			final EncounterAnalysis analysis = new EncounterAnalysis(encounter);
			analysis.analyze();

			_analysisLabel.displayAnalysis(analysis);

			boolean canSave = true;
			for (final ICreature creature : _creatures.keySet())
			{
				String id = creature.getTemplate().getId();
				ApplicationLogger.getLogger().info("ID of creature in encounter: " + id);
				if (id == null)
				{
					canSave = false;
					ApplicationLogger.getLogger().info("At least one creature is not saved yet, prevent saving encounter");
				}
			}

			_clearButton.setEnabled(true);
			_saveButton.setEnabled(canSave);
			_exportButton.setEnabled(true);
			
			_saveButton.setToolTipText(canSave ? TOOLTIP_SAVEBUTTON_OK: TOOLTIP_SAVEBUTTON_UNSAVED);
		}

		if (CreatureGui.GUI != null) CreatureGui.GUI.updateView();
	}


	@Override
	public void actionPerformed(final ActionEvent event)
	{
		if (event.getSource() == _clearButton)
		{
			ApplicationLogger.getLogger().info("Pressed button: Clear encounter");
			clearCreatures();
			updateView();
		}
		
		if (event.getSource() == _loadButton)
		{
			ApplicationLogger.getLogger().info("Pressed button: Load encounter");
			final int choice = CreatureGui.GUI.getFileChooser().showOpenDialog(this);

			if (choice == JFileChooser.APPROVE_OPTION)
			{
				final File file = CreatureGui.GUI.getFileChooser().getSelectedFile();
				loadEncounter(file);
			}
		}

		if (event.getSource() == _saveButton)
		{
			ApplicationLogger.getLogger().info("Pressed button: Save encounter");
			final int choice = CreatureGui.GUI.getFileChooser().showSaveDialog(this);

			if (choice == JFileChooser.APPROVE_OPTION)
			{
				final File file = CreatureGui.GUI.getFileChooser().getSelectedFile();
				saveEncounter(file);
			}
		}

		if (event.getSource() == _exportButton)
		{
			ApplicationLogger.getLogger().info("Pressed button: Export encounter");
			final int choice = CreatureGui.GUI.getHtmlFileChooser().showSaveDialog(this);

			if (choice == JFileChooser.APPROVE_OPTION)
			{
				final File file = CreatureGui.GUI.getHtmlFileChooser().getSelectedFile();
				exportEncounter(file);
			}
		}
	}

	
	private void loadEncounter(final File targetFile)
	{
		if (targetFile == null)
		{
			throw new IllegalArgumentException("Parameter 'targetFile' must not be null.");
		}
		
		BasicXmlFile encounter = new BasicXmlFile(targetFile);
		
		if (Encounter.ROOT_ELEMENT.equals(encounter.getRoot().getName()))
		{
			for (final Element creature : encounter.getRoot().getChildren(Encounter.ELEMENT_CREATURE))
			{
				final String id = creature.getAttributeValue(Encounter.ATTRIBUTE_ID);
				final int amount = Integer.parseInt(creature.getAttributeValue(Encounter.ATTRIBUTE_AMOUNT));
				ICreature instance = CreatureLoader.getInstance().getCreatures().get(id);
				
				if (instance != null)
				{
					addCreature(instance);
					
					_creatures.get(instance).setAmount(amount);
				}
			}
		}
		
		updateView();
	}

	
	private void saveEncounter(final File file)
	{
		if (file == null)
		{
			throw new IllegalArgumentException("Parameter 'file' must not be null.");
		}
		
		final File targetFile = LoaderHelper.forceExtension(file, LoaderHelper.EXTENSION_XML);

		final Element rootElement = new Element(Encounter.ROOT_ELEMENT);

		for (final Entry<ICreature, CreatureEncounterPanel> entry : _creatures.entrySet())
		{	
			String id = entry.getKey().getTemplate().getId();			
			int amount = entry.getValue().getAmount();
			
			if (id == null)
			{
				throw new IllegalArgumentException();
			}
			else
			{				
				final Element creatureElement = new Element(Encounter.ELEMENT_CREATURE);
				creatureElement.setAttribute(Encounter.ATTRIBUTE_ID, id);
				creatureElement.setAttribute(Encounter.ATTRIBUTE_AMOUNT, Integer.toString(amount));
				rootElement.addContent(creatureElement);
			}
		}
		
		final Document document = new Document(rootElement);

		final BasicXmlFile template = new BasicXmlFile(document, targetFile);
		template.saveToFile();

		ApplicationLogger.getLogger().info("Saving new encounter to: " + targetFile.getAbsolutePath());

		long oldLength = -1;
		long newLength = targetFile.length();
		for (int i = 0; oldLength != newLength && i < 60; ++i)
		{
			oldLength = newLength;
			newLength = targetFile.length();

			ApplicationLogger.getLogger().info("... still writing the file");

			try
			{
				Thread.sleep(1000);
			}
			catch (final InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	
	private void exportEncounter(final File targetFile)
	{
		if (targetFile == null)
		{
			throw new IllegalArgumentException("Parameter 'targetFile' must not be null.");
		}
		
		new HtmlExporter(_creatures, targetFile).saveToFile();
	}
}