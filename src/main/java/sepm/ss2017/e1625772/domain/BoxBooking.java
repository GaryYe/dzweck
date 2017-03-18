package sepm.ss2017.e1625772.domain;

/**
 */
public class BoxBooking {
    private Box box;
    private Booking booking;
    private String horseName;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoxBooking that = (BoxBooking) o;

        if (box != null ? !box.equals(that.box) : that.box != null) return false;
        if (booking != null ? !booking.equals(that.booking) : that.booking != null) return false;
        return horseName != null ? horseName.equals(that.horseName) : that.horseName == null;
    }

    @Override
    public int hashCode() {
        int result = box != null ? box.hashCode() : 0;
        result = 31 * result + (booking != null ? booking.hashCode() : 0);
        result = 31 * result + (horseName != null ? horseName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BoxBooking{" +
                "box=" + box +
                ", booking=" + booking +
                ", horseName='" + horseName + '\'' +
                '}';
    }
}
