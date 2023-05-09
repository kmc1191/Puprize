package com.estore.api.estoreapi.persistence;
 
import java.io.IOException;
import java.util.ArrayList;

import com.estore.api.estoreapi.model.Appointments.Appointment;
import com.estore.api.estoreapi.model.Profiles.PetProfile;
import com.estore.api.estoreapi.model.Profiles.Profile;
 
 
/**
* Defines the interface for the ProfileList which tracks Profile object persistence
*
* @author kmc1191 (add your name to this list if you happen to work on this file.)
*/
public interface ProfileListDAO {

    /**
     * Retrieves the overall profile list, including all {@linkplain Profile profiles}
     * 
     * @return An ArrayList of {@link Profile profiles}, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    Profile[] getProfiles() throws IOException;


    /**
     * Retrieves the {@linkplain Profile profile} with the given username
     * 
     * @param username The username of the {@link Profile profile} to get
     * 
     * @return A {@link Profile profile} with the matching username, null if no {@link Profile profile} is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    Profile getProfile(String username) throws IOException;

    /**
     * Creates and saves a {@linkplain Profile profile}
     * 
     * @param profile {@linkplain Profile profile} object to be created and saved
     *
     * @return new {@linkplain Profile profile} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    Profile createProfile(Profile profile) throws IOException;

    /**
     * Updates and saves a {@linkplain Profile profile}
     * 
     * @param profile {@link Profile profile} object to be updated and saved
     * 
     * @return updated {@link Profile profile} if successful, null if {@link Profile profile} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    Profile updateProfile(Profile profile) throws IOException;


    /**
     * Retrieves the {@linkplain Appointment appointment} with the given username
     * 
     * @param username The username of the {@link Appointment appointment} to get
     * 
     * @return A {@link Appointment appointment} with the matching username, null if no {@link Profile profile} is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    public Appointment getAppointment(String username) throws IOException;

    /**
     * Cancels the {@linkplain Appointment appointment} associated with the given username
     * 
     * @param username The username associated with the {@link Profile profile} to cancel
     * 
     * @return A {@link Appointment appointment} with the matching username, null if no {@link Profile profile} is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    public Appointment cancelAppointment(String username) throws IOException;

    /**
     * Sets the {@linkplain Appointment appointment} associated with the given username
     * 
     * @param username The username associated with the {@link Appointment appointment} to set
     * 
     * @return A {@link Profile profile} with the matching username, null if no {@link Profile profile} is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    public Profile setAppointment(String username, Appointment appointment) throws IOException;



  
}
