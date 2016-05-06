package project.thirteenthage.creatures.internal.interfaces;

import java.util.List;

/**
 * Interface for a creature.
 */
public interface ICreatureTemplate extends INamedItem, ILeveledItem
{
	/**
	 * Gets a list of labels. This list has no impact on the stats, it is rather used for sorting creatures.
	 * @return a (possibly empty) list, but never {@code null}.
	 */
	List<String> getLabels();
	
	/**
	 * <p>
	 * Gets the AC modifier of this creature.
	 * </p><p>
	 * Adding the modifier to the baseline stats of the creature gets the final stats.
	 * </p>
	 * @return the modifier.
	 */
	double getAC();

	
	/**
	 * <p>
	 * Gets the PD modifier of this creature.
	 * </p><p>
	 * Adding the modifier to the baseline stats of the creature gets the final stats.
	 * </p>
	 * @return the modifier.
	 */
	double getPD();

	
	/**
	 * <p>
	 * Gets the MD modifier of this creature.
	 * </p><p>
	 * Adding the modifier to the baseline stats of the creature gets the final stats.
	 * </p>
	 * @return the modifier.
	 */
	double getMD();
	
	
	/**
	 * <p>
	 * Gets the HP factor of this creature.
	 * </p><p>
	 * Multiplying the modifier with the baseline stats of the creature gets the final stats.
	 * </p>
	 * @return the modifier.
	 */
	double getHP();
	
	
	/**
	 * <p>
	 * Gets the initiative modifier of this creature.
	 * </p><p>
	 * Adding the modifier to the level of the creature gets the final stats.
	 * </p>
	 * @return the modifier.
	 */
	int getInitiative();
	
	
	/**
	 * Gets a list of the attacks that this creature has.
	 * @return a (possibly empty) list, but never {@code null}.
	 */
	List<IAttack> getAttacks();

	/**
	 * Gets a list of the specials that this creature has.
	 * @return a (possibly empty) list, but never {@code null}.
	 */
	List<ISpecial> getSpecials();

	/**
	 * Gets a list of the (optional) nastier specials that this creature has.
	 * @return a (possibly empty) list, but never {@code null}.
	 */
	List<ISpecial> getNastierSpecials();
}
