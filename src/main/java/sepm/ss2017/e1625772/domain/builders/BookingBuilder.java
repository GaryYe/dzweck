package sepm.ss2017.e1625772.domain.builders;

import sepm.ss2017.e1625772.domain.Booking;

import java.time.LocalDate;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public class BookingBuilder {
    private Booking built;

    public BookingBuilder(Long id) {
        this.built = new Booking();
        built.setId(id);
    }

    public BookingBuilder id(Long id) {
        this.built.setId(id);
        return this;
    }

    public BookingBuilder beginTime(LocalDate begin) {
        this.built.setBeginTime(begin);
        return this;
    }

    public BookingBuilder endTime(LocalDate end) {
        this.built.setEndTime(end);
        return this;
    }

    public BookingBuilder customer(String name) {
        this.built.setCustomerName(name);
        return this;
    }

    public Booking create() {
        return this.built;
    }
}
