package project.thirteenthage.creatures.loaders;

import project.thirteenthage.creatures.internal.BasicXmlFile;
import project.thirteenthage.creatures.internal.Constants;
import project.thirteenthage.creatures.internal.exceptions.LoaderException;
import project.thirteenthage.creatures.internal.interfaces.ISpecial;
import project.thirteenthage.creatures.mechanics.SpecialTemplate;

/**
 * Loads attack templates from a folder.
 */
public class SpecialTemplateLoader extends AbstractLoader<ISpecial>
{
	private static SpecialTemplateLoader _instance = null;


	@Override
	protected boolean checkEntry(BasicXmlFile template)
	{
		return "special".equals(template.getRoot().getName());
	}


	@Override
	protected void addEntry(BasicXmlFile file)
	{
		SpecialTemplate template = new SpecialTemplate(file);
		final String id = LoaderHelper.getId(file);

		if (getTemplates().containsKey(id))
		{
			throw new LoaderException("Cannot load from \"" + file.getFile().getAbsolutePath() + "\": the filename already exists");
		}

		getTemplates().put(id, template);
	}


	public static SpecialTemplateLoader getInstance()
	{
		if (_instance == null)
		{
			_instance = new SpecialTemplateLoader();
			_instance.load(Constants.RESOURCES);
		}

		return _instance;
	}
}
