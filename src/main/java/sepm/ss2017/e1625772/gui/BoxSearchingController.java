package sepm.ss2017.e1625772.gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sepm.ss2017.e1625772.domain.Box;
import sepm.ss2017.e1625772.exceptions.BusinessLogicException;
import sepm.ss2017.e1625772.service.BoxService;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

/**
 * sepm.ss2017.e1625772.gui.BoxSearchingController
 */
@Component
public class BoxSearchingController extends FXMLController {
    private static final Logger LOG = LoggerFactory.getLogger(BoxSearchingController.class);

    private final BoxService boxService;
    private ObservableList<PropertyBox> boxObservableList;

    @Autowired
    public BoxSearchingController(BoxService boxService) {
        super();
        this.boxService = boxService;
    }


    @FXML
    private Button searchButton;

    @FXML
    private TableColumn<PropertyBox, String> nameColumn;

    @FXML
    private TableView<PropertyBox> boxSearchTable;

    @FXML
    private TextField searchName;

    @FXML
    private Button resetButton;

    @FXML
    private TableColumn<PropertyBox, Long> idColumn;


    @FXML
    public void search(ActionEvent actionEvent) {
        LOG.error("User has requested a search via the search button");
        try {
            List<Box> boxes = boxService.findBoxes(null);
            boxObservableList.clear();
            for (Box box : boxes)
                boxObservableList.addAll(new PropertyBox(box));
            boxSearchTable.refresh();
        } catch (BusinessLogicException e) {
            e.printStackTrace();
        }
    }

    @Value("ui/sample.fxml")
    @Override
    public void setFxmlFilePath(String filePath) {
        this.fxmlFilePath = filePath;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.boxObservableList = FXCollections.observableList(Collections.emptyList());
        this.boxObservableList = FXCollections.observableArrayList(new PropertyBox(3L, "5"));
        this.boxSearchTable.setItems(boxObservableList);
        idColumn.setCellValueFactory( new PropertyValueFactory<PropertyBox, Long>("id"));
        nameColumn.setCellValueFactory( new PropertyValueFactory<PropertyBox, String>("name"));

    }

    public static class PropertyBox {
        private final SimpleLongProperty id;
        private final SimpleStringProperty name;

        public PropertyBox(Box box) {
            this(box.getId(), box.getName());
        }

        public PropertyBox(Long id, String name) {
            this.id = new SimpleLongProperty(id);
            this.name = new SimpleStringProperty(name);
        }

        public long getId() {
            return id.get();
        }

        public SimpleLongProperty idProperty() {
            return id;
        }

        public void setId(long id) {
            this.id.set(id);
        }

        public String getName() {
            return name.get();
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }

        public void setName(String name) {
            this.name.set(name);
        }
    }
}
