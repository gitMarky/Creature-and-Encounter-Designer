package project.thirteenthage.creatures.internal.gui.views;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import project.thirteenthage.creatures.internal.gui.CreatureGui;
import project.thirteenthage.creatures.mechanics.LevelAdjustment;

/**
 * Lets you choose the calculation for level adjustment.
 */
@SuppressWarnings("serial")
public class LevelAdjustmentChoicePanel extends ChoicePanel implements ActionListener
{
	private final JRadioButton _originalButton = new JRadioButton("13th Age Core Rules Level Adjustment");
	private final JRadioButton _alternateButton = new JRadioButton("Alternate Level Adjustment");


	public LevelAdjustmentChoicePanel()
	{
		super();

		this.add(_alternateButton);
		this.add(_originalButton);

		_alternateButton.setSelected(!LevelAdjustment.useOriginalCalculation());
		_originalButton.setSelected(LevelAdjustment.useOriginalCalculation());

		final ButtonGroup group = new ButtonGroup();
		group.add(_originalButton);
		group.add(_alternateButton);

		_alternateButton.addActionListener(this);
		_originalButton.addActionListener(this);

		this.setLayout(new GridLayout(2, 1));
		this.setBorder(BorderFactory.createTitledBorder("Level Adjustment Calculation"));
	}


	@Override
	public void actionPerformed(final ActionEvent event)
	{
		if (event.getSource() == _originalButton)
		{
			LevelAdjustment.setUseOriginalCalculation(true);
		}

		if (event.getSource() == _alternateButton)
		{
			LevelAdjustment.setUseOriginalCalculation(false);
		}

		updateView();
		
		CreatureGui.GUI.getCreaturePanel().getEditPanel().updateView();
	}
}
