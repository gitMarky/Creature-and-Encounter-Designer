package legacy.project.thirteenthage.creatures.values;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import project.thirteenthage.creatures.model.creature.template.CreatureTemplate;
import legacy.project.thirteenthage.creatures.creature.CreatureSize;
import legacy.project.thirteenthage.creatures.internal.conversions.Conversions;
import legacy.project.thirteenthage.creatures.internal.interfaces.IAttack;
import legacy.project.thirteenthage.creatures.internal.interfaces.ICreature;

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
		assertEquals(value, Conversions.round(_creature.getHP()));
	}

	protected void testAttack(final int index, final int bonus, final String defense, final int damage)
	{
		final IAttack attack = _creature.getAttacks().get(index);
		assertEquals(bonus, attack.getAttackBonus());
		assertEquals(damage, Conversions.round(attack.getDamageFactor()));
		assertEquals(defense, attack.getDefense());
	}
}
