package project.thirteenthage.creatures.internal.interfaces;

import java.util.List;

import project.thirteenthage.creatures.creature.CreatureSize;

/**
 * Interface for a creature.
 */
public interface ICreature extends INamedItem, ILeveledItem
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
	int getAC();


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
	int getPD();


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
	int getMD();


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
	double getHP();


	/**
	 * <p>
	 * Gets the strike damage of this creature.
	 * </p>
	 * 
	 * @return the damage.
	 */
	double getStrikeDamage();


	/**
	 * <p>
	 * Gets the fear threshold for this creature.
	 * </p>
	 * 
	 * @return the threshold.
	 */
	double getFearThreshold();


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
	int getInitiative();


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
}
