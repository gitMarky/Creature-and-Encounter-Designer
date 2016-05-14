package project.thirteenthage.creatures;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import project.thirteenthage.creatures.internal.CreatureSizeTest;
import project.thirteenthage.creatures.internal.CreatureTemplateTest;
import project.thirteenthage.creatures.internal.RegexMatcherTest;
import project.thirteenthage.creatures.internal.TextFormatterTest;
import project.thirteenthage.creatures.loaders.AttackTemplateLoaderTest;
import project.thirteenthage.creatures.loaders.CreatureTemplateLoaderTest;
import project.thirteenthage.creatures.mechanics.AttackTemplateTest;
import project.thirteenthage.creatures.mechanics.LevelAdjustmentTest;

@RunWith(value = Suite.class)
@SuiteClasses(value = { CreatureSizeTest.class,
		                CreatureTemplateTest.class,
		                AttackTemplateTest.class,
		                AttackTemplateLoaderTest.class,
		                CreatureTemplateLoaderTest.class,
		                TextFormatterTest.class,
		                RegexMatcherTest.class,
		                LevelAdjustmentTest.class})
public class AllTests
{
}
