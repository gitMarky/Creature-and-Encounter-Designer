package legacy.project.thirteenthage.creatures.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static project.thirteenthage.creatures.TestConstants.DOUBLE_EXACT_1E_8;
import static project.thirteenthage.creatures.TestConstants.DOUBLE_FUZZY_1E_3;

import java.io.File;

import org.junit.Test;

import project.thirteenthage.creatures.model.creature.template.CreatureTemplate;
import legacy.project.thirteenthage.creatures.creature.CreatureSize;
import legacy.project.thirteenthage.creatures.internal.interfaces.IAttack;
import legacy.project.thirteenthage.creatures.internal.interfaces.ICreature;

public class CreatureTemplateTest
{
	@Test
	public void testDireRatTemplate()
	{
		final double delta = DOUBLE_EXACT_1E_8;
		final File xml = new File("resources/creatures/animal/creature_dire_rat.xml");
		System.out.println(xml.getAbsolutePath());

		final CreatureTemplate rat = new CreatureTemplate(xml, "creature_dire_rat");

		assertEquals("Dire Rat", rat.getName());
		assertEquals(CreatureSize.NORMAL, rat.getSize());
		assertEquals(1, rat.getLevel());

		assertEquals(2, rat.getLabels().size());
		assertEquals("Mook", rat.getLabels().get(0));
		assertEquals("Beast", rat.getLabels().get(1));

		assertEquals(1, rat.getModifierInitiative());
		assertEquals(-2, rat.getModifierAC());
		assertEquals(0, rat.getModifierPD());
		assertEquals(-1, rat.getModifierMD());
		assertEquals(0.889, rat.getModifierHP(), delta);

		assertEquals(1, rat.getAttacks().size());
		assertEquals("Infected bite", rat.getAttacks().get(0).getName());

		// assertEquals();
		/*
		 * <name>Dire Rat</name> <size>Normal</size> <level>1</level> <labels>
		 * <label>Mook</label> <label>Beast</label> </labels> <modifiers>
		 * <ini>1</ini> <ac>-2</ac> <pd>0</pd> <md>-1</md> <hp>0.857</hp>
		 * </modifiers> <attacks> <attack id="attack_infected_bite" />
		 * </attacks> <specials> <special id="" /> </specials> <nastier>
		 * <special id="special_squealing_pack_attack" /> </nastier>
		 */
	}


	@Test
	public void testDireRatCreature()
	{
		final double delta = DOUBLE_FUZZY_1E_3;
		final File xml = new File("resources/creatures/animal/creature_dire_rat.xml");
		System.out.println(xml.getAbsolutePath());

		final CreatureTemplate template = new CreatureTemplate(xml, "creature_dire_rat");
		final ICreature rat = template.toCreature();

		assertEquals("Dire Rat", rat.getName());
		// assertEquals("Normal", rat.getSize());
		assertEquals(1, rat.getLevel());

		assertEquals(2, rat.getInitiative());
		assertEquals(15, rat.getAC());
		assertEquals(15, rat.getPD());
		assertEquals(10, rat.getMD());
		assertEquals(6.0, rat.getHP(), delta);

		assertEquals(1, rat.getAttacks().size());
		testAttack(rat.getAttacks().get(0), "Infected bite", 5, "AC", 4.0, "ongoing damage");

		final ICreature rat2 = template.toCreature();

		assertTrue(rat2.equals(rat));
	}


	private void testAttack(final IAttack attack, final String name, final int bonus, final String defense, final double damage, final String description)
	{
		assertEquals(name, attack.getName());
		assertEquals(bonus, attack.getAttackBonus());
		assertEquals(damage, attack.getDamageFactor(), 1e-3);
		assertEquals(defense, attack.getDefense());
		assertEquals(description, attack.getDescription());
	}
}
