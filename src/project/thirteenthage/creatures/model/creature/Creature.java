package project.thirteenthage.creatures.model.creature;

import java.util.ArrayList;
import java.util.List;

import legacy.project.thirteenthage.creatures.creature.CreatureSize;
import legacy.project.thirteenthage.creatures.internal.interfaces.IAttack;
import legacy.project.thirteenthage.creatures.internal.interfaces.ICreature;
import legacy.project.thirteenthage.creatures.internal.interfaces.ICreatureTemplate;
import legacy.project.thirteenthage.creatures.internal.interfaces.ISpecial;
import legacy.project.thirteenthage.creatures.mechanics.Attack;
import legacy.project.thirteenthage.creatures.mechanics.LevelAdjustment;
import legacy.project.thirteenthage.creatures.mechanics.Special;

/**
 * <p>
 * Defines a creature, implementation of {@link ICreature}.
 * </p><p>
 * The creature is an individual object that comes from a {@link ICreatureTemplate}.
 * </p>
 */
public class Creature implements ICreature
{
	private String _name = "New creature";
	private int _level = 1;
	private final List<String> _labels = new ArrayList<String>();
	private int _attack = 0;
	private int _ac = 0;
	private int _pd = 0;
	private int _md = 0;
	private double _hp = 0.0;
	private double _damage = 0.0;
	private double _fearThreshold = 0.0;
	private int _ini = 0;
	private final List<IAttack> _attacks = new ArrayList<IAttack>();
	private final List<ISpecial> _specials = new ArrayList<ISpecial>();
	private final List<ISpecial> _nastierSpecials = new ArrayList<ISpecial>();
	private CreatureSize _size = CreatureSize.NORMAL;
	private boolean _isMook = false;
	private ICreatureTemplate _template;


	@Override
	public String getName()
	{
		return _name;
	}


	@Override
	public CreatureSize getSize()
	{
		return _size;
	}


	@Override
	public int getLevel()
	{
		return getLevelBase() + getLevelAdjustment();
	}


	@Override
	public List<String> getLabels()
	{
		return _labels;
	}


	@Override
	public int getAC()
	{
		return _ac;
	}


	@Override
	public int getPD()
	{
		return _pd;
	}


	@Override
	public int getMD()
	{
		return _md;
	}


	@Override
	public double getHP()
	{
		return _hp;
	}


	@Override
	public int getInitiative()
	{
		return _ini;
	}


	@Override
	public double getStrikeDamage()
	{
		return _damage;
	}


	@Override
	public double getFearThreshold()
	{
		return _fearThreshold;
	}


	@Override
	public List<IAttack> getAttacks()
	{
		return _attacks;
	}


	@Override
	public List<ISpecial> getSpecials()
	{
		return _specials;
	}


	@Override
	public List<ISpecial> getNastierSpecials()
	{
		return _nastierSpecials;
	}


	@Override
	public boolean isMook()
	{
		return _isMook;
	}


	void setName(final String name)
	{
		if (name == null)
		{
			throw new IllegalArgumentException("Parameter 'name' must not be null.");
		}

		_name = name;
	}


	void setAttackModifier(final int attack)
	{
		_attack = attack;
	}


	void setLevel(final int level)
	{
		_level = level;
	}


	void setAC(final int ac)
	{
		_ac = ac;
	}


	void setPD(final int pd)
	{
		_pd = pd;
	}


	void setMD(final int md)
	{
		_md = md;
	}


	void setHP(final double hp)
	{
		_hp = hp;
	}


	void setInitiative(final int ini)
	{
		_ini = ini;
	}


	void setSize(final CreatureSize size)
	{
		if (size == null)
		{
			throw new IllegalArgumentException("Parameter 'size' must not be null.");
		}

		_size = size;
	}


	void setFearThreshold(final double threshold)
	{
		_fearThreshold = threshold;
	}


	void setStrikeDamage(final double damage)
	{
		_damage = damage;
	}


	void setMook(final boolean isMook)
	{
		_isMook = isMook;
	}


	public void setAttacks(final List<IAttack> attacks)
	{
		if (attacks == null)
		{
			throw new IllegalArgumentException("Parameter 'attacks' must not be null.");
		}

		_attacks.clear();
		// create the actual attacks from templates.
		final int bonus = getLevel() + 5 + _attack;
		for (final IAttack template : attacks)
		{
			final Attack attack = new Attack(this, template, bonus, getStrikeDamage());
			_attacks.add(attack);
		}
	}


	@Override
	public void setSpecials(final List<ISpecial> specials)
	{
		if (specials == null)
		{
			throw new IllegalArgumentException("Parameter 'specials' must not be null.");
		}

		_specials.clear();
		for (final ISpecial template : specials)
		{
			final Special special = new Special(this, template);
			_specials.add(special);
		}
	}


	@Override
	public void setNastierSpecials(final List<ISpecial> nastierSpecials)
	{
		if (nastierSpecials == null)
		{
			throw new IllegalArgumentException("Parameter 'nastierSpecials' must not be null.");
		}

		_nastierSpecials.clear();
		for (final ISpecial template : nastierSpecials)
		{
			final Special special = new Special(this, template);
			_nastierSpecials.add(special);
		}
	}


	@Override
	public ICreatureTemplate getTemplate()
	{
		return _template;
	}


	public void setTemplate(final ICreatureTemplate template)
	{
		if (template == null)
		{
			throw new IllegalArgumentException("Parameter 'template' must not be null.");
		}

		_template = template;
	}


	public int getLevelBase()
	{
		return _level;
	}


	public int getLevelAdjustment()
	{
		if (getTemplate() == null)
		{
			return 0;
		}

		return LevelAdjustment
				.getLevelAdjustment(getTemplate().getModifierAttack(),
						getTemplate().getModifierAC(),
						getTemplate().getModifierPD(),
						getTemplate().getModifierMD(),
						getTemplate().getModifierHP(),
						getTemplate().getModifierDamage(),
						getAttacks(), getSpecials(), getNastierSpecials());
	}


	@Override
	public boolean equals(final Object other)
	{
		if (other == null)
		{
			throw new IllegalArgumentException("Parameter 'other' must not be null.");
		}

		if (other instanceof Creature)
		{
			boolean equality = true;

			final Creature otherCreature = (Creature) other;

			equality &= otherCreature.getName().equals(this.getName());
			equality &= otherCreature.getSize() == this.getSize();
			equality &= otherCreature.getLevel() == this.getLevel();
			equality &= otherCreature.getLabels().equals(this.getLabels());
			equality &= otherCreature.getAC() == this.getAC();
			equality &= otherCreature.getPD() == this.getPD();
			equality &= otherCreature.getMD() == this.getMD();
			equality &= otherCreature.getHP() == this.getHP();
			equality &= otherCreature.getInitiative() == this.getInitiative();
			equality &= otherCreature.getStrikeDamage() == this.getStrikeDamage();
			equality &= otherCreature.getFearThreshold() == this.getFearThreshold();
			equality &= otherCreature.getTemplate().getAttacks().equals(this.getTemplate().getAttacks());
			equality &= otherCreature.getTemplate().getSpecials().equals(this.getTemplate().getSpecials());
			equality &= otherCreature.getTemplate().getNastierSpecials().equals(this.getTemplate().getNastierSpecials());
			equality &= otherCreature.isMook() == this.isMook();
			equality &= otherCreature.getLevelBase() == this.getLevelBase();
			equality &= otherCreature.getLevelAdjustment() == this.getLevelAdjustment();

			return equality;
		}
		else
		{
			return super.equals(other);
		}
	}
}