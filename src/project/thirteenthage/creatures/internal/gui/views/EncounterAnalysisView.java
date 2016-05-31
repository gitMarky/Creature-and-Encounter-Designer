package project.thirteenthage.creatures.internal.gui.views;

import java.util.List;

import javax.swing.JLabel;

import project.thirteenthage.creatures.internal.Html;
import project.thirteenthage.creatures.mechanics.analysis.EncounterAnalysis;

@SuppressWarnings("serial")
public class EncounterAnalysisView extends JLabel
{
	public EncounterAnalysisView()
	{
		super();
	}


	public void displayAnalysis(final EncounterAnalysis analysis)
	{
		StringBuilder text = new StringBuilder();

		text.append(Html.BEGIN);

		// survival times
		text.append("Players could survive " + analysis.getPlayerSurvivalRounds() + " rounds on average" + Html.LINE_BREAK);
		text.append("Monsters could survive " + analysis.getMonsterSurvivalRounds() + " rounds on average" + Html.LINE_BREAK);
		text.append("The expected combat duration is " + analysis.getAverageCombatRounds() + " rounds on average" + Html.LINE_BREAK);

		// damage report
		text.append("After combat the players will be left with: ");
		appendHP(text, analysis.getAverageCombatPlayerHP());

		text.append(Html.LINE_BREAK + "After combat the creatures will be left with: ");
		appendHP(text, analysis.getAverageCombatMonsterHP());

		text.append(Html.END);
		
		setText(text.toString());
	}
	
	
	public void displayNothing()
	{
		setText("");
	}
	


	private void appendHP(final StringBuilder text, final List<String> results)
	{
		for (final String result : results)
		{
			text.append(Html.LINE_BREAK + result);
		}
	}
}
