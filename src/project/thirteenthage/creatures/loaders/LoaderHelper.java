package project.thirteenthage.creatures.loaders;

import project.thirteenthage.creatures.internal.BasicXmlFile;

public final class LoaderHelper
{
	public static final String EXTENSION_XML = ".xml";
	
	private LoaderHelper()
	{
		
	}
	
	public static String getId(final BasicXmlFile template)
	{
		String name = template.getFile().getName();
		return name.substring(0, name.length() - EXTENSION_XML.length());
	}
}