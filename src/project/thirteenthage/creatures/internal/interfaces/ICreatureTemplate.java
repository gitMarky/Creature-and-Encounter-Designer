package project.thirteenthage.creatures.internal.interfaces;

import java.io.File;
import java.util.List;

import project.thirteenthage.creatures.creature.BetterDefense;
import project.thirteenthage.creatures.creature.CreatureSize;

/**
 * Interface for a creature.
 */
public interface ICreatureTemplate extends INamedItem, ILeveledItem
{
	/**
	 * Gets a list of labels. This list has no impact on the stats, it is rather
	 * used for sorting creatures.
	 * 
	 * @return a (possibly empty) list, but never {@code null}.
	 */
	List<String> getLabels();


	/**
	 * Gets the size of the creature.
	 * 
	 * @return the size.
	 */
	CreatureSize getSize();


	/**
	 * Gets the attack modifier for this creature.
	 * 
	 * @return the modifier.
	 */
	int getModifierAttack();


	/**
	 * <p>
	 * Gets the AC modifier of this creature.
	 * </p>
	 * <p>
	 * Adding the modifier to the baseline stats of the creature gets the final
	 * stats.
	 * </p>
	 * 
	 * @return the modifier.
	 */
	int getModifierAC();


	/**
	 * <p>
	 * Gets the PD modifier of this creature.
	 * </p>
	 * <p>
	 * Adding the modifier to the baseline stats of the creature gets the final
	 * stats.
	 * </p>
	 * 
	 * @return the modifier.
	 */
	int getModifierPD();


	/**
	 * <p>
	 * Gets the MD modifier of this creature.
	 * </p>
	 * <p>
	 * Adding the modifier to the baseline stats of the creature gets the final
	 * stats.
	 * </p>
	 * 
	 * @return the modifier.
	 */
	int getModifierMD();


	/**
	 * <p>
	 * Gets the HP factor of this creature.
	 * </p>
	 * <p>
	 * Multiplying the modifier with the baseline stats of the creature gets the
	 * final stats.
	 * </p>
	 * 
	 * @return the modifier.
	 */
	double getModifierHP();


	/**
	 * <p>
	 * Gets the damage factor of this creature.
	 * </p>
	 * <p>
	 * Multiplying the modifier with the baseline stats of the creature gets the
	 * final stats.
	 * </p>
	 * 
	 * @return the modifier.
	 */
	double getModifierDamage();


	/**
	 * <p>
	 * Gets the initiative modifier of this creature.
	 * </p>
	 * <p>
	 * Adding the modifier to the level of the creature gets the final stats.
	 * </p>
	 * 
	 * @return the modifier.
	 */
	int getModifierInitiative();


	/**
	 * Determines which defense from the monster template is the better defense:
	 * MD or PD.
	 * 
	 * @return the better defense.
	 */
	BetterDefense getBetterDefense();


	/**
	 * Gets a list of the attacks that this creature has.
	 * 
	 * @return a (possibly empty) list, but never {@code null}.
	 */
	List<IAttack> getAttacks();


	/**
	 * Gets a list of the specials that this creature has.
	 * 
	 * @return a (possibly empty) list, but never {@code null}.
	 */
	List<ISpecial> getSpecials();


	/**
	 * Gets a list of the (optional) nastier specials that this creature has.
	 * 
	 * @return a (possibly empty) list, but never {@code null}.
	 */
	List<ISpecial> getNastierSpecials();


	/**
	 * Converts this template to a creature.
	 * 
	 * @return the creature.
	 */
	ICreature toCreature();


	/**
	 * Saves the template to a file.
	 * 
	 * @return the file that the template was saved to.
	 */
	File saveToFile();


	/**
	 * Saves the template to a specific file.
	 * 
	 * @param file
	 *            the target file.
	 * @return the file that the template was saved to.
	 */
	File saveToFile(File file);
}
