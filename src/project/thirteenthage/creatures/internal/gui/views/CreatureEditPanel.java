package project.thirteenthage.creatures.internal.gui.views;

import javax.swing.JLabel;
import javax.swing.JPanel;

import project.thirteenthage.creatures.interfaces.IView;
import project.thirteenthage.creatures.internal.Constants;

@SuppressWarnings("serial")
public class CreatureEditPanel extends JPanel implements IView
{
	private final AmountChoicePanel _amountChoicePanel = new AmountChoicePanel("Level");
	
	public CreatureEditPanel()
	{
		super();
		this.add(new JLabel("TODO"));
		this.add(_amountChoicePanel);
		_amountChoicePanel.setUpdateView(this);
		_amountChoicePanel.setBounds(Constants.MIN_LEVEL, Constants.MAX_LEVEL);
	}

	@Override
	public void updateView()
	{
		// TODO Auto-generated method stub
		
	}
}
