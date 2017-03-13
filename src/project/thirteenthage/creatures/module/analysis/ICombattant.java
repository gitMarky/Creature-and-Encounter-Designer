package project.thirteenthage.creatures.module.analysis;

import project.thirteenthage.creatures.internal.interfaces.ICreature;

public interface ICombattant
{
	int getDamage(final ICreature target, final int escalationDie, AnalysisMode mode);


	ICreature getCreature();


	void initialize();


	boolean isAlive();


	void takeDamage(int damage);


	String getName();


	void setName(final String name);


	void setInvulnerable();


	int getHP();


	void setMookPool(MookPool pool);
}
