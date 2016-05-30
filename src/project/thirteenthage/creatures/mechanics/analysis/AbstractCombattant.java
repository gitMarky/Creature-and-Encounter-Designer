package project.thirteenthage.creatures.mechanics.analysis;

import java.util.logging.Level;

import project.thirteenthage.creatures.internal.ApplicationLogger;
import project.thirteenthage.creatures.internal.conversions.Conversions;
import project.thirteenthage.creatures.internal.interfaces.ICreature;

public abstract class AbstractCombattant implements ICombattant
{
	private final ICreature _creature;
	private int _hp = 0;
	private String _name;
	
	public AbstractCombattant(ICreature creature)
	{
		_creature = creature;
		_name = creature.getName() + creature.toString().substring(creature.toString().lastIndexOf("@"));
	}
	
	
	public ICreature getCreature()
	{
		return _creature;
	}	


	@Override
	public void initialize()
	{
		_hp = Conversions.round(getCreature().getHP());
	}


	@Override
	public boolean isAlive()
	{
		return _hp > 0;
	}


	@Override
	public void takeDamage(final int damage)
	{
		_hp -= damage;
		ApplicationLogger.getLogger().setLevel(Level.ALL);
		ApplicationLogger.getLogger().fine(this.getName() + " takes " + damage + " damage, has " + _hp + " HP left.");
	}


	@Override
	public String getName()
	{
		return _name;
	}


	@Override
	public void setName(final String name)
	{
		_name = name;
	}
}
