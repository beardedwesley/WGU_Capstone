package model;

import data.DerbyDBDriver;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main extends Application {
    public static Booking selected;
    private static ObservableList<Booking> bookingsListAll = FXCollections.observableArrayList();
    private static ObservableList<Contact> contactListAll = FXCollections.observableArrayList();
    private static ObservableList<Type> typeListAll = FXCollections.observableArrayList();

    public static ObservableList<Booking> getAllBookings() {
        if (bookingsListAll.isEmpty()) {
            try {
                bookingsListAll = FXCollections.observableArrayList(DerbyDBDriver.getAllBookings());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return bookingsListAll;
    }
    public static ObservableList<Booking> getDayBookings(LocalDate date) {
        ObservableList<Booking> dayList = FXCollections.observableArrayList();
        for (Booking booking : getAllBookings()) {
            if (booking.getStart().toLocalDateTime().toLocalDate().equals(date)) {
                dayList.add(booking);
            }
        }
        return dayList;
    }
    public static ObservableList<Booking> getTypeBookings(Type type) {
        ObservableList<Booking> typeList = FXCollections.observableArrayList();
        for (Booking booking : getAllBookings()) {
            if (booking.getType().equals(type)) {
                typeList.add(booking);
            }
        }
        return typeList;
    }
    public static ObservableList<Booking> getContBookings(Contact contact) {
        ObservableList<Booking> contList = FXCollections.observableArrayList();
        for (Booking booking : getAllBookings()) {
            if (booking.getContact().equals(contact)) {
                contList.add(booking);
            }
        }
        return contList;
    }
    public static ObservableList<Contact> getAllContacts() {
        if (contactListAll.isEmpty()) {
            try {
                contactListAll = FXCollections.observableArrayList(DerbyDBDriver.getAllContacts());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return contactListAll;
    }
    public static ObservableList<Type> getAllTypes() {
        if (typeListAll.isEmpty()) {
            try {
                typeListAll = FXCollections.observableArrayList(DerbyDBDriver.getAllTypes());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return typeListAll;
    }

    public static void updateLists() {
        try {
            bookingsListAll = FXCollections.observableArrayList(DerbyDBDriver.getAllBookings());
            contactListAll = FXCollections.observableArrayList(DerbyDBDriver.getAllContacts());
            typeListAll = FXCollections.observableArrayList(DerbyDBDriver.getAllTypes());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static Parent root;
    private static Scene mainScene;
    private static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainStage = primaryStage;
        root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
        mainScene = new Scene(root);
        primaryStage.setTitle("Theater Schedule");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void openHome() {
        mainStage.setScene(Main.mainScene);
        mainStage.setTitle("Theater Schedule");
        mainStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
