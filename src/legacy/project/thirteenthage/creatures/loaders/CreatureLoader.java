package legacy.project.thirteenthage.creatures.loaders;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import legacy.project.thirteenthage.creatures.internal.interfaces.ICreature;
import legacy.project.thirteenthage.creatures.internal.interfaces.ICreatureTemplate;

public class CreatureLoader
{
	private static CreatureLoader _instance = null;

	/** Maps creature ID to an instance of the creature. */
	private final Map<String, ICreature> _creatures = new HashMap<String, ICreature>();


	public void load(final CreatureTemplateLoader templates)
	{
		if (templates == null)
		{
			throw new IllegalArgumentException("Parameter 'templates' must not be null.");
		}

		for (final Entry<String, ICreatureTemplate> template : templates.getTemplates().entrySet())
		{
			addEntry(template.getKey(), template.getValue());
		}
	}


	protected void addEntry(final String id, final ICreatureTemplate template)
	{
		if (template == null)
		{
			throw new IllegalArgumentException("Parameter 'template' must not be null.");
		}

		if (!getCreatures().containsKey(id))
		{
			final ICreature creature = template.toCreature();
			getCreatures().put(id, creature);
		}
	}


	public Map<String, ICreature> getCreatures()
	{
		return _creatures;
	}


	public static CreatureLoader getInstance()
	{
		if (_instance == null)
		{
			_instance = new CreatureLoader();
		}

		return _instance;
	}


	public boolean isCreatureLoaded(final ICreature creature)
	{
		if (creature == null)
		{
			throw new IllegalArgumentException("Parameter 'creature' must not be null.");
		}

		for (final ICreature candidate : getCreatures().values())
		{
			if (candidate.equals(creature))
			{
				return true;
			}
		}

		return false;
	}
}
