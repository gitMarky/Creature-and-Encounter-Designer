package project.thirteenthage.creatures.internal.gui.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import project.thirteenthage.creatures.interfaces.IView;
import project.thirteenthage.creatures.internal.gui.CreatureGui;
import project.thirteenthage.creatures.internal.gui.StyleConstants;
import project.thirteenthage.creatures.internal.interfaces.ICreature;

/**
 * A {@link JPanel} that displays a creature, with additional settings for an
 * encounter.
 */
@SuppressWarnings("serial")
public class CreatureEncounterPanel extends JPanel implements IView, ActionListener
{
	private final ICreature _creature;
	private final CreatureViewPanel _viewPanel;
	private final JButton _removeButton = new JButton("Remove");
	private final AmountChoicePanel _amountChoicePanel = new AmountChoicePanel("Amount");


	/**
	 * Constructor.
	 * 
	 * @param creature
	 *            this creature will be displayed.
	 */
	public CreatureEncounterPanel(final ICreature creature)
	{
		super();
		if (creature == null)
		{
			throw new IllegalArgumentException("Parameter 'creature' must not be null.");
		}

		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		_creature = creature;
		_viewPanel = new CreatureViewPanel(creature);

		this.add(_viewPanel);
		this.add(configureOptionsPanel());
		this.setBorder(StyleConstants.DEFAULT_EMPTY_BORDER);
		this.setBackground(StyleConstants.BACKGROUND_DARK);
	}


	@Override
	public void updateView()
	{
		_viewPanel.updateView();
	}


	private JPanel configureOptionsPanel()
	{
		final JPanel optionsPanel = new JPanel();
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
		optionsPanel.setBackground(StyleConstants.BACKGROUND_DARK);

		_amountChoicePanel.setBackground(StyleConstants.BACKGROUND_DARK);
		_amountChoicePanel.setUpdateView(CreatureGui.GUI.getEncounterPanel());
		optionsPanel.add(_amountChoicePanel);

		optionsPanel.add(_removeButton);
		_removeButton.addActionListener(this);

		return optionsPanel;
	}


	public int getAmount()
	{
		return _amountChoicePanel.getAmount();
	}


	@Override
	public void actionPerformed(final ActionEvent event)
	{
		if (event.getSource() == _removeButton)
		{
			CreatureGui.GUI.getEncounterPanel().removeCreature(_creature);
		}
	}
}
