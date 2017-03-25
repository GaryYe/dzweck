package sepm.ss2017.e1625772.persistence.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import sepm.ss2017.e1625772.domain.Booking;
import sepm.ss2017.e1625772.exceptions.DataAccessException;
import sepm.ss2017.e1625772.persistence.BookingDAO;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

/**
 * A booking data access object implementation with JDBC retrieval methods.
 *
 * @author Gary Ye
 * @version %I% %G%
 */
@Repository
public class JDBCBookingDAO implements BookingDAO {
    private static final Logger LOG = LoggerFactory.getLogger(JDBCBookingDAO.class);
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JDBCBookingDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Collection<Booking> findAll() {
        try {
            return jdbcTemplate.query("SELECT ID, BEGIN_TIME, END_TIME, CUSTOMER_NAME FROM BOOKINGS", new
                    BookingMapper());
        } catch (org.springframework.dao.DataAccessException e) {
            LOG.error("Data access exception while query findingAll", e);
            throw new DataAccessException(e);
        }
    }

    @Override
    public Collection<Booking> findAllIntersecting(LocalDate begin, LocalDate end) {
        try {
            return jdbcTemplate.query("SELECT ID, BEGIN_TIME, END_TIME, CUSTOMER_NAME FROM BOOKINGS " +
                            "WHERE ? <= END_TIME AND BEGIN_TIME <= ?",
                    new Object[]{Date.valueOf(begin), Date.valueOf(end)}, new BookingMapper());
        } catch (org.springframework.dao.DataAccessException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void create(Booking booking) {
        if (booking == null)
            throw new IllegalArgumentException("Booking can not be null");
        try {
            jdbcTemplate.update("INSERT INTO BOOKINGS (ID, BEGIN_TIME, END_TIME, CUSTOMER_NAME) VALUES (?, ?, ?, ?)",
                    booking.getId(),
                    booking.getBeginTime() == null ? null : Date.valueOf(booking.getBeginTime()),
                    booking.getEndTime() == null ? null : Date.valueOf(booking.getEndTime()),
                    booking.getCustomerName());
        } catch (org.springframework.dao.DataAccessException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void delete(Booking booking) {
        if (booking == null)
            throw new IllegalArgumentException("Booking can not be null");
        try {
            jdbcTemplate.update("DELETE FROM BOOKINGS WHERE ID = ?", booking.getId());
        } catch (org.springframework.dao.DataAccessException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void update(Booking booking) {
        if (booking == null)
            throw new IllegalArgumentException("Booking can not be null");
        try {
            jdbcTemplate.update("UPDATE BOOKINGS SET BEGIN_TIME=?, END_TIME=?, CUSTOMER_NAME=? WHERE ID = ?",
                    booking.getBeginTime(), booking.getEndTime(), booking.getCustomerName(), booking.getId());
        } catch (org.springframework.dao.DataAccessException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public Booking findOne(Long id) throws DataAccessException {
        if (id == null)
            throw new IllegalArgumentException("Id can not be null");

        try {
            return jdbcTemplate.queryForObject("SELECT ID, BEGIN_TIME, END_TIME, CUSTOMER_NAME FROM BOOKINGS" +
                    " WHERE ID = ?", new Object[]{id}, new BookingMapper());
        } catch (org.springframework.dao.DataAccessException e) {
            throw new DataAccessException(e);
        }
    }

    private static final class BookingMapper implements RowMapper<Booking> {
        private LocalDate toLocalDate(Date date) {
            return date == null ? null : date.toLocalDate();
        }

        @Override
        public Booking mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Booking booking = new Booking();
            booking.setId(resultSet.getLong("ID"));
            booking.setBeginTime(toLocalDate(resultSet.getDate("BEGIN_TIME")));
            booking.setEndTime(toLocalDate(resultSet.getDate("END_TIME")));
            booking.setCustomerName(resultSet.getString("CUSTOMER_NAME"));
            return booking;
        }
    }
}
