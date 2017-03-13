package legacy.project.thirteenthage.creatures.internal.gui.views;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Lets you choose an amount and increase or decrease it.
 */
@SuppressWarnings("serial")
public class AmountChoicePanel extends ChoicePanel implements ActionListener
{
	private int _amount = 1;
	private final JTextField _amountField = new JTextField();
	private final JLabel _amountLabel = new JLabel(Integer.toString(_amount));
	private final JButton _minusButton = new JButton("-");
	private final JButton _plusButton = new JButton("+");

	private int _lowerBound = 1;
	private int _upperBound = Integer.MAX_VALUE;
	private int _buttonStep = 1;

	private String _outputText = "%d";


	public AmountChoicePanel(final String name)
	{
		super();

		if (name == null)
		{
			throw new IllegalArgumentException("Parameter 'name' must not be null.");
		}

		this.add(_amountLabel);
		this.add(_plusButton);
		this.add(_minusButton);

		_plusButton.addActionListener(this);
		_minusButton.addActionListener(this);
		_amountField.addActionListener(this);
		this.add(_amountField);

		// this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setLayout(new GridLayout(1, 4));
		this.setBorder(BorderFactory.createTitledBorder(name));
	}


	@Override
	public void actionPerformed(final ActionEvent event)
	{
		if (event.getSource() == _minusButton) changeAmount(-_buttonStep);
		if (event.getSource() == _plusButton) changeAmount(+_buttonStep);
		if (event.getSource() == _amountField)
		{
			try
			{
				final int amount = Integer.parseInt(_amountField.getText());
				setAmount(amount);
			}
			catch (final NumberFormatException e)
			{
				// do nothing
			}
		}
	}


	private void changeAmount(final int change)
	{
		_amount += change;
		updateAmount();
	}


	public void setAmount(final int amount)
	{
		_amount = amount;
		updateAmount();
	}


	private void updateAmount()
	{
		if (_amount < _lowerBound) _amount = _lowerBound;

		// this will probably fail with the max integer...
		if (_amount > _upperBound) _amount = _upperBound;

		_amountLabel.setText(String.format(_outputText, _amount));

		updateView();
	}


	public int getAmount()
	{
		return _amount;
	}


	public void setBounds(final int lowerBound, final int upperBound)
	{
		if (Integer.MIN_VALUE >= lowerBound)
		{
			throw new IllegalArgumentException("Lower bound is too low: " + lowerBound + " <= " + Integer.MIN_VALUE);
		}
		if (Integer.MAX_VALUE <= upperBound)
		{
			throw new IllegalArgumentException("Upper bound is too high: " + upperBound + " >= " + Integer.MAX_VALUE);
		}
		if (lowerBound >= upperBound)
		{
			throw new IllegalArgumentException("The lower bound must be less than and not equal to the upper bound: " + lowerBound + " < " + upperBound);
		}

		_lowerBound = lowerBound;
		_upperBound = upperBound;
	}


	public void setButtonStep(final int step)
	{
		_buttonStep = step;
	}


	public void setOutputText(final String text)
	{
		if (text == null)
		{
			throw new IllegalArgumentException("Parameter 'text' must not be null.");
		}

		_outputText = text;
	}
}
