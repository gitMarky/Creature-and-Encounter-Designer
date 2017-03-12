package project.thirteenthage.creatures.values;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static project.thirteenthage.creatures.TestConstants.DOUBLE_FUZZY_1E_3;

import java.io.File;

import project.thirteenthage.creatures.creature.CreatureSize;
import project.thirteenthage.creatures.creature.CreatureTemplate;
import project.thirteenthage.creatures.internal.interfaces.IAttack;
import project.thirteenthage.creatures.internal.interfaces.ICreature;

public class TemplateTest
{
	private ICreature _creature;

	protected void load(final String path)
	{
		final File xml = new File(path);
		final CreatureTemplate template = new CreatureTemplate(xml, xml.getName().replace(".xml", ""));
		_creature = template.toCreature();
	}


	protected void descriptor(final String name, final int level, final CreatureSize size, final String... labels)
	{
		assertEquals(name, _creature.getName());
		assertEquals(size, _creature.getSize());

		assertEquals(labels.length, _creature.getLabels().size());

		for (final String label : labels)
		{
			assertTrue(_creature.getLabels().contains(label));
		}

		Level(level);
	}


	protected void Level(final int value)
	{
		assertEquals(value, _creature.getLevel());
	}


	protected void AC(final int value)
	{
		assertEquals(value, _creature.getAC());
	}


	protected void PD(final int value)
	{
		assertEquals(value, _creature.getPD());
	}


	protected void MD(final int value)
	{
		assertEquals(value, _creature.getMD());
	}


	protected void initiative(final int value)
	{
		assertEquals(value, _creature.getInitiative());
	}

	protected void HP(final int value)
	{
		assertEquals(value, _creature.getHP(), DOUBLE_FUZZY_1E_3);
	}

	protected void testAttack(final int index, final String name, final int bonus, final String defense, final double damage)
	{
		final IAttack attack = _creature.getAttacks().get(index);
		assertEquals(name, attack.getName());
		assertEquals(bonus, attack.getAttackBonus());
		assertEquals(damage, attack.getDamageFactor(), DOUBLE_FUZZY_1E_3);
		assertEquals(defense, attack.getDefense());
	}
}
