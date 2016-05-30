package project.thirteenthage.creatures.player;

import java.util.List;

import project.thirteenthage.creatures.creature.CreatureSize;
import project.thirteenthage.creatures.internal.interfaces.IAttack;
import project.thirteenthage.creatures.internal.interfaces.ISpecial;

public class AveragePlayerCharacter extends PlayerCharacter
{

	@Override
	protected int getAttackModifier()
	{
		int mod = 4;
		if (getLevel() >= 4) mod += 1;
		if (getLevel() >= 8) mod += 1;
		return mod;
	}


	@Override
	protected int baseAC()
	{
		return 15;
	}


	@Override
	protected int basePD()
	{
		return 10;
	}


	@Override
	protected int baseMD()
	{
		return 10;
	}


	@Override
	protected int getACmodifier()
	{
		int mod = 2;
		if (getLevel() >= 2) mod += 1;
		return mod;
	}


	@Override
	protected int getMDmodifier()
	{
		int mod = 2;
		if (getLevel() >= 2) mod += 1;
		return mod;
	}


	@Override
	protected int getPDmodifier()
	{
		int mod = 2;
		if (getLevel() >= 2) mod += 1;
		return mod;
	}


	@Override
	protected int baseHP()
	{
		return 8;
	}


	@Override
	protected int getConModifier()
	{
		int mod = 2;
		if (getLevel() >= 6) mod += 1;
		return mod;
	}


	@Override
	public int getDamageModifier()
	{
		int mod = 3;
		if (getLevel() >= 6) mod += 1;
		return mod;
	}


	@Override
	public CreatureSize getSize()
	{
		return CreatureSize.NORMAL;
	}


	@Override
	public double getStrikeDamage()
	{
		return getPlayerStrikeDamage(true);
	}


	private double getPlayerStrikeDamage(final boolean withPowers)
	{
		// assume an average of D8 damage, with +3 damage

		double base = 4.5;
		double dice = base * getLevel();
		double mod = Math.max(1, (getLevel() + 1) / 3);

		double damage = dice + getDamageModifier() * mod;
		return damage * (withPowers ? 1.5 : 1.0); // multiply with 1.5 for
													// powers
	}


	@Override
	public double getFearThreshold()
	{
		return 0;
	}


	@Override
	public int getInitiative()
	{
		return +3;
	}


	@Override
	public List<IAttack> getAttacks()
	{
		return null;
	}


	@Override
	public List<ISpecial> getSpecials()
	{
		return null;
	}


	@Override
	public List<ISpecial> getNastierSpecials()
	{
		return null;
	}


	@Override
	public String getName()
	{
		return "Avrg. Player";
	}
}
