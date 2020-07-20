package BLT_Scheduler.data;

import BLT_Scheduler.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class DerbyDBDriver {
    private static Connection dbConn = null;
    private final static String protocol = "jdbc:derby:";
    private final static String dbName = "theaterDB";

    private static Connection getDbConn() {
        if (dbConn == null) {
            try {
                dbConn = DriverManager.getConnection(protocol+dbName);
            } catch (SQLException noDB) {
                try {
                    dbConn = DriverManager.getConnection(protocol + dbName + ";create=true");
                    genTables(dbConn);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        /*
        try {
            if (!dbExists) {
                dbConn = DriverManager.getConnection(protocol + dbName + ";create=true");
                genTables(dbConn);
                dbExists = true;
            } else {
                dbConn = DriverManager.getConnection(protocol + dbName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        */
        return dbConn;
    }
    public static void close() {
        try {
            DriverManager.getConnection(protocol + ";shutdown=true");
        } catch (SQLException e) {
            System.out.println("TheaterDB shut down successfully.");
        }
    }

    private static void genTables (Connection dbConn) throws SQLException {
        File file = new File("src/BLT_Scheduler/data/createTables.txt");
        executeScript(file);
    }

    /* Helper functions */
    private static boolean executeScript(File file) {
        Statement statement = null;
        BufferedReader bis = null;
        String line = null;
        try {
            bis = new BufferedReader(new FileReader(file));

            statement = getDbConn().createStatement();
            line = bis.readLine();
            while (line != null) {
                statement.execute(line);
                statement = getDbConn().createStatement();
                line = bis.readLine();
            }

            if (bis != null) {
                bis.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    private static ResultSet executeQuery(String[] args) {
        ResultSet rs = null;
        try {
            PreparedStatement statement = getDbConn().prepareStatement(args[0]);
            if (args.length > 1) {
                for (int i = 1; i < args.length; i++) {
                    statement.setString(i, args[i]);
                }
            }
            if (statement.execute()) {
                rs = statement.getResultSet();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    private static ObservableList<Booking> bookingsFromData(ResultSet rs) throws SQLException {
        ObservableList<Booking> bookings = FXCollections.observableArrayList();
        while (rs.next()) {
            Booking booking = null;
            switch (rs.getInt(5)) {
                case 1:
                    booking = new Performance(
                            rs.getInt(1),
                            contactFromID(rs.getInt(2)),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getTimestamp(6),
                            rs.getTimestamp(7),
                            rs.getTimestamp(8));
                    break;
                case 2:
                    booking = new Rehearsal(
                            rs.getInt(1),
                            contactFromID(rs.getInt(2)),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getTimestamp(6),
                            rs.getTimestamp(7),
                            rs.getTimestamp(8));
                    break;
                case 3:
                    booking = new PrivateEvent(
                            rs.getInt(1),
                            contactFromID(rs.getInt(2)),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getTimestamp(6),
                            rs.getTimestamp(7),
                            rs.getTimestamp(8));
                    break;
                case 4:
                    booking = new PublicEvent(
                            rs.getInt(1),
                            contactFromID(rs.getInt(2)),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getTimestamp(6),
                            rs.getTimestamp(7),
                            rs.getTimestamp(8));
                    break;
                case 5:
                    booking = new StudentGroup(
                            rs.getInt(1),
                            contactFromID(rs.getInt(2)),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getTimestamp(6),
                            rs.getTimestamp(7),
                            rs.getTimestamp(8));
                    break;
                default:
                    //error, null pointer will be added to list
                    System.out.println("Error adding Booking data to list of Booking objects.");

            }
            bookings.add(booking);
        }

        return bookings;
    }
    private static ObservableList<Contact> contactsFromData(ResultSet rs) throws SQLException {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        while (rs.next()) {
            Contact contact = new Contact(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(4),
                    rs.getString(5),
                    rs.getString(6),
                    csFromID(rs.getInt(7)),
                    rs.getTimestamp(8)
            );
            contacts.add(contact);
        }

        return contacts;
    }
    private static ObservableList<CityState> cityStatesFromData(ResultSet rs) throws SQLException {
        ObservableList<CityState> cityStates = FXCollections.observableArrayList();
        while (rs.next()) {
            CityState cityState = new CityState(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getInt(4),
                    rs.getTimestamp(5)
            );
            cityStates.add(cityState);
        }

        return cityStates;
    }

    private static Booking bookingFromID(int bookID) throws SQLException {
        Booking booking = null;
        String sql = "SELECT * FROM booking WHERE bookid = ?";
        String[] query = {sql, ((Integer) bookID).toString()};
        ObservableList<Booking> bookings = bookingsFromData(executeQuery(query));

        return  bookings.get(0);
    }
    private static Contact contactFromID(int contactID) throws SQLException {
        Contact contact = null;
        String sql = "SELECT * FROM contact WHERE contactid = ?";
        String[] query = {sql, ((Integer) contactID).toString()};
        ResultSet rs = executeQuery(query);
        if (rs.next()) {
            contact = new Contact(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getInt(4),
                rs.getString(5),
                rs.getString(6),
                csFromID(rs.getInt(7)),
                rs.getTimestamp(8)
            );
        }
        return contact;
    }
    private static CityState csFromID(int csID) throws SQLException {
        CityState cityState = null;
        String sql = "SELECT * FROM citystate WHERE csid = ?";
        String[] query = {sql, ((Integer) csID).toString()};
        ResultSet rs = executeQuery(query);
        if (rs.next()) {
            cityState = new CityState(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getInt(4),
                rs.getTimestamp(5)
            );
        }
        return cityState;
    }

    /* Data calls */
    public static ObservableList<Booking> getAllBookings() throws SQLException {
        String sql = "select * from booking order by starttime";
        String[] query = {sql};
        return bookingsFromData(executeQuery(query));
    }
    public static Booking addBooking(Contact contact, String title, String description, TypeID type, Timestamp starttime, Timestamp endtime) {
        Booking booking = null;
        int bookID;

        //Get the next Booking ID
        String sql = "values(next value for seq_bookid)";
        String[] query = {sql};
        ResultSet rs = executeQuery(query);
        try {
            rs.next();
            bookID = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        //Insert the new Booking
        sql = "insert into booking (bookid, contactid, title, description, typeid, starttime, endtime, updated) values (?, ?, ?, ?, ?, ?, ?, current_timestamp)";
        query = new String[]{sql, ((Integer) bookID).toString(), ((Integer) contact.getContactID()).toString(), title, description, ((Integer) type.getID()).toString(), starttime.toString(), endtime.toString()};
        executeQuery(query);

        //Pull the new Booking
        sql = "select * from booking where bookid = ?";
        query = new String[]{sql, ((Integer) bookID).toString()};
        try {
            booking = bookingsFromData(executeQuery(query)).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booking;
    }
    public static void updateBooking(Booking booking) {
        String sql = "update booking set contactid = ?, title = ?, description = ?, typeid = ?, starttime = ?, endtime = ?, updated = current_timestamp where bookid = ?";
        String[] query = {
                sql,
                ((Integer) booking.getContact().getContactID()).toString(),
                booking.getTitle(),
                booking.getDescription(),
                ((Integer) booking.getType().getID()).toString(),
                booking.getStart().toString(),
                booking.getEnd().toString(),
                ((Integer) booking.getBookID()).toString()
        };
        executeQuery(query);
    }
    public static void deleteBooking(Booking booking) {
        String sql = "delete from booking where bookid = ?";
        String[] query = {sql, ((Integer) booking.getBookID()).toString()};
        executeQuery(query);
    }

    public static ObservableList<Contact> getAllContacts() throws SQLException {
        String sql = "select * from contact order by firstname, lastname";
        String[] query = {sql};
        return contactsFromData(executeQuery(query));
    }
    public static Contact addContact(String firstName, String lastName, int phone, String address1, String address2, CityState cityState) {
        Contact contact = null;
        int contactID;

        //Get the next Contact ID
        String sql = "values(next value for seq_contactid)";
        String[] query = {sql};
        ResultSet rs = executeQuery(query);
        try {
            rs.next();
            contactID = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        //Insert the new Contact
        sql = "insert into contact (contactid, firstname, lastname, phone, address1, address2, cityid, updated) values (?, ?, ?, ?, ?, ?, ?, current_timestamp)";
        query = new String[]{sql, ((Integer) contactID).toString(), firstName, lastName, ((Integer) phone).toString(), address1, address2, ((Integer) cityState.getCsID()).toString()};
        executeQuery(query);

        //Pull the new Contact
        sql = "select * from contact where contactid = ?";
        query = new String[]{sql, ((Integer) contactID).toString()};
        try {
            contact = contactsFromData(executeQuery(query)).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contact;
    }
    public static void updateContact(Contact contact) {
        String sql = "update contact set firstname = ?, lastname = ?, phone = ?, address1 = ?, address2 = ?, cityid = ?, updated = current_timestamp where contactid = ?";
        String[] query = {
                sql,
                contact.getFirstName(),
                contact.getLastName(),
                ((Integer) contact.getPhone()).toString(),
                contact.getAddress1(),
                contact.getAddress2(),
                ((Integer) contact.getCityState().getCsID()).toString(),
                ((Integer) contact.getContactID()).toString()
        };
        executeQuery(query);
    }
    public static void deleteContact(Contact contact) {
        String sql = "delete from booking where contactid = ?";
        String[] query = {sql, ((Integer) contact.getContactID()).toString()};
        executeQuery(query);

        sql = "delete from contact where contactid = ?";
        query = new String[]{sql, ((Integer) contact.getContactID()).toString()};
        executeQuery(query);
    }

    public static ObservableList<CityState> getAllCityStates() throws SQLException {
        String sql = "select * from citystate order by city, state";
        String[] query = {sql};
        return cityStatesFromData(executeQuery(query));
    }
    public static CityState addCityState(String city, String state, int zipcode) throws SQLException {
        CityState cityState = null;
        int csID;

        //Get the next CityState ID
        String sql = "values(next value for seq_csid)";
        String[] query = {sql};
        ResultSet rs = executeQuery(query);
        try {
            rs.next();
            csID = rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        //Insert the new CityState
        sql = "insert into citystate (csid, city, state, zipcode, updated) values (?, ?, ?, ?, current_timestamp)";
        query = new String[]{sql, ((Integer) csID).toString(), city, state, ((Integer) zipcode).toString()};
        executeQuery(query);

        //Pull the new CityState
        sql = "select * from citystate where csid = ?";
        query = new String[]{sql, ((Integer) csID).toString()};
        try {
            cityState = cityStatesFromData(executeQuery(query)).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cityState;
    }
    public static void updateCityState(CityState cityState) {
        String sql = "update citystate set city = ?, state = ?, updated = current_timestamp where csid = ?";
        String[] query = {
                sql,
                cityState.getCity(),
                cityState.getState(),
                ((Integer) cityState.getCsID()).toString()
        };
        executeQuery(query);
    }
    public static void deleteCityState(CityState cityState) {
        String sql = "delete from booking where contactid in(select contactid from contact where csid = ?)";
        String[] query = {sql, ((Integer) cityState.getCsID()).toString()};
        executeQuery(query);

        sql = "delete from contact where csid = ?";
        query = new String[]{sql, ((Integer) cityState.getCsID()).toString()};
        executeQuery(query);

        sql = "delete from citystate where csid = ?";
        query = new String[]{sql, ((Integer) cityState.getCsID()).toString()};
        executeQuery(query);
    }
    public static CityState findCityState(String city, String state, int zipcode) {
        CityState cityState = null;
        String sql = "select * from citystate where city = ? and state = ? and zipcode = ?";
        String[] query = {sql, city, state, ((Integer) zipcode).toString()};
        try {
            cityState = cityStatesFromData(executeQuery(query)).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException i) {
            return null;
        }
        return cityState;
    }

}
