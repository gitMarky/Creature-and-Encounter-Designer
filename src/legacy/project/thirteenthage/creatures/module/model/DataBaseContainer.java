package legacy.project.thirteenthage.creatures.module.model;

import legacy.project.thirteenthage.creatures.loaders.AttackTemplateLoader;
import legacy.project.thirteenthage.creatures.loaders.CreatureLoader;
import legacy.project.thirteenthage.creatures.loaders.CreatureTemplateLoader;
import legacy.project.thirteenthage.creatures.loaders.SpecialTemplateLoader;

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
