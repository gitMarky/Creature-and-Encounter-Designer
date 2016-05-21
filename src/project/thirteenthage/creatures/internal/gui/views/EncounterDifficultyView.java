package project.thirteenthage.creatures.internal.gui.views;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class EncounterDifficultyView extends JPanel
{
	public static String DIFFICULTY_NOT_SET = "-";

	private JLabel _difficultyLabel = new JLabel(DIFFICULTY_NOT_SET);


	public EncounterDifficultyView()
	{
		super();

		this.setLayout(new GridLayout(1, 2));
		this.add(new JLabel("Difficulty: "));
		this.add(_difficultyLabel);
	}


	public void displayDifficulty(double difficulty)
	{
		if (Double.isNaN(difficulty))
		{
			_difficultyLabel.setText(DIFFICULTY_NOT_SET);
		}
		else
		{
			_difficultyLabel.setText(String.format("%.2f", difficulty));
		}
	}
}
