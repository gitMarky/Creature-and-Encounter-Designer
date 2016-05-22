package project.thirteenthage.creatures.internal.gui.views;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionListener;

import project.thirteenthage.creatures.creature.EditableCreatureTemplate;
import project.thirteenthage.creatures.interfaces.IView;
import project.thirteenthage.creatures.internal.Constants;
import project.thirteenthage.creatures.internal.gui.CreatureGui;
import project.thirteenthage.creatures.internal.gui.CreaturePanel;
import project.thirteenthage.creatures.internal.interfaces.IAttack;
import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.internal.interfaces.ICreatureTemplate;
import project.thirteenthage.creatures.internal.interfaces.ISpecial;
import project.thirteenthage.creatures.lists.Lists;
import project.thirteenthage.creatures.loaders.AttackTemplateLoader;
import project.thirteenthage.creatures.loaders.SpecialTemplateLoader;
import project.thirteenthage.creatures.mechanics.LevelAdjustment;

@SuppressWarnings("serial")
public class CreatureEditPanel extends JPanel implements IView, ActionListener
{
	// editing the stats
	private final LevelAdjustPanel _levelAdjust = new LevelAdjustPanel();
	private final AmountChoicePanel _levelSetter = new AmountChoicePanel("Base Level");
	private final AmountChoicePanel _attackSetter = new AmountChoicePanel("Attack");
	private final AmountChoicePanel _acSetter = new AmountChoicePanel("AC");
	private final AmountChoicePanel _pdSetter = new AmountChoicePanel("PD");
	private final AmountChoicePanel _mdSetter = new AmountChoicePanel("MD");
	private final AmountChoicePanel _hpSetter = new AmountChoicePanel("HP");
	private final AmountChoicePanel _damageSetter = new AmountChoicePanel("Damage");
	private final AmountChoicePanel _iniSetter = new AmountChoicePanel("Initiative");
	private final DefenseChoicePanel _defenseSetter = new DefenseChoicePanel();
	private final TextChoicePanel _nameSetter = new TextChoicePanel("Name");
	private final SizeChoicePanel _sizeSetter = new SizeChoicePanel();
	// editing labels
	private final JButton _labelsButton = new JButton("Labels");
	// editing attacks
	private final JButton _attacksButton = new JButton("Attacks");
	// editing specials
	private final JButton _specialsButton = new JButton("Specials");
	// editing nastier specials
	private final JButton _nastierButton = new JButton("Nastier Specials");
	// the creature view
	private CreaturePanel _creaturePanel;
	private JFrame _listFrame;

	// creature data
	private ICreature _originalCreature = null;
	private EditableCreatureTemplate _editedCreature = null;
	private boolean _isCreatureReset;


	public CreatureEditPanel()
	{
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(new JLabel("TODO"));
		// layout similar to the creature display:
		// left column
		addSetter(_nameSetter);
		addSetter(_levelSetter, Constants.MIN_LEVEL, Constants.MAX_LEVEL);
		this.add(_levelAdjust);
		addSetter(_sizeSetter);
		this.add(_labelsButton);
		_labelsButton.addActionListener(this);
		// center column
		this.add(_attacksButton);
		_attacksButton.addActionListener(this);
		this.add(_specialsButton);
		_specialsButton.addActionListener(this);
		this.add(_nastierButton);
		_nastierButton.addActionListener(this);
		// right column
		addSetter(_iniSetter, Constants.MIN_INITIATIVE, Constants.MAX_INITIATIVE);
		addSetter(_attackSetter, Constants.MIN_STAT_MODIFIER, Constants.MAX_STAT_MODIFIER);
		addSetter(_acSetter, Constants.MIN_STAT_MODIFIER, Constants.MAX_STAT_MODIFIER);
		addSetter(_pdSetter, Constants.MIN_STAT_MODIFIER, Constants.MAX_STAT_MODIFIER);
		addSetter(_mdSetter, Constants.MIN_STAT_MODIFIER, Constants.MAX_STAT_MODIFIER);
		addSetter(_hpSetter, Constants.MIN_HP_MODIFIER, Constants.MAX_HP_MODIFIER);
		addSetter(_damageSetter, Constants.MIN_HP_MODIFIER, Constants.MAX_HP_MODIFIER);
		addSetter(_defenseSetter);

		_listFrame = new JFrame();

		_hpSetter.setOutputText("%+d %%"); // display percent
		_hpSetter.setButtonStep(5); // change 5% at once

		_damageSetter.setOutputText("%+d %%"); // display percent
		_damageSetter.setButtonStep(5); // change 5% at once
	}


	private void addSetter(final AmountChoicePanel setter, final int lowerBound, final int upperBound)
	{
		if (setter == null)
		{
			throw new IllegalArgumentException("Parameter 'setter' must not be null.");
		}

		addSetter(setter);
		setter.setBounds(lowerBound, upperBound);
	}


	private void addSetter(final ChoicePanel setter)
	{
		if (setter == null)
		{
			throw new IllegalArgumentException("Parameter 'setter' must not be null.");
		}

		this.add(setter);
		setter.setUpdateView(this);
	}


	@Override
	public void updateView()
	{
		updateCreature();
		displayCreature();
	}


	public void applyEditing(final CreaturePanel creaturePanel, final ICreature originalCreature)
	{
		if (creaturePanel == null)
		{
			throw new IllegalArgumentException("Parameter 'creaturePanel' must not be null.");
		}
		if (originalCreature == null)
		{
			throw new IllegalArgumentException("Parameter 'originalCreature' must not be null.");
		}

		if (_editedCreature == null) return;

		displayCreature();
	}


	private void displayCreature()
	{
		if (_editedCreature != null) _creaturePanel.displayCreature(build());
	}


	private ICreature build()
	{
		return _editedCreature.toCreature();
	}


	public void cancelEditing(final CreaturePanel creaturePanel, final ICreature originalCreature)
	{
		if (creaturePanel == null)
		{
			throw new IllegalArgumentException("Parameter 'creaturePanel' must not be null.");
		}
		if (originalCreature == null)
		{
			throw new IllegalArgumentException("Parameter 'originalCreature' must not be null.");
		}

		if (_originalCreature == null) return;

		creaturePanel.displayCreature(_originalCreature);
	}


	public void startEditing(final CreaturePanel creaturePanel, final ICreature originalCreature)
	{
		if (creaturePanel == null)
		{
			throw new IllegalArgumentException("Parameter 'creaturePanel' must not be null.");
		}
		if (originalCreature == null)
		{
			throw new IllegalArgumentException("Parameter 'originalCreature' must not be null.");
		}

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
			_editedCreature.setInitiative(_iniSetter.getAmount());
			_editedCreature.setAttack(_attackSetter.getAmount());
			_editedCreature.setAC(_acSetter.getAmount());
			_editedCreature.setPD(_pdSetter.getAmount());
			_editedCreature.setMD(_mdSetter.getAmount());
			_editedCreature.setHP(integerToPercentage(_hpSetter.getAmount()));
			_editedCreature.setDamage(integerToPercentage(_damageSetter.getAmount()));
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
		if (template == null)
		{
			throw new IllegalArgumentException("Parameter 'template' must not be null.");
		}

		_isCreatureReset = false;
		_nameSetter.setText(template.getName());
		_levelSetter.setAmount(template.getLevel());
		_attackSetter.setAmount(template.getModifierAttack());
		_iniSetter.setAmount(template.getModifierInitiative());
		_acSetter.setAmount(template.getModifierAC());
		_pdSetter.setAmount(template.getModifierPD());
		_mdSetter.setAmount(template.getModifierMD());
		_hpSetter.setAmount(percentageToInteger(template.getModifierHP()));
		_damageSetter.setAmount(percentageToInteger(template.getModifierDamage()));
		_defenseSetter.setBetterDefense(template.getBetterDefense());
		_sizeSetter.setCreatureSize(template.getSize());
		_isCreatureReset = true;
		
		updateLevelAdjust();
	}


	private void updateLevelAdjust()
	{
		_damageSetter.setVisible(!LevelAdjustment.useOriginalCalculation());

		_levelAdjust.display(_attackSetter.getAmount(), _acSetter.getAmount(), _pdSetter.getAmount(), _mdSetter.getAmount(), integerToPercentage(_hpSetter.getAmount()));
	}


	private double integerToPercentage(final int amount)
	{
		return 1.0 + amount / 100.0;
	}


	private int percentageToInteger(final double percentage)
	{
		return (int) Math.round((percentage - 1.0) * 100);
	}


	@Override
	public void actionPerformed(final ActionEvent action)
	{
		if (action.getSource() == _labelsButton)
		{
			showLabelsSelection();
		}

		if (action.getSource() == _attacksButton)
		{
			showAttacksSelection();
		}

		if (action.getSource() == _specialsButton)
		{
			showSpecialsSelection();
		}

		if (action.getSource() == _nastierButton)
		{
			showNastierSpecialsSelection();
		}
	}


	private void showLabelsSelection()
	{
		final ListTransferPanel<String> listTransfer = new ListTransferPanel<String>(Lists.labels(), _editedCreature.getLabels());
		setupListSelectionPanel(listTransfer);
		setupListSelectionFrame("Select labels", listTransfer);
		setButtonsEnabled(false);
	}


	private void showAttacksSelection()
	{
		final ListTransferPanel<IAttack> listTransfer = new ListTransferPanel<IAttack>(new ArrayList<IAttack>(AttackTemplateLoader.getInstance().getTemplates().values()), _editedCreature.getAttacks());
		setupListSelectionPanel(listTransfer);
		setupListSelectionFrame("Select attacks", listTransfer);
		setButtonsEnabled(false);
		addAttackDescription(listTransfer);
	}


	private void showSpecialsSelection()
	{
		final ListTransferPanel<ISpecial> listTransfer = new ListTransferPanel<ISpecial>(new ArrayList<ISpecial>(SpecialTemplateLoader.getInstance().getTemplates().values()), _editedCreature.getSpecials());
		setupListSelectionPanel(listTransfer);
		setupListSelectionFrame("Select specials", listTransfer);
		setButtonsEnabled(false);

		addSpecialsDescription(listTransfer);
	}


	private void showNastierSpecialsSelection()
	{
		// set up the list
		final ListTransferPanel<ISpecial> listTransfer = new ListTransferPanel<ISpecial>(new ArrayList<ISpecial>(SpecialTemplateLoader.getInstance().getTemplates().values()), _editedCreature.getNastierSpecials());
		setupListSelectionPanel(listTransfer);
		setupListSelectionFrame("Select nastier specials", listTransfer);
		setButtonsEnabled(false);

		addSpecialsDescription(listTransfer);
	}


	private void confirmListSelection()
	{
		setButtonsEnabled(true);
		if (_listFrame != null) _listFrame.setVisible(false);
		updateView();
	}


	private void setButtonsEnabled(final boolean enabled)
	{
		_labelsButton.setEnabled(enabled);
		_attacksButton.setEnabled(enabled);
		_specialsButton.setEnabled(enabled);
		_nastierButton.setEnabled(enabled);
	}


	private <T> void setupListSelectionPanel(final ListTransferPanel<T> listTransfer)
	{
		if (listTransfer == null)
		{
			throw new IllegalArgumentException("Parameter 'listTransfer' must not be null.");
		}

		listTransfer.setUpdateView(this);
		listTransfer.setLeftListLocked(true);
		listTransfer.setLeftListUnique(true);
		listTransfer.setRightListUnique(true);
		listTransfer.getConfirmButton().addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(final ActionEvent action)
			{
				confirmListSelection();
			}
		});
	}


	private <T> void setupListSelectionFrame(final String title, final ListTransferPanel<T> listTransfer)
	{
		if (title == null)
		{
			throw new IllegalArgumentException("Parameter 'title' must not be null.");
		}
		if (listTransfer == null)
		{
			throw new IllegalArgumentException("Parameter 'listTransfer' must not be null.");
		}

		_listFrame = new JFrame(title);
		_listFrame.setLayout(new GridLayout(1, 2));
		_listFrame.add(listTransfer);
		_listFrame.pack();
		_listFrame.setVisible(true);
		_listFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	}


	private void addAttackDescription(final ListTransferPanel<IAttack> listTransfer)
	{
		if (listTransfer == null)
		{
			throw new IllegalArgumentException("Parameter 'listTransfer' must not be null.");
		}

		final JPanel innerPanel = new JPanel();
		final ListSelectionListener listener = createListSelectionListener(innerPanel, IAttack.class, AttackViewLabel.class);
		addListSelectionDescription(listTransfer, innerPanel, listener);
	}


	private void addSpecialsDescription(final ListTransferPanel<ISpecial> listTransfer)
	{
		if (listTransfer == null)
		{
			throw new IllegalArgumentException("Parameter 'listTransfer' must not be null.");
		}

		final JPanel innerPanel = new JPanel();
		final ListSelectionListener listener = createListSelectionListener(innerPanel, ISpecial.class, SpecialViewLabel.class);
		addListSelectionDescription(listTransfer, innerPanel, listener);
	}


	private <T> void addListSelectionDescription(final ListTransferPanel<T> listTransfer, final JPanel innerPanel, final ListSelectionListener listener)
	{
		if (listTransfer == null)
		{
			throw new IllegalArgumentException("Parameter 'listTransfer' must not be null.");
		}
		if (listener == null)
		{
			throw new IllegalArgumentException("Parameter 'listener' must not be null.");
		}
		if (innerPanel == null)
		{
			throw new IllegalArgumentException("Parameter 'innerPanel' must not be null.");
		}

		innerPanel.setBorder(BorderFactory.createTitledBorder("Description"));
		_listFrame.add(innerPanel);
		listTransfer.getLeftList().addListSelectionListener(listener);
		listTransfer.getRightList().addListSelectionListener(listener);
	}


	private <T, C extends Component> ListSelectionListener createListSelectionListener(final JPanel innerPanel, final Class<T> listSelectionType, final Class<C> descriptionComponentType)
	{
		return new DisplayDescriptionListener<T, C>(_listFrame, innerPanel, listSelectionType, descriptionComponentType);
	};
}
