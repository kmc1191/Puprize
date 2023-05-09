package com.estore.api.estoreapi.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.estore.api.estoreapi.model.Appointments.Appointment;
//Unsure if i have to change to .Products.Profile or no. -kaitlynn
import com.estore.api.estoreapi.model.Products.*;
import com.estore.api.estoreapi.model.Profiles.Profile;
import com.estore.api.estoreapi.persistence.AppointmentFileDAO;
import com.estore.api.estoreapi.persistence.ProfileListDAO;

/*
* Handles the REST API Requests for the Profile resources
*
* @author kmc119 jbc9236 (for docs)
*/
@RestController
@RequestMapping("profiles")
public class ProfileController {
    /* The Log for messages for errors and statuses*/
    private static final Logger LOG = Logger.getLogger(ProfileController.class.getName());
    /* The DAO object for accessing the list of profiles */
    private ProfileListDAO profileListDAO; 
    /* The DAO object for accessing all the appointments */
    private AppointmentFileDAO appointmentFileDAO;

    /**
     * Constructs the Profile Controller
     * @param profileListDAO The DAO object for accessing the user's profile
     * @param appointmentFileDAO The DAO object for accessing the user's appointments
     */
    public ProfileController(ProfileListDAO profileListDAO, AppointmentFileDAO appointmentFileDAO) {
        this.profileListDAO = profileListDAO;
        this.appointmentFileDAO = appointmentFileDAO;
    }



    /**
     * Updates the given {@linkplain Profile profile} 
     * 
     * @param profile The {@link Profile profile} to update
     * 
     * @return ResponseEntity with {@link Profile profile} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Profile> updateProfile(@RequestBody Profile profile) {
        LOG.info("PUT /profiles " + profile);
        try {
            Profile uProfile= profileListDAO.updateProfile(profile);
            if (uProfile != null) {
                return new ResponseEntity<Profile>(uProfile, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Responds to the GET request for a {@linkplain Profile profile} for the given username
     * 
     * @param username The username used to locate the {@link Profile profile}
     * 
     * @return ResponseEntity with {@link Profile profile} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{username}")
    public ResponseEntity<Profile> getProfile(@PathVariable String username) {
        LOG.info("GET /profiles/" +username);
        try{
            Profile profile = profileListDAO.getProfile(username);
            if (profile != null){
                return new ResponseEntity<Profile>(profile, HttpStatus.OK);
            } else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(IOException e){
                LOG.log(Level.SEVERE, e.getLocalizedMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }



    /**
     * Responds to the GET request for all {@linkplain Profile profiles}
     * 
     * @return ResponseEntity with ArrayList of {@link Profile profile} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Profile[]> getProfiles() {
        //Jade
        LOG.info("GET /profile");
        try{
            Profile[] profileList = profileListDAO.getProfiles();
            return new ResponseEntity<Profile[]>(profileList, HttpStatus.OK);
        } catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain Profile profile} with the provided profile object
     * 
     * @param product - The {@link Profile profile} to create
     * 
     * @return ResponseEntity with created {@link Profile profile} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Profile profile} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) {

        LOG.info("POST /profiles " + profile);

        try{

            String username = profile.getUsername();
            
            if(profileListDAO.getProfile(username) == null){
                Profile profile1 = profileListDAO.createProfile(profile);
                return new ResponseEntity<Profile>(profile1,HttpStatus.CREATED);
            }
            else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //Removed pet profile controller methods.


    /**
     * Responds to the GET request for a {@linkplain Appointment appointment} for the given username
     * 
     * @param username The username used to locate the {@link Appointment appointment}
     * 
     * @return ResponseEntity with {@link Appointment appointment} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("app")
    public ResponseEntity<Appointment> getAppointment(@RequestParam String username) throws IOException {
        LOG.info("GET /Profiles " + username) ;
        if( this.profileListDAO.getProfile(username) == null ) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Appointment appointment = this.profileListDAO.getAppointment(username) ;
        return new ResponseEntity<Appointment>(appointment, HttpStatus.OK);

    }


    /**
     * Sets a {@linkplain Appointment appointment} with associated username
     * 
     * @param username The username associated with the {@link Appointment appointment} to set
     * 
     * @return ResponseEntity HTTP status of OK if set<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     */
    @PostMapping("*app")
    public ResponseEntity<Profile> setAppointment(@RequestParam String username, @RequestParam int appointmentID) throws IOException {
        LOG.info("POST /Profiles " + username + ", " + appointmentID) ;
        if( this.profileListDAO.getProfile(username) == null ) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
        }
        Appointment appointment = this.appointmentFileDAO.getAppointment(appointmentID) ;
        if( appointment == null ) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
        }

        Profile profile = this.profileListDAO.setAppointment(username, appointment) ;
        return new ResponseEntity<Profile>(profile, HttpStatus.OK);

    }


    /**
     * Cancels a {@linkplain Appointment appointment} with associated username
     * 
     * @param username The username associated with the {@link Appointment appointment} to cancel
     * 
     * @return ResponseEntity HTTP status of OK if canceled<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     */
    @PostMapping("-app")
    public ResponseEntity<Appointment> cancelAppointment(@RequestParam String username) throws IOException {
        LOG.info("POST /Profiles " + username) ;
        if( this.profileListDAO.getProfile(username) == null ) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
        }

        Appointment appointment = this.profileListDAO.cancelAppointment(username) ;
        return new ResponseEntity<Appointment>(appointment, HttpStatus.OK);
    }


}
