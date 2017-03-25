package sepm.ss2017.e1625772.domain;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public class BoxStatistics {
    private Map<Box, int[]> data;

    public BoxStatistics(Map<Box, int[]> data) {
        this.data = data;
    }

    public List<Box> boxes() {
        return new ArrayList<>(data.keySet());
    }

    public int numberOfReservations(Box box, DayOfWeek dayOfWeek) {
        return data.get(box)[dayOfWeek.getValue() - 1];
    }

    public void setData(Map<Box, int[]> data) {
        this.data = data;
    }
}
