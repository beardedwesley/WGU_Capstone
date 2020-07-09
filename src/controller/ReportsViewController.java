package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Booking;
import model.Contact;
import model.Main;
import model.Type;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class ReportsViewController implements Initializable {
    @FXML
    private ListView<Type> typeList;
    @FXML
    private ListView<Contact> contList;
    @FXML
    private TableView<Booking> typeBookTbl, contBookTbl;
    @FXML
    private TableColumn<Booking, LocalDate> typeDateCol, contDateCol;
    @FXML
    private TableColumn<Booking, LocalTime> typeTimeCol, contTimeCol;
    @FXML
    private TableColumn<Booking, String> typeTitleCol, typeContCol, contTitleCol, contTypeCol;

    private void populateTypeTbl(Type type) {
        typeBookTbl.setItems(Main.getTypeBookings(type));
        typeDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        typeTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        typeTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeContCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }
    private void populateContTbl(Contact contact) {
        contBookTbl.setItems(Main.getContBookings(contact));
        contDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        contTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
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
