package sepm.ss2017.e1625772.gui;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
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
    private Label nameLabel;

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
    private Button saveButton;
    @FXML
    private Button createNewButton;

    @FXML
    private TableColumn<PropertyBox, Long> idColumn;

    // DETAIL VIEW BEGIN
    @FXML
    private TextField boxNameTextBox;
    @FXML
    private TextField boxIdTextBox;
    @FXML
    private Button selectImageButton;

    @FXML
    private ImageView imageView;

    @FXML
    private CheckBox hasWindowsCheckbox;

    @FXML
    private CheckBox indoorCheckbox;

    @FXML
    private TextField areaTextBox;

    @FXML
    private TextField dailyRateTextBox;

    @FXML
    private Label currentStateLabel;

    private final String EDITING_STATE = "Viewing";
    private final String CREATING_STATE = "Creating";

    // DETAIL VIEW END

    // http://stackoverflow.com/questions/26424769/javafx8-how-to-create-listener-for-selection-of-row-in-tableview

    private void setCreateNewState() {
        boxIdTextBox.setText("");
        boxIdTextBox.setEditable(true);

        boxNameTextBox.setText("");
        areaTextBox.setText("");
        dailyRateTextBox.setText("");
        hasWindowsCheckbox.setSelected(false);
        indoorCheckbox.setSelected(false);
        currentStateLabel.setText(CREATING_STATE);
    }

    @FXML
    public void search(ActionEvent actionEvent) {
        LOG.error("User has requested a search via the search button");
        try {
            // Maybe in method
            List<Box> boxes = boxService.findBoxes(null);
            boxObservableList.clear();
            for (Box box : boxes)
                boxObservableList.addAll(new PropertyBox(box));
            boxSearchTable.refresh();
        } catch (BusinessLogicException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void createNew(ActionEvent actionEvent) {
        LOG.error("User has requested creating a new Box");
        setCreateNewState();
    }

    @FXML
    public void save(ActionEvent actionEvent) {
        LOG.error("User clicked the save button");

        Box box = new Box();
        try {
            box.setId(Long.valueOf(boxIdTextBox.getText()));
            box.setName(boxNameTextBox.getText());
            box.setArea(Double.valueOf(areaTextBox.getText()));
            box.setDailyRate(Double.valueOf(dailyRateTextBox.getText()));
            box.setHasWindows(hasWindowsCheckbox.isSelected());
            box.setIndoor(indoorCheckbox.isSelected());
        } catch (Exception e) {
            // Something something
            LOG.error("Something went wrong parsing the box", e);
            return;
        }

        if (currentStateLabel.getText().equals(CREATING_STATE)) {
            try {
                boxService.createBox(box);
                LOG.info("Box {} successfully created", box);
            } catch (BusinessLogicException e) {
                LOG.error("Something went wrong when creating the box ", e);
            }
        } else {
            try {
                boxService.updateBox(box);
                LOG.info("Box {} successfully updated", box);
            } catch (BusinessLogicException e) {
                LOG.error("Something went wrong when updating the box ", e);
            }
        }

        setStateViewingBox(box.getId());
    }

    /**
     * Sets the form state to a viewing state of the given box.
     *
     * @param id the id of the box (maybe take Box instead?)
     */
    private void setStateViewingBox(Long id) {
        if (id == null)
            throw new IllegalArgumentException();
        LOG.info("Loading view of box = {}", id);
        try {
            Box box = boxService.findBox(id);
            boxIdTextBox.setText(String.valueOf(id));
            boxIdTextBox.setEditable(false);

            boxNameTextBox.setText(box.getName());
            areaTextBox.setText(String.format("%.2f", box.getArea()));
            dailyRateTextBox.setText(String.format("%.2f", box.getDailyRate()));
            hasWindowsCheckbox.setSelected(box.hasWindows());
            indoorCheckbox.setSelected(box.isIndoor());
            currentStateLabel.setText(EDITING_STATE);
        } catch (BusinessLogicException e) {
            LOG.error("Something went wrong with retrieving the box id={}", id, e);
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
        this.boxObservableList = FXCollections.observableArrayList();
        this.boxSearchTable.setItems(boxObservableList);
        idColumn.setCellValueFactory(new PropertyValueFactory<PropertyBox, Long>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<PropertyBox, String>("name"));
        boxSearchTable.getSelectionModel()
                .selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                LOG.info("Selected id = {}", newSelection.getId());
                setStateViewingBox(newSelection.getId());
            }
        });
        setCreateNewState();
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
