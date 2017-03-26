package sepm.ss2017.e1625772.gui.utils;

import sepm.ss2017.e1625772.domain.Receipt;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public interface ReceiptRenderer<OutputFormat> {
    OutputFormat render(Receipt receipt);
}
