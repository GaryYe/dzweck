package sepm.ss2017.e1625772.gui;

import sepm.ss2017.e1625772.domain.Receipt;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public interface ReceiptRenderer<OutputFormat> {
    OutputFormat render(Receipt receipt);
}
