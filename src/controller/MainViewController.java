package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Main;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    private ResourceBundle rB;

    @FXML
    private HomeViewController homeViewController;
    @FXML
    private CalWeekViewController weekViewController;
    @FXML
    private CalMonthViewController monthViewController;
    @FXML
    private ContactListViewController contactListViewController;
    @FXML
    private ReportsViewController reportsViewController;

    public void homeSelected() throws MalformedURLException {
        homeViewController.initialize(new URL("file:src/view/HomeView.fxml"), rB);
    }
    public void weekSelected() throws MalformedURLException {
        weekViewController.initialize(new URL("file:src/view/CalWeekView.fxml"), rB);
    }
    public void monthSelected() throws MalformedURLException {
        monthViewController.initialize(new URL("file:src/view/CalMonthView.fxml"), rB);
    }
    public void contactSelected() throws MalformedURLException {
        contactListViewController.initialize(new URL("file:src/view/ContactListView.fxml"), rB);
    }
    public void reportSelected() throws MalformedURLException {
        reportsViewController.initialize(new URL("file:src/view/ReportsView.fxml"), rB);
    }

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        rB = resourceBundle;
        Main.updateLists();
    }
}
