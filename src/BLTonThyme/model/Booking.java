package BLTonThyme.model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Booking implements Comparable<Booking> {

    /* Instance Variables */
    private int bookID;
    private Contact contact;
    private String title, description;
    private TypeID type;
    private Timestamp startTime, endTime, updated;

    /* Constructor */
    public Booking(int bookID, Contact contact, String title, String description, Timestamp startTime, Timestamp endTime, Timestamp updated) {
        this.bookID = bookID;
        this.contact = contact;
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.updated = updated;
    }

    /* Accessors & Mutators */
    public int getBookID() {
        return bookID;
    }
    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public Contact getContact() {
        return contact;
    }
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getStart() {
        return startTime;
    }
    public void setStart(Timestamp start) {
        this.startTime = start;
    }

    public Timestamp getEnd() {
        return endTime;
    }
    public void setEnd(Timestamp end) {
        this.endTime = end;
    }

    public Timestamp getUpdated() {
        return updated;
    }
    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public LocalDate getStartDate() {
        return startTime.toLocalDateTime().toLocalDate();
    }
    public LocalTime getStartTime() {
        return startTime.toLocalDateTime().toLocalTime();
    }
    public LocalTime getEndTime() {
        return endTime.toLocalDateTime().toLocalTime();
    }
    public String getTime() {
        return getStartTime().toString() + "\n" + getEndTime().toString();
    }

    public abstract TypeID getType();
    public abstract String getDetails();

    /* Overrides */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass().equals(Booking.class)) {
            return this.bookID == ((Booking) obj).getBookID();
        }
        return false;
    }
    @Override
    public int compareTo(Booking booking) {
        if (booking == null) {
            throw new NullPointerException();
        }
        return this.startTime.compareTo(booking.getStart());
    }
    @Override
    public String toString() {
        return title + "\nStart: " + startTime.toLocalDateTime() + "\nEnd: " + endTime.toLocalDateTime();
    }
}
