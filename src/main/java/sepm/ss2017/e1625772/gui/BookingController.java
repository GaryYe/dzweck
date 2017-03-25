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
import sepm.ss2017.e1625772.domain.Receipt;
import sepm.ss2017.e1625772.domain.builders.BoxBookingBuilder;
import sepm.ss2017.e1625772.exceptions.*;
import sepm.ss2017.e1625772.gui.properties.PropertyBooking;
import sepm.ss2017.e1625772.gui.properties.PropertyBoxBooking;
import sepm.ss2017.e1625772.service.BookingService;
import sepm.ss2017.e1625772.service.BoxBookingService;
import sepm.ss2017.e1625772.service.ReceiptService;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import static sepm.ss2017.e1625772.gui.FXUtils.alertErrorMessage;
import static sepm.ss2017.e1625772.gui.FXUtils.confirmationDialog;


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
    private final ReceiptService receiptService;
    private final ReceiptRenderer<String> receiptRenderer;
    private Booking currentBooking;

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
    private ObservableList<PropertyBoxBooking> boxBookingProperties;
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
    public BookingController(BookingService bookingService, BoxBookingService boxBookingService, ReceiptService
            receiptService, ReceiptRenderer<String> receiptRenderer) {
        this.bookingService = bookingService;
        this.boxBookingService = boxBookingService;
        this.receiptService = receiptService;
        this.receiptRenderer = receiptRenderer;
    }

    private void clearAddBoxForm() {
        horseNameTextField.clear();
        agreedDailyRateTextField.clear();
        boxIDTextField.clear();
    }

    private BoxBooking parseBoxBooking() throws FormParsingException {
        try {
            return new BoxBookingBuilder(currentBooking.getId(), Long.valueOf(boxIDTextField.getText()))
                    .agreedDailyRate(Double.valueOf(agreedDailyRateTextField.getText()))
                    .horseName(horseNameTextField.getText())
                    .create();
        } catch (NumberFormatException e) {
            throw new FormParsingException(e);
        }
    }

    private void loadBoxBookingProperties(Booking booking) {
        if (booking == null)
            throw new PresentationException("Can not load the box bookings from a null booking");
        boxBookingProperties.clear();
        for (BoxBooking boxBooking : boxBookingService.findAllByBooking(booking.getId())) {
            boxBookingProperties.addAll(new PropertyBoxBooking(boxBooking));
        }
    }

    @FXML
    public void addBox(ActionEvent actionEvent) {
        LOG.info("User has pressed the add box button");

        if (currentBooking == null)
            throw new PresentationException("User wanted to add a box to an unloaded booking");

        try {
            BoxBooking boxBooking = parseBoxBooking();
            boxBookingService.create(boxBooking);

            horseNameTextField.clear();
            boxIDTextField.clear();
            agreedDailyRateTextField.clear();
        } catch (FormParsingException e) {
            alertErrorMessage("Error while parsing the given box: " + e.getMessage());
        } catch (BoxBookingCollisionException e) {
            alertErrorMessage(e.getMessage());
        } catch (ObjectNotFoundException e) {
            // TODO: Would be clean to check whether it is the box that is missing
            alertErrorMessage("The requested box does not exist");
        } catch (DuplicatedObjectException e) {
            alertErrorMessage("The given box already exists!");
        } finally {
            loadBoxBookingProperties(currentBooking);
        }
    }

    @FXML
    public void deleteBox(ActionEvent actionEvent) {
        LOG.info("User has requested deleting a box");

        if (currentBooking == null)
            throw new PresentationException("User wanted to delete a box from an unloaded booking");

        PropertyBoxBooking selected = boxListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            alertErrorMessage("You have to select the box booking you want to delete");
            return;
        }

        BoxBooking boxBooking = new BoxBookingBuilder(currentBooking.getId(), selected.getBoxId()).create();

        try {
            boxBookingService.delete(boxBooking);
            loadBoxBookingProperties(currentBooking);
        } catch (ObjectNotFoundException e) {
            // Usually happens when some other service already deleted this object
            alertErrorMessage("The box booking relationship doesn't exist anymore");
        }
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
            List<Booking> list = bookingService.findAllIntersecting(beginTime, endTime);
            LOG.info("Service found {} bookings", list.size());
            searchTableList.clear();
            for (Booking booking : list) {
                searchTableList.addAll(new PropertyBooking(booking));
            }
        } catch (ServiceException e) {
            throw e;
            // alertErrorMessage(e.getMessage());
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
        this.boxBookingProperties.clear();

        this.currentBooking = null;
    }

    private void setStateEditing(Booking booking, List<BoxBooking> boxBookings) {
        if (booking == null || boxBookings == null)
            throw new IllegalArgumentException("Can not edit a null booking");
        this.currentBooking = booking;

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

        this.boxBookingProperties.clear();
        for (BoxBooking boxBooking : boxBookings) {
            this.boxBookingProperties.addAll(new PropertyBoxBooking(boxBooking));
        }
    }

    @FXML
    void createNew(ActionEvent event) {
        if (confirmationDialog("Do you want to create a new box (the entered data, if any, will be lost)?"))
            setStateCreateNew();
    }

    @FXML
    void save(ActionEvent event) {
        Booking booking;
        try {
            booking = parseBooking();
        } catch (Exception e) {
            alertErrorMessage("Can not parse the book: " + e.getMessage());
            return;
        }

        if (currentStateLabel.getText().equals(CREATING_STATE)) {
            if (!confirmationDialog("Do you really want to create the box?"))
                return;
            try {
                bookingService.create(booking);
            } catch (Exception e) {

            }
            setStateCreateNew();
        } else {
            if (!confirmationDialog("Do you really want to update the box?"))
                return;
            try {
                bookingService.update(booking);
            } catch (BoxBookingCollisionException e) {
                alertErrorMessage(e.getMessage());
            }
        }
        search(event);
    }

    @FXML
    void openReceipt(ActionEvent event) {
        LOG.info("User has pressed the receipt button");
        if (currentBooking == null)
            throw new PresentationException("User requested receipt while current booking was not loaded");
        // TODO: Receipt service call
        Receipt receipt = receiptService.calculateReceipt(currentBooking.getId());
        confirmationDialog("This is the receipt of booking\n" + receiptRenderer.render(receipt));
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
        LOG.info("User has clicked the delete button");
        if (currentBooking == null)
            throw new PresentationException("User has requested a delete while the booking was not loaded");
        // TODO: Show the user which other entities are also affected?
        if (confirmationDialog("Do you really want to delete?")) {
            try {
                bookingService.delete(currentBooking);
                search(event);
                setStateCreateNew();
            } catch (ServiceException e) { // TODO
                LOG.error("Could not delete the booking", e);
                alertErrorMessage("Something went wrong when calling the service to delete the given box");
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchTableList = FXCollections.observableArrayList();
        searchTable.setItems(searchTableList);

        boxBookingProperties = FXCollections.observableArrayList();
        boxListView.setItems(boxBookingProperties);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        beginColumn.setCellValueFactory(new PropertyValueFactory<>("beginTime"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));

        searchTable.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                LOG.info("User has selected bookingId = {}", newSelection.getId());
                try {
                    Booking booking = bookingService.findOne(newSelection.getId());
                    List<BoxBooking> boxBookings = boxBookingService.findAllByBooking(booking.getId());
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

