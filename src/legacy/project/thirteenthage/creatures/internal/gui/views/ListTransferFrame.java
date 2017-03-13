package legacy.project.thirteenthage.creatures.internal.gui.views;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * A frame that allows transfer from one list to another list.
 */
@SuppressWarnings("serial")
public class ListTransferFrame extends JFrame
{
	public static void main(final String[] args)
	{
		final JFrame frame = new JFrame();

		final List<String> listA = new ArrayList<String>();
		listA.add("A");
		listA.add("B");
		listA.add("C");
		listA.add("E");
		listA.add("F");

		final List<String> listB = new ArrayList<String>();
		listB.add("D");

		final ListTransferPanel<String> panel = new ListTransferPanel<String>(listA, listB);
		panel.setLeftListLocked(true);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
