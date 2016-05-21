package project.thirteenthage.creatures.internal.gui;

import java.io.File;

import javax.swing.JFrame;

import project.thirteenthage.creatures.creature.CreatureTemplate;
import project.thirteenthage.creatures.internal.gui.views.CreatureViewPanel;
import project.thirteenthage.creatures.internal.interfaces.ICreature;

public class Tester
{
	public static void main(final String[] args)
	{
		final File xml = new File("resources/creatures/creature_dire_rat.xml");

		final CreatureTemplate template = new CreatureTemplate(xml);
		final ICreature rat = template.toCreature();

		final JFrame frame = new JFrame("Test viewer");

		final CreatureViewPanel panel = new CreatureViewPanel(rat);

		frame.add(panel);
		panel.setVisible(true);
		frame.pack();
		frame.setVisible(true);
	}
}
