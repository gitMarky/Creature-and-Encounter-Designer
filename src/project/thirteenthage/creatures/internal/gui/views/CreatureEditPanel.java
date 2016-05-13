package project.thirteenthage.creatures.internal.gui.views;

import javax.swing.JLabel;
import javax.swing.JPanel;

import project.thirteenthage.creatures.interfaces.IView;
import project.thirteenthage.creatures.internal.Constants;
import project.thirteenthage.creatures.internal.gui.CreaturePanel;
import project.thirteenthage.creatures.internal.interfaces.ICreature;

@SuppressWarnings("serial")
public class CreatureEditPanel extends JPanel implements IView
{
	private final AmountChoicePanel _amountChoicePanel = new AmountChoicePanel("Level");
	
	private ICreature _originalCreature = null;
	private ICreature _editedCreature = null;
	
	public CreatureEditPanel()
	{
		super();
		this.add(new JLabel("TODO"));
		this.add(_amountChoicePanel);
		_amountChoicePanel.setUpdateView(this);
		_amountChoicePanel.setBounds(Constants.MIN_LEVEL, Constants.MAX_LEVEL);
	}
	
	public void setLevel(int level)
	{
		_amountChoicePanel.setAmount(level);
	}

	@Override
	public void updateView()
	{
		// TODO Auto-generated method stub
		
	}

	public void applyEditing(CreaturePanel creaturePanel, ICreature originalCreature)
	{
		if (_editedCreature == null) return;
		
		creaturePanel.displayCreature(_editedCreature);
	}

	public void cancelEditing(CreaturePanel creaturePanel, ICreature originalCreature)
	{
		if (_originalCreature == null) return;
		
		creaturePanel.displayCreature(_originalCreature);
	}

	public void startEditing(CreaturePanel creaturePanel, ICreature originalCreature)
	{
		_originalCreature = originalCreature;
		
//		_editedCreature = new Creature(originalCreature);
	}
}
