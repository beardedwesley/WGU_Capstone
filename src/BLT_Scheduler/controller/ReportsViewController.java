package BLT_Scheduler.controller;

import BLT_Scheduler.Main;
import BLT_Scheduler.model.TypeID;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import BLT_Scheduler.model.Booking;
import BLT_Scheduler.model.Contact;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class ReportsViewController implements Initializable {
    @FXML
    private ListView<TypeID> typeList;
    @FXML
    private ListView<Contact> contList;
    @FXML
    private TableView<Booking> typeBookTbl, contBookTbl;
    @FXML
    private TableColumn<Booking, LocalDate> typeDateCol, contDateCol;
    @FXML
    private TableColumn<Booking, LocalTime> typeStartTimeCol, typeEndTimeCol, contStartTimeCol, contEndTimeCol;
    @FXML
    private TableColumn<Booking, String> typeTitleCol, typeContCol, contTitleCol, contTypeCol;

    private void populateTypeTbl(TypeID type) {
        typeBookTbl.setItems(Main.getTypeBookings(type));
        typeDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        typeStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        typeEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        typeTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeContCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }
    private void populateContTbl(Contact contact) {
        contBookTbl.setItems(Main.getContBookings(contact));
        contDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        contStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        contEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        contTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        contTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeList.setItems(Main.getAllTypes());
        typeList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        typeList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateTypeTbl(newValue);
            }
        });

        contList.setItems(Main.getAllContacts());
        contList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        contList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                populateContTbl(newValue);
            }
        });
    }
}
