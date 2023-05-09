package com.estore.api.estoreapi.model.Profiles;

import java.util.logging.Logger;

import com.estore.api.estoreapi.model.Appointments.Appointment;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a Profile entity
 * 
 * @author kmc1191 (add your username to this list if you happen to work on this file.)
 */
public class Profile {

    private static final Logger LOG = Logger.getLogger(Profile.class.getName());

    //Unsure if public keyword should be there but test for toString wont work otherwise
    public static final String STRING_FORMAT = "Profile [name=%s, username=%s, password=%s, email=%s, phone=%s";

    @JsonProperty("name") private String name; 
    @JsonProperty("username") private String username;
    @JsonProperty("password") private String password;
    @JsonProperty("email") private String email;
    @JsonProperty("phone") private String phone;
    @JsonProperty("isOwner") private boolean isOwner;

    @JsonProperty("appointment") private Appointment appointment;

    /**
     * Create a product with the given name, price, and quantity
     * 
     * @param id       The id of the product
     * @param name     The name of the product
     * @param price    The initial price of the product
     * @param quantity The initial quantity IN STOCK for this product
     */
    public Profile(@JsonProperty("name") String name, @JsonProperty("username") String username, 
    @JsonProperty("password") String password, @JsonProperty("email") String email, 
    @JsonProperty("phone") String phone, @JsonProperty("isOwner") boolean isOwner) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.isOwner = isOwner;
    }


    /**
     * Retrieves the name of the profile
     * 
     * @return The name of the profile
     */
    public String getName() {return this.name;}

    /**
     * Retrieves the username of the profile
     * 
     * @return The username of the profile
     */
    public String getUsername() {return this.username;}

    /**
     * Retrieves the password of the profile
     * 
     * @return The password of the profile
     */
    public String getPassword() {return this.password;}

    /**
     * Retrieves the email of the profile
     * 
     * @return The email of the profile
     */
    public String getEmail() {return this.email;}

    /** 
     * Retrieves the phone number of the profile
     * 
     * @return The phone number of the profile
     */
    public String getPhone() {return this.phone;}

    /**
     * Retrieves whether the current user logged in is the owner
     * 
     * @return boolean indicating if its the owner or not
     */
    public boolean getIsOwner() {
        return this.isOwner;
    }

    /**
     * Retrieves the current apppointment reserved for this user
     * 
     * @return the booked appointment for the user
     */
    public Appointment getAppointment(){
        return this.appointment;
    }

    /**
     * Sets the name of the profile
     * 
     * @param name to change to
     */
    public void setName(String name) {this.name = name;}

    /**
     * Sets the username of the profile
     * 
     * @param username to change to
     */
    public void setUsername(String username) {this.username = username;}

    /**
     * Sets the password of the profile
     * 
     * @param password to change to
     */
    public void setPassword(String password) {this.password = password;}

    /**
     * Sets the email of the profile
     * 
     * @param email email to change to 
     */
    public void setEmail(String email) {this.email = email;}

    /**
     * Sets the phone number of the profile
     * 
     * @param phone to change to
     */
    public void setPhone(String phone) {this.phone = phone;}

    /**
     * Sets the status of the owner user being logged in
     * 
     * @param ifOwner if the current user is the owner or not
     */
    public void setOwner(boolean ifOwner) { isOwner = ifOwner; }

    /**
     * Sets the appointment the user has booked 
     * 
     * @param appointment the appointment associated with the user
     */
    public void setAppointment(Appointment appointment) { this.appointment = appointment; }

    /**
     * Generates string for a profile
     * 
     * @return String representation of the profile object
     */
    public String toString(){
        return String.format(STRING_FORMAT, name, username, password, email, phone, isOwner);
    }

    
}
