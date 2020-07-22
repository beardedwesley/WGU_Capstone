package BLTonThyme.controller;

import BLTonThyme.Main;
import BLTonThyme.data.DerbyDBDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import BLTonThyme.model.*;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.ResourceBundle;

public class HomeViewController implements Initializable {

    /* Instance Variables */
    private boolean flagIsNew = false;
    private final ObservableList<LocalTime> bookStart = FXCollections.observableArrayList();
    private final ObservableList<LocalTime> bookEnd = FXCollections.observableArrayList();
    private final ObservableList<Booking> futureBookList = FXCollections.observableArrayList();
    private ObservableList<Booking> dayBookList = FXCollections.observableArrayList();

    /* GUI Elements */
    @FXML
    private TextField bookTitleTxt, bookDateSearch, bookTimeSearch, bookTitleSearch, bookContSearch;
    @FXML
    private DatePicker bookStartDatePkr;
    @FXML
    private ComboBox<LocalTime> bookStartTimeOpt, bookEndTimeOpt;
    @FXML
    private ComboBox<Contact> bookContOpt;
    @FXML
    private ComboBox<TypeID> bookTypeOpt;
    @FXML
    private TextArea bookDescTxt;

    @FXML
    private TableView<Booking> agendaTbl;
    @FXML
    private TableColumn<Booking, LocalDate> bookDateCol;
    @FXML
    private TableColumn<Booking, LocalTime> bookTimeCol;
    @FXML
    private TableColumn<Booking, String> bookTitleCol, bookContCol;

    /* Action-Triggered Methods */
    @FXML
    void bookNewBtnClk(ActionEvent event) {
        agendaTbl.getSelectionModel().clearSelection();
        clearAllFields();
        flagIsNew = true;
    }
    @FXML
    void bookDelBtnClk(ActionEvent event) {
        if (flagIsNew) {
            fillAllFields(Main.selectedBooking);
            flagIsNew = false;
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this" +
                " booking? This cannot be undone.");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Main.deleteBooking(Main.selectedBooking);
                DerbyDBDriver.deleteBooking(Main.selectedBooking);
                if (Main.selectedBooking.getStartDate().equals(LocalDate.now()) || Main.selectedBooking.getStartDate().isAfter(LocalDate.now())) {
                    futureBookList.remove(Main.selectedBooking);
                }
                agendaTbl.getSelectionModel().select(1);//selected = null;
                }
            });

    }
    @FXML
    void bookSaveBtnClk(ActionEvent event) {
        //Confirm fields filled
        if (bookTitleTxt.getText().isBlank() || bookStartDatePkr.getValue() == null || bookStartTimeOpt.getValue() == null
            || bookEndTimeOpt.getValue() == null || bookContOpt.getValue() == null || bookTypeOpt.getValue() == null
            || bookDescTxt.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "All fields must have a value");
            alert.showAndWait();
            return;
        }
        if (bookTitleTxt.getText().length() > 155) {
            bookTitleTxt.setText(bookTitleTxt.getText(0, 255));
        }
        Timestamp startTmstmp = Timestamp.valueOf(LocalDateTime.of(bookStartDatePkr.getValue(), bookStartTimeOpt.getValue()));
        Timestamp endTmstmp = Timestamp.valueOf(LocalDateTime.of(bookStartDatePkr.getValue(), bookEndTimeOpt.getValue()));
        Booking booking;

        //New booking
        if (flagIsNew) {
            booking = DerbyDBDriver.addBooking(bookContOpt.getValue(), bookTitleTxt.getText(), bookDescTxt.getText(), bookTypeOpt.getValue(), startTmstmp, endTmstmp);
            Main.addBooking(booking);
            if (booking.getStartDate().equals(LocalDate.now()) || booking.getStartDate().isAfter(LocalDate.now())) {
                futureBookList.add(booking);
            }
        } else {//Update Booking
            if (Main.selectedBooking.getType().equals(bookTypeOpt.getValue())) {
                Main.selectedBooking.setContact(bookContOpt.getValue());
                Main.selectedBooking.setTitle(bookTitleTxt.getText());
                Main.selectedBooking.setDescription(bookDescTxt.getText());
                Main.selectedBooking.setStart(startTmstmp);
                Main.selectedBooking.setEnd(endTmstmp);
                DerbyDBDriver.updateBooking(Main.selectedBooking);
                booking = Main.selectedBooking;
            } else {//specifically for type changes
                DerbyDBDriver.deleteBooking(Main.selectedBooking);
                booking = DerbyDBDriver.addBooking(bookContOpt.getValue(), bookTitleTxt.getText(), bookDescTxt.getText(), bookTypeOpt.getValue(), startTmstmp, endTmstmp);
            }
        }
        flagIsNew = false;
        agendaTbl.getSelectionModel().select(booking);
    }
    @FXML
    void bookCanxBtnClk(ActionEvent event) {
        fillAllFields(Main.selectedBooking);
        flagIsNew = false;
    }

    @FXML
    void setStartAvailability(ActionEvent event) {
        bookStart.clear();
        bookEnd.clear();
        if (bookStartDatePkr.getValue() == null) {
            return; //want lists to be blank if date has not yet been selected & event occurs when field is cleared by system
        }
        dayBookList = Main.getDayBookings(bookStartDatePkr.getValue());
        FXCollections.sort(dayBookList);

        LocalTime busEnd = LocalTime.of(20, 0);
        LocalTime busCurr = LocalTime.of(8, 0);

        for (Booking booking : dayBookList) {
            if (booking.equals(Main.selectedBooking)) {
                for (;busCurr.isBefore(booking.getEndTime()); busCurr = busCurr.plusMinutes(15)) {
                    LocalTime avail = LocalTime.of(busCurr.getHour(), busCurr.getMinute());
                    bookStart.add(avail);
                }
                continue;
            }

            for (; busCurr.isBefore(busEnd); busCurr = busCurr.plusMinutes(15)) {
                if (busCurr.isBefore(booking.getStartTime())) {
                    LocalTime avail = LocalTime.of(busCurr.getHour(), busCurr.getMinute());
                    bookStart.add(avail);
                } else if (busCurr.equals(booking.getStartTime()) ||
                        (busCurr.isAfter(booking.getStartTime()) && busCurr.isBefore(booking.getEndTime()))) {
                    continue;
                } else if (busCurr.equals(booking.getEndTime())) {
                    break;
                }
            }
        }

        if (busCurr.isBefore(busEnd)) {
            for (;busCurr.isBefore(busEnd); busCurr = busCurr.plusMinutes(15)) {
                LocalTime avail = LocalTime.of(busCurr.getHour(), busCurr.getMinute());
                bookStart.add(avail);
            }
        }
    }
    @FXML
    void setEndAvailability(ActionEvent event) {
        if (bookStart.isEmpty() || bookStartTimeOpt.getValue() == null) {return;}

        LocalTime busEnd = LocalTime.of(20, 1);
        LocalTime busCurr = bookStartTimeOpt.getValue();

        if (dayBookList.isEmpty() || dayBookList.size() == 1) {
            busCurr = busCurr.plusMinutes(15);
            for (;busCurr.isBefore(busEnd); busCurr = busCurr.plusMinutes(15)) {
                LocalTime avail = LocalTime.of(busCurr.getHour(), busCurr.getMinute());
                bookEnd.add(avail);
            }
        } else {
            for (Booking booking : dayBookList) {
                if (dayBookList.indexOf(booking) == (dayBookList.size() - 1)) {
                    busCurr = busCurr.plusMinutes(15);
                    for (; busCurr.isBefore(busEnd); busCurr = busCurr.plusMinutes(15)) {
                        LocalTime avail = LocalTime.of(busCurr.getHour(), busCurr.getMinute());
                        bookEnd.add(avail);
                    }
                } else {
                    for (; busCurr.isBefore(busEnd); busCurr = busCurr.plusMinutes(15)) {
                        if (busCurr.isBefore(booking.getStartTime())) {
                            LocalTime avail = LocalTime.of(busCurr.getHour(), busCurr.getMinute());
                            bookEnd.add(avail);
                        } else if (busCurr.equals(booking.getStartTime())) {
                            LocalTime avail = LocalTime.of(busCurr.getHour(), busCurr.getMinute());
                            bookEnd.add(avail);
                            return;
                        } else if (busCurr.isAfter(booking.getStartTime())) {
                            return;
                        }
                    }
                }
            }
        }
    }

    @FXML
    void search(KeyEvent event) {
        ObservableList<Booking> filteredBookings = FXCollections.observableArrayList();

        String dateSearch = bookDateSearch.getText();
        boolean flagDate = !(dateSearch.isBlank());
        String timeSearch = bookTimeSearch.getText();
        boolean flagTime = !(timeSearch.isBlank());
        String titleSearch = bookTitleSearch.getText();
        boolean flagTitle = !(titleSearch.isBlank());
        String contactSearch = bookContSearch.getText();
        boolean flagContact = !(contactSearch.isBlank());

        if (flagDate || flagTime || flagTitle || flagContact) {
            agendaTbl.setItems(filteredBookings);
        } else {
            agendaTbl.setItems(futureBookList);
            return;
        }

        for (Booking booking : futureBookList) {
            boolean dateMatch = false, timeMatch = false, titleMatch = false, contactMatch = false;

            if (flagDate) {
                if (booking.getStartDate().toString().contains(dateSearch) ||
                        booking.getEnd().toLocalDateTime().toLocalDate().toString().contains(dateSearch)) {
                    dateMatch = true;
                }
            }

            if (flagTime) {
                if (booking.getStartTime().toString().contains(timeSearch) ||
                    booking.getEndTime().toString().contains(timeSearch)) {
                    timeMatch = true;
                }
            }

            if (flagTitle) {
                if (booking.getTitle().toLowerCase().contains(titleSearch.toLowerCase())) {
                    titleMatch = true;
                }
            }

            if (flagContact) {
                if (booking.getContact().toString().toLowerCase().contains(contactSearch.toLowerCase())) {
                    contactMatch = true;
                }
            }

            if ((flagDate == dateMatch) && (flagTime == timeMatch) && (flagTitle == titleMatch) && (flagContact == contactMatch)) {
                filteredBookings.add(booking);
            }
        }

    }

    /* Prep All the Things! */
    @Override
    public void initialize(URL location, ResourceBundle rB) {
        futureBookings(Main.getAllBookings());

        //TableView for Agenda set up
        agendaTbl.setItems(futureBookList);
        bookDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        bookTimeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        bookTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookContCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        agendaTbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selectedBooking = newValue;
                fillAllFields(Main.selectedBooking);
            }
        }));

        //Set resources for option boxes and event/selection listeners
        bookContOpt.setItems(Main.getAllContacts());
        bookContOpt.setVisibleRowCount(7);
        bookStartTimeOpt.setItems(bookStart);
        bookStartTimeOpt.setVisibleRowCount(7);
        bookEndTimeOpt.setItems(bookEnd);
        bookEndTimeOpt.setVisibleRowCount(7);
        bookTypeOpt.setItems(Main.getAllTypes());

        //If no current selection, select first booking for today
        if (Main.selectedBooking == null) {
            agendaTbl.getSelectionModel().selectFirst();
        }
    }

    /* Helper Methods */
    private void clearAllFields(){
        bookTitleTxt.clear();
        bookStartDatePkr.setValue(LocalDate.now());
        bookStartTimeOpt.getSelectionModel().clearSelection();
        bookStartTimeOpt.setValue(null);
        bookEndTimeOpt.getSelectionModel().clearSelection();
        bookEndTimeOpt.setValue(null);
        bookContOpt.getSelectionModel().clearSelection();
        bookTypeOpt.getSelectionModel().clearSelection();
        bookDescTxt.clear();
    }
    private void fillAllFields(Booking booking){
        bookTitleTxt.setText(booking.getTitle());
        bookStartDatePkr.setValue(booking.getStartDate());
        bookStartTimeOpt.setValue(booking.getStartTime());
        bookEndTimeOpt.setValue(booking.getEndTime());
        bookContOpt.setValue(booking.getContact());
        bookTypeOpt.setValue(booking.getType());
        bookDescTxt.setText(booking.getDescription());
    }
    private void futureBookings(ObservableList<Booking> allBookings) {
        futureBookList.clear();
        for (Booking booking : allBookings) {
            if (booking.getStartDate().equals(LocalDate.now()) || booking.getStartDate().isAfter(LocalDate.now())) {
                futureBookList.add(booking);
            }
        }
        FXCollections.sort(futureBookList);
    }
}
