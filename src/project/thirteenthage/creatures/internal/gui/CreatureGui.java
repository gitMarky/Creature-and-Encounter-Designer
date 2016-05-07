package project.thirteenthage.creatures.internal.gui;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import project.thirteenthage.creatures.loaders.AttackTemplateLoader;
import project.thirteenthage.creatures.loaders.CreatureTemplateLoader;

public class CreatureGui
{
	public static void main(String[] args)
	{
		new CreatureGui();
	}
	

	private CreatureGui()
	{
		AttackTemplateLoader.getInstance();
		CreatureTemplateLoader.getInstance();
		
		final JFrame frame = new JFrame();
		final JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel.add(new MenuSelectionPanel());
		panel.add(new CreaturePanel());
		panel.add(new EncounterPanel());

		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}
	
	private class MenuSelectionPanel extends JPanel
	{
	}
	
	private class CreaturePanel extends JPanel
	{
		
	}
	
	private class EncounterPanel extends JPanel
	{
		
	}
}
