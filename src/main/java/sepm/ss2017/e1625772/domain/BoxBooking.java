package sepm.ss2017.e1625772.domain;

/**
 * An aggregate root for the relationship between boxes and bookings.
 */
public class BoxBooking {
    /**
     * The id of the box
     */
    private Long boxId;
    /**
     * The id of the booking
     */
    private Long bookingId;

    /**
     * The name of the horse
     */
    private String horseName;
    /**
     * The daily rate of the box that was agreed on, which is usually the daily rate of the box at the time of
     * booking. This daily rate could have also been bargained, and therefore specified (nice to have).
     */
    private Double agreedDailyRate;

    public BoxBooking(Long boxId, Long bookingId, String horseName, Double agreedDailyRate) {
        this.boxId = boxId;
        this.bookingId = bookingId;
        this.horseName = horseName;
        this.agreedDailyRate = agreedDailyRate;
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

    public Long getBoxId() {
        return boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BoxBooking that = (BoxBooking) o;

        if (boxId != null ? !boxId.equals(that.boxId) : that.boxId != null) return false;
        if (bookingId != null ? !bookingId.equals(that.bookingId) : that.bookingId != null) return false;
        if (horseName != null ? !horseName.equals(that.horseName) : that.horseName != null) return false;
        return agreedDailyRate != null ? agreedDailyRate.equals(that.agreedDailyRate) : that.agreedDailyRate == null;
    }

    @Override
    public int hashCode() {
        int result = boxId != null ? boxId.hashCode() : 0;
        result = 31 * result + (bookingId != null ? bookingId.hashCode() : 0);
        result = 31 * result + (horseName != null ? horseName.hashCode() : 0);
        result = 31 * result + (agreedDailyRate != null ? agreedDailyRate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BoxBooking{" +
                "boxId=" + boxId +
                ", bookingId=" + bookingId +
                ", horseName='" + horseName + '\'' +
                ", agreedDailyRate=" + agreedDailyRate +
                '}';
    }
}
