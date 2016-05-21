package project.thirteenthage.creatures.internal.gui.views;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

/**
 * Lets you enter a text.
 */
@SuppressWarnings("serial")
public class TextChoicePanel extends ChoicePanel implements ActionListener
{
	private final JTextField _amountField = new JTextField();


	public TextChoicePanel(final String name)
	{
		super();
		
		if (name == null)
		{
			throw new IllegalArgumentException("Parameter 'name' must not be null.");
		}

		_amountField.addActionListener(this);
		this.add(_amountField);

		// this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setLayout(new GridLayout(1, 4));
		this.setBorder(BorderFactory.createTitledBorder(name));
	}


	@Override
	public void actionPerformed(final ActionEvent event)
	{
		updateView();
	}


	public String getText()
	{
		return _amountField.getText();
	}


	public void setText(final String text)
	{
		if (text == null)
		{
			throw new IllegalArgumentException("Parameter 'text' must not be null.");
		}
		
		_amountField.setText(text);
	}
}
