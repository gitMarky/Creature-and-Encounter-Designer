package project.thirteenthage.creatures.mechanics.analysis;

import project.thirteenthage.creatures.internal.conversions.Conversions;
import project.thirteenthage.creatures.internal.interfaces.ICreature;

public abstract class AbstractCombattant implements ICombattant
{
	private final ICreature _creature;
	private int _hp = 0;
	private String _name;
	private boolean _invulnerable = false;
	private MookPool _mookPool = null;


	public AbstractCombattant(ICreature creature)
	{
		_creature = creature;
		//String objectNumber = creature.toString().substring(creature.toString().lastIndexOf("@") + 1);
		String objectNumber = this.toString().substring(this.toString().lastIndexOf("@") + 1);
		
		while(objectNumber.length() < 8)
		{
			objectNumber = " " + objectNumber;
		}
		
		_name = creature.getName() + " " + objectNumber;
	}


	public ICreature getCreature()
	{
		return _creature;
	}


	@Override
	public void initialize()
	{
		_hp = Conversions.round(getCreature().getHP());
		_invulnerable = false;
	}


	@Override
	public boolean isAlive()
	{
		return _invulnerable || _hp > 0;
	}


	@Override
	public void takeDamage(final int damage)
	{
		_hp -= damage;
		
		if (!_invulnerable && _hp < 0 && getCreature().isMook() && _mookPool != null)
		{
			_mookPool.takeDamage(-_hp);
		}
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


	@Override
	public void setInvulnerable()
	{
		_invulnerable = true;
	}
	
	
	@Override
	public void setMookPool(final MookPool pool)
	{
		_mookPool = pool;
	}
	
	
	@Override
	public int getHP()
	{
		return _hp;
	}
}
