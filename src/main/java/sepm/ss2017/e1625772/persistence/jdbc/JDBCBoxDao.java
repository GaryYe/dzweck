package sepm.ss2017.e1625772.persistence.jdbc;

import org.apache.log4j.Logger;
import sepm.ss2017.e1625772.domain.Box;
import sepm.ss2017.e1625772.persistence.BoxDao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Formatter;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public class JDBCBoxDao implements BoxDao {
    private static final Logger LOG = Logger.getLogger(JDBCBoxDao.class);
    private final Connection connection;

    public JDBCBoxDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Box findOne(Long id) {
        ResultSet rs = null;
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM BOXES WHERE ID = ?")) {
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Box box = new Box();
                box.setId((long) rs.getInt("ID"));
                box.setArea(rs.getDouble("AREA"));
                box.setName(rs.getString("NAME"));
                box.setDailyRate(rs.getBigDecimal("DAILY_RATE").doubleValue());
                box.setHasWindows(rs.getBoolean("WINDOWS"));
                box.setIndoor(rs.getBoolean("INDOOR"));
                // TODO image
                return box;
            } else {
                return null;
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while finding all boxes.", e);
        }
        return null;
    }

    @Override
    public Collection<Box> findAll() {
        Collection<Box> result = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM BOXES")) {
            while (rs.next()) {
                Box box = new Box();
                box.setId((long) rs.getInt("ID"));
                box.setArea(rs.getDouble("AREA"));
                box.setName(rs.getString("NAME"));
                box.setDailyRate(rs.getBigDecimal("DAILY_RATE").doubleValue());
                box.setHasWindows(rs.getBoolean("WINDOWS"));
                box.setIndoor(rs.getBoolean("INDOOR"));
                // TODO: Insert image in next version
                result.add(box);
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while finding all boxes.", e);
        }
        return result;
    }

    @Override
    public Collection<Box> findAll(Box box) {
        return null;
    }

    @Override
    public void create(Box box) {
        String insertSql = "INSERT INTO BOXES (ID, INDOOR, WINDOWS, DAILY_RATE, AREA, NAME) VALUES (?,?,?,?,?,?) ";
        try (PreparedStatement stmt = connection.prepareStatement(insertSql)) {
            stmt.setLong(1, box.getId());
            stmt.setBoolean(2, box.isIndoor());
            stmt.setBoolean(3, box.hasWindows());
            stmt.setBigDecimal(4, BigDecimal.valueOf(box.getDailyRate()));
            stmt.setBigDecimal(5, BigDecimal.valueOf(box.getArea()));
            stmt.setString(6, box.getName());
            // TODO: Update with Image byte array
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while creating the box", e);
        }
    }

    @Override
    public void delete(Box box) {
        String deleteSql = "DELETE FROM BOXES WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(deleteSql)) {
            stmt.setLong(1, box.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while deleting the box", e);
        }

    }

    @Override
    public void update(Box box) {
        String updateSql = "UPDATE BOXES SET INDOOR=?, WINDOWS=?, DAILY_RATE=?, AREA=?, NAME=? WHERE BOXES.ID=?";
        try (PreparedStatement stmt = connection.prepareStatement(updateSql)) {
            stmt.setBoolean(1, box.isIndoor());
            stmt.setBoolean(2, box.hasWindows());
            stmt.setBigDecimal(3, BigDecimal.valueOf(box.getDailyRate()));
            stmt.setBigDecimal(4, BigDecimal.valueOf(box.getArea()));
            stmt.setString(5, box.getName());
            stmt.setLong(6, box.getId());
            stmt.execute();
            // TODO: image
        } catch (SQLException e) {
            LOG.error("Error while updating box", e);
        }
    }
}
