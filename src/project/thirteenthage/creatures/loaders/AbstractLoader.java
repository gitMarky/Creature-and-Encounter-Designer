package project.thirteenthage.creatures.loaders;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import project.thirteenthage.creatures.internal.ApplicationLogger;
import project.thirteenthage.creatures.internal.BasicXmlFile;

public abstract class AbstractLoader<T extends Object>
{
	private final Map<String, T> _templates = new HashMap<String, T>();


	public void load(final File fromDirectory)
	{
		if (fromDirectory == null)
		{
			throw new IllegalArgumentException("File was null");
		}
		
		if (fromDirectory.isFile())
		{
			loadFile(fromDirectory);
		}
		else if (fromDirectory.isDirectory())
		{
			for (final File file : fromDirectory.listFiles())
			{
				load(file);
			}
		}
		else
		{
			throw new IllegalArgumentException("Not a file or directory: " + fromDirectory.getAbsolutePath());
		}
	}


	private void loadFile(final File file)
	{
		if (file == null)
		{
			throw new IllegalArgumentException("Parameter 'file' must not be null.");
		}

		// skip anything that is not an xml file
		if (!file.getName().endsWith(LoaderHelper.EXTENSION_XML)) return;
		if (file.getName().startsWith("template")) return;

		final BasicXmlFile template = new BasicXmlFile(file);

		if (checkEntry(template))
		{
			ApplicationLogger.getLogger().info("Loading files from path: " + file.getAbsolutePath());
			addEntry(template);
		}
	}


	public Map<String, T> getTemplates()
	{
		return _templates;
	}


	public T get(final String id)
	{
		return getTemplates().get(id);
	}


	/**
	 * Checks whether a file should be added to the list.
	 * 
	 * @param template
	 *            the file to check
	 * @return {@code true} if the file defines the correct type.
	 */
	protected abstract boolean checkEntry(BasicXmlFile template);


	/**
	 * Adds a file to the list.
	 * 
	 * @param file
	 *            the file to add.
	 */
	protected abstract void addEntry(final BasicXmlFile file);


	public String getId(final T object)
	{
		if (_templates == null)
		{
			throw new IllegalArgumentException("Parameter '_templates' must not be null.");
		}

		for (final Entry<String, T> entry : _templates.entrySet())
		{
			if (entry.getValue().equals(object))
			{
				return entry.getKey();
			}
		}

		return "*** invalid id ***";
	}

}
