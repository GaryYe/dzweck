package sepm.ss2017.e1625772.gui;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import sepm.ss2017.e1625772.domain.Booking;
import sepm.ss2017.e1625772.exceptions.BusinessLogicException;
import sepm.ss2017.e1625772.gui.properties.PropertyBooking;
import sepm.ss2017.e1625772.service.BookingService;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;


/**
 * @author Gary Ye
 * @version %I% %G%
 */
@Controller
public class BookingController extends FXMLController {
    private static final Logger LOG = LoggerFactory.getLogger(BookingController.class);

    @FXML
    private Button createNewButton;

    @FXML
    private TextField idTextField;

    @FXML
    private TableView<PropertyBooking> searchTable;
    @FXML
    private TableColumn<PropertyBooking, Long> idColumn;
    @FXML
    private TableColumn<PropertyBooking, String> customerColumn;
    @FXML
    private TableColumn<PropertyBooking, String> beginColumn;
    @FXML
    private TableColumn<PropertyBooking, String> endColumn;

    @FXML
    private Button searchButton;

    @FXML
    private DatePicker endBeginTimePicker;

    @FXML
    private Button deleteButton;

    @FXML
    private DatePicker formBeginTimePicker;

    @FXML
    private TextField customerTextField;

    @FXML
    private DatePicker searchBeginTimePicker;

    @FXML
    private Label currentStateLabel;

    @FXML
    private DatePicker formEndTimePicker;

    @FXML
    private Button saveButton;

    @FXML
    private Button resetButton;


    @FXML
    void search(ActionEvent event) {
        LOG.info("User has requested search by clicking the search button");

        // Search all or with ranges?
        try {
            List<Booking> list = bookingService.findAll();
            searchTableList.clear();
            for(Booking booking: list) {
                searchTableList.addAll(new PropertyBooking(booking));
            }

        } catch (BusinessLogicException e) {
            alertErrorMessage(e.getMessage());
        }
    }

    @FXML
    void createNew(ActionEvent event) {

    }

    @FXML
    void save(ActionEvent event) {

    }

    @FXML
    void delete(ActionEvent event) {

    }

    private ObservableList<PropertyBooking> searchTableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchTableList = FXCollections.observableArrayList();
        searchTable.setItems(searchTableList);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        beginColumn.setCellValueFactory(new PropertyValueFactory<>("beginTime"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        searchTable.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                LOG.info("Selected id = {}", newSelection.getId());
                setStateViewingBox(newSelection.getId());
            }
        });
    }

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Value("ui/booking.fxml")
    @Override
    public void setFxmlFilePath(String filePath) {
        this.fxmlFilePath = filePath;
    }

    public void setStateViewingBox(Long stateViewingBox) {
        LOG.info("");
    }
}

