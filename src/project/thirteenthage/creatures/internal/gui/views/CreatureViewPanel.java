package project.thirteenthage.creatures.internal.gui.views;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.BorderPane;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import project.thirteenthage.creatures.interfaces.IView;
import project.thirteenthage.creatures.internal.interfaces.ICreature;

/**
 * A {@link JPanel} that displays a creature.
 */
@SuppressWarnings("serial")
public class CreatureViewPanel extends JPanel implements IView
{
	private final ICreature _creature;
	private final CreatureInfoPanel _infoPanel;
	private final JPanel _attackPanel;
	private final JPanel _statsPanel;
	
	
	public CreatureViewPanel(final ICreature creature)
	{
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		_creature = creature;
		
		JPanel namePanel = new JPanel();
		JPanel blockPanel = new JPanel(); //new GridLayout(1, 3));
		blockPanel.setLayout(new BoxLayout(blockPanel, BoxLayout.X_AXIS));
		
		_infoPanel = new CreatureInfoPanel();
		_attackPanel = new JPanel();
		_statsPanel = new JPanel();
		
		_attackPanel.add(new JLabel("Attacks"));
		_statsPanel.add(new JLabel("Stats"));
		
		blockPanel.add(_infoPanel);
		blockPanel.add(_attackPanel);
		blockPanel.add(_statsPanel);
		
		namePanel.add(new JLabel(_creature.getName()));
		
		add(namePanel);
		add(blockPanel);
	}

	@Override
	public void updateView()
	{
		_infoPanel.updateView();
		_infoPanel.setVisible(true);
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
}
