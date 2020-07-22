package BLTonThyme.model;

import java.sql.Timestamp;

public class Contact implements Comparable<Contact> {

    /* Instance Variables*/
    private int contactID, phone;
    private String firstName, lastName, address1, address2;
    private CityState cityState;
    private Timestamp updated;

    /* Constructor */
    public Contact(int contactID, String firstName, String lastName, int phone, String address1, String address2, CityState cityState, Timestamp updated) {
        this.contactID = contactID;
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address1 = address1;
        this.address2 = address2;
        this.cityState = cityState;
        this.updated = updated;
    }
    /* Accessors & Mutators */
    public int getContactID() {
        return contactID;
    }
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public int getPhone() {
        return phone;
    }
    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getAddress1() {
        return address1;
    }
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public CityState getCityState() {
        return cityState;
    }
    public void setCityState(CityState cityState) {
        this.cityState = cityState;
    }

    public String getCity() {
        return cityState.getCity();
    }
    public String getState() {
        return cityState.getState();
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
        if (obj.getClass().equals(Contact.class)) {
            return this.contactID == ((Contact) obj).getContactID();
        }
        return false;
    }
    @Override
    public int compareTo(Contact contact) {
        if (contact == null) {
            throw new NullPointerException();
        }
        return this.toString().compareTo(contact.toString());
    }
    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
