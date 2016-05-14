package project.thirteenthage.creatures.internal.gui.views;

import javax.swing.JLabel;
import javax.swing.JPanel;

import project.thirteenthage.creatures.creature.EditableCreatureTemplate;
import project.thirteenthage.creatures.interfaces.IView;
import project.thirteenthage.creatures.internal.Constants;
import project.thirteenthage.creatures.internal.gui.CreaturePanel;
import project.thirteenthage.creatures.internal.interfaces.ICreature;

@SuppressWarnings("serial")
public class CreatureEditPanel extends JPanel implements IView
{
	private final AmountChoicePanel _levelSetter = new AmountChoicePanel("Level");
	private final AmountChoicePanel _acSetter = new AmountChoicePanel("AC");
	private CreaturePanel _creaturePanel;

	private ICreature _originalCreature = null;
	private EditableCreatureTemplate _editedCreature = null;


	public CreatureEditPanel()
	{
		super();
		this.add(new JLabel("TODO"));
		addSetter(_levelSetter, Constants.MIN_LEVEL, Constants.MAX_LEVEL);
		addSetter(_acSetter, Constants.MIN_STAT_MODIFIER, Constants.MAX_STAT_MODIFIER);
	}


	private void addSetter(final AmountChoicePanel setter, int lowerBound, int upperBound)
	{
		addSetter(setter);
		setter.setBounds(lowerBound, upperBound);
	}


	private void addSetter(final AmountChoicePanel setter)
	{
		this.add(setter);
		setter.setUpdateView(this);
	}


	@Override
	public void updateView()
	{
		updateCreature();
		displayCreature();
	}


	public void applyEditing(CreaturePanel creaturePanel, ICreature originalCreature)
	{
		if (_editedCreature == null)
			return;

		displayCreature();
	}


	private void displayCreature()
	{
		_creaturePanel.displayCreature(build());
	}


	private ICreature build()
	{
		return _editedCreature.toCreature();
	}


	public void cancelEditing(CreaturePanel creaturePanel, ICreature originalCreature)
	{
		if (_originalCreature == null)
			return;

		creaturePanel.displayCreature(_originalCreature);
	}


	public void startEditing(CreaturePanel creaturePanel, ICreature originalCreature)
	{
		_creaturePanel = creaturePanel;
		_originalCreature = originalCreature;

		_editedCreature = new EditableCreatureTemplate(originalCreature.getTemplate());
		resetToDefaults();
	}


	/**
	 * Sets the edited creature template stats to the values in the setters.
	 */
	private void updateCreature()
	{
		_editedCreature.setLevel(_levelSetter.getAmount());
		_editedCreature.setAC(_acSetter.getAmount());
	}


	/**
	 * Sets the edited creature template stats to the values of its original template.
	 */
	private void resetToDefaults()
	{
		_levelSetter.setAmount(_editedCreature.getLevel());
		_acSetter.setAmount(_editedCreature.getAC());
	}
}
