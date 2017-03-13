package project.thirteenthage.creatures.loaders;

import java.io.File;

import project.marky.library.xml.BasicXmlFile;

public final class LoaderHelper
{
	public static final String EXTENSION_XML = ".xml";
	public static final String EXTENSION_HTML = ".html";


	private LoaderHelper()
	{

	}
	
	
	public static File forceExtension(final File file, final String extension)
	{
		File targetFile = file;
		if (!targetFile.getName().endsWith(extension))
		{
			targetFile = new File(targetFile.getAbsolutePath() + extension);
		}
		return targetFile;
	}


	public static String getId(final BasicXmlFile template)
	{
		if (template == null)
		{
			throw new IllegalArgumentException("Parameter 'template' must not be null.");
		}

		final String name = template.getFile().getName();
		return name.substring(0, name.length() - EXTENSION_XML.length());
	}
}
