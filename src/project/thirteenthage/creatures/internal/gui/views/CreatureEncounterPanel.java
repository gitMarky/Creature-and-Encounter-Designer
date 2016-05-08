package project.thirteenthage.creatures.internal.gui.views;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

import project.thirteenthage.creatures.interfaces.IView;
import project.thirteenthage.creatures.internal.gui.StyleConstants;
import project.thirteenthage.creatures.internal.interfaces.ICreature;

/**
 * A {@link JPanel} that displays a creature, with additional settings
 * for an encounter.
 */
@SuppressWarnings("serial")
public class CreatureEncounterPanel extends JPanel implements IView
{
	private final ICreature _creature;
	private final CreatureViewPanel _viewPanel;

	/**
	 * Constructor.
	 * @param creature this creature will be displayed.
	 */
	public CreatureEncounterPanel(final ICreature creature)
	{
		super();
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
		return optionsPanel;
	}
}
