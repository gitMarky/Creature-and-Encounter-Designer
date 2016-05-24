package project.thirteenthage.creatures.player;

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
}
