package controller;

import data.DerbyDBDriver;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CityState;
import model.Contact;
import model.Main;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ContactListViewController implements Initializable {
    private TableView.TableViewSelectionModel<Contact> contSelectModel;
    private Contact selected;
    private boolean flagIsNew = false;
    @FXML
    private TextField contFirstTxt, contLastTxt, contPhoneTxt, contAdd1Txt, contAdd2Txt, contCityTxt, contStateTxt, contPCTxt;
    @FXML
    private TableView<Contact> contListTbl;
    @FXML
    private TableColumn<Contact, String> contNameCol, contCityCol, contStateCol;
    @FXML
    private TableColumn<Contact, Integer> contPhoneCol;

    @FXML
    void onNewBtn(ActionEvent event) {
        clearAllFields();
        flagIsNew = true;
    }

    @FXML
    void onDelBtn(ActionEvent event) {
        if (flagIsNew) {
            fillAllFields(selected);
            flagIsNew = false;
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this" +
                    " contact? All appointments with this contact will be removed. This cannot be undone.");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    DerbyDBDriver.deleteContact(selected);
                    Main.updateLists();
                    contSelectModel.selectFirst();
                }
            });
        }
    }

    @FXML
    void onSaveBtn(ActionEvent event) {

        //Check that fields are filled in
        if (contFirstTxt.getText().isBlank() || contLastTxt.getText().isBlank() || contPhoneTxt.getText().isBlank() ||
            contAdd1Txt.getText().isBlank() || contCityTxt.getText().isBlank() || contStateTxt.getText().isBlank() ||
            contPCTxt.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "All fields must have a value.");
            alert.showAndWait();
            return;
        }

        //Enforce character limits
        if (contFirstTxt.getText().length() > 20) {
            contFirstTxt.setText(contFirstTxt.getText(0, 20));
        }
        if (contLastTxt.getText().length() > 30) {
            contLastTxt.setText(contLastTxt.getText(0, 30));
        }
        if (contAdd1Txt.getText().length() > 50) {
            contAdd1Txt.setText(contAdd1Txt.getText(0, 50));
        }
        if (contAdd2Txt.getText().length() > 50) {
            contAdd2Txt.setText(contAdd2Txt.getText(0, 50));
        }
        if (contCityTxt.getText().length() > 30) {
            contCityTxt.setText(contCityTxt.getText(0, 30));
        }
        if (contStateTxt.getText().length() > 30) {
            contStateTxt.setText(contStateTxt.getText(0, 30));
        }

        if (flagIsNew) {
            CityState nwCityState = DerbyDBDriver.findCityState(contCityTxt.getText(), contStateTxt.getText(), Integer.parseInt(contPCTxt.getText()));
            if (nwCityState == null) {
                try {
                    nwCityState = DerbyDBDriver.addCityState(contCityTxt.getText(), contStateTxt.getText(), Integer.parseInt(contPCTxt.getText()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            DerbyDBDriver.addContact(contFirstTxt.getText(), contLastTxt.getText(), Integer.parseInt(contPhoneTxt.getText()), contAdd1Txt.getText(), contAdd2Txt.getText(), nwCityState);
        } else {
            CityState upCityState = DerbyDBDriver.findCityState(contCityTxt.getText(), contStateTxt.getText(), Integer.parseInt(contPCTxt.getText()));;
            if (upCityState == null) {
                upCityState = selected.getCityState();
                upCityState.setCity(contCityTxt.getText());
                upCityState.setState(contStateTxt.getText());
                upCityState.setZipcode(Integer.parseInt(contPCTxt.getText()));
                DerbyDBDriver.updateCityState(upCityState);
            }
            selected.setFirstName(contFirstTxt.getText());
            selected.setLastName(contLastTxt.getText());
            selected.setPhone(Integer.parseInt(contPhoneTxt.getText()));
            selected.setAddress1(contAdd1Txt.getText());
            selected.setAddress2(contAdd2Txt.getText());
            selected.setCityState(upCityState);
            DerbyDBDriver.updateContact(selected);
        }
        flagIsNew = false;
        Main.updateLists();
    }

    @FXML
    void onCnxBtn(ActionEvent event) {
        fillAllFields(selected);
        flagIsNew = false;
    }

    /* Helper methods */
    private void fillAllFields(Contact contact) {
        contFirstTxt.setText(contact.getFirstName());
        contLastTxt.setText(contact.getLastName());
        contPhoneTxt.setText(((Integer) contact.getPhone()).toString());
        contAdd1Txt.setText(contact.getAddress1());
        contAdd2Txt.setText(contact.getAddress2());
        contCityTxt.setText(contact.getCityState().getCity());
        contStateTxt.setText(contact.getCityState().getState());
        contPCTxt.setText(((Integer) contact.getCityState().getZipcode()).toString());
    }
    private void clearAllFields() {
        contFirstTxt.clear();
        contLastTxt.clear();
        contPhoneTxt.clear();
        contAdd1Txt.clear();
        contAdd2Txt.clear();
        contCityTxt.clear();
        contStateTxt.clear();
        contPCTxt.clear();
    }

    /* Overrides */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contListTbl.setItems(Main.getAllContacts());
        contNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        contPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        contCityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        contStateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        contSelectModel = contListTbl.selectionModelProperty().get();
        contSelectModel.selectedIndexProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selected = contSelectModel.getSelectedItem();
                fillAllFields(selected);
                flagIsNew = false;
            }
        }));
        contSelectModel.selectFirst();

    }
}
