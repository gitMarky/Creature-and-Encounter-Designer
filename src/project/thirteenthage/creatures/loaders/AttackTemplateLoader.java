package project.thirteenthage.creatures.loaders;

import project.library.marky.logger.ApplicationLogger;
import project.marky.library.xml.BasicXmlFile;
import project.thirteenthage.creatures.internal.Constants;
import project.thirteenthage.creatures.internal.exceptions.LoaderException;
import project.thirteenthage.creatures.internal.interfaces.IAttack;
import project.thirteenthage.creatures.mechanics.AttackTemplate;

/**
 * Loads attack templates from a folder.
 */
public class AttackTemplateLoader extends AbstractLoader<IAttack>
{
	private static AttackTemplateLoader _instance = null;


	@Override
	protected boolean checkEntry(final BasicXmlFile template)
	{
		if (template == null)
		{
			throw new IllegalArgumentException("Parameter 'template' must not be null.");
		}
		return "attack".equals(template.getRoot().getName());
	}


	@Override
	protected void addEntry(final BasicXmlFile file)
	{
		if (file == null)
		{
			throw new IllegalArgumentException("Parameter 'file' must not be null.");
		}

		final AttackTemplate template = new AttackTemplate(file);
		final String id = LoaderHelper.getId(file);

		if (getTemplates().containsKey(id))
		{
			throw new LoaderException("Cannot load from \"" + file.getFile().getAbsolutePath() + "\": the filename already exists");
		}

		getTemplates().put(id, template);
	}


	public static AttackTemplateLoader getInstance()
	{
		if (_instance == null)
		{
			ApplicationLogger.getLogger().info("Setting up new loader instance");
			_instance = new AttackTemplateLoader();
			_instance.load(Constants.RESOURCES);
		}

		return _instance;
	}
}
