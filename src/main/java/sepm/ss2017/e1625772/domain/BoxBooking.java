package sepm.ss2017.e1625772.domain;

/**
 */
public class BoxBooking {
    private Box box;
    private Booking booking;
    private String horseName;
    /**
     * The daily rate of the box that was agreed on, which is usually the daily rate of the box at the time of
     * booking. This daily rate could have also been bargained, and therefore specified (nice to have).
     */
    private Double agreedDailyRate;

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public String getHorseName() {
        return horseName;
    }

    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }

    public Double getAgreedDailyRate() {
        return agreedDailyRate;
    }

    public void setAgreedDailyRate(Double agreedDailyRate) {
        this.agreedDailyRate = agreedDailyRate;
    }

    @Override
    public String toString() {
        return "BoxBooking{" +
                "box=" + box +
                ", booking=" + booking +
                ", horseName='" + horseName + '\'' +
                ", agreedDailyRate=" + agreedDailyRate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoxBooking that = (BoxBooking) o;

        if (box != null ? !box.equals(that.box) : that.box != null) return false;
        if (booking != null ? !booking.equals(that.booking) : that.booking != null) return false;
        if (horseName != null ? !horseName.equals(that.horseName) : that.horseName != null) return false;
        return agreedDailyRate != null ? agreedDailyRate.equals(that.agreedDailyRate) : that.agreedDailyRate == null;
    }

    @Override
    public int hashCode() {
        int result = box != null ? box.hashCode() : 0;
        result = 31 * result + (booking != null ? booking.hashCode() : 0);
        result = 31 * result + (horseName != null ? horseName.hashCode() : 0);
        result = 31 * result + (agreedDailyRate != null ? agreedDailyRate.hashCode() : 0);
        return result;
    }
}
