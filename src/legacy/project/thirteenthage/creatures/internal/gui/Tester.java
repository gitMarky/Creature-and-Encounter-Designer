package legacy.project.thirteenthage.creatures.internal.gui;

import javax.swing.JFrame;

import legacy.project.thirteenthage.creatures.internal.gui.views.CreatureViewPanel;
import legacy.project.thirteenthage.creatures.internal.interfaces.ICreature;
import legacy.project.thirteenthage.creatures.internal.interfaces.ICreatureTemplate;
import legacy.project.thirteenthage.creatures.loaders.CreatureTemplateLoader;

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
