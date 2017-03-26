package sepm.ss2017.e1625772.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
@Controller
public class RootController extends FXMLController {
    private static final Logger LOG = getLogger(RootController.class);

    private final BookingController bookingController;
    private final BoxController boxController;
    private final StatisticsController statisticsController;
    @FXML
    private Tab bookingTab;
    @FXML
    private Tab boxesTab;
    @FXML
    private Tab statsTab;

    @Autowired
    public RootController(BookingController bookingController, BoxController boxController, StatisticsController
            statisticsController) {
        this.bookingController = bookingController;
        this.boxController = boxController;
        this.statisticsController = statisticsController;
    }


    @Value("ui/root.fxml")
    @Override
    public void setFxmlFilePath(String filePath) {
        this.fxmlFilePath = filePath;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bookingTab.setContent(bookingController.getView());
        boxesTab.setContent(boxController.getView());
        statsTab.setContent(statisticsController.getView());
        LOG.info("Initialized root controller");
    }
}
