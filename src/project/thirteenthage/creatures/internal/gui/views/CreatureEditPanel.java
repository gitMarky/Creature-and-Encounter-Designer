package project.thirteenthage.creatures.internal.gui.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import project.thirteenthage.creatures.creature.EditableCreatureTemplate;
import project.thirteenthage.creatures.interfaces.IView;
import project.thirteenthage.creatures.internal.Constants;
import project.thirteenthage.creatures.internal.gui.CreaturePanel;
import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.internal.interfaces.ICreatureTemplate;
import project.thirteenthage.creatures.lists.Lists;

@SuppressWarnings("serial")
public class CreatureEditPanel extends JPanel implements IView, ActionListener
{
	private final LevelAdjustPanel _levelAdjust = new LevelAdjustPanel();
	private final AmountChoicePanel _levelSetter = new AmountChoicePanel("Base Level");
	private final AmountChoicePanel _attackSetter = new AmountChoicePanel("Attack");
	private final AmountChoicePanel _acSetter = new AmountChoicePanel("AC");
	private final AmountChoicePanel _pdSetter = new AmountChoicePanel("PD");
	private final AmountChoicePanel _mdSetter = new AmountChoicePanel("MD");
	private final AmountChoicePanel _hpSetter = new AmountChoicePanel("HP");
	private final DefenseChoicePanel _defenseSetter = new DefenseChoicePanel();
	private final TextChoicePanel _nameSetter = new TextChoicePanel("Name");
	private final SizeChoicePanel _sizeSetter = new SizeChoicePanel();
	private final JButton _labelsButton = new JButton("Labels");
	private JFrame _labelsFrame;
	private CreaturePanel _creaturePanel;

	private ICreature _originalCreature = null;
	private EditableCreatureTemplate _editedCreature = null;
	private boolean _isCreatureReset;


	public CreatureEditPanel()
	{
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(new JLabel("TODO"));
		addSetter(_nameSetter);
		addSetter(_levelSetter, Constants.MIN_LEVEL, Constants.MAX_LEVEL);
		this.add(_levelAdjust);
		addSetter(_sizeSetter);
		this.add(_labelsButton); _labelsButton.addActionListener(this);
		addSetter(_attackSetter, Constants.MIN_STAT_MODIFIER, Constants.MAX_STAT_MODIFIER);
		addSetter(_acSetter, Constants.MIN_STAT_MODIFIER, Constants.MAX_STAT_MODIFIER);
		addSetter(_pdSetter, Constants.MIN_STAT_MODIFIER, Constants.MAX_STAT_MODIFIER);
		addSetter(_mdSetter, Constants.MIN_STAT_MODIFIER, Constants.MAX_STAT_MODIFIER);
		addSetter(_hpSetter, Constants.MIN_HP_MODIFIER, Constants.MAX_HP_MODIFIER);
		addSetter(_defenseSetter);
		
		_labelsFrame = new JFrame();
		
		_hpSetter.setOutputText("%+d %%"); // display percent
		_hpSetter.setButtonStep(5); // change 5% at once
	}


	private void addSetter(final AmountChoicePanel setter, int lowerBound, int upperBound)
	{
		addSetter(setter);
		setter.setBounds(lowerBound, upperBound);
	}


	private void addSetter(final ChoicePanel setter)
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
			_editedCreature.setName(_nameSetter.getText());
			_editedCreature.setLevel(_levelSetter.getAmount());
			_editedCreature.setAttack(_attackSetter.getAmount());
			_editedCreature.setAC(_acSetter.getAmount());
			_editedCreature.setPD(_pdSetter.getAmount());
			_editedCreature.setMD(_mdSetter.getAmount());
			_editedCreature.setHP(integerToPercentage(_hpSetter.getAmount()));
			_editedCreature.setBetterDefense(_defenseSetter.getBetterDefense());
			_editedCreature.setSize(_sizeSetter.getCreatureSize());
		}
		updateLevelAdjust();
	}


	/**
	 * Sets the edited creature template stats to the values of its original
	 * template.
	 */
	private void resetToDefaults(final ICreatureTemplate template)
	{
		_isCreatureReset = false;
		_nameSetter.setText(template.getName());
		_levelSetter.setAmount(template.getLevel());
		_attackSetter.setAmount(template.getModifierAttack());
		_acSetter.setAmount(template.getModifierAC());
		_pdSetter.setAmount(template.getModifierPD());
		_mdSetter.setAmount(template.getModifierMD());
		_hpSetter.setAmount(percentageToInteger(template.getModifierHP()));
		_defenseSetter.setBetterDefense(template.getBetterDefense());
		_sizeSetter.setCreatureSize(template.getSize());
		_isCreatureReset = true;
		
//		_labelsFrame.setVisible(false);
//		_labelsFrame.removeAll();
//		ListTransferPanel<String> listTransfer = new ListTransferPanel<String>(Lists.labels(), _editedCreature.getLabels());
//		_labelsFrame.add(listTransfer);

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


	@Override
	public void actionPerformed(ActionEvent action)
	{
		if (action.getSource() == _labelsButton)
		{
			showLabelsSelection();
		}
	}


	private void showLabelsSelection()
	{
		_labelsFrame = new JFrame("Select labels");
		ListTransferPanel<String> listTransfer = new ListTransferPanel<String>(Lists.labels(), _editedCreature.getLabels());
		listTransfer.setUpdateView(this);
		_labelsFrame.add(listTransfer);
		_labelsFrame.pack();
		_labelsFrame.setVisible(true);
		_labelsFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		_labelsButton.setEnabled(false);
		listTransfer.setLeftListLocked(true);
		listTransfer.setLeftListUnique(true);
		listTransfer.setRightListUnique(true);
		listTransfer.getConfirmButton().addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent action)
			{
				confirmLabelsSelection();
			}
		});
	}
	
	
	private void confirmLabelsSelection()
	{
		_labelsButton.setEnabled(true);
		if (_labelsFrame != null) _labelsFrame.setVisible(false);
		updateView();
	}
}
