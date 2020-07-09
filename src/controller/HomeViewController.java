package controller;

import data.DerbyDBDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.util.ListIterator;
import java.util.ResourceBundle;

public class HomeViewController implements Initializable {

    /* Instance Variables */
    private boolean flagIsNew = false;
    private ObservableList<LocalTime> bookStart = FXCollections.observableArrayList();
    private ObservableList<LocalTime> bookEnd = FXCollections.observableArrayList();
    private ObservableList<Booking> dayBookList = FXCollections.observableArrayList();

    /* GUI Elements */
    @FXML
    private TextField bookTitleTxt;
    @FXML
    private DatePicker bookStartDatePkr;
    @FXML
    private ComboBox<LocalTime> bookStartTimeOpt, bookEndTimeOpt;
    @FXML
    private ComboBox<Contact> bookContOpt;
    @FXML
    private ComboBox<Type> bookTypeOpt;
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
        clearAllFields();
        flagIsNew = true;
    }
    @FXML
    void bookDelBtnClk(ActionEvent event) {
        if (flagIsNew) {
            fillAllFields(Main.selected);
            flagIsNew = false;
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this" +
                " booking? This cannot be undone.");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                DerbyDBDriver.deleteBooking(Main.selected);
                Main.updateLists();
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

        //New booking
        if (flagIsNew) {
            Booking nwBook = DerbyDBDriver.addBooking(bookContOpt.getValue(), bookTitleTxt.getText(), bookDescTxt.getText(), bookTypeOpt.getValue(), startTmstmp, endTmstmp);
        } else {
            Main.selected.setContact(bookContOpt.getValue());
            Main.selected.setTitle(bookTitleTxt.getText());
            Main.selected.setDescription(bookDescTxt.getText());
            Main.selected.setType(bookTypeOpt.getValue());
            Main.selected.setStart(startTmstmp);
            Main.selected.setEnd(endTmstmp);
            DerbyDBDriver.updateBooking(Main.selected);
        }
        flagIsNew = false;
        Main.updateLists();
    }
    @FXML
    void bookCanxBtnClk(ActionEvent event) {
        fillAllFields(Main.selected);
        flagIsNew = false;
    }


    @FXML
    void setStartAvailability(ActionEvent event) {
        bookStart.clear();
        bookEnd.clear();
        dayBookList = Main.getDayBookings(bookStartDatePkr.getValue());

        LocalTime busEnd = LocalTime.of(20, 0);
        LocalTime busCurr = LocalTime.of(8, 0);

        for (Booking booking : dayBookList) {
            if (booking.equals(Main.selected)) {
                for (;busCurr.isBefore(booking.getEnd().toLocalDateTime().toLocalTime()); busCurr = busCurr.plusMinutes(15)) {
                    LocalTime avail = LocalTime.of(busCurr.getHour(), busCurr.getMinute());
                    bookStart.add(avail);
                }
                continue;
            }

            for (; busCurr.isBefore(busEnd); busCurr = busCurr.plusMinutes(15)) {
                if (busCurr.isBefore(booking.getStart().toLocalDateTime().toLocalTime())) {
                    LocalTime avail = LocalTime.of(busCurr.getHour(), busCurr.getMinute());
                    bookStart.add(avail);
                } else if (busCurr.equals(booking.getStart().toLocalDateTime().toLocalTime()) ||
                        (busCurr.isAfter(booking.getStart().toLocalDateTime().toLocalTime()) && busCurr.isBefore(booking.getEnd().toLocalDateTime().toLocalTime()))) {
                    continue;
                } else if (busCurr.equals(booking.getEnd().toLocalDateTime().toLocalTime())) {
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
                        if (busCurr.isBefore(booking.getStart().toLocalDateTime().toLocalTime())) {
                            LocalTime avail = LocalTime.of(busCurr.getHour(), busCurr.getMinute());
                            bookEnd.add(avail);
                        } else if (busCurr.equals(booking.getStart().toLocalDateTime().toLocalTime())) {
                            LocalTime avail = LocalTime.of(busCurr.getHour(), busCurr.getMinute());
                            bookEnd.add(avail);
                            return;
                        } else if (busCurr.isAfter(booking.getStart().toLocalDateTime().toLocalTime())) {
                            return;
                        }
                    }
                }
            }
        }
    }


    /* Prep All the Things! */
    @Override
    public void initialize(URL location, ResourceBundle rB) {
        //TableView for Agenda set up
        agendaTbl.setItems(Main.getAllBookings());
        bookDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        bookTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        bookTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        bookContCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        agendaTbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selected = newValue;
                fillAllFields(Main.selected);
            }
        }));

        //Set resources for option boxes and event/selection listeners
        bookContOpt.setItems(Main.getAllContacts());
        bookContOpt.setVisibleRowCount(7);
        bookStartTimeOpt.setItems(bookStart);
        bookStartTimeOpt.setVisibleRowCount(7);
        bookEndTimeOpt.setItems(bookEnd);
        bookEndTimeOpt.setVisibleRowCount(7);

        //If no current selection, select first booking for today
        if (Main.selected == null) {
            boolean past = true;
            Booking today = null;
            ListIterator<Booking> iterator = Main.getAllBookings().listIterator();
            while (past) {
                today = iterator.next();
                if (today == null) {
                    break;
                } else if (today.getStart().toLocalDateTime().toLocalDate().equals(LocalDate.now())) {
                    past = false;
                }
            }
            agendaTbl.getSelectionModel().select(today);
        }
    }

    /* Helper Methods */
    private void clearAllFields(){
        bookTitleTxt.setText("");
        bookStartDatePkr.setValue(LocalDate.now());
        bookStartTimeOpt.getSelectionModel().clearSelection();
        bookEndTimeOpt.getSelectionModel().clearSelection();
        bookContOpt.getSelectionModel().clearSelection();
        bookTypeOpt.getSelectionModel().clearSelection();
        bookDescTxt.setText("");
    }
    private void fillAllFields(Booking booking){
        bookTitleTxt.setText(booking.getTitle());
        bookStartDatePkr.setValue(booking.getStart().toLocalDateTime().toLocalDate());
        bookStartTimeOpt.setValue(booking.getStart().toLocalDateTime().toLocalTime());
        bookEndTimeOpt.setValue(booking.getEnd().toLocalDateTime().toLocalTime());
        bookContOpt.setValue(booking.getContact());
        bookTypeOpt.setValue(booking.getType());
        bookDescTxt.setText(booking.getDescription());
    }
}
