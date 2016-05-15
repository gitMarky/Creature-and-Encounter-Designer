package project.thirteenthage.creatures.internal.gui.views;

import javax.swing.JLabel;
import javax.swing.JPanel;

import project.thirteenthage.creatures.creature.EditableCreatureTemplate;
import project.thirteenthage.creatures.interfaces.IView;
import project.thirteenthage.creatures.internal.Constants;
import project.thirteenthage.creatures.internal.gui.CreaturePanel;
import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.internal.interfaces.ICreatureTemplate;

@SuppressWarnings("serial")
public class CreatureEditPanel extends JPanel implements IView
{
	private final LevelAdjustPanel _levelAdjust = new LevelAdjustPanel();
	private final AmountChoicePanel _levelSetter = new AmountChoicePanel("Level");
	private final AmountChoicePanel _attackSetter = new AmountChoicePanel("Attack");
	private final AmountChoicePanel _acSetter = new AmountChoicePanel("AC");
	private final AmountChoicePanel _pdSetter = new AmountChoicePanel("PD");
	private final AmountChoicePanel _mdSetter = new AmountChoicePanel("MD");
	private final AmountChoicePanel _hpSetter = new AmountChoicePanel("HP");
	private CreaturePanel _creaturePanel;

	private ICreature _originalCreature = null;
	private EditableCreatureTemplate _editedCreature = null;
	private boolean _isCreatureReset;


	public CreatureEditPanel()
	{
		super();
		this.add(new JLabel("TODO"));
		addSetter(_levelSetter, Constants.MIN_LEVEL, Constants.MAX_LEVEL);
		this.add(_levelAdjust);
		addSetter(_attackSetter, Constants.MIN_STAT_MODIFIER, Constants.MAX_STAT_MODIFIER);
		addSetter(_acSetter, Constants.MIN_STAT_MODIFIER, Constants.MAX_STAT_MODIFIER);
		addSetter(_pdSetter, Constants.MIN_STAT_MODIFIER, Constants.MAX_STAT_MODIFIER);
		addSetter(_mdSetter, Constants.MIN_STAT_MODIFIER, Constants.MAX_STAT_MODIFIER);
		addSetter(_hpSetter, Constants.MIN_HP_MODIFIER, Constants.MAX_HP_MODIFIER);
		
		_hpSetter.setOutputText("%+d %%"); // display percent
		_hpSetter.setButtonStep(5); // change 5% at once
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
		resetToDefaults(_editedCreature);
	}


	/**
	 * Sets the edited creature template stats to the values in the setters.
	 */
	private void updateCreature()
	{
		if (_isCreatureReset)
		{
			_editedCreature.setLevel(_levelSetter.getAmount());
			_editedCreature.setAttack(_attackSetter.getAmount());
			_editedCreature.setAC(_acSetter.getAmount());
			_editedCreature.setPD(_pdSetter.getAmount());
			_editedCreature.setMD(_mdSetter.getAmount());
			_editedCreature.setHP(integerToPercentage(_hpSetter.getAmount()));
		}
		updateLevelAdjust();
	}


	/**
	 * Sets the edited creature template stats to the values of its original template.
	 */
	private void resetToDefaults(final ICreatureTemplate template)
	{
		_isCreatureReset = false;
		_levelSetter.setAmount(template.getLevel());
		_attackSetter.setAmount(template.getModifierAttack());
		_acSetter.setAmount(template.getModifierAC());
		_pdSetter.setAmount(template.getModifierPD());
		_mdSetter.setAmount(template.getModifierMD());
		_hpSetter.setAmount(percentageToInteger(template.getModifierHP()));
		_isCreatureReset = true;
		updateLevelAdjust();
	}


	private void updateLevelAdjust()
	{
		_levelAdjust.display(_attackSetter.getAmount(), _acSetter.getAmount(), _pdSetter.getAmount(), _mdSetter.getAmount(), integerToPercentage(_hpSetter.getAmount()));
	}
	
	private double integerToPercentage(int amount)
	{
		return 1.0 + amount / 100.0;
	}
	
	private int percentageToInteger(double percentage)
	{
		return (int) Math.round((percentage - 1.0) * 100);
	}
}
