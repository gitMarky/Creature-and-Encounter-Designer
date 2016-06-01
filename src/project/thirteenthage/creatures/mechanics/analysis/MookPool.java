package project.thirteenthage.creatures.mechanics.analysis;

import java.util.ArrayList;
import java.util.List;

import project.thirteenthage.creatures.internal.ApplicationLogger;
import project.thirteenthage.creatures.internal.Constants;

public class MookPool
{
	private final List<ICombattant> _mooks = new ArrayList<ICombattant>();
	
	public boolean addMook(final ICombattant mook)
	{
		if (mook == null)
		{
			throw new IllegalArgumentException("Parameter 'mook' must not be null.");
		}
		
		if (!mook.getCreature().isMook())
		{
			throw new IllegalArgumentException("Parameter 'mook' is not a mook.");
		}
		
		if (!canAddMook())
		{
			return false;
		}
		
		ApplicationLogger.getLogger().info("Added to mook pool " + this.toString() + ": " + mook.getCreature().getName());
		_mooks.add(mook);
		mook.setMookPool(this);
		return true;
	}

	public boolean canAddMook()
	{
		return _mooks.size() < Constants.MAX_MOOK_POOL_SIZE;
	}
	
	public void takeDamage(final int damage)
	{
		for (final ICombattant mook : _mooks)
		{
			if (mook.isAlive())
			{
				mook.takeDamage(damage);
				ApplicationLogger.getLogger().info("Redirecting " + damage + " damage to other mook " + mook.getHP() + "HP left");
				break;
			}
		}
	}
}
