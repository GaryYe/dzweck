package sepm.ss2017.e1625772.persistence;

import sepm.ss2017.e1625772.domain.BoxImage;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public interface BoxImageDAO {
    /**
     * Finds the image of the given box
     *
     * @param boxId the id of the box
     * @return the image of the box
     */
    BoxImage findByBox(Long boxId);

    /**
     * Creates the image
     *
     * @param boxImage the box image to create
     */
    void create(BoxImage boxImage);

    /**
     * Updates the image
     *
     * @param boxImage the image to update, the old one will be overwritten
     */
    void update(BoxImage boxImage);
}
