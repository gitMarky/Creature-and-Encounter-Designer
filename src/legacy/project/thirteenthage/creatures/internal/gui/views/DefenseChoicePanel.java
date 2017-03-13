package legacy.project.thirteenthage.creatures.internal.gui.views;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

import project.library.marky.logger.ApplicationLogger;
import legacy.project.thirteenthage.creatures.creature.BetterDefense;

/**
 * Lets you choose the better of two defenses.
 */
@SuppressWarnings("serial")
public class DefenseChoicePanel extends ChoicePanel implements ActionListener
{
	private final JRadioButton _pdButton = new JRadioButton("PD");
	private final JRadioButton _mdButton = new JRadioButton("MD");

	private BetterDefense _better = BetterDefense.PD;


	public DefenseChoicePanel()
	{
		super();

		this.add(_mdButton);
		this.add(_pdButton);

		_mdButton.setSelected(false);
		_pdButton.setSelected(true);

		final ButtonGroup group = new ButtonGroup();
		group.add(_pdButton);
		group.add(_mdButton);

		_mdButton.addActionListener(this);
		_pdButton.addActionListener(this);

		this.setLayout(new GridLayout(2, 1));
		this.setBorder(BorderFactory.createTitledBorder("Better defense"));
	}


	@Override
	public void actionPerformed(final ActionEvent event)
	{
		if (event.getSource() == _pdButton)
		{
			ApplicationLogger.getLogger().info("Pressed button: Better defense is PD");
			_better = BetterDefense.PD;
		}

		if (event.getSource() == _mdButton)
		{
			ApplicationLogger.getLogger().info("Pressed button: Better defense is MD");
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
		if (defense == null)
		{
			throw new IllegalArgumentException("Parameter 'defense' must not be null.");
		}

		if (defense == BetterDefense.MD)
		{
			_mdButton.setSelected(true);
			_pdButton.setSelected(false);
		}
		else
		{
			_mdButton.setSelected(false);
			_pdButton.setSelected(true);
		}
	}
}
