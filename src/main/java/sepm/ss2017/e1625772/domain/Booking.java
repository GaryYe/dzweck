package sepm.ss2017.e1625772.domain;

import java.time.LocalDate;

/**
 */
public class Booking {
    private Long id;
    private String customerName;
    private LocalDate beginTime;
    private LocalDate endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDate beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Booking booking = (Booking) o;

        if (id != null ? !id.equals(booking.id) : booking.id != null) return false;
        if (customerName != null ? !customerName.equals(booking.customerName) : booking.customerName != null)
            return false;
        if (beginTime != null ? !beginTime.equals(booking.beginTime) : booking.beginTime != null) return false;
        return endTime != null ? endTime.equals(booking.endTime) : booking.endTime == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (customerName != null ? customerName.hashCode() : 0);
        result = 31 * result + (beginTime != null ? beginTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                '}';
    }

    public static class BookingBuilder {
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
}
