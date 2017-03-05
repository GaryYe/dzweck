package sepm.ss2017.e1625772.persistence.jdbc;

import sepm.ss2017.e1625772.domain.Box;
import sepm.ss2017.e1625772.persistence.BoxDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by gary on 05.03.17.
 */
public class JDBCBoxDao implements BoxDao {
    private final Connection connection;

    public JDBCBoxDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Collection<Box> findAll() {
        Collection<Box> result = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM BOXES");
            while (rs.next()) {
                Box box = new Box();
                box.setId((long) rs.getInt("ID"));
                box.setArea(rs.getDouble("AREA"));
                box.setName(rs.getString("NAME"));
                // todo: image
                box.setDailyRate(rs.getBigDecimal("DAILY_RATE").doubleValue());
                box.setHasWindows(rs.getBoolean("WINDOWS"));
                box.setIndoor(rs.getBoolean("INDOOR"));
                result.add(box);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Collection<Box> findAll(Box box) {
        return null;
    }

    @Override
    public void delete(Box box) {

    }

    @Override
    public void update(Box box) {

    }
}
