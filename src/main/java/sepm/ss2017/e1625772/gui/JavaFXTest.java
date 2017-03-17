package sepm.ss2017.e1625772.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
@Component
public class JavaFXTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml"));

        URL url = getClass().getClassLoader().getResource("ui/sample.fxml");
        if (url == null)
            return;
        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root, 300, 275);

        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
    }
}
