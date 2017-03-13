package legacy.project.thirteenthage.creatures.interfaces;

import legacy.project.thirteenthage.creatures.creature.CreatureSize;
import legacy.project.thirteenthage.creatures.module.creature.Creature;

public interface ICreatureBuilder
{

	ICreatureBuilder name(String name);


	ICreatureBuilder level(int level);


	ICreatureBuilder size(CreatureSize size);


	ICreatureBuilder addAttack(int amount);


	ICreatureBuilder addAC(int amount);


	ICreatureBuilder addPD(int amount);


	ICreatureBuilder addMD(int amount);


	ICreatureBuilder addInitiative(int amount);


	ICreatureBuilder scaleHP(double factor);


	ICreatureBuilder scaleDamage(double factor);


	ICreatureBuilder betterDefenseIsMD();


	Creature buildCreature();


	Creature buildMook();


	Creature build(boolean isMook);

}