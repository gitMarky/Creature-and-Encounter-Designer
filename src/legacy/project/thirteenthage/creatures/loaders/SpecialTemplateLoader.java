package legacy.project.thirteenthage.creatures.loaders;

import project.library.marky.logger.ApplicationLogger;
import project.marky.library.xml.BasicXmlFile;
import legacy.project.thirteenthage.creatures.internal.Constants;
import legacy.project.thirteenthage.creatures.internal.exceptions.LoaderException;
import legacy.project.thirteenthage.creatures.internal.interfaces.ISpecial;
import legacy.project.thirteenthage.creatures.mechanics.SpecialTemplate;

/**
 * Loads attack templates from a folder.
 */
public class SpecialTemplateLoader extends AbstractLoader<ISpecial>
{
	private static SpecialTemplateLoader _instance = null;


	@Override
	protected boolean checkEntry(final BasicXmlFile template)
	{
		if (template == null)
		{
			throw new IllegalArgumentException("Parameter 'template' must not be null.");
		}

		return "special".equals(template.getRoot().getName());
	}


	@Override
	protected void addEntry(final BasicXmlFile file)
	{
		if (file == null)
		{
			throw new IllegalArgumentException("Parameter 'file' must not be null.");
		}

		final SpecialTemplate template = new SpecialTemplate(file);
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
			ApplicationLogger.getLogger().info("Setting up new loader instance");
			_instance = new SpecialTemplateLoader();
			_instance.load(Constants.RESOURCES);
		}

		return _instance;
	}
}
