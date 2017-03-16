package sepm.ss2017.e1625772.persistence.jdbc;

import org.junit.Test;
import sepm.ss2017.e1625772.domain.Box;
import sepm.ss2017.e1625772.persistence.BoxDao;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * Tests the BoxDAO implementation.
 */
public abstract class AbstractBoxDaoTest {
    protected BoxDao boxDao;

    protected void setBoxDao(BoxDao boxDao) {
        this.boxDao = boxDao;
    }

    @Test
    public void testCreateBoxWithAllAttributesSet() {
        Box box = new Box.BoxBuilder(42L)
                .area(37.8)
                .dailyRate(-22.2)
                .windows(false)
                .indoor(true)
                .name("Box 42")
                // .image(new BufferedImage(1, 1, 3)) TODO
                .create();
        boxDao.update(box);
        List<Box> storedBoxes = new ArrayList<>(boxDao.findAll());
        assertEquals(1, storedBoxes.size());
        assertEquals(box, storedBoxes.get(0));
    }

    @Test
    public void findAllShouldReturnEmptyWhenNoElementsInside() throws Exception {
        assertTrue(boxDao.findAll().isEmpty());
    }
}
