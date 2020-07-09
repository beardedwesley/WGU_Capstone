package model;

import java.sql.Timestamp;

public class Type {

    /* Instance Variables */
    private int typeID;
    private String name;
    private Timestamp updated;

    /* Constructors */
    public Type(int typeID, String name, Timestamp updated) {
        this.typeID = typeID;
        this.name = name;
        this.updated = updated;
    }

    /* Accessors & Mutators */
    public int getTypeID() {
        return typeID;
    }
    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getUpdated() {
        return updated;
    }
    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    /* Overrides */
    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().equals(Type.class)) {
            if (this.typeID == ((Type) obj).getTypeID()) {
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString() {
        return name;
    }
}
