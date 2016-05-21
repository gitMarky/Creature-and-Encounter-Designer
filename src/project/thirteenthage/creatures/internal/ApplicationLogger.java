package project.thirteenthage.creatures.internal;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import project.thirteenthage.creatures.internal.gui.CreatureGui;

public class ApplicationLogger
{
	private static Logger _logger = null;


	public static Logger getLogger()
	{
		if (_logger == null)
		{
			_logger = Logger.getLogger(CreatureGui.class.getName());

			FileHandler fh = null;
			try
			{
				fh = new FileHandler(new File("ApplicationLog.log").getAbsolutePath());
			}
			catch (SecurityException e)
			{
				throw new IllegalStateException(e);
			}
			catch (IOException e)
			{
				throw new IllegalStateException(e);
			}
			fh.setLevel(Level.ALL);
			fh.setFormatter(new ApplicationLogFormatter());
			_logger.addHandler(fh);
			_logger.info("Message:");

		}

		return _logger;
	}

	private static class ApplicationLogFormatter extends Formatter
	{
		@SuppressWarnings("deprecation")
		@Override
		public String format(final LogRecord record)
		{
			StringBuilder format = new StringBuilder();
			format.append(new Date(record.getMillis()).toLocaleString());
			extend(format, 25);
			format.append(getShortClassName(record) + "." + record.getSourceMethodName() + "()");
			extend(format, 60);
			format.append(record.getLevel());
			extend(format, 70);
			format.append(record.getMessage());
			format.append(Constants.NEWLINE);
			return format.toString();
		}


		private String getShortClassName(LogRecord record)
		{
			String name = record.getSourceClassName();
			return name.substring(name.lastIndexOf(".") + 1);
		}


		private void extend(final StringBuilder builder, int size)
		{
			while (builder.length() < size)
				builder.append(" ");
		}
	}
}
