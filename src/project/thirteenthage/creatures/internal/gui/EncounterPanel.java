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
import javax.swing.JScrollPane;

import project.thirteenthage.creatures.interfaces.IView;
import project.thirteenthage.creatures.internal.Constants;
import project.thirteenthage.creatures.internal.gui.views.AmountChoicePanel;
import project.thirteenthage.creatures.internal.gui.views.CreatureEncounterPanel;
import project.thirteenthage.creatures.internal.gui.views.EncounterAnalysisView;
import project.thirteenthage.creatures.internal.gui.views.EncounterDifficultyView;
import project.thirteenthage.creatures.internal.interfaces.ICreature;
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
	private final JPanel _buttonPanel = new JPanel();
	private final JPanel _creatureListPanel = new JPanel();
	private final JLabel _creatureListEmpty = new JLabel("No creatures were added to the encounter yet");
	private final EncounterDifficultyView _difficultyLabel = new EncounterDifficultyView();
	private final AmountChoicePanel _playerLevel = new AmountChoicePanel("Player level");
	private final AmountChoicePanel _playerAmount = new AmountChoicePanel("Player amount");
	private final EncounterAnalysisView _analysisLabel = new EncounterAnalysisView();

	private final JButton _clearButton = new JButton("Clear");

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
		_buttonPanel.add(_clearButton);
		_buttonPanel.add(_difficultyLabel);
		_buttonPanel.add(_playerLevel);
		_buttonPanel.add(_playerAmount);
		_buttonPanel.add(_analysisLabel);

		_playerLevel.setUpdateView(this);
		_playerLevel.setBounds(Constants.MIN_PLAYER_LEVEL, Constants.MAX_PLAYER_LEVEL);

		_playerAmount.setUpdateView(this);
		_playerAmount.setBounds(Constants.MIN_PLAYER_AMOUNT, Constants.MAX_PLAYER_AMOUNT);

		//JPanel innerPanel = new JPanel();
		//innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.X_AXIS));

		JScrollPane creatureScrollBar = new JScrollPane(_creatureListPanel);
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(_buttonPanel);
//		innerPanel.add(_creatureListPanel);
//		innerPanel.add(_analysisLabel);
//		this.add(innerPanel);
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
		for (final CreatureEncounterPanel panel : _creatures.values())
		{
			panel.updateView();
		}

		
		if (_creatures.isEmpty())
		{
			_difficultyLabel.displayDifficulty(Double.NaN);
			_analysisLabel.displayNothing();
		}
		else
		{
			Encounter encounter = new Encounter(_creatures);
			encounter.setPlayerLevel(_playerLevel.getAmount());
			encounter.setPlayerAmount(_playerAmount.getAmount());
			_difficulty = new EncounterDifficulty(encounter);
			_difficultyLabel.displayDifficulty(_difficulty.getEncounterDifficulty());
			
			EncounterAnalysis analysis = new EncounterAnalysis(encounter);
			analysis.analyze();
			
			_analysisLabel.displayAnalysis(analysis);
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