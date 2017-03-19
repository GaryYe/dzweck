package sepm.ss2017.e1625772.gui.properties;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import sepm.ss2017.e1625772.domain.Booking;

import java.time.LocalDate;

/**
 * @author Gary Ye
 * @version %I% %G%
 */


public class PropertyBooking {

    private final SimpleLongProperty id;
    private final SimpleStringProperty name;
    private final SimpleStringProperty beginTime;
    private final SimpleStringProperty endTime;

    public PropertyBooking(Booking booking) {
        this(booking.getId(), booking.getCustomerName(), booking.getBeginTime(), booking.getEndTime());
    }

    private static String dateToString(LocalDate date) {
        return date == null ? "Unknown" : date.toString();
    }

    public PropertyBooking(Long id, String name, LocalDate beginTime, LocalDate endTime) {
        this.id = new SimpleLongProperty(id);
        this.name = new SimpleStringProperty(name);
        this.beginTime = new SimpleStringProperty(dateToString(beginTime));
        this.endTime = new SimpleStringProperty(dateToString(endTime));
    }

    public long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getBeginTime() {
        return beginTime.get();
    }

    public SimpleStringProperty beginTimeProperty() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime.set(beginTime);
    }

    public String getEndTime() {
        return endTime.get();
    }

    public SimpleStringProperty endTimeProperty() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime.set(endTime);
    }
}

