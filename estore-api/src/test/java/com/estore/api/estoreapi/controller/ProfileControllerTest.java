package com.estore.api.estoreapi.controller; 
import static org.junit.jupiter.api.Assertions.assertEquals; 
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

import com.estore.api.estoreapi.controller.ProductController;
import com.estore.api.estoreapi.model.Profiles.PetProfile;
import com.estore.api.estoreapi.model.Profiles.Profile;
import com.estore.api.estoreapi.persistence.AppointmentDAO;
import com.estore.api.estoreapi.persistence.AppointmentFileDAO;
import com.estore.api.estoreapi.persistence.CartDAO;
import com.estore.api.estoreapi.persistence.ProfileListDAO;


import com.estore.api.estoreapi.model.Appointments.Appointment;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import com.estore.api.estoreapi.persistence.ProfileListFileDAO;


@Tag("Controller-tier")
public class ProfileControllerTest {

    private ProfileController profileController ;
    private AppointmentController appointmentController;
    private ProfileListDAO mockProfileListDAO ;
    private AppointmentFileDAO mockAppointmentFileDAO ;
    private CartDAO mockCartDAO;

    /**
     * Before each test, create a new ProfileController object and inject
     * a mock Inventory DAO 
     */
    @BeforeEach
    public void setupProfileController() {
        mockProfileListDAO = mock(ProfileListDAO.class); 
        mockAppointmentFileDAO = mock(AppointmentFileDAO.class);
        //mockCartDAO = mock(CartDAO.class);
        profileController = new ProfileController(mockProfileListDAO, mockAppointmentFileDAO); 
        appointmentController = new AppointmentController(mockAppointmentFileDAO);
    }

    @Test
    public void testgetProfile() throws IOException {
        // Setup
        Profile profile = new Profile("Kaitlynn", "Cozmic", "tabletop", "kmc@gmail", "123", false);
        when(mockProfileListDAO.getProfile(profile.getUsername())).thenReturn(profile); 

        // Invoke 
        ResponseEntity<Profile> response = profileController.getProfile(profile.getUsername()); 

        // Analyze 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(profile, response.getBody());

    }

    @Test
    public void testGetProfileNotFound() throws IOException {
        // Setup
        String profileUser = "NotExist" ;
        when(mockProfileListDAO.getProfile(profileUser)).thenReturn(null); 

        // Invoke 
        // when no hero is found for the id, null is returned with an error code of
        // "not found"
        ResponseEntity<Profile> response = profileController.getProfile(profileUser); 

        // Analyze 
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    public void testGetProfileHandleException() throws Exception {
        // Setup
        String profileUser = "NotExist" ; ;
        doThrow(new IOException()).when(mockProfileListDAO).getProfile(profileUser); 

        // Invoke 
        // testing the statement that throws an IOException given a file error
        ResponseEntity<Profile> response = profileController.getProfile(profileUser); 

        // Analyze 
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }


    @Test
    public void testGetInventory() throws IOException {
        // Setup
        Profile product1 = new Profile("Kaitlynn", "Cozmic", "tabletop", "kmc@gmail", "123", false);
        Profile product2 = new Profile("Yazz", "scubaby", "skateboard", "ym@gmail", "456", false);
        Profile[] prioritizedProfiles = new Profile[2] ;
        prioritizedProfiles[0] = product2;
        prioritizedProfiles[1] = product1;
        when(mockProfileListDAO.getProfiles()).thenReturn(prioritizedProfiles); 

        // Invoke
        ResponseEntity<Profile[]> response = profileController.getProfiles();  

        // Analyze 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(prioritizedProfiles, response.getBody());

    }

    @Test
    public void testGetInventoryHandleException() throws IOException {
        // Setup
        doThrow(new IOException()).when(mockProfileListDAO).getProfiles(); 

        // Invoke
        ResponseEntity<Profile[]> response = profileController.getProfiles();  

        // Analyze 
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }


    @Test
    public void testCreateProfile() throws IOException {
        // Setup
        Profile profile = new Profile("Jade", "doglover23", "puppies", "Jade@gmail", "321", false);
        when(mockProfileListDAO.createProfile(profile)).thenReturn(profile);

        //Invoke
        ResponseEntity<Profile> response = profileController.createProfile(profile);
        //Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(profile,response.getBody());

    }

    @Test
    public void testCreateProfileFailed() throws IOException {
        // Setup
        Profile profile = new Profile("Jade", "IReallyLikeDogs", "puppies", "Jade@gmail", "321", false);

        //Invoke
        when(mockProfileListDAO.getProfile(profile.getUsername())).thenReturn(profile);
        ResponseEntity<Profile> response = profileController.createProfile(profile);
        //Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        
    }

    @Test
    public void testCreateProfileHandleException() throws IOException {
        // Setup
        Profile profile= new Profile("Dom", "MyDogBelle", "dogs", "Dom@gmail", "728", false);
        doThrow(new IOException()).when(mockProfileListDAO).createProfile(profile);
        //Invoke
        ResponseEntity<Profile> response = profileController.createProfile(profile);
        //Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

        
    }

    @Test
    public void testUpdateProfile() throws IOException {
        // Setup
        Profile profile= new Profile("Holly", "BelleIsCool", "doggie", "Holly@gmail", "295", false);
        when(mockProfileListDAO.updateProfile(profile)).thenReturn(profile);
        ResponseEntity<Profile> response = profileController.updateProfile(profile);
        profile.setName("Holly Shaw");
        //Invoke
        response = profileController.updateProfile(profile);
        //Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(profile,response.getBody());

    }

    @Test
    public void testUpdateProfileFailed() throws IOException {
        // Setup
        Profile profile = new Profile("Holly", "BelleIsCool", "doggie", "Holly@gmail", "295", false);
        when(mockProfileListDAO.updateProfile(profile)).thenReturn(null);
        //Invoke
        ResponseEntity<Profile> response = profileController.updateProfile(profile);

        //Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());


    }

    @Test
    public void testUpdateProfileHandleException() throws IOException {
        // Setup
        Profile profile = new Profile("Holly", "BelleIsCool", "doggie", "Holly@gmail", "295", false);
        doThrow(new IOException()).when(mockProfileListDAO).updateProfile(profile);
        //Invoke
        ResponseEntity<Profile> response = profileController.updateProfile(profile);

        //Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

    }


    @Test
    public void testGetAppointment() throws IOException {
        // Setup
        Profile profile = new Profile("Kaitlynn", "Cozmic", "tabletop", "kmc@gmail", "123", false);
        Appointment appointment = new Appointment(25, "12_21_22", "2_00", 
        "Hunter", "Paul", false);

        when(mockProfileListDAO.getAppointment("Cozmic")).thenReturn(appointment);
        when(mockProfileListDAO.getProfile("Cozmic")).thenReturn(profile);

        profileController.createProfile(profile);
        profileController.setAppointment("Cozmic", appointment.getId());

        ResponseEntity<Appointment> response = profileController.getAppointment("Cozmic");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(appointment, response.getBody());

    }

    @Test
    public void testGetAppointmentNotFound() throws IOException {
        // Setup
        Profile profile = new Profile("Kaitlynn", "Cozmic", "tabletop", "kmc@gmail", "123", false);
        when(mockProfileListDAO.getProfile(profile.getUsername())).thenReturn(null); 

        // Invoke 
        ResponseEntity<Profile> response = profileController.getProfile(profile.getUsername()); 

        // Analyze 
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    public void testGetAppointmentHandleException() throws IOException {
        // Setup
        Profile profile = new Profile("Kaitlynn", "Cozmic", "tabletop", "kmc@gmail", "123", false);
        doThrow(new IOException()).when(mockProfileListDAO).getProfile(profile.getUsername()) ;
        // Invoke 
        ResponseEntity<Profile> response = profileController.getProfile(profile.getUsername()); 

        // Analyze 
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    


    @Test
    public void testSetAppointment() throws IOException {
        // Setup
        Appointment appointment = new Appointment(25, "12_21_22", "2_00", 
        "Hunter", "Paul", false);
        Profile profile = new Profile("Kaitlynn", "Cozmic", "tabletop", "kmc@gmail", "123", false);
        profileController.setAppointment("Cozmic", appointment.getId());

        when(mockProfileListDAO.getAppointment("Cozmic")).thenReturn(appointment);
        when(mockProfileListDAO.setAppointment(profile.getUsername(), appointment)).thenReturn(profile);
        when(mockProfileListDAO.getProfile("Cozmic")).thenReturn(profile);
        when(mockAppointmentFileDAO.getAppointment(appointment.getId())).thenReturn(appointment);

        ResponseEntity<Profile> response = profileController.setAppointment(profile.getUsername(), appointment.getId());

        // Analyze 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(profile, response.getBody());

    }

    @Test
    public void testSetAppointmentProfileNotFound() throws IOException {
         // Setup
         Appointment appointment = new Appointment(25, "12_21_22", "2_00", 
         "Hunter", "Paul", false);
         Profile profile = new Profile("Kaitlynn", "Cozmic", "tabletop", "kmc@gmail", "123", false);
         profileController.setAppointment("Cozmic", appointment.getId());
 
         when(mockProfileListDAO.getAppointment("Cozmic")).thenReturn(appointment);
         when(mockProfileListDAO.setAppointment(profile.getUsername(), appointment)).thenReturn(profile);
         when(mockProfileListDAO.getProfile("Cozmic")).thenReturn(null);
         when(mockAppointmentFileDAO.getAppointment(appointment.getId())).thenReturn(appointment);
 
         ResponseEntity<Profile> response = profileController.setAppointment(profile.getUsername(), appointment.getId());
 
         // Analyze 
         assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    public void testSetAppointmentAppNotFound() throws IOException {
         // Setup
         Appointment appointment = new Appointment(25, "12_21_22", "2_00", 
         "Hunter", "Paul", false);
         Profile profile = new Profile("Kaitlynn", "Cozmic", "tabletop", "kmc@gmail", "123", false);
         profileController.setAppointment("Cozmic", appointment.getId());
 
         when(mockProfileListDAO.getAppointment("Cozmic")).thenReturn(appointment);
         when(mockProfileListDAO.setAppointment(profile.getUsername(), appointment)).thenReturn(profile);
         when(mockProfileListDAO.getProfile("Cozmic")).thenReturn(profile);
         when(mockAppointmentFileDAO.getAppointment(appointment.getId())).thenReturn(null);
 
         ResponseEntity<Profile> response = profileController.setAppointment(profile.getUsername(), appointment.getId());
 
         // Analyze 
         assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    
    @Test
    public void testCancelAppointmentNotFound() throws IOException {
         // Setup
         Appointment appointment = new Appointment(25, "12_21_22", "2_00", 
         "Hunter", "Paul", false);
         Profile profile = new Profile("Kaitlynn", "Cozmic", "tabletop", "kmc@gmail", "123", false);
         profileController.setAppointment("Cozmic", appointment.getId());
 
         when(mockProfileListDAO.getAppointment("Cozmic")).thenReturn(appointment);
         when(mockProfileListDAO.setAppointment(profile.getUsername(), appointment)).thenReturn(profile);
         when(mockProfileListDAO.getProfile("Cozmic")).thenReturn(null);
 
         ResponseEntity<Appointment> response = profileController.cancelAppointment(profile.getUsername());
 
         // Analyze 
         assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    public void testCancelAppointment() throws IOException {
        // Setup
        Appointment appointment = new Appointment(25, "12_21_22", "2_00", 
         "Hunter", "Paul", false);
         Profile profile = new Profile("Kaitlynn", "Cozmic", "tabletop", "kmc@gmail", "123", false);
         profileController.setAppointment("Cozmic", appointment.getId());
 
         when(mockProfileListDAO.getAppointment("Cozmic")).thenReturn(appointment);
         when(mockProfileListDAO.cancelAppointment(profile.getUsername())).thenReturn(appointment);
         when(mockProfileListDAO.getProfile("Cozmic")).thenReturn(profile);
 
         ResponseEntity<Appointment> response = profileController.cancelAppointment(profile.getUsername());

        // Analyze 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(appointment, response.getBody());

    } 

}
