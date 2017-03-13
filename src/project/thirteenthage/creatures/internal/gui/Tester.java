package project.thirteenthage.creatures.internal.gui;

import javax.swing.JFrame;

import project.thirteenthage.creatures.internal.gui.views.CreatureViewPanel;
import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.internal.interfaces.ICreatureTemplate;
import project.thirteenthage.creatures.loaders.CreatureTemplateLoader;

public class Tester
{
	public static void main(final String[] args)
	{
		final ICreatureTemplate template = CreatureTemplateLoader.getInstance().get("creature_dire_rat");
		final ICreature rat = template.toCreature();

		final JFrame frame = new JFrame("Test viewer");

		final CreatureViewPanel panel = new CreatureViewPanel(rat);

		frame.add(panel);
		panel.setVisible(true);
		frame.pack();
		frame.setVisible(true);
	}
}
