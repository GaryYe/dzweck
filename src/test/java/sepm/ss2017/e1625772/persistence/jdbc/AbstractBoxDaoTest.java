package sepm.ss2017.e1625772.persistence.jdbc;

import org.junit.Test;
import sepm.ss2017.e1625772.domain.Box;
import sepm.ss2017.e1625772.persistence.BoxDao;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;

/**
 * Tests the BoxDAO implementation.
 * <p>
 * The implementation that should be tested must be set with the {@link AbstractBoxDaoTest#setBoxDao(BoxDao)} method.
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
        boxDao.create(box);
        List<Box> storedBoxes = new ArrayList<>(boxDao.findAll());
        assertEquals(1, storedBoxes.size());
        assertEquals(box, storedBoxes.get(0));
    }

    @Test
    public void testFindAllShouldReturnEmptyWhenNoElementsInside() {
        assertTrue(boxDao.findAll().isEmpty());
    }

    @Test
    public void testDeleteExistingElement() {
        Box box = new Box.BoxBuilder(23L).create();
        boxDao.create(box);
        assertFalse(boxDao.findAll().isEmpty());
        boxDao.delete(new Box.BoxBuilder(23L).create());
        assertTrue(boxDao.findAll().isEmpty());
    }

    @Test
    public void testDeleteNonExistingElement() {
        Box box = new Box.BoxBuilder(23L).create();
        boxDao.create(box);
        boxDao.delete(new Box.BoxBuilder(42L).create());
        assertFalse(boxDao.findAll().isEmpty());
    }

    @Test
    public void testDeleteAttributesShouldNotMatter() {
        Box box = new Box.BoxBuilder(42L)
                .area(37.8)
                .dailyRate(-22.2)
                .windows(false)
                .indoor(true)
                .name("Box 42")
                // .image(new BufferedImage(1, 1, 3)) TODO
                .create();
        boxDao.create(box);
        assertFalse(boxDao.findAll().isEmpty());
        boxDao.delete(new Box.BoxBuilder(42L)
                .area(12.8)
                .dailyRate(293.0)
                .windows(false)
                .indoor(true)
                .name("Box")
                .create());
        assertTrue(boxDao.findAll().isEmpty());
    }

    @Test
    public void testUpdateExisting() {
        Box before = new Box.BoxBuilder(42L)
                .area(37.8)
                .dailyRate(-22.2)
                .windows(false)
                .indoor(true)
                .name("Box 42")
                // .image(new BufferedImage(1, 1, 3)) TODO
                .create();
        boxDao.create(before);
        Box after = new Box.BoxBuilder(42L)
                .area(3939.9)
                .dailyRate(+25.2)
                .windows(true)
                .indoor(true)
                .name("Box 4333")
                // .image(new BufferedImage(1, 1, 3)) TODO
                .create();

        boxDao.update(after);
        List<Box> list = new ArrayList<>(boxDao.findAll());
        assertEquals(after, list.get(0));
    }

    @Test
    public void testFindOneNonExistingShouldReturnNull() {
        assertNull(boxDao.findOne(1L));
    }

    @Test
    public void testFindOneExistingShouldReturnMatching() {
        Box box = new Box.BoxBuilder(23L).create();
        boxDao.create(box);
        assertEquals(box, boxDao.findOne(23L));
    }
}
