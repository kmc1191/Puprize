package com.estore.api.estoreapi.model.Appointments;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents an appointment entity
 * 
 * @author hab1466 (add your username to this list if you happen to work on this file.)
 */
public class Appointment {

    private static final Logger LOG = Logger.getLogger(Appointment.class.getName());

    public static final String STRING_FORMAT = "Appointment [id=%d, date=%s, time=%s, user=%s, groomer=%s";


    @JsonProperty("id") private int id;
    @JsonProperty("date") private String date; 
    @JsonProperty("time") private String time;
    @JsonProperty("user") private String user;
    @JsonProperty("groomer") private String groomer;
    @JsonProperty("reserved") private boolean reserved;

    /**
     * Create an appointment with the given id, date, time, user, groomer, and reseration status
     * 
     * @param id        The id of the appointment
     * @param date      The date of the appointment
     * @param time      The time of the appointment
     * @param user      The name of the user who reserved the appointment
     * @param groomer   The name of the groomer assigned to the appointment
     * @param reserved  The reservation status of the appointment
     */
    public Appointment(@JsonProperty("id") int id, @JsonProperty("date") String date, 
    @JsonProperty("time") String time, @JsonProperty("user") String user, @JsonProperty("groomer") String groomer, 
    @JsonProperty("reserved") boolean reserved) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.user = user;
        this.groomer = groomer;
        this.reserved = reserved;
    }

    /**
     * Retrieves the id of the appointment
     * 
     * @return The id of the product
     */
    public int getId() {return id;}

    /**
     * Retrieves the date of the appointment
     * 
     * @return The date of the appointment
     */
    public String getDate() {return date;}

    /**
     * Sets the date of the appointment
     * Should only be used in loading the .json
     * @param date The new date for the appointment
     */
    public void setDate(String date) {this.date = date;}

    /**
     * Retrieves the time of the appointment
     * @return The time of the appointment
     */
    public String getTime() {return time;}

    /**
     * Sets the time of the appointment
     * Should only be used in loading the .json
     * @param time The new time for the appointment
     */
    public void setTime(String time) {this.time = time;}


    /**
     * Retrieves the user who reserved the appointment
     * @return The user who reserved the appointment
     */
    public String getUser() {return user;}

    /**
     * Sets the user who is reserving the appointment
     * @param user The user who is reserving the appointment
     */
    public void setUser(String user) {this.user = user;}

    /**
     * Retrieves the groomer who is assigned to the appointment
     * @return The groomer who is assigned to the appointment
     */
    public String getGroomer() {return groomer;}

    /**
     * Assigns the groomer to an appointment
     * This operation should only be available to the owner
     * @param user The user who is reserving the appointment
     */
    public void setGroomer(String groomer) {this.groomer = groomer;}

    /**
     * Returns a boolean for the appointment's reservation status
     * @return The reservation status
     */
    public boolean getReserved() {return reserved;}

    /**
     * Sets the reservation status for the appointment
     * @param reserved A boolean for the new reservation status
     */
    public void setReserved(boolean reserved) {this.reserved = reserved;}

    /**
     * toString method for cURL purposes
     */
    public String toString(){
        return String.format(STRING_FORMAT, id, date, time, user, groomer, reserved);
    }
}