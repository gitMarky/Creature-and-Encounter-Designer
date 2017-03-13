package legacy.project.thirteenthage.creatures.internal.gui.views;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import legacy.project.thirteenthage.creatures.mechanics.LevelAdjustment;

/**
 * Does not adjust the level, but displays the level adjustment.
 */
@SuppressWarnings("serial")
public class LevelAdjustPanel extends JPanel
{
	private final JLabel _label = new JLabel();


	public LevelAdjustPanel()
	{
		super();
		this.setBorder(BorderFactory.createTitledBorder("Level adjustment"));
		this.add(_label);
	}


	public void display(final int attack, final int ac, final int pd, final int md, final double hp, final double damage)
	{
		final int integer = LevelAdjustment.getLevelAdjustment(attack, ac, pd, md, hp, damage);
		final double fine = LevelAdjustment.getLevelAdjustmentFine(attack, ac, pd, md, hp, damage);

		_label.setText(String.format("%+d, (%+.2f)", integer, fine));
	}
}
