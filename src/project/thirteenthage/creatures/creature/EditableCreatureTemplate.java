package project.thirteenthage.creatures.creature;

import project.thirteenthage.creatures.internal.interfaces.ICreature;
import project.thirteenthage.creatures.internal.interfaces.ICreatureTemplate;

/**
 * Defines a creature, implementation of {@link ICreature}.
 */
public class EditableCreatureTemplate extends AbstractCreatureTemplate
{
	public EditableCreatureTemplate(ICreatureTemplate template)
	{
		setName(template.getName());
		setLevel(template.getLevel());
		setSize(template.getSize());
		getLabels().addAll(template.getLabels());
		setAttack(template.getModifierAttack());
		setAC(template.getModifierAC());
		setPD(template.getModifierPD());
		setMD(template.getModifierMD());
		setHP(template.getModifierHP());
		setInitiative(template.getModifierInitiative());
		getAttacks().addAll(template.getAttacks());
		getSpecials().addAll(template.getSpecials());
		getNastierSpecials().addAll(template.getNastierSpecials());
	}
}
