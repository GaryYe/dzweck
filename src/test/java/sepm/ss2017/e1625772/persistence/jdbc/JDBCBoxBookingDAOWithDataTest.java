package sepm.ss2017.e1625772.persistence.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import sepm.ss2017.e1625772.domain.BoxBooking;
import sepm.ss2017.e1625772.domain.builders.BookingBuilder;
import sepm.ss2017.e1625772.domain.builders.BoxBookingBuilder;
import sepm.ss2017.e1625772.domain.builders.BoxBuilder;
import sepm.ss2017.e1625772.exceptions.DataAccessException;
import sepm.ss2017.e1625772.exceptions.DuplicatedObjectException;
import sepm.ss2017.e1625772.exceptions.ObjectNotFoundException;
import sepm.ss2017.e1625772.persistence.BookingDAO;
import sepm.ss2017.e1625772.persistence.BoxBookingDAO;
import sepm.ss2017.e1625772.persistence.BoxDAO;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Integration tests with some boxes and bookings already in DB.
 *
 * @author Gary Ye
 * @version %I% %G%
 */
public class JDBCBoxBookingDAOWithDataTest {
    private EmbeddedDatabase db;

    private BookingDAO bookingDAO;
    private BoxDAO boxDAO;
    private BoxBookingDAO boxBookingDAO;

    @Before
    public void setUp() throws DataAccessException {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db/create-db.sql")
                .build();
        boxBookingDAO = new JDBCBoxBookingDAO(db);
        boxDAO = new JDBCBoxDAO(db);
        bookingDAO = new JDBCBookingDAO(db);

        boxDAO.create(new BoxBuilder(1L).create());
        boxDAO.create(new BoxBuilder(2L).create());
        boxDAO.create(new BoxBuilder(3L).create());
        boxDAO.create(new BoxBuilder(4L).create());

        bookingDAO.create(new BookingBuilder(1L).create());
        bookingDAO.create(new BookingBuilder(2L).create());
        bookingDAO.create(new BookingBuilder(3L).create());
        bookingDAO.create(new BookingBuilder(4L).create());
    }

    @After
    public void tearDown() {
        db.shutdown();
    }

    @Test
    public void testCreateShouldWork() throws DataAccessException, DuplicatedObjectException {
        BoxBooking boxBooking1 = new BoxBookingBuilder(1L, 1L).create();
        BoxBooking boxBooking2 = new BoxBookingBuilder(1L, 2L).create();
        List<BoxBooking> expected = Arrays.asList(boxBooking1, boxBooking2);
        boxBookingDAO.create(boxBooking1);
        boxBookingDAO.create(boxBooking2);
        assertEquals(expected, boxBookingDAO.findAll());
    }

    @Test
    public void testFindAllByBooking() throws DataAccessException, DuplicatedObjectException {
        BoxBooking boxBooking1 = new BoxBookingBuilder(1L, 1L).create();
        BoxBooking boxBooking2 = new BoxBookingBuilder(1L, 2L).create();
        BoxBooking boxBooking3 = new BoxBookingBuilder(2L, 2L).create();
        boxBookingDAO.create(boxBooking1);
        boxBookingDAO.create(boxBooking2);
        boxBookingDAO.create(boxBooking3);
        assertEquals(Arrays.asList(boxBooking1, boxBooking2), boxBookingDAO.findAllByBooking(1L));
        assertEquals(Arrays.asList(boxBooking3), boxBookingDAO.findAllByBooking(2L));
    }

    @Test
    public void testFindAllByBox() throws DataAccessException, DuplicatedObjectException {
        BoxBooking boxBooking1 = new BoxBookingBuilder(1L, 1L).create();
        BoxBooking boxBooking2 = new BoxBookingBuilder(1L, 2L).create();
        BoxBooking boxBooking3 = new BoxBookingBuilder(2L, 2L).create();
        boxBookingDAO.create(boxBooking1);
        boxBookingDAO.create(boxBooking2);
        boxBookingDAO.create(boxBooking3);
        assertEquals(Arrays.asList(boxBooking1), boxBookingDAO.findAllByBox(1L));
        assertEquals(Arrays.asList(boxBooking2, boxBooking3), boxBookingDAO.findAllByBox(2L));
    }

    @Test(expected = DuplicatedObjectException.class)
    public void testCreateTwiceShouldThrowDuplicatedObjectException() throws DuplicatedObjectException {
        BoxBooking boxBooking1 = new BoxBookingBuilder(1L, 1L).create();
        boxBookingDAO.create(boxBooking1);
        boxBookingDAO.create(boxBooking1);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testFindAllByBookingNullShouldThrow() throws DataAccessException {
        boxBookingDAO.findAllByBooking(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindAllByBoxNullShouldThrow() throws DataAccessException {
        boxBookingDAO.findAllByBox(null);
    }

    @Test
    public void testDeleteShouldHappen() throws ObjectNotFoundException, DuplicatedObjectException {
        BoxBooking boxBooking = new BoxBookingBuilder(3L, 4L).create();
        boxBookingDAO.create(boxBooking);
        assertTrue(!boxBookingDAO.findAll().isEmpty());
        boxBookingDAO.delete(boxBooking);
        assertTrue(boxBookingDAO.findAll().isEmpty());
    }
}
