package sepm.ss2017.e1625772.service;

import sepm.ss2017.e1625772.domain.Booking;
import sepm.ss2017.e1625772.exceptions.BoxBookingCollisionException;

import java.time.LocalDate;
import java.util.List;

/**
 * The booking service is responsible for handling data requests regarding the bookings.
 *
 * @author Gary Ye
 * @version %I% %G%
 */
public interface BookingService {

    /**
     * Finds all bookings that exist in the system.
     *
     * @return all bookings in a list
     */
    List<Booking> findAll();

    /**
     * All intersecting bookings will be found and returned. Two time intervals intersect if they have at least one
     * common day.
     *
     * @param begin the begin date
     * @param end   the end date
     * @return all bookings that have an time span intersecting with the given one.
     */
    List<Booking> findAllIntersecting(LocalDate begin, LocalDate end);

    /**
     * Creates a new booking.
     *
     * @param booking the booking to create
     */
    void create(Booking booking);

    /**
     * Deletes an existing booking.
     *
     * @param booking the booking to delete
     */
    void delete(Booking booking);

    /**
     * Updates the booking with the given id with the new attributes that are also given.
     *
     * @param booking the booking should contain the id that we should update and the new attributes. Literally all
     *                attributes will be taken, even null values.
     * @throws BoxBookingCollisionException if a box booking collision exists after updating the booking.
     */
    void update(Booking booking) throws BoxBookingCollisionException;

    /**
     * Finds a booking with the given booking id.
     *
     * @param bookingId the id of the booking
     * @return the corresponding booking
     */
    Booking findOne(Long bookingId);
}
