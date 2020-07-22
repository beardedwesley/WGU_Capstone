package BLTonThyme.controller;

import BLTonThyme.Main;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import BLTonThyme.model.Booking;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CalWeekViewController implements Initializable {
    @FXML
    private TableView<Booking> day1Tbl, day2Tbl, day3Tbl, day4Tbl, day5Tbl, day6Tbl, day7Tbl;
    @FXML
    private TableColumn<Booking, String> day1Col, day2Col, day3Col, day4Col, day5Col, day6Col, day7Col;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        day1Tbl.setItems(FXCollections.observableArrayList(Main.getDayBookings(LocalDate.now())));
        day2Tbl.setItems(FXCollections.observableArrayList(Main.getDayBookings(LocalDate.now().plusDays(1))));
        day3Tbl.setItems(FXCollections.observableArrayList(Main.getDayBookings(LocalDate.now().plusDays(2))));
        day4Tbl.setItems(FXCollections.observableArrayList(Main.getDayBookings(LocalDate.now().plusDays(3))));
        day5Tbl.setItems(FXCollections.observableArrayList(Main.getDayBookings(LocalDate.now().plusDays(4))));
        day6Tbl.setItems(FXCollections.observableArrayList(Main.getDayBookings(LocalDate.now().plusDays(5))));
        day7Tbl.setItems(FXCollections.observableArrayList(Main.getDayBookings(LocalDate.now().plusDays(6))));

        day1Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day2Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day3Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day4Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day5Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day6Col.setCellValueFactory(new PropertyValueFactory<>("details"));
        day7Col.setCellValueFactory(new PropertyValueFactory<>("details"));

        day1Col.setText(LocalDate.now().getDayOfWeek().toString());
        day2Col.setText(LocalDate.now().plusDays(1).getDayOfWeek().toString());
        day3Col.setText(LocalDate.now().plusDays(2).getDayOfWeek().toString());
        day4Col.setText(LocalDate.now().plusDays(3).getDayOfWeek().toString());
        day5Col.setText(LocalDate.now().plusDays(4).getDayOfWeek().toString());
        day6Col.setText(LocalDate.now().plusDays(5).getDayOfWeek().toString());
        day7Col.setText(LocalDate.now().plusDays(6).getDayOfWeek().toString());

        day1Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selectedBooking = newValue;
            }
        }));
        day2Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selectedBooking = newValue;
            }
        }));
        day3Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selectedBooking = newValue;
            }
        }));
        day4Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selectedBooking = newValue;
            }
        }));
        day5Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selectedBooking = newValue;
            }
        }));
        day6Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selectedBooking = newValue;
            }
        }));
        day7Tbl.selectionModelProperty().get().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                Main.selectedBooking = newValue;
            }
        }));
    }
}
