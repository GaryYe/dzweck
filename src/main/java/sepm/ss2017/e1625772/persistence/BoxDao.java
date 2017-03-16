package sepm.ss2017.e1625772.persistence;

import sepm.ss2017.e1625772.domain.Box;

import java.util.Collection;

/**
 * The DAO interface to access the data objects of the box entity.
 */
public interface BoxDao {
    /**
     * @return all boxes
     */
    Collection<Box> findAll();

    /**
     * Finds all boxes that have the same parameters as the given box. NULL values will be ignored.
     * @param box the box to match with
     * @return all similar boxes
     */
    Collection<Box> findAll(Box box);

    void create(Box box);
    void delete(Box box);
    void update(Box box);
}
