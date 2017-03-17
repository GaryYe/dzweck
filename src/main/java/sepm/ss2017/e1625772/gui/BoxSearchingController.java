package sepm.ss2017.e1625772.gui;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import sepm.ss2017.e1625772.domain.Box;
import sepm.ss2017.e1625772.exceptions.BusinessLogicException;
import sepm.ss2017.e1625772.service.BoxService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Gary Ye
 * @version %I% %G%
 */

@Controller
public class BoxSearchingController extends FXMLController {
    private final BoxService boxService;

    private ObservableList<Box> boxObservableList;

    @FXML
    private TableView<Box> boxSearchTable;

    @Autowired
    public BoxSearchingController(BoxService boxService, @Value("custom_control.fxml") String fxmlFile) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("custom_control.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.boxService = boxService;
        boxObservableList = FXCollections.emptyObservableList();
        boxSearchTable = new TableView<Box>(boxObservableList);
    }

    @FXML
    public void search(ActionEvent actionEvent) {
        try {
            List<Box> boxes = boxService.findBoxes(null);
            boxObservableList.clear();
            // maybe progress bar here?
            boxObservableList.addAll(boxes);
        } catch (BusinessLogicException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setFxmlFilePath(String filePath) {
        this.fxmlFilePath = filePath;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
