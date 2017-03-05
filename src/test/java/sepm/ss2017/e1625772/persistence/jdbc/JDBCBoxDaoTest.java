package sepm.ss2017.e1625772.persistence.jdbc;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;

import java.sql.SQLException;

import static org.junit.Assert.*;

/**
 * Testing with the test_db
 */
public class JDBCBoxDaoTest extends AbstractBoxDaoTest {
    JdbcDataSource dataSource;

    @Before
    public void setUp() throws SQLException {
        dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:~/test_db");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        setBoxDao(new JDBCBoxDao(dataSource.getConnection()));
        this.dataSource.getConnection().setAutoCommit(false);
    }

    @After
    public void tearDown() throws SQLException {
        dataSource.getConnection().rollback();
    }

    @Test
    public void findAllShouldReturnEmptyWhenNoElementsInside() throws Exception {
        assertTrue(boxDao.findAll().isEmpty());
    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

}