package com.estore.api.estoreapi.persistence;
 
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
 
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
 
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Appointments.Appointment;
import com.estore.api.estoreapi.model.Profiles.Login;
import com.estore.api.estoreapi.model.Profiles.PetProfile;
import com.estore.api.estoreapi.model.Profiles.Profile;
 
/**
* Implements the functionality for JSON file-based persistence for Profiles
*
* {@literal @}Component Spring annotation instantiates a single instance of this
* class and injects the instance into other classes as needed
*
* @author kmc1191 (add your username to this list if you happen to work on this file.)
*/
@Component
public class ProfileListFileDAO implements ProfileListDAO {

    private static final Logger LOG = Logger.getLogger(ProfileListFileDAO.class.getName()); // currently unused
    Map<String, Profile> profileList;    // Provides a local map/cache of the profile objects so
                                        // that we don't have to read from the file every time
    private ObjectMapper objectMapper;  // provides conversion between Profile objects and JSON text format
                                        // written to the file
    private String filename; // the file to be read from and to write to


    private CartDAO cartDAO;
    private AppointmentDAO appointmentDAO;

     /**
     * creates an ProfileList File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectmapper procides JSON object to/from Java Object serialization
     *                      and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public ProfileListFileDAO(@Value("${profiles.file}") String filename, ObjectMapper objectmapper, CartDAO cartDAO, AppointmentDAO appointmentDAO) throws IOException {
        this.filename = filename; 
        this.objectMapper = objectmapper;
        this.cartDAO = cartDAO;
        this.appointmentDAO = appointmentDAO;
        load(); // loads the profile from the file into profileList, the local cache
    }

    /**
     * Saves the {@linkplain Profile profile} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@linkplain Profile profile} were written successfully
     * 
     * @throws IOException when the file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Profile[] profileArray = getProfiles();

        // Serialization of the java objects into JSON objects. 
        // writeValue will throw an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), profileArray);
        return true;
    }


    /**
     * Loads {@linkplain Profile profile} from the JSON file into the map
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when the file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        profileList = new TreeMap<>();

        // Deserialization of the JSON objects from the file into an array of profiles. 
        // readValue will throw an IOException if there is an issue
        // with the file or reading from the file
        Profile[] profileArray = objectMapper.readValue(new File(filename), Profile[].class);


        for (Profile profile : profileArray) {
            profileList.put(profile.getUsername(), profile);
        }

        return true;
    }



    /**
     * {@inheritDoc}}
     */
    @Override
    public Profile[] getProfiles() throws IOException{
        
        ArrayList<Profile> profileArrayList = new ArrayList<>();

        for (Profile profile : profileList.values()){
            profileArrayList.add(profile);
        }

        if(profileArrayList.size() == 0) {
            return new Profile[0];
        }

        Profile[] profileArray = new Profile[profileArrayList.size()];
        profileArrayList.toArray(profileArray) ;
        return profileArray;

        //return profileArrayList;

    }


    /**
     * {@inheritDoc}}
     */
    @Override
    public Profile getProfile(String username) throws IOException{

        synchronized(profileList){

            // checks to see if the profileList contains the desired profile, by username
            if(profileList.containsKey(username)){
                return profileList.get(username);
            }
            else{
                // if the profileList does not contain the profile, then getProfile fails:
                // null is returned instead of a Profile object
                return null;
            }


        }

    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public Profile createProfile(Profile profile) throws IOException{
            Profile newProfile = new Profile(profile.getName(), profile.getUsername(), profile.getPassword(), profile.getEmail(), profile.getPhone(), profile.getIsOwner());
            profileList.put(newProfile.getUsername(), newProfile); 

            cartDAO.createCart(profile.getUsername());

            save(); // may throw IOException
            return newProfile; 

    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public Profile updateProfile(Profile profile) throws IOException{
        synchronized(profileList) {
            if(profileList.containsKey(profile.getUsername()) == false){
                return null; // product does not exists and therefore cannot be updated
            }
            profileList.put(profile.getUsername(), profile);
            save(); // may throw an IOException
            return profile;
        } 

    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public Appointment getAppointment(String username) throws IOException{
        synchronized(profileList) {
            Profile profile = getProfile(username) ;
            if( profile == null ) {
                return null ;
            }

            return profile.getAppointment() ;
        } 

    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public Profile setAppointment(String username, Appointment appointment) throws IOException{
        synchronized(profileList) {

            Profile profile = getProfile(username) ;
            if( profile == null ) {
                return null ;
            }

           profile.setAppointment(appointment) ;
           return profile ;
        } 

    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public Appointment cancelAppointment(String username) throws IOException{
        synchronized(profileList) {
            Profile profile = getProfile(username) ;
            if( profile == null ) {
                return null ;
            }

            Appointment appointment = profile.getAppointment() ;
            profile.setAppointment(null) ;
            this.appointmentDAO.reopenAppointment(appointment.getId());

            return appointment;
        } 

    }

  
}
 
