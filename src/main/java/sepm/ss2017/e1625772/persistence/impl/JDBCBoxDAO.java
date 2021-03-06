package sepm.ss2017.e1625772.persistence.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sepm.ss2017.e1625772.domain.Box;
import sepm.ss2017.e1625772.exceptions.DataAccessException;
import sepm.ss2017.e1625772.persistence.BoxDAO;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
@Repository
public class JDBCBoxDAO implements BoxDAO {
    private static final Logger LOG = LoggerFactory.getLogger(JDBCBoxDAO.class);
    private final DataSource dataSource;

    @Autowired
    public JDBCBoxDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Box findOne(Long id) throws DataAccessException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM BOXES WHERE ID = ?")) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Box box = new Box();
                box.setId((long) rs.getInt("ID"));
                box.setArea(rs.getDouble("AREA"));
                box.setName(rs.getString("NAME"));
                box.setDailyRate(rs.getBigDecimal("DAILY_RATE").doubleValue());
                box.setHasWindows(rs.getBoolean("WINDOWS"));
                box.setIndoor(rs.getBoolean("INDOOR"));
                return box;
            } else {
                return null;
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while finding the box with id={}", id, e);
            throw new DataAccessException(e);
        }
    }

    @Override
    public Collection<Box> findAll() throws DataAccessException {
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM BOXES")) {
            return parseBoxes(rs);
        } catch (SQLException e) {
            LOG.error("Error occurred while finding all boxes.", e);
            throw new DataAccessException(e);
        }
    }

    private Collection<Box> parseBoxes(ResultSet rs) throws SQLException {
        Collection<Box> result = new ArrayList<>();
        while (rs.next()) {
            Box box = new Box();
            box.setId((long) rs.getInt("ID"));
            box.setArea(rs.getDouble("AREA"));
            box.setName(rs.getString("NAME"));
            box.setDailyRate(rs.getBigDecimal("DAILY_RATE").doubleValue());
            box.setHasWindows(rs.getBoolean("WINDOWS"));
            box.setIndoor(rs.getBoolean("INDOOR"));
            result.add(box);
        }
        return result;
    }

    @Override
    public Collection<Box> findAll(Box box) throws DataAccessException {
        String querySql = "SELECT * FROM BOXES WHERE NAME LIKE ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(querySql);) {
            stmt.setString(1, "%" + box.getName() + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                return parseBoxes(rs);
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while finding all boxes.", e);
            throw new DataAccessException(e);
        }
    }

    @Override
    public void create(Box box) throws DataAccessException {
        if (box == null)
            throw new IllegalArgumentException();

        String insertSql = "INSERT INTO BOXES (ID, INDOOR, WINDOWS, DAILY_RATE, AREA, NAME) VALUES (?,?,?,?,?,?) ";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(insertSql)) {
            stmt.setLong(1, box.getId());
            stmt.setBoolean(2, box.isIndoor());
            stmt.setBoolean(3, box.hasWindows());
            stmt.setBigDecimal(4, BigDecimal.valueOf(box.getDailyRate()));
            stmt.setBigDecimal(5, BigDecimal.valueOf(box.getArea()));
            stmt.setString(6, box.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while creating the box={}", box.toString(), e);
            throw new DataAccessException(e);
        }
    }

    @Override
    public void delete(Box box) throws DataAccessException {
        if (box == null)
            throw new IllegalArgumentException();
        String deleteSql = "DELETE FROM BOXES WHERE ID = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(deleteSql)) {
            stmt.setLong(1, box.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error while deleting box {}", box.toString(), e);
            throw new DataAccessException(e);
        }

    }

    @Override
    public void update(Box box) throws DataAccessException {
        String updateSql = "UPDATE BOXES SET INDOOR=?, WINDOWS=?, DAILY_RATE=?, AREA=?, NAME=? WHERE BOXES.ID=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(updateSql)) {
            stmt.setBoolean(1, box.isIndoor());
            stmt.setBoolean(2, box.hasWindows());
            stmt.setBigDecimal(3, BigDecimal.valueOf(box.getDailyRate()));
            stmt.setBigDecimal(4, BigDecimal.valueOf(box.getArea()));
            stmt.setString(5, box.getName());
            stmt.setLong(6, box.getId());
            stmt.execute();
        } catch (SQLException e) {
            LOG.error("Error while updating box=", box.toString(), e);
            throw new DataAccessException(e);
        }
    }
}
