package legacy.project.thirteenthage.creatures.module.analysis;

import java.util.ArrayList;
import java.util.List;

import project.library.marky.logger.ApplicationLogger;
import legacy.project.thirteenthage.creatures.internal.Constants;

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
		ICombattant target = null;
		for (final ICombattant mook : _mooks)
		{
			if (mook.isAlive())
			{
				if (target == null || mook.getHP() < target.getHP())
				{
					target = mook;
				}
			}
			// ApplicationLogger.getLogger().info("* Mook pool cannot redirect to "
			// + mook.toString());
		}

		if (target != null)
		{
			target.takeDamage(damage);
			ApplicationLogger.getLogger().info("Redirecting " + damage + " damage to other mook " + target.getHP() + " HP left");
		}
		else
		{
			ApplicationLogger.getLogger().info("Mook pool " + this.toString() + " was destroyed");
		}
	}
}
