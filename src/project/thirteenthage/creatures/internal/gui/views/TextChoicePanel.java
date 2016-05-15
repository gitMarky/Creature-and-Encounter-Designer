package project.thirteenthage.creatures.internal.gui.views;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;

import project.thirteenthage.creatures.interfaces.IView;

/**
 * Lets you enter a text.
 */
@SuppressWarnings("serial")
public class TextChoicePanel extends JPanel implements ActionListener
{
	private JTextField _amountField = new JTextField();

	private IView _updateOnAmountChanged = null;


	public TextChoicePanel(final String name)
	{
		super();

		_amountField.addActionListener(this);
		this.add(_amountField);

		// this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setLayout(new GridLayout(1, 4));
		this.setBorder(BorderFactory.createTitledBorder(name));
	}


	@Override
	public void actionPerformed(ActionEvent event)
	{
		if (_updateOnAmountChanged != null)
		{
			_updateOnAmountChanged.updateView();
		}
	}


	public void setUpdateView(final IView view)
	{
		_updateOnAmountChanged = view;
	}


	public String getText()
	{
		return _amountField.getText();
	}


	public void setText(final String text)
	{
		_amountField.setText(text);
	}
}
