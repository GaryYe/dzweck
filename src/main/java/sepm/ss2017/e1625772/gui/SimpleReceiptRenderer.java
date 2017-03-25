package sepm.ss2017.e1625772.gui;

import org.springframework.stereotype.Service;
import sepm.ss2017.e1625772.domain.Box;
import sepm.ss2017.e1625772.domain.Receipt;

import java.util.Map;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
@Service
public class SimpleReceiptRenderer implements ReceiptRenderer<String> {
    @Override
    public String render(Receipt receipt) {
        StringBuilder sb = new StringBuilder("");
        sb.append(String.format("%s to %s (%d days)\n", receipt.getBeginTime(), receipt.getEndTime(), receipt
                .getNumberOfDays()));
        for (Map.Entry<Box, Double> entry : receipt.getPricePerBox().entrySet()) {
            sb.append(String.format("Box Id=%d Price=%.2f\n", entry.getKey().getId(), entry.getValue()));
        }
        sb.append(String.format("Total price = %.2f\n", receipt.getTotalPrice()));
        return sb.toString();
    }
}
