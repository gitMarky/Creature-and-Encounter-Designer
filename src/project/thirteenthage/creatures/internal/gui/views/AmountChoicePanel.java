package project.thirteenthage.creatures.internal.gui.views;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import project.thirteenthage.creatures.interfaces.IView;

/**
 * Lets you choose an amount and increase or decrease it.
 */
@SuppressWarnings("serial")
public class AmountChoicePanel extends JPanel implements ActionListener
{
	private int _amount = 1;
	private JTextField _amountField = new JTextField();
	private JLabel _amountLabel = new JLabel(Integer.toString(_amount));
	private JButton _minusButton = new JButton("-");
	private JButton _plusButton = new JButton("+");
	
	private int _lowerBound = 1;
	private int _upperBound = Integer.MAX_VALUE;

	private IView _updateOnAmountChanged = null;


	public AmountChoicePanel(final String name)
	{
		super();

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
	public void actionPerformed(ActionEvent event)
	{
		if (event.getSource() == _minusButton)
			changeAmount(-1);
		if (event.getSource() == _plusButton)
			changeAmount(+1);
		if (event.getSource() == _amountField)
		{
			try
			{
				int amount = Integer.parseInt(_amountField.getText());
				setAmount(amount);
			} catch (final NumberFormatException e)
			{
				// do nothing
			}
		}
	}


	private void changeAmount(int change)
	{
		_amount += change;
		updateAmount();
	}


	public void setAmount(int amount)
	{
		_amount = amount;
		updateAmount();
	}


	private void updateAmount()
	{
		if (_amount < _lowerBound)
			_amount = _lowerBound;
		
		// this will probably fail with the max integer...
		if (_amount > _upperBound)
			_amount = _upperBound;

		_amountLabel.setText(Integer.toString(_amount));

		if (_updateOnAmountChanged != null)
		{
			_updateOnAmountChanged.updateView();
		}
	}


	public void setUpdateView(final IView view)
	{
		_updateOnAmountChanged = view;
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
}
