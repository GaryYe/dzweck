package sepm.ss2017.e1625772.gui;

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
import sepm.ss2017.e1625772.domain.BoxBooking;
import sepm.ss2017.e1625772.domain.builders.BoxBookingBuilder;
import sepm.ss2017.e1625772.exceptions.FormParsingException;
import sepm.ss2017.e1625772.exceptions.ServiceException;
import sepm.ss2017.e1625772.gui.properties.PropertyBooking;
import sepm.ss2017.e1625772.gui.properties.PropertyBoxBooking;
import sepm.ss2017.e1625772.service.BookingService;
import sepm.ss2017.e1625772.service.BoxBookingService;

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
    private final String EDITING_STATE = "Editing";
    private final String CREATING_STATE = "Creating";
    private final BookingService bookingService;
    private final BoxBookingService boxBookingService;
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
    private Button receiptButton;
    @FXML
    private DatePicker searchEndTimePicker;
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
    private Button addBoxButton;
    @FXML
    private Button deleteBoxButton;
    private ObservableList<PropertyBoxBooking> boxBookings;
    @FXML
    private ListView<PropertyBoxBooking> boxListView;
    @FXML
    private TextField boxIDTextField;
    @FXML
    private TextField agreedDailyRateTextField;
    @FXML
    private TextField horseNameTextField;
    private ObservableList<PropertyBooking> searchTableList;

    @Autowired
    public BookingController(BookingService bookingService, BoxBookingService boxBookingService) {
        this.bookingService = bookingService;
        this.boxBookingService = boxBookingService;
    }

    private void clearAddBoxForm() {
        horseNameTextField.clear();
        agreedDailyRateTextField.clear();
        boxIDTextField.clear();
    }

    private PropertyBoxBooking parsePropertyBoxBooking() throws FormParsingException {
        try {
            PropertyBoxBooking box = new PropertyBoxBooking(Long.valueOf(boxIDTextField.getText()),
                    horseNameTextField.getText(), Double.valueOf(agreedDailyRateTextField.getText()));
            return box;
        } catch (NumberFormatException e) {
            throw new FormParsingException(e);
        }
    }

    public void reloadBoxes() {

    }

    @FXML
    public void addBox(ActionEvent actionEvent) {
        LOG.info("User has pressed the add box button");

        PropertyBoxBooking boxBookingProperty = null;
        Booking booking = null;
        try {
            boxBookingProperty = parsePropertyBoxBooking();
        } catch (FormParsingException e) {
            alertErrorMessage("Error while parsing the given box: " + e.getMessage());
            return;
        }

        try {
            booking = parseBooking();
        } catch (FormParsingException e) {
            alertErrorMessage("Error while parsing the given booking: " + e.getMessage());
            return;
        }

        BoxBooking boxBooking = new BoxBookingBuilder(booking.getId(), boxBookingProperty.getBoxId())
                .agreedDailyRate(boxBookingProperty.getAgreedDailyRate())
                .horseName(boxBookingProperty.getHorseName())
                .create();

        try {
            boxBookingService.create(boxBooking);
            LOG.info("BoxBooking between boxId={} and bookingId={} was created", boxBooking.getBoxId(), boxBooking
                    .getBookingId());
        } catch (ServiceException e) {
            // TODO: Relationship alraedy exists exception ? So user can know :D
            // TODO: And maybe check if already reserved
            LOG.error("Service could not add the box booking relationship", e);
            alertErrorMessage("Service error: Something went wrong when adding the box to the booking.");
            return;
        }

        horseNameTextField.clear();
        boxIDTextField.clear();
        agreedDailyRateTextField.clear();
        // TODO: Maybe reload box, easier
        boxBookings.addAll(boxBookingProperty);
    }

    @FXML
    public void deleteBox(ActionEvent actionEvent) {
        LOG.info("User has requested deleting a box");
    }

    @FXML
    private void search(ActionEvent event) {
        LOG.info("User has requested search by clicking the search button");

        LocalDate beginTime = searchBeginTimePicker.getValue();
        LocalDate endTime = searchEndTimePicker.getValue();

        // TODO: can this be improved?
        if (beginTime == null)
            beginTime = BookingService.MIN;
        if (endTime == null)
            endTime = BookingService.MAX;

        if (beginTime.isAfter(endTime)) {
            alertErrorMessage("End time can not be after begin time");
            return;
        }

        try {
            List<Booking> list = bookingService.findAllBetween(beginTime, endTime);
            LOG.info("Service found {} bookings", list.size());
            searchTableList.clear();
            for (Booking booking : list) {
                searchTableList.addAll(new PropertyBooking(booking));
            }
        } catch (ServiceException e) {
            alertErrorMessage(e.getMessage());
        }
    }

    private void setStateCreateNew() {
        this.currentStateLabel.setText(CREATING_STATE);
        this.idTextField.setText("");
        this.idTextField.setEditable(true);
        this.formBeginTimePicker.setValue(null);
        this.formEndTimePicker.setValue(null);
        this.customerTextField.setText("");
        this.deleteButton.setDisable(true);
        this.receiptButton.setDisable(true);
        this.addBoxButton.setDisable(true);
        this.deleteBoxButton.setDisable(true);

        this.boxIDTextField.setText("");
        this.boxIDTextField.setEditable(false);
        this.agreedDailyRateTextField.setText("");
        this.agreedDailyRateTextField.setEditable(false);
        this.horseNameTextField.setText("");
        this.horseNameTextField.setEditable(false);
        this.boxBookings.clear();
    }

    private void setStateEditing(Booking booking, List<BoxBooking> boxBookings) {
        if (booking == null) {
            setStateCreateNew();
        } else {
            this.currentStateLabel.setText(EDITING_STATE);
            this.idTextField.setText(String.valueOf(booking.getId()));
            this.idTextField.setEditable(false);
            this.formBeginTimePicker.setValue(booking.getBeginTime());
            this.formEndTimePicker.setValue(booking.getEndTime());
            this.customerTextField.setText(booking.getCustomerName());
            this.deleteButton.setDisable(false);
            this.receiptButton.setDisable(false);
            this.addBoxButton.setDisable(false);
            this.deleteBoxButton.setDisable(false);
            this.boxIDTextField.setEditable(true);
            this.agreedDailyRateTextField.setEditable(true);
            this.horseNameTextField.setEditable(true);

            this.boxBookings.clear();
            for (BoxBooking boxBooking : boxBookings) {
                this.boxBookings.addAll(new PropertyBoxBooking(boxBooking));
            }
        }
    }

    @FXML
    void createNew(ActionEvent event) {
        if (confirmationDialog("Do you want to create a new box (the entered data, if any, will be lost)?"))
            setStateCreateNew();
    }

    @FXML
    void save(ActionEvent event) {
        Booking booking = null;
        try {
            booking = parseBooking();
        } catch (Exception e) {
            alertErrorMessage("Can not parse the book: " + e.getMessage());
            return;
        }

        if (currentStateLabel.getText().equals(CREATING_STATE)) {
            if (confirmationDialog("Do you really want to create the box?")) {
                try {
                    bookingService.create(booking);

                    for (PropertyBoxBooking pbb : boxBookings) {
                        BoxBooking boxBooking = new BoxBookingBuilder(booking.getId(), pbb.getBoxId())
                                .agreedDailyRate(pbb.getAgreedDailyRate())
                                .horseName(pbb.getHorseName())
                                .create();
                        boxBookingService.create(boxBooking); // TODO: create or update ?
                        // Somehow determine which one were deleted
                        // Or add to add
                    }

                    // TODO: save relationship from
                    search(event);
                    setStateCreateNew();
                } catch (ServiceException e) {
                    alertErrorMessage("Something went wrong when the service created the box " + e.getMessage());
                }
            }
        } else {
            if (confirmationDialog("Do you really want to update the box?")) {
                try {
                    bookingService.delete(booking);
                    bookingService.create(booking); // TODO: can be improved , and has to be, cause of data integrity
                    search(event);
                } catch (ServiceException e) {
                    alertErrorMessage("Something went wrong when the service updated the box " + e.getMessage());
                }
                // Handle data inconsistency error
            }
        }
    }

    @FXML
    void openReceipt(ActionEvent event) {
        LOG.info("User has pressed the receipt button");
        Booking booking = null;
        try {
            booking = parseBooking();
        } catch (Exception e) {
            e.printStackTrace();
        }

        alertErrorMessage("This is the receipt of booking: " + booking);
    }

    private Booking parseBooking() throws FormParsingException {
        Booking booking = new Booking();

        try {
            booking.setId(Long.valueOf(idTextField.getText()));
            if (formBeginTimePicker.getValue() == null)
                throw new FormParsingException("Begin time has to be present");
            booking.setBeginTime(formBeginTimePicker.getValue());
            if (formEndTimePicker.getValue() == null)
                throw new FormParsingException("End time has to be present");
            booking.setEndTime(formEndTimePicker.getValue());

            booking.setCustomerName(customerTextField.getText());
        } catch (NumberFormatException e) {
            throw new FormParsingException("Id could not be parsed (it must be a positive number)");
        }

        return booking;
    }

    @FXML
    void resetSearch(ActionEvent event) {
        LOG.info("User has clicked the reset button");
        searchBeginTimePicker.setValue(null);
        searchEndTimePicker.setValue(null);
    }

    @FXML
    void delete(ActionEvent event) {
        Booking booking = null;
        try {
            booking = parseBooking();
        } catch (Exception e) {
            alertErrorMessage("Something went wrong when parsing the box" + e.getMessage());
            return;
        }

        if (confirmationDialog("Do you really want to delete?")) {
            try {
                bookingService.delete(booking);
                search(event);
                setStateCreateNew();
            } catch (ServiceException e) {
                LOG.error("Could not delete the booking", e);
                alertErrorMessage("Something went wrong when calling the service to delete the given box");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchTableList = FXCollections.observableArrayList();
        searchTable.setItems(searchTableList);

        boxBookings = FXCollections.observableArrayList();
        boxListView.setItems(boxBookings);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        beginColumn.setCellValueFactory(new PropertyValueFactory<>("beginTime"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        searchTable.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                LOG.info("Selected id = {}", newSelection.getId());
                try {
                    Booking booking = bookingService.findOne(newSelection.getId());
                    List<BoxBooking> boxBookings = boxBookingService.findAllByBox(booking.getId());
                    setStateEditing(booking, boxBookings);
                } catch (ServiceException e) {
                    alertErrorMessage(String.format("The service could not find the box with id = %d", newSelection
                            .getId()));
                }
            }
        });

        setStateCreateNew();
    }

    @Value("ui/booking.fxml")
    @Override
    public void setFxmlFilePath(String filePath) {
        this.fxmlFilePath = filePath;
    }
}

