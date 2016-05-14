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
public class EditableCreatureTemplate extends AbstractCreatureTemplate
{
	private String _name = "New creature";
	private int _level = 1;
	private CreatureSize _size = CreatureSize.NORMAL;
	private List<String> _labels = new ArrayList<String>();
	private int _attack = 0;
	private int _ac = 0;
	private int _pd = 0;
	private int _md = 0;
	private double _hp = 1.0;
	private int _ini = 0;
	private List<IAttack> _attacks = new ArrayList<IAttack>();
	private List<ISpecial> _specials = new ArrayList<ISpecial>();
	private List<ISpecial> _nastier = new ArrayList<ISpecial>();


	public EditableCreatureTemplate(ICreatureTemplate template)
	{
		_name = template.getName();
		_level = template.getLevel();
		_size = template.getSize();
		_labels.addAll(template.getLabels());
		_attack = template.getModifierAttack();
		_ac = template.getModifierAC();
		_pd = template.getModifierPD();
		_md = template.getModifierMD();
		_hp = template.getModifierHP();
		_ini = template.getModifierInitiative();
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
	public int getModifierAttack()
	{
		return _attack;
	}


	@Override
	public int getModifierAC()
	{
		return _ac;
	}


	@Override
	public int getModifierPD()
	{
		return _pd;
	}


	@Override
	public int getModifierMD()
	{
		return _md;
	}


	@Override
	public double getModifierHP()
	{
		return _hp;
	}


	@Override
	public int getModifierInitiative()
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


	public void setAttack(int attack)
	{
		_attack = attack;
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
