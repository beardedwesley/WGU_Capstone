package BLTonThyme;

import BLTonThyme.data.DerbyDBDriver;
import BLTonThyme.model.TypeID;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import BLTonThyme.model.Booking;
import BLTonThyme.model.Contact;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main extends Application {
    public static Booking selectedBooking;
    public static Contact selectedContact;
    private static ObservableList<Booking> bookingsListAll = FXCollections.observableArrayList();
    private static ObservableList<Contact> contactListAll = FXCollections.observableArrayList();
    private static final ObservableList<TypeID> typeListAll = FXCollections.observableArrayList();

    public static ObservableList<Booking> getAllBookings() {
        if (bookingsListAll.isEmpty()) {
            try {
                bookingsListAll = FXCollections.observableArrayList(DerbyDBDriver.getAllBookings());
                FXCollections.sort(bookingsListAll);
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
        FXCollections.sort(dayList);
        return dayList;
    }
    public static ObservableList<Booking> getTypeBookings(TypeID type) {
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
    public static ObservableList<TypeID> getAllTypes() {
        if (typeListAll.isEmpty()) {
            typeListAll.add(TypeID.PERFORMANCE);
            typeListAll.add(TypeID.REHEARSAL);
            typeListAll.add(TypeID.PRIVATE_EVENT);
            typeListAll.add(TypeID.PUBLIC_EVENT);
            typeListAll.add(TypeID.STUDENT_GROUP);
        }
        return typeListAll;
    }

    public static void addBooking(Booking booking) {
        bookingsListAll.add(booking);
        FXCollections.sort(bookingsListAll);
    }
    public static void deleteBooking(Booking booking) {
        bookingsListAll.remove(booking);
    }
    public static void addContact(Contact contact) {
        contactListAll.add(contact);
        FXCollections.sort(contactListAll);
    }
    public static void deleteContact(Contact contact) {
        contactListAll.remove(contact);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Theater Schedule");
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/BLTonThyme/view/MainView.fxml"))));
        primaryStage.setOnCloseRequest(windowEvent -> DerbyDBDriver.close());
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
