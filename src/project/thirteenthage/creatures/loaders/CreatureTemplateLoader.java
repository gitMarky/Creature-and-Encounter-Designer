package project.thirteenthage.creatures.loaders;

import project.thirteenthage.creatures.creature.CreatureTemplate;
import project.thirteenthage.creatures.internal.BasicXmlFile;
import project.thirteenthage.creatures.internal.Constants;
import project.thirteenthage.creatures.internal.exceptions.LoaderException;
import project.thirteenthage.creatures.internal.interfaces.ICreatureTemplate;

/**
 * Loads attack templates from a folder.
 */
public class CreatureTemplateLoader extends AbstractLoader<ICreatureTemplate>
{
	private static CreatureTemplateLoader _instance = null;


	@Override
	protected boolean checkEntry(BasicXmlFile template)
	{
		return "creature".equals(template.getRoot().getName());
	}


	@Override
	protected void addEntry(BasicXmlFile file)
	{
		CreatureTemplate template = new CreatureTemplate(file);
		final String id = getId(file);

		if (getTemplates().containsKey(id))
		{
			throw new LoaderException("Cannot load from \"" + file.getFile().getAbsolutePath() + "\": the filename already exists");
		}

		getTemplates().put(id, template);
	}


	public static CreatureTemplateLoader getInstance()
	{
		if (_instance == null)
		{
			_instance = new CreatureTemplateLoader();
			_instance.load(Constants.RESOURCES);
		}

		return _instance;
	}
}
