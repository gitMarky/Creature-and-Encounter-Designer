package project.thirteenthage.creatures.internal.gui.views;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import project.thirteenthage.creatures.creature.CreatureSize;
import project.thirteenthage.creatures.internal.ApplicationLogger;

/**
 * Lets you choose the better of two defenses.
 */
@SuppressWarnings("serial")
public class SizeChoicePanel extends ChoicePanel implements ActionListener
{
	private CreatureSize _choice;

	private final Map<JRadioButton, CreatureSize> _map = new HashMap<JRadioButton, CreatureSize>();


	public SizeChoicePanel()
	{
		super();

		final ButtonGroup group = new ButtonGroup();

		for (final CreatureSize size : CreatureSize.values())
		{
			final JRadioButton button = new JRadioButton(size.toGuiText());
			_map.put(button, size);
			this.add(button);
			group.add(button);
			button.addActionListener(this);
		}

		_choice = _map.values().iterator().next();
		_map.keySet().iterator().next().setSelected(true);

		this.setLayout(new GridLayout(CreatureSize.values().length, 1));
		this.setBorder(BorderFactory.createTitledBorder("Size"));
	}


	@Override
	public void actionPerformed(final ActionEvent event)
	{
		final CreatureSize creatureSize = _map.get(event.getSource());
		if (creatureSize != null)
		{
			ApplicationLogger.getLogger().info("Pressed button: Creature size");
			_choice = creatureSize;
		}

		updateView();
	}


	public CreatureSize getCreatureSize()
	{
		return _choice;
	}


	public void setCreatureSize(final CreatureSize size)
	{
		if (size == null)
		{
			throw new IllegalArgumentException("Parameter 'size' must not be null.");
		}

		_choice = size;
		for (final Entry<JRadioButton, CreatureSize> entry : _map.entrySet())
		{
			entry.getKey().setSelected(entry.getValue() == size);
		}
	}
}
