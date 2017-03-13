package project.thirteenthage.creatures.internal.gui;

import project.thirteenthage.creatures.module.model.DataBaseContainer;

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
