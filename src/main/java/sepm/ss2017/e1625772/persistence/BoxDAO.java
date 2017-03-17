package sepm.ss2017.e1625772.persistence;

import sepm.ss2017.e1625772.domain.Box;

import java.util.Collection;

/**
 * The DAO interface to access the data objects of the box entity.
 */
public interface BoxDAO {
    /**
     * Finds one box that has the same ID as the given box.
     *
     * @param id the id of the box
     * @return the box that matches with the given ID
     */
    Box findOne(Long id);

    /**
     * @return all boxes
     */
    Collection<Box> findAll();

    /**
     * Finds all boxes that have the same parameters as the given box. Attributes that have NULL values will be
     * regarded as wildcards.
     *
     * @param box the box to match with
     * @return all similar boxes that match with the given box
     */
    Collection<Box> findAll(Box box);

    /**
     * Creates a new box
     * TODO: What if box already inserted?
     *
     * @param box the box to create
     */
    void create(Box box);

    /**
     * Deletes a given box by matching the ID
     *
     * @param box the box to delete
     */
    void delete(Box box);

    /**
     * Updates the box with the same ID as the given one and sets all its attributes according to the new one.
     * TODO: What if not there?
     *
     * @param box the new box
     */
    void update(Box box);
}
