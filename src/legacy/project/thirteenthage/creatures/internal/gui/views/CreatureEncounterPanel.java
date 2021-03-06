package legacy.project.thirteenthage.creatures.internal.gui.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import project.library.marky.logger.ApplicationLogger;
import legacy.project.thirteenthage.creatures.interfaces.IView;
import legacy.project.thirteenthage.creatures.internal.gui.CreatureGui;
import legacy.project.thirteenthage.creatures.internal.gui.StyleConstants;
import legacy.project.thirteenthage.creatures.internal.interfaces.ICreature;

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
		ApplicationLogger.getLogger().info("Update view");
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
	
	
	public void setAmount(int amount)
	{
		_amountChoicePanel.setAmount(amount);
	}


	@Override
	public void actionPerformed(final ActionEvent event)
	{
		if (event.getSource() == _removeButton)
		{
			ApplicationLogger.getLogger().info("Pressed button: Remove from encounter");
			CreatureGui.GUI.getEncounterPanel().removeCreature(_creature);
		}
	}
}
