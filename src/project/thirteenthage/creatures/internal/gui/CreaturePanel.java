package project.thirteenthage.creatures.internal.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import project.thirteenthage.creatures.interfaces.IView;
import project.thirteenthage.creatures.internal.gui.views.CreatureEditPanel;
import project.thirteenthage.creatures.internal.gui.views.CreatureViewPanel;
import project.thirteenthage.creatures.internal.interfaces.ICreature;

//property change stuff

/**
 * The creature panel should display the currently selected creature. For this
 * purpose it hosts a {@link CreatureViewPanel}.
 */
@SuppressWarnings("serial")
class CreaturePanel extends JPanel implements ActionListener, IView
{
	private JPanel _innerPanel = new JPanel();
	private CreatureEditPanel _editPanel = new CreatureEditPanel();
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
		JPanel buttonPanel = new JPanel();

		_innerPanel.setLayout(new BoxLayout(_innerPanel, BoxLayout.Y_AXIS));
		_innerPanel.add(buttonPanel);
		_innerPanel.add(_editPanel);

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
	public void displayCreature(ICreature creature)
	{
		if (_panel != null)
		{
			_innerPanel.remove(_panel);
		}

		_selectedCreature = creature;
		_panel = new CreatureViewPanel(_selectedCreature);
		_innerPanel.add(_panel);
	}


	@Override
	public void actionPerformed(ActionEvent event)
	{
		if (event.getSource() == _addButton)
		{
			transferCreatureToEncounter(_selectedCreature);
		}
		if (event.getSource() == _editButton)
		{
			startEditing();
		}
		if (event.getSource() == _cancelButton)
		{
			cancelEditing();
		}
		if (event.getSource() == _applyButton)
		{
			applyEditing();
		}
		if (event.getSource() == _saveButton)
		{
			saveCreature();
		}
	}


	private void startEditing()
	{
		_isInEditMode = true;
		_editPanel.setLevel(_selectedCreature.getLevel());
		updateView();
	}
	
	private void cancelEditing()
	{
		stopEditing();
	}
	
	private void applyEditing()
	{
		stopEditing();
	}
	
	private void stopEditing()
	{
		_isInEditMode = false;		
		updateView();
	}
	
	private void saveCreature()
	{
		
	}

	private void transferCreatureToEncounter(ICreature creature)
	{
		CreatureGui.GUI.getEncounterPanel().addCreature(creature);
	}


	@Override
	public void updateView()
	{
		if (_isInEditMode)
		{
			_editButton.setEnabled(false);
			_cancelButton.setEnabled(true);
			_applyButton.setEnabled(true);
			_saveButton.setEnabled(false);
			_addButton.setEnabled(false);
			_editPanel.setVisible(true);
		}
		else
		{
			_editButton.setEnabled(true);
			_cancelButton.setEnabled(false);
			_applyButton.setEnabled(false);
			_saveButton.setEnabled(true);
			_addButton.setEnabled(true);
			_editPanel.setVisible(false);
		}
		
		if (CreatureGui.GUI != null) CreatureGui.GUI.updateView();
	}
}