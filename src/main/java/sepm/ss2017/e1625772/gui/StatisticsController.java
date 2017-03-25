package sepm.ss2017.e1625772.gui;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
@Controller
public class StatisticsController extends FXMLController {
    @Value("ui/stats.fxml")
    @Override
    public void setFxmlFilePath(String filePath) {
        this.fxmlFilePath = filePath;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
