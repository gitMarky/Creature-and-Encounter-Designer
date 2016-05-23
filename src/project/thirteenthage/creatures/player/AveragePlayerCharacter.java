package project.thirteenthage.creatures.player;

public class AveragePlayerCharacter extends PlayerCharacter
{

	@Override
	protected int getAttackModifier()
	{
		return 4;
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
		return 2;
	}

	@Override
	protected int getMDmodifier()
	{
		return 2;
	}

	@Override
	protected int getPDmodifier()
	{
		return 2;
	}

	@Override
	protected int baseHP()
	{
		return 8;
	}

	@Override
	protected int getConModifier()
	{
		return 2;
	}

}
