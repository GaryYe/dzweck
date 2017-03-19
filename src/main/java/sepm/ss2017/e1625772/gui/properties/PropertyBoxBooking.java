package sepm.ss2017.e1625772.gui.properties;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import sepm.ss2017.e1625772.domain.BoxBooking;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public class PropertyBoxBooking {
    private SimpleLongProperty boxId;
    private SimpleStringProperty horseName;
    private SimpleDoubleProperty agreedDailyRate;

    public PropertyBoxBooking(BoxBooking boxBooking) {
        this(boxBooking.getBoxId(), boxBooking.getHorseName(), boxBooking.getAgreedDailyRate());
    }

    public PropertyBoxBooking(Long boxId, String horseName, Double agreedDailyRate) {
        this.boxId = new SimpleLongProperty(boxId);
        this.horseName = new SimpleStringProperty(horseName);
        this.agreedDailyRate = new SimpleDoubleProperty(agreedDailyRate);
    }

    public long getBoxId() {
        return boxId.get();
    }

    public SimpleLongProperty boxIdProperty() {
        return boxId;
    }

    public void setBoxId(long boxId) {
        this.boxId.set(boxId);
    }

    public String getHorseName() {
        return horseName.get();
    }

    public SimpleStringProperty horseNameProperty() {
        return horseName;
    }

    public void setHorseName(String horseName) {
        this.horseName.set(horseName);
    }

    public double getAgreedDailyRate() {
        return agreedDailyRate.get();
    }

    public SimpleDoubleProperty agreedDailyRateProperty() {
        return agreedDailyRate;
    }

    public void setAgreedDailyRate(double agreedDailyRate) {
        this.agreedDailyRate.set(agreedDailyRate);
    }

    public String toString() {
        return "Box: " + boxId.getValue() + " Horse: " + horseName.getValue() + " Daily Rate: " + agreedDailyRate
                .getValue();
    }
}
