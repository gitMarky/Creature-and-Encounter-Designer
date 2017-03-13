package legacy.project.thirteenthage.creatures.internal.gui.views;

import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import legacy.project.thirteenthage.creatures.internal.interfaces.IAttack;
import legacy.project.thirteenthage.creatures.internal.interfaces.ISpecial;
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


	public void display(final int attack, final int ac, final int pd, final int md, final double hp, final double damage, final List<IAttack> attacks, final List<ISpecial> specials, final List<ISpecial> nastier)
	{
		final int integer = LevelAdjustment.getLevelAdjustment(attack, ac, pd, md, hp, damage, attacks, specials, nastier);
		final double fine = LevelAdjustment.getLevelAdjustmentFine(attack, ac, pd, md, hp, damage, attacks, specials, nastier);

		_label.setText(String.format("%+d, (%+.2f)", integer, fine));
	}
}
