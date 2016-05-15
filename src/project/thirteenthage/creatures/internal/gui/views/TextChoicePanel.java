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
	private JTextField _amountField = new JTextField();


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
		updateView();
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
