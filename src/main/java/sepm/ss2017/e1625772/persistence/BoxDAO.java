package sepm.ss2017.e1625772.persistence;

import sepm.ss2017.e1625772.domain.Box;
import sepm.ss2017.e1625772.exceptions.DataAccessException;

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
    Box findOne(Long id) throws DataAccessException;

    /**
     * @return all boxes
     */
    Collection<Box> findAll() throws DataAccessException;

    /**
     * Finds all boxes that have the same parameters as the given box. Attributes that have NULL values will be
     * regarded as wildcards.
     *
     * @param box the box to match with
     * @return all similar boxes that match with the given box
     */
    Collection<Box> findAll(Box box) throws DataAccessException;

    /**
     * Creates a new box
     * TODO: What if box already inserted?
     *
     * @param box the box to create
     */
    void create(Box box) throws DataAccessException;

    /**
     * Deletes a given box by matching the ID
     *
     * @param box the box to delete
     * @throws IllegalArgumentException if the given box was null
     * @throws DataAccessException      if an error occurred while accessing the data layer
     */
    void delete(Box box) throws DataAccessException;

    /**
     * Updates the box with the same ID as the given one and sets all its attributes according to the new one.
     * TODO: What if not there?
     *
     * @param box the new box
     */
    void update(Box box) throws DataAccessException;
}
