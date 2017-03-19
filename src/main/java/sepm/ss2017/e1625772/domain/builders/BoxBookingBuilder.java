package sepm.ss2017.e1625772.domain.builders;

import sepm.ss2017.e1625772.domain.BoxBooking;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public final class BoxBookingBuilder {
    private BoxBooking boxBooking;

    public BoxBookingBuilder(Long bookingId, Long boxId) {
        boxBooking = new BoxBooking();
        boxBooking.setBookingId(bookingId);
        boxBooking.setBoxId(boxId);
    }

    public BoxBookingBuilder bookingId(Long bookingId) {
        boxBooking.setBookingId(bookingId);
        return this;
    }

    public BoxBookingBuilder boxId(Long boxId) {
        boxBooking.setBoxId(boxId);
        return this;
    }

    public BoxBookingBuilder horseName(String horseName) {
        boxBooking.setHorseName(horseName);
        return this;
    }

    public BoxBookingBuilder agreedDailyRate(Double agreedDailyRate) {
        boxBooking.setAgreedDailyRate(agreedDailyRate);
        return this;
    }

    public BoxBooking create() {
        return this.boxBooking;
    }
}
