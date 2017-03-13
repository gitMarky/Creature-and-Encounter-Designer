package project.thirteenthage.creatures;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import project.thirteenthage.creatures.loaders.AttackTemplateLoaderTest;
import project.thirteenthage.creatures.loaders.CreatureTemplateLoaderTest;
import project.thirteenthage.creatures.values.TemplateTestAnimal;
import project.thirteenthage.creatures.values.TemplateTestMisc;
import legacy.project.thirteenthage.creatures.internal.CreatureSizeTest;
import legacy.project.thirteenthage.creatures.internal.CreatureTemplateTest;
import legacy.project.thirteenthage.creatures.internal.RegexMatcherTest;
import legacy.project.thirteenthage.creatures.internal.TextFormatterTest;
import legacy.project.thirteenthage.creatures.mechanics.AttackTemplateTest;
import legacy.project.thirteenthage.creatures.mechanics.LevelAdjustmentTest;
import legacy.project.thirteenthage.creatures.module.analysis.CombatTest;

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
		TemplateTestMisc.class,
		CombatTest.class})
public class AllTests
{
}
