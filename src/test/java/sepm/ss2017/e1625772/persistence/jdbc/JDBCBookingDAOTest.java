package sepm.ss2017.e1625772.persistence.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import sepm.ss2017.e1625772.domain.Booking;
import sepm.ss2017.e1625772.domain.builders.BookingBuilder;
import sepm.ss2017.e1625772.exceptions.DataAccessException;
import sepm.ss2017.e1625772.persistence.BookingDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDate.of;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public class JDBCBookingDAOTest {

    private EmbeddedDatabase db;
    private BookingDAO dao;

    @Before
    public void setUp() throws SQLException {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db/create-db.sql")
                .build();
        dao = new JDBCBookingDAO(db);
    }

    @After
    public void tearDown() throws SQLException {
        db.shutdown();
    }

    @Test
    public void testFindAllShouldReturnNothing() throws DataAccessException {
        assertTrue(dao.findAll().isEmpty());
    }

    @Test
    public void testCreateNormalShouldWork() throws DataAccessException {
        Booking booking = new BookingBuilder(404L)
                .beginTime(of(2017, 3, 12))
                .endTime(of(2017, 3, 14))
                .customer("Elon Musk")
                .create();
        assertTrue(dao.findAll().isEmpty());
        dao.create(booking);
        assertEquals(1, dao.findAll().size());
        assertEquals(booking, new ArrayList<>(dao.findAll()).get(0));
    }

    @Test
    public void testCreateWithNullAttributesShouldWork() throws DataAccessException {
        Booking booking = new BookingBuilder(404L)
                .beginTime(null)
                .endTime(of(2017, 3, 14))
                .customer(null)
                .create();
        assertTrue(dao.findAll().isEmpty());
        dao.create(booking);
        assertEquals(1, dao.findAll().size());
        assertEquals(booking, new ArrayList<>(dao.findAll()).get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNullShouldThrow() throws DataAccessException {
        dao.create(null);
    }

    @Test
    public void findAllBetweenEmpty() throws Exception {
        assertTrue(dao.findAllBetween(of(2017, 3, 1), of(2017, 3, 5)).isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteNullShouldThrow() throws DataAccessException {
        dao.delete(null);
    }

    @Test
    public void testDeleteNotExistingShouldJustDoNothing() throws DataAccessException {
        dao.delete(new BookingBuilder(30L).create());
    }

    @Test
    public void testDeleteOneShouldWork() throws DataAccessException {
        Booking booking = new BookingBuilder(30L).create();
        dao.create(booking);
        assertTrue(!dao.findAll().isEmpty());
        dao.delete(booking);
        assertTrue(dao.findAll().isEmpty());
    }

    private void testBookingRange(LocalDate begin, LocalDate end, LocalDate queryBegin, LocalDate queryEnd, boolean isInside) throws DataAccessException {
        Booking booking = new BookingBuilder(1L).beginTime(begin).endTime(end).create();
        dao.create(booking);
        List<Booking> expected = new ArrayList<>();
        if (isInside)
            expected.add(booking);
        assertEquals(expected, dao.findAllBetween(queryBegin, queryEnd));
        dao.delete(booking);
    }
    @Test
    public void testFindAllBetweenWithNormalQueryRanges() throws Exception {
        LocalDate queryBegin = of(2017, 3, 1);
        LocalDate queryEnd = of(2017, 3, 10);

        testBookingRange(of(2017, 3, 1), of(2017, 3, 1), queryBegin, queryEnd, true);
        testBookingRange(of(2017, 3, 1), of(2017, 3, 10), queryBegin, queryEnd, true);
        testBookingRange(of(2017, 2, 28), of(2017, 3, 11), queryBegin, queryEnd, true);

        testBookingRange(of(2017, 2, 28), of(2017, 2, 28), queryBegin, queryEnd, false);
        testBookingRange(of(2017, 3, 11), of(2018, 3, 5), queryBegin, queryEnd, false);
    }

    @Test
    public void testFindAllBetweenWithIllegalQueries() throws Exception {
        LocalDate queryBegin = of(2017, 3, 1);
        LocalDate queryEnd = of(2017, 3, 10);
        
    }
}