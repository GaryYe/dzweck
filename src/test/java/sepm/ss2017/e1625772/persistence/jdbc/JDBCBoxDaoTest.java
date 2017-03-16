package sepm.ss2017.e1625772.persistence.jdbc;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sepm.ss2017.e1625772.domain.Box;

import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Testing with the test_db
 */
public class JDBCBoxDaoTest extends AbstractBoxDaoTest {
    private JdbcDataSource dataSource;

    @Before
    public void setUp() throws SQLException {
        final String url = "jdbc:h2:mem:test;INIT=runscript from './scripts/database.sql'\\;";
        dataSource = new JdbcDataSource();
        dataSource.setURL(url);
        dataSource.setUser("sa");
        dataSource.setPassword("");
        setBoxDao(new JDBCBoxDao(dataSource.getConnection()));
        dataSource.getConnection().setAutoCommit(false);
    }

    @After
    public void tearDown() throws SQLException {
        dataSource.getConnection().rollback();
    }
}