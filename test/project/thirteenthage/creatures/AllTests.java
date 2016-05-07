package project.thirteenthage.creatures;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import project.thirteenthage.creatures.internal.CreatureSizeTest;
import project.thirteenthage.creatures.internal.CreatureTemplateTest;
import project.thirteenthage.creatures.mechanics.AttackTemplateTest;

@RunWith(value = Suite.class)
@SuiteClasses(value = { CreatureSizeTest.class,
		                CreatureTemplateTest.class,
		                AttackTemplateTest.class})
public class AllTests
{
}
