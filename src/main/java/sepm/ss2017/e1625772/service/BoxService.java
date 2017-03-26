package sepm.ss2017.e1625772.service;

import sepm.ss2017.e1625772.domain.Box;
import sepm.ss2017.e1625772.domain.BoxImage;
import sepm.ss2017.e1625772.exceptions.ServiceException;

import java.util.List;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public interface BoxService {
    /**
     * Creates the given box and registers it into the system.
     *
     * @param box the box to register
     * @throws ServiceException if an error occurred while the service was processing the request
     */
    void createBox(Box box);

    /**
     * Retrieves the box with the given id.
     *
     * @param id the id of the box to return
     * @return either null, if the box was not found, or else the box with the matching id
     * @throws ServiceException if an error occurred while the service was processing the request
     */
    Box findBox(Long id);

    /**
     * Finds all boxes that match the given one
     *
     * @param box the box to match with
     * @return the boxes
     * @throws ServiceException if an error occurred at the service
     */
    List<Box> findBoxes(Box box);

    /**
     * Updates the given box with the new attributes. A registered box must exist, otherwise an exception will be
     * thrown.
     *
     * @param box the box with the new attributes.
     * @throws ServiceException if an error occurred while the service was processing the request
     */
    void updateBox(Box box);

    /**
     * Deletes the given box.
     *
     * @param box the box to delete
     * @throws ServiceException if an error occurred while the service was processing the request
     */
    void deleteBox(Box box);


    /**
     * Finds the image of the corresponding box.
     *
     * @param boxId the id of the box
     * @return null if not found or the image
     * @throws ServiceException if an error occurred while the service was processing the request
     */
    BoxImage findImage(Long boxId);

    /**
     * Saves the given image by the given box or attempts to overwrite it.
     *
     * @param boxImage the image of the box
     * @throws ServiceException if an error occurred while the service was processing the request
     */
    void saveImage(BoxImage boxImage);
}
