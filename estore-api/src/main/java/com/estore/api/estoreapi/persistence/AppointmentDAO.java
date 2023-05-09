package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import java.util.ArrayList;

import com.estore.api.estoreapi.model.Appointments.Appointment;

/**
 * Defines the interface for the AppointmentDAO which tracks Appointment object persistence
 * 
 * @author hab1466 phc6515
 */
public interface AppointmentDAO {

    /**
     * Retrieves all {@linkplain Appointment appointments}
     * Will be used for the master calendar and displaying appointments, should decide to display based on reserved or not
     * 
     * @return An array of {@link Appointment appointments}, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Appointment[] getAppointments() throws IOException;

    /**
     * Finds all {@linkplain Appointment appointments} whose date and time equals the given text
     * 
     * @param date The date to match against
     * @param time The time to match against
     * 
     * @return An array of {@link Appointment appointments} whose date and time equals the given text, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Appointment[] findAppointments(String date, String time) throws IOException;

    /**
     * Retrieves the {@linkplain Appointment appointment} with the given id
     * 
     * @param appointmentId The id of the {@link Appointment appointment} to get
     * 
     * @return A {@link Appointment appointment} with the matching id, null if no {@link Appointment appointment} is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Appointment getAppointment(int appointmentId) throws IOException;

    /**
     * Creates and saves a {@linkplain Appointment appointment}
     * 
     * @param appointment {@linkplain Appointment appointment} object to be created and saved
     *
     * The date of the given appointment object is assigned to the new appointment object.
     * The time of the given appointment object is assigned to the new appointment object.
     * The user reserving the given appointment object is assigned to the new appointment object.
     * The groomer assigned to the given appointment object is assigned to the new appointment object.
     * The id of the given appointment object is ignored and a new unique id is assigned.
     *
     * @return new {@link Appointment appointment} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Appointment createAppointment(Appointment appointment) throws IOException;

    /**
     * Updates and saves a {@linkplain Appointment appointment}
     * 
     * @param appointment {@link Appointment appointment} object to be updated and saved
     * 
     * @return updated {@link Appointment appointment} if successful, null if {@link Appointment appointment} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Appointment updateAppointment(Appointment appointment) throws IOException;

    /**
     * Deletes a {@linkplain Appointment appointment} with the given name
     * 
     * @param appointmentId The id of the {@link Appointment appointment}
     * 
     * @return true if the {@link Appointment appointment} was deleted
     * <br>
     * false if appointment with the given name does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deleteAppointment(int appointmentId) throws IOException;

    /**
     * Reopens a {@linkplain Appointment appointment} with the given id to update its visbility to users.
     * 
     * @param appointmentId The id of the {@link Appointment appointment}
     * 
     * @return updated {@link Appointment appointment} if successful, null if {@link Appointment appointment} could not be found
     * 
     * @throws IOException IOException if an issue with underlying storage
     */
    public Appointment reopenAppointment(int appointmentId) throws IOException;

}
