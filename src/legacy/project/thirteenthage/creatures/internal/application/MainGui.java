package legacy.project.thirteenthage.creatures.internal.application;

import project.thirteenthage.creatures.model.data.DataBaseContainer;
import legacy.project.thirteenthage.creatures.internal.gui.CreatureGui;
import legacy.project.thirteenthage.creatures.internal.gui.GuiContainer;

public class MainGui extends GuiContainer
{
	public static void main(final String[] args)
	{
		new MainGui().execute();
	}

	@Override
	protected Runnable getMainTask()
	{
		final Runnable mainTask = new Runnable()
		{
			@Override
			public void run()
			{
				DataBaseContainer.load();
				CreatureGui.start();
			}
		};
		return mainTask;
	}
}
