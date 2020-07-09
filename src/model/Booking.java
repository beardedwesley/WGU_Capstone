package model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Booking {

    /* Instance Variables */
    private int bookID;
    private Contact contact;
    private String title, description;
    private Type type;
    private Timestamp start, end, updated;

    private DateTimeFormatter sDTF = DateTimeFormatter.ISO_DATE_TIME;

    /* Constructor */
    public Booking(int bookID, Contact contact, String title, String description, Type type, Timestamp start, Timestamp end, Timestamp updated) {
        this.bookID = bookID;
        this.contact = contact;
        this.title = title;
        this.description = description;
        this.type = type;
        this.start = start;
        this.end = end;
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

    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }

    public Timestamp getStart() {
        return start;
    }
    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }
    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public Timestamp getUpdated() {
        return updated;
    }
    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public LocalDate getStartDate() {
        return start.toLocalDateTime().toLocalDate();
    }
    public LocalTime getStartTime() {
        return start.toLocalDateTime().toLocalTime();
    }

    public String getDetails() {
        return new StringBuilder().append(title).append("\n").append(sDTF.format(start.toInstant())).toString();
    }

    /* Overrides */
    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().equals(Booking.class)) {
            if (this.bookID == ((Booking) obj).getBookID()) {
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString() {
        return title + sDTF.format(start.toInstant()) + sDTF.format(end.toInstant());
    }
}
