package legacy.project.thirteenthage.creatures;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import legacy.project.thirteenthage.creatures.internal.CreatureSizeTest;
import legacy.project.thirteenthage.creatures.internal.CreatureTemplateTest;
import legacy.project.thirteenthage.creatures.internal.RegexMatcherTest;
import legacy.project.thirteenthage.creatures.internal.TextFormatterTest;
import legacy.project.thirteenthage.creatures.loaders.AttackTemplateLoaderTest;
import legacy.project.thirteenthage.creatures.loaders.CreatureTemplateLoaderTest;
import legacy.project.thirteenthage.creatures.mechanics.AttackTemplateTest;
import legacy.project.thirteenthage.creatures.mechanics.LevelAdjustmentTest;
import legacy.project.thirteenthage.creatures.module.analysis.CombatTest;
import legacy.project.thirteenthage.creatures.values.TemplateTestAnimal;

@RunWith(value = Suite.class)
@SuiteClasses(value = { CreatureSizeTest.class,
		CreatureTemplateTest.class,
		AttackTemplateTest.class,
		AttackTemplateLoaderTest.class,
		CreatureTemplateLoaderTest.class,
		TextFormatterTest.class,
		RegexMatcherTest.class,
		LevelAdjustmentTest.class,
		TemplateTestAnimal.class,
		CombatTest.class})
public class AllTests
{
}
