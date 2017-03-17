package sepm.ss2017.e1625772.persistence.jdbc;

import org.junit.Test;
import sepm.ss2017.e1625772.domain.Box;
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
    public void testCreateBoxWithAllAttributesSet() {
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
    public void testFindAllShouldReturnEmptyWhenNoElementsInside() {
        assertTrue(boxDAO.findAll().isEmpty());
    }

    @Test
    public void testDeleteExistingElement() {
        Box box = new Box.BoxBuilder(23L).create();
        boxDAO.create(box);
        assertFalse(boxDAO.findAll().isEmpty());
        boxDAO.delete(new Box.BoxBuilder(23L).create());
        assertTrue(boxDAO.findAll().isEmpty());
    }

    @Test
    public void testDeleteNonExistingElement() {
        Box box = new Box.BoxBuilder(23L).create();
        boxDAO.create(box);
        boxDAO.delete(new Box.BoxBuilder(42L).create());
        assertFalse(boxDAO.findAll().isEmpty());
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
    public void testUpdateExisting() {
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
    public void testFindOneNonExistingShouldReturnNull() {
        assertNull(boxDAO.findOne(1L));
    }

    @Test
    public void testFindOneExistingShouldReturnMatching() {
        Box box = new Box.BoxBuilder(23L).create();
        boxDAO.create(box);
        assertEquals(box, boxDAO.findOne(23L));
    }
}
