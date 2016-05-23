package project.thirteenthage.creatures.player;

import project.thirteenthage.creatures.internal.conversions.Conversions;
import project.thirteenthage.creatures.internal.interfaces.ILeveledItem;

public abstract class PlayerCharacter implements ILeveledItem
{
	private int _level = 1;
	
	
	public int getAttackBonus()
	{
		return getAttackModifier() + getLevel();
	}
	
	
	public int getAC()
	{
		return baseAC() + getACmodifier() + getLevel();
	}
	
	
	public int getPD()
	{
		return basePD() + getPDmodifier() + getLevel();
	}
	
	
	public int getMD()
	{
		return baseMD() + getMDmodifier() + getLevel();		
	}
	
	
	public int getHP()
	{
		return Conversions.round(baseHP() + getConModifier()) * getHPlevelFactor();
	}
	

	@Override
	public int getLevel()
	{
		return _level;
	}
	
	
	public void setLevel(int level)
	{
		_level = Math.min(Math.max(1, level), 10);
	}
	
	
	protected abstract int getAttackModifier();
	
	protected abstract int baseAC();
	protected abstract int basePD();
	protected abstract int baseMD();
	
	protected abstract int getACmodifier();
	protected abstract int getMDmodifier();
	protected abstract int getPDmodifier();
	
	protected abstract int baseHP();
	protected abstract int getConModifier();
	
	private int getHPlevelFactor()
	{
		switch (getLevel())
		{
			case 1: return 3;
			case 2: return 4;
			case 3: return 5;
			case 4: return 6;
			case 5: return 8;
			case 6: return 10;
			case 7: return 12;
			case 8: return 16;
			case 9: return 20;
			case 10: return 24;
		}
		return 1;
	}
	
}
