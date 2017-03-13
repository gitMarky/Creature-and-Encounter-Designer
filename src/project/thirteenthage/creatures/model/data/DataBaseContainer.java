package project.thirteenthage.creatures.model.data;

import project.thirteenthage.creatures.loaders.AttackTemplateLoader;
import project.thirteenthage.creatures.loaders.CreatureLoader;
import project.thirteenthage.creatures.loaders.CreatureTemplateLoader;
import project.thirteenthage.creatures.loaders.SpecialTemplateLoader;

/**
 * Is responsible for loading all files.
 */
public class DataBaseContainer
{
	/**
	 * Loads the data.
	 */
	public static void load()
	{
		AttackTemplateLoader.getInstance();
		CreatureTemplateLoader.getInstance();
		CreatureLoader.getInstance().load(CreatureTemplateLoader.getInstance());
		SpecialTemplateLoader.getInstance();
	}
}
