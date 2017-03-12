package project.thirteenthage.creatures.values;

import static org.junit.Assert.assertEquals;
import static project.thirteenthage.creatures.TestConstants.DOUBLE_FUZZY_1E_3;

import java.io.File;

import org.junit.Test;

import project.thirteenthage.creatures.creature.CreatureSize;
import project.thirteenthage.creatures.creature.CreatureTemplate;
import project.thirteenthage.creatures.internal.interfaces.IAttack;
import project.thirteenthage.creatures.internal.interfaces.ICreature;

public class TemplateTestAnimal
{
	@Test
	public void testDireRat()
	{
		final File xml = new File("resources/creatures/animal/creature_dire_rat.xml");

		final CreatureTemplate template = new CreatureTemplate(xml, "creature_dire_rat");
		final ICreature rat = template.toCreature();

		assertEquals("Dire Rat", rat.getName());
		assertEquals(CreatureSize.NORMAL, rat.getSize());
		assertEquals(1, rat.getLevel());

		assertEquals(2, rat.getInitiative());
		assertEquals(15, rat.getAC());
		assertEquals(15, rat.getPD());
		assertEquals(10, rat.getMD());
		assertEquals(6.0, rat.getHP(), DOUBLE_FUZZY_1E_3);

		assertEquals(1, rat.getAttacks().size());
		testAttack(rat.getAttacks().get(0), "Infected bite", 5, "AC", 4.0);
	}


	private void testAttack(final IAttack attack, final String name, final int bonus, final String defense, final double damage)
	{
		assertEquals(name, attack.getName());
		assertEquals(bonus, attack.getAttackBonus());
		assertEquals(damage, attack.getDamageFactor(), DOUBLE_FUZZY_1E_3);
		assertEquals(defense, attack.getDefense());
	}
}
