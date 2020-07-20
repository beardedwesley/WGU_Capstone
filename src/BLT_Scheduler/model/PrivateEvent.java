package BLT_Scheduler.model;

import java.sql.Timestamp;

public class PrivateEvent extends Booking{
    private final TypeID type = TypeID.PRIVATE_EVENT;

    public PrivateEvent(int bookID, Contact contact, String title, String description, Timestamp startTime, Timestamp endTime, Timestamp updated) {
        super(bookID, contact, title, description, startTime, endTime, updated);
    }

    @Override
    public TypeID getType() {
        return type;
    }

    @Override
    public String getDetails() {
        return new StringBuilder().append(super.getTitle()).append("\n").append(type).append("\n").append(super.getStartDate()).append("\n").append(super.getStartTime()).append(" - ").append(super.getEndTime()).toString();
    }
}
