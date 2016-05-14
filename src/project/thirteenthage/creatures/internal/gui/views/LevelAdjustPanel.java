package project.thirteenthage.creatures.internal.gui.views;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import project.thirteenthage.creatures.mechanics.LevelAdjustment;


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
	
	
	public void display(int attack, int ac, int pd, int md, double hp)
	{
		int integer = LevelAdjustment.getLevelAdjustment(attack, ac, pd, md, hp);
		double fine = LevelAdjustment.getLevelAdjustmentFine(attack, ac, pd, md, hp);
		
		_label.setText(String.format("%+d, (%+.2f)", integer, fine));
	}
}
