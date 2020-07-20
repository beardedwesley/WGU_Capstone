package BLT_Scheduler.model;

public enum TypeID {
    PERFORMANCE (1, "Performance"),
    REHEARSAL (2, "Rehearsal"),
    PRIVATE_EVENT (3, "Private event"),
    PUBLIC_EVENT (4, "Public event"),
    STUDENT_GROUP (5, "Student group");

    private final int id;
    private final String type;

    TypeID(int i, String t) {
        this.id = i;
        this.type = t;
    }

    public int getID() {
        return this.id;
    }
    @Override
    public String toString() {
        return this.type;
    }
}
