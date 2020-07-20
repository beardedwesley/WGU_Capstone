package BLT_Scheduler.controller;

import BLT_Scheduler.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    private URL location;
    private ResourceBundle rB;

    @FXML
    private HomeViewController homeViewController;
    @FXML
    private CalWeekViewController calWeekViewController;
    @FXML
    private CalMonthViewController calMonthViewController;
    @FXML
    private ContactListViewController contactListViewController;
    @FXML
    private ReportsViewController reportsViewController;

    @FXML
    public void homeSelected() {
        homeViewController.initialize(location, rB);
    }
    @FXML
    public void weekSelected() {
        calWeekViewController.initialize(location, rB);
    }
    @FXML
    public void monthSelected() {
        calMonthViewController.initialize(location, rB);
    }
    @FXML
    public void contactSelected() {
        contactListViewController.initialize(location, rB);
    }
    @FXML
    public void reportSelected() {
        reportsViewController.initialize(location, rB);
    }

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        this.location = location;
        this.rB = resourceBundle;
    }
}
