package legacy.project.thirteenthage.creatures.internal.gui;

import project.library.marky.logger.ApplicationLogger;
import legacy.project.thirteenthage.creatures.internal.Constants;

public abstract class GuiContainer
{
	protected void execute()
	{
		final Runnable mainTask = getMainTask();

		final Runnable shutdownTask = new Runnable()
		{
			@Override
			public void run()
			{
				ApplicationLogger.getLogger().info(Constants.HLINE);
				ApplicationLogger.getLogger().info("Shutting down");
				ApplicationLogger.getLogger().info(Constants.HLINE);
			}
		};

		final Thread shutdownHook = new Thread(shutdownTask);
		Runtime.getRuntime().addShutdownHook(shutdownHook);


		try
		{
			new Thread(mainTask).start();
		}
		catch (final Exception e)
		{
			ApplicationLogger.getLogger().info("Caught exception");
			ApplicationLogger.getLogger().throwing("", "", e);
		}
	}


	protected abstract Runnable getMainTask();
}
