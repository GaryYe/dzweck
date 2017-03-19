package sepm.ss2017.e1625772.persistence.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import sepm.ss2017.e1625772.domain.BoxBooking;
import sepm.ss2017.e1625772.exceptions.DataAccessException;
import sepm.ss2017.e1625772.persistence.BoxBookingDAO;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
@Repository
public class JDBCBoxBookingDAO implements BoxBookingDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JDBCBoxBookingDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public List<BoxBooking> findAll() throws DataAccessException {
        try {
            return jdbcTemplate.query("SELECT * FROM BOXBOOKINGS ", new BoxBookingRowMapper());
        } catch (org.springframework.dao.DataAccessException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public List<BoxBooking> findAllByBooking(Long bookingId) throws DataAccessException {
        if (bookingId == null)
            throw new IllegalArgumentException("Booking ID can not be null");
        try {
            return jdbcTemplate.query("SELECT * FROM BOXBOOKINGS WHERE BOOKING_ID = ?", new Object[]{bookingId},
                    new BoxBookingRowMapper());
        } catch (org.springframework.dao.DataAccessException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public List<BoxBooking> findAllByBox(Long boxId) throws DataAccessException {
        if (boxId == null)
            throw new IllegalArgumentException("Box ID can not be null");
        try {
            return jdbcTemplate.query("SELECT * FROM BOXBOOKINGS WHERE BOX_ID = ?", new Object[]{boxId},
                    new BoxBookingRowMapper());
        } catch (org.springframework.dao.DataAccessException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void create(BoxBooking boxBooking) throws DataAccessException {
        if (boxBooking == null)
            throw new IllegalArgumentException("BoxBooking can not be null");
        try {
            jdbcTemplate.update("INSERT INTO BOXBOOKINGS (BOOKING_ID, BOX_ID, HORSE_NAME, DAILY_RATE) VALUES (?,?,?,?)",
                    boxBooking.getBookingId(), boxBooking.getBoxId(), boxBooking.getHorseName(), boxBooking.getAgreedDailyRate());
        } catch (org.springframework.dao.DataAccessException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void delete(BoxBooking boxBooking) throws DataAccessException {
        if (boxBooking == null)
            throw new IllegalArgumentException("BoxBooking can not be null");
        if (boxBooking.getBookingId() == null || boxBooking.getBoxId() == null)
            throw new IllegalArgumentException("Primary keys of BoxBooking can not be null as they are needed for " +
                    "this operation");
        try {
            jdbcTemplate.update("DELETE FROM BOXBOOKINGS WHERE BOOKING_ID = ? AND BOX_ID = ?",
                    boxBooking.getBookingId(), boxBooking.getBoxId());
        } catch (org.springframework.dao.DataAccessException e) {
            throw new DataAccessException(e);
        }
    }
}


class BoxBookingRowMapper implements RowMapper<BoxBooking> {

    @Override
    public BoxBooking mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        BoxBooking boxBooking = new BoxBooking();
        boxBooking.setBookingId(resultSet.getLong("BOOKING_ID"));
        boxBooking.setBoxId(resultSet.getLong("BOX_ID"));
        // getDouble actually returns 0.0 if the object was null
        boxBooking.setAgreedDailyRate(resultSet.getObject("DAILY_RATE", Double.class));
        boxBooking.setHorseName(resultSet.getString("HORSE_NAME"));
        return boxBooking;
    }
}
