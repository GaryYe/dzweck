package sepm.ss2017.e1625772.persistence.impl;

import org.junit.After;
import org.junit.Before;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.sql.SQLException;

/**
 * Testing with the test_db
 */
public class JDBCBoxDAOTest extends AbstractBoxDaoTest {
    private EmbeddedDatabase db;

    @Before
    public void setUp() throws SQLException {
        db = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2) //.H2 or .DERBY
                .addScript("db/create-db.sql")
                .build();
        setBoxDAO(new JDBCBoxDAO(db));
    }

    @After
    public void tearDown() throws SQLException {
        db.shutdown();
    }
}