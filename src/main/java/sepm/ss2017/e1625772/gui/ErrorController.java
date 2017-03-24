package sepm.ss2017.e1625772.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public class ErrorController {
    @FXML
    private Label errorMessage;

    public void setErrorText(String text) {
        errorMessage.setText(text);
    }

    @FXML
    private void close() {
        errorMessage.getScene().getWindow().hide();
    }
}
