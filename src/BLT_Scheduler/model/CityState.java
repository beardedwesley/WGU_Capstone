package BLT_Scheduler.model;

import java.sql.Timestamp;

public class CityState {

    /* Instance Variables*/
    private int csID, zipcode;
    private String city, state;
    private Timestamp updated;

    /* Constructor */
    public CityState(int csID, String city, String state, int zipcode, Timestamp updated) {
        this.csID = csID;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.updated = updated;
    }

    /* Accessors & Mutators */
    public int getCsID() {
        return csID;
    }
    public void setCsID(int csID) {
        this.csID = csID;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public int getZipcode() {
        return zipcode;
    }
    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
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
        if (obj == null) {
            return false;
        }
        if (obj.getClass().equals(CityState.class)) {
            return this.csID == ((CityState) obj).getCsID();
        }
        return false;
    }
    @Override
    public String toString() {
        return city + ", " + state + " " + ((Integer) zipcode).toString();
    }
}
