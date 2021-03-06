package legacy.project.thirteenthage.creatures.internal.gui.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import project.library.marky.html.Html;
import project.library.marky.logger.ApplicationLogger;
import legacy.project.thirteenthage.creatures.interfaces.IView;
import legacy.project.thirteenthage.creatures.internal.LevelHelper;
import legacy.project.thirteenthage.creatures.internal.conversions.Conversions;
import legacy.project.thirteenthage.creatures.internal.gui.StyleConstants;
import legacy.project.thirteenthage.creatures.internal.interfaces.IAttack;
import legacy.project.thirteenthage.creatures.internal.interfaces.ICreature;
import legacy.project.thirteenthage.creatures.internal.interfaces.ISpecial;

/**
 * A {@link JPanel} that displays a creature.
 */
@SuppressWarnings("serial")
public class CreatureViewPanel extends JPanel implements IView
{
	private final ICreature _creature;
	private final CreatureInfoPanel _infoPanel;
	private final CreatureAttackPanel _attackPanel;
	private final CreatureStatsPanel _statsPanel;
	private final JLabel _nastierLabel = new JLabel(Html.BEGIN + Html.BEGIN_UNDERLINE + "Nastier specials" + Html.END_UNDERLINE + Html.END);


	/**
	 * Constructor.
	 * 
	 * @param creature
	 *            this creature will be displayed.
	 */
	public CreatureViewPanel(final ICreature creature)
	{
		super();

		final int preferredHeight = StyleConstants.CREATURE_VIEW_PANEL_HEIGHT;
		final int preferredWidth = StyleConstants.CREATURE_VIEW_PANEL_WIDTH;

		if (creature == null)
		{
			throw new IllegalArgumentException("Parameter 'creature' must not be null.");
		}

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		_creature = creature;

		final JPanel namePanel = new JPanel();
		final JPanel blockPanel = new JPanel();
		blockPanel.setLayout(new BoxLayout(blockPanel, BoxLayout.X_AXIS));

		_infoPanel = new CreatureInfoPanel();
		_attackPanel = new CreatureAttackPanel();
		_statsPanel = new CreatureStatsPanel();
		_nastierLabel.setBorder(StyleConstants.DEFAULT_EMPTY_BORDER);

		_infoPanel.setPreferredSize(new Dimension(preferredWidth * 12 / 100, preferredHeight));
		_statsPanel.setPreferredSize(new Dimension(preferredWidth * 12 / 100, preferredHeight));
		_attackPanel.setPreferredSize(new Dimension(preferredWidth * 70 / 100, preferredHeight));

		final JScrollPane attackScrollbar = new JScrollPane(_attackPanel);
		attackScrollbar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		attackScrollbar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		attackScrollbar.setPreferredSize(new Dimension(preferredWidth * 86 / 100, preferredHeight));

		blockPanel.add(_infoPanel);
		blockPanel.add(attackScrollbar);
		blockPanel.add(_statsPanel);

		final JLabel nameLabel = new JLabel(_creature.getName());
		namePanel.add(nameLabel);
		namePanel.setBackground(StyleConstants.BACKGROUND_DARK);
		nameLabel.setForeground(Color.WHITE);

		add(namePanel);
		add(blockPanel);

		this.setBorder(StyleConstants.DEFAULT_EMPTY_BORDER);
		this.setBackground(StyleConstants.BACKGROUND_DARK);
		this.setPreferredSize(new Dimension(preferredWidth, preferredHeight));

		updateView();
	}


	@Override
	public void updateView()
	{
		ApplicationLogger.getLogger().info("Update view");
		_infoPanel.updateView();
		_attackPanel.updateView();
		_statsPanel.updateView();
	}

	/**
	 * The info panel contains the level, size and labels of the creature.
	 */
	private class CreatureInfoPanel extends JPanel implements IView
	{
		private final JLabel _size;
		private final JLabel _level;
		private final List<JLabel> _labels = new ArrayList<JLabel>();


		private CreatureInfoPanel()
		{
			_size = new JLabel();
			_level = new JLabel();

			this.add(_size);
			this.add(_level);

			this.setBackground(StyleConstants.BACKGROUND_LIGHT);
			this.setBorder(StyleConstants.DEFAULT_EMPTY_BORDER);

			updateView();
		}


		@Override
		public void updateView()
		{
			// update the components
			_size.setText(_creature.getSize().toGuiText());
			_level.setText(LevelHelper.getLevelText(_creature.getLevel()));

			for (final JLabel label : _labels)
			{
				this.remove(label);
			}

			_labels.clear();

			for (final String labelText : _creature.getLabels())
			{
				final JLabel label = new JLabel(labelText);
				_labels.add(label);
			}

			// update the actual view

			setLayout(new GridLayout(2 + _labels.size(), 1));
			for (final JLabel label : _labels)
			{
				this.add(label);
			}
		}
	}

	/**
	 * The attack panel contains the initiative, the individual attacks with
	 * special triggers, special abilities, and the 'nastier specials'.
	 */
	private class CreatureAttackPanel extends JPanel implements IView
	{
		private final JLabel _initiative = new JLabel();

		private final List<AttackViewLabel> _attacks = new ArrayList<AttackViewLabel>();
		private final List<JLabel> _specials = new ArrayList<JLabel>();
		private final List<JLabel> _nastier = new ArrayList<JLabel>();


		private CreatureAttackPanel()
		{
			super();
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

			_initiative.setBorder(StyleConstants.DEFAULT_EMPTY_BORDER);
			this.add(_initiative);
			this.setBorder(StyleConstants.DEFAULT_EMPTY_BORDER);
		}


		@Override
		public void updateView()
		{
			_initiative.setText("Initiative +" + _creature.getInitiative());

			for (final AttackViewLabel label : _attacks)
				remove(label);
			for (final JLabel label : _specials)
				remove(label);
			for (final JLabel label : _nastier)
				remove(label);

			remove(_nastierLabel);

			_attacks.clear();
			_specials.clear();
			_nastier.clear();

			for (final IAttack attack : _creature.getAttacks())
			{
				final AttackViewLabel label = new AttackViewLabel(attack);
				_attacks.add(label);
				this.add(label);
			}
			for (final ISpecial special : _creature.getSpecials())
			{
				final SpecialViewLabel label = new SpecialViewLabel(special);
				_specials.add(label);
				this.add(label);
			}

			if (!_creature.getNastierSpecials().isEmpty()) this.add(_nastierLabel);

			for (final ISpecial special : _creature.getNastierSpecials())
			{
				final SpecialViewLabel label = new SpecialViewLabel(special);
				_nastier.add(label);
				this.add(label);
			}
		}
	}

	/**
	 * The stats panel contains the defenses and hit points.
	 */
	private class CreatureStatsPanel extends JPanel implements IView
	{
		private final JLabel _labelAC;
		private final JLabel _labelPD;
		private final JLabel _labelMD;
		private final JLabel _labelHP;


		private CreatureStatsPanel()
		{
			super();

			_labelAC = new JLabel();
			_labelPD = new JLabel();
			_labelMD = new JLabel();
			_labelHP = new JLabel();

			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.add(_labelAC);
			this.add(_labelPD);
			this.add(_labelMD);
			this.add(_labelHP);
			this.setBorder(StyleConstants.DEFAULT_EMPTY_BORDER);
			this.setBackground(StyleConstants.BACKGROUND_LIGHT);

			updateView();
		}


		@Override
		public void updateView()
		{
			_labelAC.setText("AC " + _creature.getAC());
			_labelPD.setText("PD " + _creature.getPD());
			_labelMD.setText("MD " + _creature.getMD());
			_labelHP.setText("HP " + Conversions.round(_creature.getHP()));
		}
	}
}
