package sepm.ss2017.e1625772.persistence.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import sepm.ss2017.e1625772.domain.BoxBooking;
import sepm.ss2017.e1625772.domain.builders.BoxBookingBuilder;
import sepm.ss2017.e1625772.exceptions.DataAccessException;
import sepm.ss2017.e1625772.persistence.BoxBookingDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public class JDBCBoxBookingDAOWithEmptyDBTest {
    private BoxBookingDAO dao;
    private EmbeddedDatabase db;

    @Before
    public void setUp() {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db/create-db.sql")
                .build();
        dao = new JDBCBoxBookingDAO(db);
    }

    @After
    public void tearDown() throws SQLException {
        db.shutdown();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNullShouldThrow() throws Exception {
        dao.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteNullShouldThrow() throws Exception {
        dao.delete(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteNullBoxIdShouldThrow() throws Exception {
        dao.delete(new BoxBookingBuilder(3L, null).create());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteNullBookingIdShouldThrow() throws Exception {
        dao.delete(new BoxBookingBuilder(null, 3L).create());
    }

    @Test
    public void testDeleteShouldHappen() throws Exception {
        BoxBooking boxBooking = new BoxBookingBuilder(3L, 4L).create();
        // dao.create(boxBooking);
    }

    @Test(expected = DataAccessException.class)
    public void testCreateWithoutExistingBoxesShouldThrow() throws DataAccessException {
        BoxBooking boxBooking = new BoxBookingBuilder(3L, 5L).create();
        dao.create(boxBooking);
    }
}