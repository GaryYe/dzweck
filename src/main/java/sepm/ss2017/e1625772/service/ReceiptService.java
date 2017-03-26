package sepm.ss2017.e1625772.service;

import sepm.ss2017.e1625772.domain.Receipt;
import sepm.ss2017.e1625772.exceptions.ObjectNotFoundException;
import sepm.ss2017.e1625772.exceptions.ServiceException;

/**
 * Implementations of this service should provide the necessary efforts to calculate receipts for certain bookings.
 *
 * @author Gary Ye
 * @version %I% %G%
 */
public interface ReceiptService {
    /**
     * Calculates the receipt of the given booking. The receipt should contain data regarding the bookings and prices.
     *
     * @param bookingId the id of the booking
     * @return the receipt of the booking
     * @throws ObjectNotFoundException if the booking was not found
     * @throws ServiceException        if an error occurred during the service procedure
     */
    Receipt calculateReceipt(Long bookingId) throws ObjectNotFoundException;
}
