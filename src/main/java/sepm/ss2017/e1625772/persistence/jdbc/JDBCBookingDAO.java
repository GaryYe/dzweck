package sepm.ss2017.e1625772.persistence.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import sepm.ss2017.e1625772.domain.Booking;
import sepm.ss2017.e1625772.persistence.BookingDAO;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
@Repository
public class JDBCBookingDAO implements BookingDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JDBCBookingDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Collection<Booking> findAll() {
        return this.jdbcTemplate.query("select * from BOOKING", new BookingMapper());
    }

    @Override
    public Collection<Booking> findAllBetween(LocalDateTime begin, LocalDateTime end) {
        return null;
    }

    @Override
    public Booking create(Booking booking) {
        return null;
    }

    private static final class BookingMapper implements RowMapper<Booking> {

        @Override
        public Booking mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return null;
        }
    }
}
