package sepm.ss2017.e1625772.gui;

import org.junit.Before;
import org.junit.Test;
import sepm.ss2017.e1625772.domain.Box;
import sepm.ss2017.e1625772.domain.Receipt;
import sepm.ss2017.e1625772.domain.builders.BoxBuilder;
import sepm.ss2017.e1625772.gui.utils.ReceiptRenderer;
import sepm.ss2017.e1625772.gui.utils.impl.SimpleReceiptRenderer;

import java.util.HashMap;
import java.util.Map;

import static java.time.LocalDate.of;
import static junit.framework.TestCase.assertEquals;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public class SimpleReceiptRendererTest {
    private ReceiptRenderer<String> receiptRenderer;

    @Before
    public void setUp() {
        receiptRenderer = new SimpleReceiptRenderer();
    }

    @Test
    public void renderCorrect() throws Exception {
        Receipt receipt = new Receipt();
        receipt.setNumberOfDays(3);
        receipt.setBeginTime(of(2017, 3, 12));
        receipt.setEndTime(of(2017, 3, 13));
        Map<Box, Double> pricePerBox = new HashMap<>();
        pricePerBox.put(new BoxBuilder(2L).create(), 5.0);
        pricePerBox.put(new BoxBuilder(3L).create(), 10.0);
        receipt.setPricePerBox(pricePerBox);
        Double totalPrice = 3 * (5.0 + 10.0);
        receipt.setTotalPrice(totalPrice);
        assertEquals("2017-03-12 to 2017-03-13 (3 days)\n" +
                "Box Id=3 Price=10.00\n" +
                "Box Id=2 Price=5.00\n" +
                "Total price = 45.00\n", receiptRenderer.render(receipt));
        // System.out.println(receiptRenderer.render(receipt));
    }
}