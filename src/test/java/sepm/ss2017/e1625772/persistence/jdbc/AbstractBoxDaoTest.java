package sepm.ss2017.e1625772.persistence.jdbc;

import org.junit.Test;
import sepm.ss2017.e1625772.domain.Box;
import sepm.ss2017.e1625772.exceptions.DataAccessException;
import sepm.ss2017.e1625772.persistence.BoxDAO;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;

/**
 * Tests the BoxDAO implementation.
 * <p>
 * The implementation that should be tested must be set with the {@link AbstractBoxDaoTest#setBoxDAO(BoxDAO)} method.
 */
public abstract class AbstractBoxDaoTest {
    protected BoxDAO boxDAO;

    protected void setBoxDAO(BoxDAO boxDAO) {
        this.boxDAO = boxDAO;
    }

    @Test
    public void testCreateBoxWithAllAttributesSet() throws DataAccessException {
        Box box = new Box.BoxBuilder(42L)
                .area(37.8)
                .dailyRate(-22.2)
                .windows(false)
                .indoor(true)
                .name("Box 42")
                // .image(new BufferedImage(1, 1, 3)) TODO
                .create();
        boxDAO.create(box);
        List<Box> storedBoxes = new ArrayList<>(boxDAO.findAll());
        assertEquals(1, storedBoxes.size());
        assertEquals(box, storedBoxes.get(0));
    }

    @Test
    public void testFindAllShouldReturnEmptyWhenNoElementsInside() throws DataAccessException {
        assertTrue(boxDAO.findAll().isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteNullShouldThrowException() throws DataAccessException {
        boxDAO.delete(null);
    }

    @Test
    public void testDeleteExistingElement() throws DataAccessException {
        Box box = new Box.BoxBuilder(23L).create();
        boxDAO.create(box);
        assertFalse(boxDAO.findAll().isEmpty());
        boxDAO.delete(new Box.BoxBuilder(23L).create());
        assertTrue(boxDAO.findAll().isEmpty());
    }

    @Test
    public void testDeleteNonExistingElement() throws DataAccessException {
        Box box = new Box.BoxBuilder(23L).create();
        boxDAO.create(box);
        boxDAO.delete(new Box.BoxBuilder(42L).create());
        assertFalse(boxDAO.findAll().isEmpty());
    }

    @Test
    public void testDeleteAttributesShouldNotMatter() throws DataAccessException {
        Box box = new Box.BoxBuilder(42L)
                .area(37.8)
                .dailyRate(-22.2)
                .windows(false)
                .indoor(true)
                .name("Box 42")
                // .image(new BufferedImage(1, 1, 3)) TODO
                .create();
        boxDAO.create(box);
        assertFalse(boxDAO.findAll().isEmpty());
        boxDAO.delete(new Box.BoxBuilder(42L)
                .area(12.8)
                .dailyRate(293.0)
                .windows(false)
                .indoor(true)
                .name("Box")
                .create());
        assertTrue(boxDAO.findAll().isEmpty());
    }

    @Test
    public void testUpdateExisting() throws DataAccessException {
        Box before = new Box.BoxBuilder(42L)
                .area(37.8)
                .dailyRate(-22.2)
                .windows(false)
                .indoor(true)
                .name("Box 42")
                // .image(new BufferedImage(1, 1, 3)) TODO
                .create();
        boxDAO.create(before);
        Box after = new Box.BoxBuilder(42L)
                .area(3939.9)
                .dailyRate(+25.2)
                .windows(true)
                .indoor(true)
                .name("Box 4333")
                // .image(new BufferedImage(1, 1, 3)) TODO
                .create();

        boxDAO.update(after);
        List<Box> list = new ArrayList<>(boxDAO.findAll());
        assertEquals(after, list.get(0));
    }

    @Test
    public void testFindOneNonExistingShouldReturnNull() throws DataAccessException {
        assertNull(boxDAO.findOne(1L));
    }

    @Test
    public void testFindOneExistingShouldReturnMatching() throws DataAccessException {
        Box box = new Box.BoxBuilder(23L).create();
        boxDAO.create(box);
        assertEquals(box, boxDAO.findOne(23L));
    }
}
