package project.thirteenthage.creatures.internal.gui.views;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import project.thirteenthage.creatures.creature.BetterDefense;

/**
 * Lets you choose the better of two defenses.
 */
@SuppressWarnings("serial")
public class DefenseChoicePanel extends ChoicePanel implements ActionListener
{
	private JRadioButton _pdButton = new JRadioButton("PD");
	private JRadioButton _mdButton = new JRadioButton("MD");

	private BetterDefense _better = BetterDefense.PD;


	public DefenseChoicePanel()
	{
		super();

		this.add(_mdButton);
		this.add(_pdButton);

		_mdButton.setSelected(false);
		_pdButton.setSelected(true);

		ButtonGroup group = new ButtonGroup();
		group.add(_pdButton);
		group.add(_mdButton);

		_mdButton.addActionListener(this);
		_pdButton.addActionListener(this);

		this.setLayout(new GridLayout(1, 4));
		this.setBorder(BorderFactory.createTitledBorder("Better defense"));
	}


	@Override
	public void actionPerformed(ActionEvent event)
	{
		if (event.getSource() == _pdButton)
		{
			_better = BetterDefense.PD;
		}

		if (event.getSource() == _mdButton)
		{
			_better = BetterDefense.MD;
		}

		updateView();
	}


	public BetterDefense getBetterDefense()
	{
		return _better;
	}


	public void setBetterDefense(final BetterDefense defense)
	{
		if (defense == BetterDefense.MD)
		{
			_mdButton.setSelected(true);
			_pdButton.setSelected(false);
		} else
		{
			_mdButton.setSelected(false);
			_pdButton.setSelected(true);
		}
	}
}
