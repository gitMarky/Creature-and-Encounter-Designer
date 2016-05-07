package project.thirteenthage.creatures.internal.gui.views;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import project.thirteenthage.creatures.interfaces.IView;
import project.thirteenthage.creatures.internal.interfaces.IAttack;
import project.thirteenthage.creatures.internal.interfaces.ICreature;

/**
 * A {@link JPanel} that displays a creature.
 */
@SuppressWarnings("serial")
public class CreatureViewPanel extends JPanel implements IView
{
	private final ICreature _creature;
	private final CreatureInfoPanel _infoPanel;
	private final CreatureAttackPanel _attackPanel;
	private final CreatureStatsPanel _statsPanel;
	
	
	public CreatureViewPanel(final ICreature creature)
	{
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		_creature = creature;
		
		JPanel namePanel = new JPanel();
		JPanel blockPanel = new JPanel();
		blockPanel.setLayout(new BoxLayout(blockPanel, BoxLayout.X_AXIS));
		
		_infoPanel = new CreatureInfoPanel();
		_attackPanel = new CreatureAttackPanel();
		_statsPanel = new CreatureStatsPanel();
		
		
		blockPanel.add(_infoPanel);
		blockPanel.add(_attackPanel);
		blockPanel.add(_statsPanel);
		
		namePanel.add(new JLabel(_creature.getName()));
		
		add(namePanel);
		add(blockPanel);
		
		updateView();
	}

	@Override
	public void updateView()
	{
		_infoPanel.updateView();
		_attackPanel.updateView();
		_statsPanel.updateView();
	}
	
	private class CreatureInfoPanel extends JPanel implements IView
	{
		private JLabel _size;
		private JLabel _level;
		private List<JLabel> _labels = new ArrayList<JLabel>();
		
		private CreatureInfoPanel()
		{
			_size = new JLabel();
			_level = new JLabel();
			
			this.add(_size);
			this.add(_level);
						
			updateView();
		}

		@Override
		public void updateView()
		{
			// update the components
			_size.setText(_creature.getSize().name());
			_level.setText(getLevelText(_creature.getLevel()));

			for (final JLabel label : _labels)
			{
				this.remove(label);
			}
			
			_labels.clear();
			
			for (final String labelText : _creature.getLabels())
			{
				JLabel label = new JLabel(labelText);
				_labels.add(label);
			}
			
			// update the actual view
			
			setLayout(new GridLayout(2 + _labels.size(), 1));
			for (final JLabel label : _labels)
			{
				this.add(label);
			}
		}

		private String getLevelText(int level)
		{
			return ith(level) + " level";
		}

		private String ith(int level)
		{
			switch (level % 10)
			{
				case 1: return level + "st";
				case 2: return level + "nd";
				case 3: return level + "rd";
				default: return level + "th";
			}
		}
	}
	
	private class CreatureAttackPanel extends JPanel implements IView
	{
		private final JLabel _initiative = new JLabel();
		
		private final List<JLabel> _attacks = new ArrayList<JLabel>();		
		private final List<JLabel> _specials = new ArrayList<JLabel>();
		private final List<JLabel> _nastier = new ArrayList<JLabel>();
		
		private CreatureAttackPanel()
		{
			super();
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			this.add(_initiative);
		}
		
		@Override
		public void updateView()
		{
			_initiative.setText("Initiative +" + _creature.getInitiative());
			
			for (final JLabel label : _attacks) remove(label);
			for (final JLabel label : _specials) remove(label);
			for (final JLabel label : _nastier) remove(label);
			
			_attacks.clear();
			_specials.clear();
			_nastier.clear();
			
			for (final IAttack attack : _creature.getAttacks())
			{
				JLabel label = new JLabel(attack.toGuiText());
				_attacks.add(label);
				this.add(label);
			}
		}
	}
	
	private class CreatureStatsPanel extends JPanel implements IView
	{
		private final JLabel _labelAC;
		private final JLabel _labelPD;
		private final JLabel _labelMD;
		private final JLabel _labelHP;

		private CreatureStatsPanel()
		{
			super();

			_labelAC = new JLabel();
			_labelPD = new JLabel();
			_labelMD = new JLabel();
			_labelHP = new JLabel();
			
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.add(_labelAC);
			this.add(_labelPD);
			this.add(_labelMD);
			this.add(_labelHP);
			updateView();
		}
		
		@Override
		public void updateView()
		{
			_labelAC.setText("AC " + _creature.getAC());
			_labelPD.setText("PD " + _creature.getPD());
			_labelMD.setText("MD " + _creature.getMD());
			_labelHP.setText("HP " + (int) _creature.getHP());
		}
	}
}
