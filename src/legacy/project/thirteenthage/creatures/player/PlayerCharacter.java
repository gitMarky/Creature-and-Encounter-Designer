package legacy.project.thirteenthage.creatures.player;

import java.util.List;

import legacy.project.thirteenthage.creatures.internal.interfaces.ICreature;
import legacy.project.thirteenthage.creatures.internal.interfaces.ICreatureTemplate;
import legacy.project.thirteenthage.creatures.internal.interfaces.ILeveledItem;
import legacy.project.thirteenthage.creatures.internal.interfaces.ISpecial;

public abstract class PlayerCharacter implements ILeveledItem, ICreature
{
	private int _level = 1;


	public int getAttackBonus()
	{
		return getAttackModifier() + getLevel();
	}


	@Override
	public int getAC()
	{
		return baseAC() + getACmodifier() + getLevel();
	}


	@Override
	public int getPD()
	{
		return basePD() + getPDmodifier() + getLevel();
	}


	@Override
	public int getMD()
	{
		return baseMD() + getMDmodifier() + getLevel();
	}


	@Override
	public double getHP()
	{
		return (baseHP() + getConModifier()) * getHPlevelFactor();
	}


	@Override
	public int getLevel()
	{
		return _level;
	}


	public void setLevel(final int level)
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


	public abstract int getDamageModifier();


	private int getHPlevelFactor()
	{
		switch (getLevel())
		{
			case 1:
				return 3;
			case 2:
				return 4;
			case 3:
				return 5;
			case 4:
				return 6;
			case 5:
				return 8;
			case 6:
				return 10;
			case 7:
				return 12;
			case 8:
				return 16;
			case 9:
				return 20;
			case 10:
				return 24;
		}
		return 1;
	}


	@Override
	public List<String> getLabels()
	{
		return null;
	}


	@Override
	public boolean isMook()
	{
		return false;
	}


	@Override
	public void setNastierSpecials(final List<ISpecial> nastierSpecials)
	{
		// TODO Auto-generated method stub
	}


	@Override
	public void setSpecials(final List<ISpecial> specials)
	{
		// TODO Auto-generated method stub
	}


	@Override
	public ICreatureTemplate getTemplate()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
