package BLT_Scheduler.controller;

import BLT_Scheduler.Main;
import BLT_Scheduler.data.DerbyDBDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import BLT_Scheduler.model.CityState;
import BLT_Scheduler.model.Contact;
import javafx.scene.input.KeyEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ContactListViewController implements Initializable {
    private TableView.TableViewSelectionModel<Contact> contSelectModel;
    private boolean flagIsNew = false;
    @FXML
    private TextField contFirstTxt, contLastTxt, contPhoneTxt, contAdd1Txt, contAdd2Txt, contCityTxt, contStateTxt, contPCTxt, contNameSearch, contPhoneSearch, contCitySearch, contStateSearch;
    @FXML
    private TableView<Contact> contListTbl;
    @FXML
    private TableColumn<Contact, String> contNameCol, contCityCol, contStateCol;
    @FXML
    private TableColumn<Contact, Integer> contPhoneCol;

    /* Action-Triggered events */
    @FXML
    void onNewBtn(ActionEvent event) {
        clearAllFields();
        flagIsNew = true;
    }
    @FXML
    void onDelBtn(ActionEvent event) {
        if (flagIsNew) {
            fillAllFields(Main.selectedContact);
            flagIsNew = false;
            return;
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this" +
                    " contact? All appointments with this contact will be removed. This cannot be undone.");
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Main.deleteContact(Main.selectedContact);
                    DerbyDBDriver.deleteContact(Main.selectedContact);
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

        Contact contact;
        if (flagIsNew) {
            CityState nwCityState = DerbyDBDriver.findCityState(contCityTxt.getText(), contStateTxt.getText(), Integer.parseInt(contPCTxt.getText()));
            if (nwCityState == null) {
                try {
                    nwCityState = DerbyDBDriver.addCityState(contCityTxt.getText(), contStateTxt.getText(), Integer.parseInt(contPCTxt.getText()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            contact = DerbyDBDriver.addContact(contFirstTxt.getText(), contLastTxt.getText(), Integer.parseInt(contPhoneTxt.getText()), contAdd1Txt.getText(), contAdd2Txt.getText(), nwCityState);
        } else {
            CityState upCityState = DerbyDBDriver.findCityState(contCityTxt.getText(), contStateTxt.getText(), Integer.parseInt(contPCTxt.getText()));
            if (upCityState == null) {
                upCityState = Main.selectedContact.getCityState();
                upCityState.setCity(contCityTxt.getText());
                upCityState.setState(contStateTxt.getText());
                upCityState.setZipcode(Integer.parseInt(contPCTxt.getText()));
                try {
                    upCityState = DerbyDBDriver.addCityState(contCityTxt.getText(), contStateTxt.getText(), Integer.parseInt(contPCTxt.getText()));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            Main.selectedContact.setFirstName(contFirstTxt.getText());
            Main.selectedContact.setLastName(contLastTxt.getText());
            Main.selectedContact.setPhone(Integer.parseInt(contPhoneTxt.getText()));
            Main.selectedContact.setAddress1(contAdd1Txt.getText());
            Main.selectedContact.setAddress2(contAdd2Txt.getText());
            Main.selectedContact.setCityState(upCityState);
            DerbyDBDriver.updateContact(Main.selectedContact);
            contact = Main.selectedContact;
        }
        flagIsNew = false;
        Main.addContact(contact);
        contSelectModel.select(contact);
    }
    @FXML
    void onCnxBtn(ActionEvent event) {
        fillAllFields(Main.selectedContact);
        flagIsNew = false;
    }

    @FXML
    void search(KeyEvent event) {
        ObservableList<Contact> filteredContacts = FXCollections.observableArrayList();

        String nameSearch = contNameSearch.getText();
        boolean flagName = !(nameSearch.isBlank());
        String phoneSearch = contPhoneSearch.getText();
        boolean flagPhone = !(phoneSearch.isBlank());
        String citySearch = contCitySearch.getText();
        boolean flagCity = !(citySearch.isBlank());
        String stateSearch = contStateSearch.getText();
        boolean flagState = !(stateSearch.isBlank());

        if (flagName || flagPhone || flagCity || flagState) {
            contListTbl.setItems(Main.getAllContacts());
        } else {
            contListTbl.setItems(filteredContacts);
            return;
        }

        for (Contact contact : Main.getAllContacts()) {
            boolean nameMatch = false, phoneMatch = false, cityMatch = false, stateMatch = false;

            if (flagName) {
                if (contact.toString().toLowerCase().contains(nameSearch.toLowerCase())) {
                    nameMatch = true;
                }
            }

            if (flagPhone) {
                if (((Integer)contact.getPhone()).toString().contains(phoneSearch)) {
                    phoneMatch = true;
                }
            }

            if (flagCity) {
                if (contact.getCity().toLowerCase().contains(citySearch.toLowerCase())) {
                    cityMatch = true;
                }
            }

            if (flagState) {
                if (contact.getState().toLowerCase().contains(stateSearch.toLowerCase())) {
                    stateMatch = true;
                }
            }

            if ((flagName == nameMatch) && (flagPhone == phoneMatch) && (flagCity == cityMatch) && (flagState == stateMatch)) {
                filteredContacts.add(contact);
            }
        }
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
                Main.selectedContact = contSelectModel.getSelectedItem();
                fillAllFields(Main.selectedContact);
                this.flagIsNew = false;
            }
        }));
        if (Main.selectedContact == null) {
            contSelectModel.selectFirst();
        }

    }
}
