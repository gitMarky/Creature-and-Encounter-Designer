package project.thirteenthage.creatures.loaders;

import java.io.File;

import project.marky.library.xml.BasicXmlFile;


/**
 * Utility class that helps the loader classes.
 */
public final class LoaderHelper
{
	public static final String EXTENSION_XML = ".xml";
	public static final String EXTENSION_HTML = ".html";


	private LoaderHelper()
	{
		throw new UnsupportedOperationException("Do not instantiate utility class");
	}


	/**
	 * Forces the file extension. Does not do any conversions,
	 * and the file may as well be invalid.
	 * 
	 * @param file the file.
	 * @param extension the extension that the file should have.
	 * @return the new file. Note that the old file still exists.
	 */
	public static File forceExtension(final File file, final String extension)
	{
		File targetFile = file;
		if (!targetFile.getName().endsWith(extension))
		{
			targetFile = new File(targetFile.getAbsolutePath() + extension);
		}
		return targetFile;
	}


	/**
	 * Converts the name of a file to an ID.
	 * 
	 * @param template the template file.
	 * @return the ID, which is the file name without its extension.
	 */
	public static String getId(final BasicXmlFile template)
	{
		if (template == null)
		{
			throw new IllegalArgumentException("Parameter 'template' must not be null.");
		}

		return getId(template.getFile());
	}


	/**
	 * Converts the name of a file to an ID.
	 * 
	 * @param template the template file.
	 * @return the ID, which is the file name without its extension.
	 */
	public static String getId(final File template)
	{
		if (template == null)
		{
			throw new IllegalArgumentException("Parameter 'template' must not be null.");
		}

		final String name = template.getName();
		return name.substring(0, name.length() - EXTENSION_XML.length());
	}
}
