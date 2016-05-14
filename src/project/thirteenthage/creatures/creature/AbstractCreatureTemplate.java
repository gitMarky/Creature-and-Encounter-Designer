package project.thirteenthage.creatures.creature;

import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.internal.interfaces.ICreatureTemplate;

public abstract class AbstractCreatureTemplate implements ICreatureTemplate
{
	@Override
	public ICreature toCreature()
	{
		CreatureBuilder builder = new CreatureBuilder();
		builder.name(getName())
		       .size(getSize())
		       .level(getLevel())
		       .addInitiative(getModifierInitiative())
		       .addAttack(getModifierAttack())
		       .addAC(getModifierAC())
		       .addPD(getModifierPD())
		       .addMD(getModifierMD())
		       .scaleHP(getModifierHP());

		final Creature creature;
		if (getLabels().contains("Mook"))
		{
			creature = builder.buildMook();
		} else
		{
			creature = builder.buildCreature();
		}

		creature.setAttacks(getAttacks());
		creature.getLabels().addAll(getLabels());
		creature.setSpecials(getSpecials());
		creature.setNastierSpecials(getNastierSpecials());
		creature.setTemplate(this);

		return creature;
	}

}