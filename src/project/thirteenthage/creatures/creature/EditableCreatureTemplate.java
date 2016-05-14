package project.thirteenthage.creatures.creature;

import java.util.ArrayList;
import java.util.List;

import project.thirteenthage.creatures.internal.interfaces.IAttack;
import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.internal.interfaces.ICreatureTemplate;
import project.thirteenthage.creatures.internal.interfaces.ISpecial;

/**
 * Defines a creature, implementation of {@link ICreature}.
 */
public class EditableCreatureTemplate implements ICreatureTemplate
{
	private String _name;
	private int _level;
	private CreatureSize _size;
	private List<String> _labels = new ArrayList<String>();
	private int _ac;
	private int _pd;
	private int _md;
	private double _hp;
	private int _ini;
	private List<IAttack> _attacks = new ArrayList<IAttack>();
	private List<ISpecial> _specials = new ArrayList<ISpecial>();
	private List<ISpecial> _nastier = new ArrayList<ISpecial>();


	public EditableCreatureTemplate(ICreatureTemplate template)
	{
		_name = template.getName();
		_level = template.getLevel();
		_size = template.getSize();
		_labels.addAll(template.getLabels());
		_ac = template.getAC();
		_pd = template.getPD();
		_md = template.getMD();
		_hp = template.getHP();
		_ini = template.getInitiative();
		_attacks.addAll(template.getAttacks());
		_specials.addAll(template.getSpecials());
		_nastier.addAll(template.getNastierSpecials());
	}


	@Override
	public String getName()
	{
		return _name;
	}


	@Override
	public int getLevel()
	{
		return _level;
	}


	@Override
	public CreatureSize getSize()
	{
		return _size;
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
		return _nastier;
	}


	@Override
	public ICreature toCreature()
	{
		CreatureBuilder builder = new CreatureBuilder();
		builder.name(getName()).size(getSize()).level(getLevel()).addInitiative(getInitiative()).addAC(getAC()).addPD(getPD()).addMD(getMD()).scaleHP(getHP());

		final Creature creature;
		if (getLabels().contains("Mook"))
		{
			creature = builder.buildMook();
		} else
		{
			creature = builder.buildCreature();
		}

		creature.setAttacks(getAttacks());
		creature.getLabels().addAll(getLabels());
		creature.setSpecials(getSpecials());
		creature.setNastierSpecials(getNastierSpecials());
		creature.setTemplate(this);

		return creature;
	}


	public void setName(String name)
	{
		_name = name;
	}


	public void setLevel(int level)
	{
		_level = level;
	}


	public void setSize(CreatureSize size)
	{
		_size = size;
	}


	public void setAC(int ac)
	{
		_ac = ac;
	}


	public void setPD(int pd)
	{
		_pd = pd;
	}


	public void setMD(int md)
	{
		_md = md;
	}


	public void setHP(double hp)
	{
		_hp = hp;
	}


	public void setInitiative(int ini)
	{
		_ini = ini;
	}

}
