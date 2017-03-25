package sepm.ss2017.e1625772.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
@Controller
public class RootController extends FXMLController {
    private final BookingController bookingController;
    private final BoxController boxController;
    @FXML
    private Tab bookingTab;
    @FXML
    private Tab boxesTab;

    @Autowired
    public RootController(BookingController bookingController, BoxController boxController) {
        this.bookingController = bookingController;
        this.boxController = boxController;
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
    }
}
