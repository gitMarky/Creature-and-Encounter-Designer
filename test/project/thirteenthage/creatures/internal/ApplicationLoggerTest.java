package project.thirteenthage.creatures.internal;

import java.io.IOException;

import org.junit.Test;

public class ApplicationLoggerTest
{

	@Test
	public void test() throws SecurityException, IOException
	{		
		ApplicationLogger.getLogger().info("Blablub");
		ApplicationLogger.getLogger().info("Blablub");
		ApplicationLogger.getLogger().info("Blablub");
	}
}
