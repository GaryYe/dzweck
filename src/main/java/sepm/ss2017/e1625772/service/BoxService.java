package sepm.ss2017.e1625772.service;

import sepm.ss2017.e1625772.domain.Box;
import sepm.ss2017.e1625772.exceptions.BusinessLogicException;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public interface BoxService {
    /**
     * Creates the given box and registers it into the system.
     *
     * @param box the box to register
     */
    void createBox(Box box) throws BusinessLogicException;

    /**
     * Retrieves the box with the given id.
     *
     * @param id the id of the box to return
     * @return either null, if the box was not found, or else the box with the matching id
     */
    Box findBox(Long id) throws BusinessLogicException;

    /**
     * Updates the given box with the new attributes. A registered box must exist, otherwise an exception will be
     * thrown.
     *
     * @param box the box with the new attributes.
     */
    void updateBox(Box box) throws BusinessLogicException;

    /**
     * Deletes the given box.
     * @param box the box to delete
     */
    void deleteBox(Box box) throws BusinessLogicException;
}
