package sepm.ss2017.e1625772.service;

import sepm.ss2017.e1625772.domain.Receipt;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public interface ReceiptService {
    Receipt calculateReceipt(Long bookingId);
}
