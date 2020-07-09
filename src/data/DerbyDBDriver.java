package data;

import model.Booking;
import model.CityState;
import model.Contact;
import model.Type;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class DerbyDBDriver {
    private static Connection dbConn;
    private static String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private static String framework = "embedded";
    private static String protocol = "jdbc:derby:";
    private static String dbName = "theaterDB";
    private static boolean dbExists = false;

    static {
        try {
            if (dbExists) {
                dbConn = DriverManager.getConnection(protocol + dbName + ";create=true");
                genTables(dbConn);
                dbExists = true;
            } else {
                dbConn = DriverManager.getConnection(protocol + dbName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void close() {
        try {
            DriverManager.getConnection(protocol + ";shutdown=true");
        } catch (SQLException e) {
            if ((e.getErrorCode() == 4500) && (e.getSQLState().equals("08006"))) {
                System.out.println("Derby shut down as expected");
            } else {
                e.printStackTrace();
            }
        }
    }

    private static void genTables (Connection dbConn) throws SQLException {
        File file = new File("createTables.sql");
        executeScript(file);
    }

    /* Helper functions */
    private static boolean executeScript(File file) throws SQLException {
        BufferedReader bis = null;
        String line = null;
        try {
            bis = new BufferedReader(new FileReader(file));

            Statement statement = dbConn.createStatement();
            line = bis.readLine();
            while (line != null) {
                statement.execute(line);
                line = bis.readLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                return true;
            }
        }
    }
    private static ResultSet executeQuery(String[] args) {
        ResultSet rs = null;
        try {
            PreparedStatement statement = dbConn.prepareStatement(args[0]);
            if (args.length > 1) {
                for (int i = 1; i <= args.length; i++) {
                    statement.setString(i, args[i]);
                }
            }
            rs = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
          return rs;
        }
    }

    private static ArrayList<Booking> bookingsFromData(ResultSet rs) throws SQLException {
        ArrayList<Booking> bookings = new ArrayList<>();
        while (rs.next()) {
            Booking booking = new Booking(
                    rs.getInt(1),
                    contactFromID(rs.getInt(2)),
                    rs.getString(3),
                    rs.getString(4),
                    typeFromID(rs.getInt(5)),
                    rs.getTimestamp(6),
                    rs.getTimestamp(7),
                    rs.getTimestamp(8)
            );
            bookings.add(booking);
        }

        return bookings;
    }
    private static ArrayList<Contact> contactsFromData(ResultSet rs) throws SQLException {
        ArrayList<Contact> contacts = new ArrayList<>();
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
    private static ArrayList<Type> typesFromData(ResultSet rs) throws SQLException {
        ArrayList<Type> types = new ArrayList<>();
        while (rs.next()) {
            Type type = new Type(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getTimestamp(3)
            );
            types.add(type);
        }

        return types;
    }
    private static ArrayList<CityState> cityStatesFromData(ResultSet rs) throws SQLException {
        ArrayList<CityState> cityStates = new ArrayList<>();
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
        ResultSet rs = executeQuery(query);
        if (rs.next()) {
            booking = new Booking(
                    rs.getInt(1),
                    contactFromID(rs.getInt(2)),
                    rs.getString(3),
                    rs.getString(4),
                    typeFromID(rs.getInt(5)),
                    rs.getTimestamp(6),
                    rs.getTimestamp(7),
                    rs.getTimestamp(8)
            );
        }
        return booking;
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
    private static Type typeFromID(int typeID) throws SQLException {
        Type type = null;
        String sql = "SELECT * FROM type WHERE typeid = ?";
        String[] query = {sql, ((Integer) typeID).toString()};
        ResultSet rs = executeQuery(query);
        if (rs.next()) {
            type = new Type(
                rs.getInt(1),
                rs.getString(2),
                rs.getTimestamp(3)
            );
        }
        return type;
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
    public static ArrayList<Booking> getAllBookings() throws SQLException {
        String sql = "select * from booking order by start";
        String[] query = {sql};
        return bookingsFromData(executeQuery(query));
    }
    public static Booking addBooking(Contact contact, String title, String description, Type type, Timestamp start, Timestamp end) {
        Booking booking = null;
        String sql = "select next value for seq_bookid";
        String[] query = {sql};
        ResultSet rs = executeQuery(query);

        sql = "insert into booking values (?, ?, ?, ?, ?, ?, ?, current_timestamp)";
        try {
            rs.next();
            query = new String[]{sql, ((Integer) rs.getInt(1)).toString(), ((Integer) contact.getContactID()).toString(), title, description, ((Integer) type.getTypeID()).toString(), start.toString(), end.toString()};
            booking = bookingsFromData(executeQuery(query)).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booking;
    }
    public static void updateBooking(Booking booking) {
        String sql = "update booking set contactid = ?, title = ?, description = ?, typeid = ?, start = ?, end = ?, updated = current_timestamp where bookid = ?";
        String[] query = {
                sql,
                ((Integer) booking.getContact().getContactID()).toString(),
                booking.getTitle(),
                booking.getDescription(),
                ((Integer) booking.getType().getTypeID()).toString(),
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

    public static ArrayList<Contact> getAllContacts() throws SQLException {
        String sql = "select * from contact order by firstname, lastname";
        String[] query = {sql};
        return contactsFromData(executeQuery(query));
    }
    public static Contact addContact(String firstName, String lastName, int phone, String address1, String address2, CityState cityState) {
        Contact contact = null;
        String sql = "select next value for seq_contactid";
        String[] query = {sql};
        ResultSet rs = executeQuery(query);

        sql = "insert into contact values (?, ?, ?, ?, ?, ?, ?, current_timestamp)";
        try {
            rs.next();
            query = new String[]{sql, ((Integer) rs.getInt(1)).toString(), firstName, lastName, ((Integer) phone).toString(), address1, address2, ((Integer) cityState.getCsID()).toString()};
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

    public static ArrayList<Type> getAllTypes() throws SQLException {
        String sql = "select * from type order by name";
        String[] query = {sql};
        return typesFromData(executeQuery(query));
    }
    public static Type addType(String name) {
        Type type = null;
        String sql = "select next value for seq_typeid";
        String[] query = {sql};
        ResultSet rs = executeQuery(query);

        sql = "insert into type values (?, ?, current_timestamp)";
        try {
            rs.next();
            query = new String[]{sql, ((Integer) rs.getInt(1)).toString(), name};
            type = typesFromData(executeQuery(query)).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return type;
    }
    public static void updateType(Type type) {
        String sql = "update type set name = ?, updated = current_timestamp where typeid = ?";
        String[] query = {
                sql,
                type.getName(),
                ((Integer) type.getTypeID()).toString()
        };
        executeQuery(query);
    }
    public static void deleteType(Type type) {
        String sql = "delete from booking where typeid = ?";
        String[] query = {sql, ((Integer) type.getTypeID()).toString()};
        executeQuery(query);

        sql = "delete from type where typeid = ?";
        query = new String[]{sql, ((Integer) type.getTypeID()).toString()};
        executeQuery(query);
    }

    public static ArrayList<CityState> getAllCityStates() throws SQLException {
        String sql = "select * from citystate order by city, state";
        String[] query = {sql};
        return cityStatesFromData(executeQuery(query));
    }
    public static CityState addCityState(String city, String state, int zipcode) throws SQLException {
        CityState cityState = null;
        String sql = "select next value for seq_csid";
        String[] query = {sql};
        ResultSet rs = executeQuery(query);

        sql = "insert into citystate values (?, ?, ?, ?, current_timestamp)";
        try {
            rs.next();
            query = new String[]{sql, ((Integer) rs.getInt(1)).toString(), city, state, ((Integer) zipcode).toString()};
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
        String sql = "select * from citystate where city = ?, state = ?, zipcode = ?";
        String[] query = {sql, city, state, ((Integer) zipcode).toString()};
        try {
            cityState = cityStatesFromData(executeQuery(query)).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cityState;
    }

}
