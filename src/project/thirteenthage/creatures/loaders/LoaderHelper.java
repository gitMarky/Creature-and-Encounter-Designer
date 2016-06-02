package project.thirteenthage.creatures.loaders;

import project.thirteenthage.creatures.internal.BasicXmlFile;

public final class LoaderHelper
{
	public static final String EXTENSION_XML = ".xml";
	public static final String EXTENSION_HTML = ".html";


	private LoaderHelper()
	{

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
