package project.thirteenthage.creatures.loaders;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.internal.interfaces.ICreatureTemplate;

public class CreatureLoader
{
	private static CreatureLoader _instance = null;
	private Map<String, ICreature> _creatures = new HashMap<String, ICreature>();


	public void load(final CreatureTemplateLoader templates)
	{
		for (final Entry<String, ICreatureTemplate> template : templates.getTemplates().entrySet())
		{
			addEntry(template.getKey(), template.getValue());
		}
	}


	protected void addEntry(final String id, final ICreatureTemplate template)
	{
		ICreature creature = template.toCreature();
		getCreatures().put(id, creature);
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
}
