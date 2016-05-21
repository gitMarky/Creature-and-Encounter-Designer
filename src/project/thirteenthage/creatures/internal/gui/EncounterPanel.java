package project.thirteenthage.creatures.internal.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import project.thirteenthage.creatures.interfaces.IView;
import project.thirteenthage.creatures.internal.Constants;
import project.thirteenthage.creatures.internal.gui.views.AmountChoicePanel;
import project.thirteenthage.creatures.internal.gui.views.CreatureEncounterPanel;
import project.thirteenthage.creatures.internal.gui.views.EncounterDifficultyView;
import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.mechanics.EncounterDifficulty;

/**
 * This panel should be able to contain several creatures that were selected for
 * an encounter. It should also contain a encounter difficulty summary.
 */
@SuppressWarnings("serial")
public class EncounterPanel extends JPanel implements IView, ActionListener
{
	private final JPanel _buttonPanel = new JPanel();
	private final JPanel _creatureListPanel = new JPanel();
	private final JLabel _creatureListEmpty = new JLabel("No creatures were added to the encounter yet");
	private final EncounterDifficultyView _difficultyLabel = new EncounterDifficultyView();
	private final AmountChoicePanel _playerLevel = new AmountChoicePanel("Player level");

	private final JButton _clearButton = new JButton("Clear");

	private final Map<ICreature, CreatureEncounterPanel> _creatures = new HashMap<ICreature, CreatureEncounterPanel>();
	private EncounterDifficulty _difficulty = null;


	EncounterPanel()
	{
		// set up creature panel
		_creatureListPanel.setBorder(BorderFactory.createTitledBorder("Creatures"));
		_creatureListPanel.setLayout(new BoxLayout(_creatureListPanel, BoxLayout.Y_AXIS));
		_creatureListPanel.setAutoscrolls(true);

		clearCreatures();

		// set up buttons
		_clearButton.addActionListener(this);
		_buttonPanel.add(_clearButton);
		_buttonPanel.add(_difficultyLabel);
		_buttonPanel.add(_playerLevel);

		_playerLevel.setUpdateView(this);
		_playerLevel.setBounds(Constants.MIN_PLAYER_LEVEL, Constants.MAX_PLAYER_LEVEL);

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(_buttonPanel);
		this.add(_creatureListPanel);
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
		if (_creatures.isEmpty())
		{
			_difficultyLabel.displayDifficulty(Double.NaN);
		}
		else
		{
			_difficulty = new EncounterDifficulty(_creatures);
			_difficulty.setPlayerLevel(_playerLevel.getAmount());
			_difficultyLabel.displayDifficulty(_difficulty.getEncounterDifficulty());
		}

		if (CreatureGui.GUI != null) CreatureGui.GUI.updateView();
	}


	@Override
	public void actionPerformed(final ActionEvent event)
	{
		if (event.getSource() == _clearButton)
		{
			clearCreatures();
			updateView();
		}
	}
}