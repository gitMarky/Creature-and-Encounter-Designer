package project.thirteenthage.creatures.loaders;

import java.util.Collections;

import project.thirteenthage.creatures.creature.CreatureTemplate;
import project.thirteenthage.creatures.internal.BasicXmlFile;
import project.thirteenthage.creatures.internal.Constants;
import project.thirteenthage.creatures.internal.exceptions.LoaderException;
import project.thirteenthage.creatures.internal.interfaces.ICreatureTemplate;
import project.thirteenthage.creatures.lists.Lists;

/**
 * Loads attack templates from a folder.
 */
public class CreatureTemplateLoader extends AbstractLoader<ICreatureTemplate>
{
	private static CreatureTemplateLoader _instance = null;


	@Override
	protected boolean checkEntry(final BasicXmlFile template)
	{
		return CreatureTemplate.ROOT_ELEMENT.equals(template.getRoot().getName());
	}


	@Override
	protected void addEntry(final BasicXmlFile file)
	{
		final CreatureTemplate template = new CreatureTemplate(file);
		final String id = LoaderHelper.getId(file);

		if (getTemplates().containsKey(id))
		{
			throw new LoaderException("Cannot load from \"" + file.getFile().getAbsolutePath() + "\": the filename already exists");
		}

		getTemplates().put(id, template);

		for (final String label : template.getLabels())
		{
			if (!Lists.labels().contains(label)) Lists.labels().add(label);
		}
		Collections.sort(Lists.labels());
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
