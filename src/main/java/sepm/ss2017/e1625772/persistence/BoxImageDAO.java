package sepm.ss2017.e1625772.persistence;

import sepm.ss2017.e1625772.domain.BoxImage;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public interface BoxImageDAO {
    /**
     *
     * @param boxId the id of the box
     * @return
     */
    BoxImage findByBox(Long boxId);

    void create(BoxImage boxImage);

    void update(BoxImage boxImage);
}
