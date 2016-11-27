package project.thirteenthage.creatures.internal.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import project.library.marky.logger.ApplicationLogger;
import project.thirteenthage.creatures.interfaces.IView;
import project.thirteenthage.creatures.internal.gui.views.CreatureEditPanel;
import project.thirteenthage.creatures.internal.gui.views.CreatureViewPanel;
import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.loaders.CreatureLoader;
import project.thirteenthage.creatures.loaders.CreatureTemplateLoader;

//property change stuff

/**
 * The creature panel should display the currently selected creature. For this
 * purpose it hosts a {@link CreatureViewPanel}.
 */
@SuppressWarnings("serial")
public class CreaturePanel extends JPanel implements ActionListener, IView
{
	private final JPanel _innerPanel = new JPanel();
	private final CreatureEditPanel _editPanel = new CreatureEditPanel();
	private final JScrollPane _editScrollbar;
	private CreatureViewPanel _panel = null;
	private final JButton _addButton = new JButton("Add to encounter");
	private final JButton _editButton = new JButton("Edit");
	private final JButton _cancelButton = new JButton("Cancel");
	private final JButton _applyButton = new JButton("Apply");
	private final JButton _saveButton = new JButton("Save as");
	private ICreature _selectedCreature = null;

	private boolean _isInEditMode = false;


	CreaturePanel()
	{
		super();
		final JPanel buttonPanel = new JPanel();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// top row: buttons

		this.add(buttonPanel);

		buttonPanel.add(_addButton);
		_addButton.addActionListener(this);

		buttonPanel.add(_editButton);
		_editButton.addActionListener(this);

		buttonPanel.add(_applyButton);
		_applyButton.addActionListener(this);

		buttonPanel.add(_cancelButton);
		_cancelButton.addActionListener(this);

		buttonPanel.add(_saveButton);
		_saveButton.addActionListener(this);

		// bottom row: inner panel with boxes from left to right: creature edit,
		// creature view

		_innerPanel.setLayout(new BoxLayout(_innerPanel, BoxLayout.X_AXIS));
		// _innerPanel.add(_editPanel);
		_editScrollbar = new JScrollPane(_editPanel);
		_editScrollbar.setPreferredSize(new Dimension(StyleConstants.CREATURE_EDIT_PANEL_WIDTH, StyleConstants.CREATURE_VIEW_PANEL_HEIGHT));
		_innerPanel.add(_editScrollbar);

		stopEditing();

		this.add(_innerPanel);
		this.setBorder(BorderFactory.createTitledBorder("Selected creature"));
	}


	/**
	 * Displays a creature in this panel.
	 * 
	 * @param creature
	 *            the creature.
	 */
	public void displayCreature(final ICreature creature)
	{
		if (creature == null)
		{
			throw new IllegalArgumentException("Parameter 'creature' must not be null.");
		}

		if (_panel != null)
		{
			_innerPanel.remove(_panel);
		}

		_selectedCreature = creature;
		_panel = new CreatureViewPanel(_selectedCreature);
		_innerPanel.add(_panel);
		updateView();
	}


	@Override
	public void actionPerformed(final ActionEvent event)
	{
		if (event.getSource() == _addButton)
		{
			ApplicationLogger.getLogger().info("Pressed button: Add to encounter");
			transferCreatureToEncounter(_selectedCreature);
		}
		if (event.getSource() == _editButton)
		{
			ApplicationLogger.getLogger().info("Pressed button: Edit creature");
			startEditing();
		}
		if (event.getSource() == _cancelButton)
		{
			ApplicationLogger.getLogger().info("Pressed button: Cancel editing creature");
			cancelEditing();
		}
		if (event.getSource() == _applyButton)
		{
			ApplicationLogger.getLogger().info("Pressed button: Apply editing creature");
			applyEditing();
		}
		if (event.getSource() == _saveButton)
		{
			ApplicationLogger.getLogger().info("Pressed button: Save creature to xml file");
			final int choice = CreatureGui.GUI.getFileChooser().showSaveDialog(this);

			if (choice == JFileChooser.APPROVE_OPTION)
			{
				final File file = CreatureGui.GUI.getFileChooser().getSelectedFile();
				saveCreature(file);
			}
		}
	}


	private void startEditing()
	{
		_isInEditMode = true;
		_editPanel.startEditing(this, _selectedCreature);
		updateView();
	}


	private void cancelEditing()
	{
		_editPanel.cancelEditing(this, _selectedCreature);
		stopEditing();
	}


	private void applyEditing()
	{
		_editPanel.applyEditing(this, _selectedCreature);
		stopEditing();
	}


	private void stopEditing()
	{
		_isInEditMode = false;
		updateView();
	}


	private void saveCreature(final File file)
	{
		final File template = _selectedCreature.getTemplate().saveToFile(file);

		ApplicationLogger.getLogger().info("Loading the newly saved creature");
		CreatureTemplateLoader.getInstance().load(template);
		CreatureLoader.getInstance().load(CreatureTemplateLoader.getInstance());

		CreatureGui.GUI.getMenuSelectionPanel().updateView();

		updateView();
	}


	private void transferCreatureToEncounter(final ICreature creature)
	{
		CreatureGui.GUI.getEncounterPanel().addCreature(creature);
	}


	@Override
	public void updateView()
	{
		ApplicationLogger.getLogger().info("Update view");
		
		if (_isInEditMode)
		{
			_editButton.setEnabled(false);
			_cancelButton.setEnabled(true);
			_applyButton.setEnabled(true);
			_addButton.setEnabled(false);
			_editScrollbar.setVisible(true);
			_editPanel.setVisible(true);
		}
		else
		{
			_editButton.setEnabled(true);
			_cancelButton.setEnabled(false);
			_applyButton.setEnabled(false);
			_addButton.setEnabled(true);
			_editScrollbar.setVisible(false);
			_editPanel.setVisible(false);
		}

		if (canSave())
		{
			_saveButton.setEnabled(true);
		}
		else
		{
			_saveButton.setEnabled(false);
		}

		if (_panel != null) _panel.updateView();
		if (CreatureGui.GUI != null) CreatureGui.GUI.updateView();
	}


	private boolean canSave()
	{
		if (_selectedCreature == null)
		{
			return false;
		}
		else
		{
			return !_isInEditMode && !CreatureLoader.getInstance().isCreatureLoaded(_selectedCreature);
		}
	}


	public CreatureEditPanel getEditPanel()
	{
		return _editPanel;
	}
}