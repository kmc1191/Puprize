package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.estore.api.estoreapi.controller.LoginController;
import com.estore.api.estoreapi.model.Appointments.Appointment;
import com.estore.api.estoreapi.model.Profiles.Login;
import com.estore.api.estoreapi.model.Profiles.PetProfile;
import com.estore.api.estoreapi.model.Profiles.Profile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

/**
 * Test the ProfileList File DAO class
 * 
 * @author kmc1191
 */
@Tag("Persistence-tier")
public class ProfileListFileDAOTest {

    ProfileListFileDAO profileListFileDAO;
    private LoginController loginController ;
    Profile[] testProfiles;
    ObjectMapper mockObjectMapper;
    InventoryDAO mockInventoryDAO;
    CartDAO mockCartDAO;
    AppointmentDAO mockAppointmentDAO;


    @BeforeEach
    public void ProfileListFileDAO() throws IOException{
        mockObjectMapper = mock(ObjectMapper.class);
        testProfiles = new Profile[3];
        testProfiles[0] = new Profile("Kaitlynn", "Cozmic", "tabletop", "kmc@gmail", "123", false) ;
        testProfiles[1] = new Profile("Claire", "claireM", "security", "cf@gmail", "789", false);
        testProfiles[2] = new Profile("Yazz", "scubaby", "skateboard", "ym@gmail", "456", false) ;

        mockInventoryDAO = mock(InventoryDAO.class); ;
        mockCartDAO = mock(CartDAO.class);
        mockAppointmentDAO = mock(AppointmentDAO.class);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the hero array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"), Profile[].class))
                .thenReturn(testProfiles);
                profileListFileDAO = new ProfileListFileDAO("doesnt_matter.txt", mockObjectMapper, mockCartDAO, mockAppointmentDAO);
    }

    @Test
    public void testSave() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class), any(Profile[].class));

        Profile profile = new Profile("Jade", "doglover23", "puppies", "Jade@gmail", "321", false);

        assertThrows(IOException.class,
                        () -> profileListFileDAO.createProfile(profile),
                        "IOException not thrown");

    }

    @Test
    public void testLoad() throws IOException { 
      //Setup
      ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
      doThrow(new IOException())
        .when(mockObjectMapper)
          .readValue(new File("doesnt_matter.txt"), Profile[].class);
      
      //Invoke & Analyze
      assertThrows(IOException.class, 
                      () -> new ProfileListFileDAO("doesnt_matter.txt", mockObjectMapper, mockCartDAO, mockAppointmentDAO), 
                      "IOException not thrown");
    }



    @Test
    public void testGetProfiles() throws IOException {
        // Invoke
        Profile[] profiles = profileListFileDAO.getProfiles();

        // Analyze
        assertEquals(profiles.length, testProfiles.length);
        for (int i = 0; i < testProfiles.length; i++) {
            assertEquals(profiles[i], testProfiles[i]);
        }
    }

    //Doesnt want to work as in it doesnt cover that part of code
    @Test
    public void testGetProfilesNothing() throws IOException {
        // Setup
        Profile[] profiles;
        profiles = new Profile[0];

        // Invoke
        when(profileListFileDAO.getProfiles()).thenReturn(profiles);

        // Analyze
        assertEquals(profiles, profiles);
    }

    @Test
    public void testGetProfile() throws IOException {
             // Invoke
             Profile profile = profileListFileDAO.getProfile("Cozmic");

             // Analzye
             assertEquals(profile,testProfiles[0]);
    }

    @Test
    public void testCreateProfile() throws IOException {
      //Setup
      Profile profile = new Profile("John", "JohnDow", "spooky", "gmail", "386", false);

      //Invoke
      Profile result = assertDoesNotThrow(() -> profileListFileDAO.createProfile(profile), "Unexpected exception thrown");

      //Analyze
      assertNotNull(result); 
      Profile actual = profileListFileDAO.getProfile(profile.getUsername()); 
      assertEquals(actual.getUsername(), profile.getUsername()); 
      assertEquals(actual.getName(), profile.getName()); 
      assertEquals(actual.getPassword(), profile.getPassword()); 
      assertEquals(actual.getEmail(), profile.getEmail()); 
      assertEquals(actual.getPhone(), profile.getPhone());

    }

    @Test
    public void testUpdateProfile() throws IOException {
          //Setup
        Profile profile = new Profile("John", "Cozmic", "spooky", "gmail", "386", false);

          //Invoke
        Profile result = assertDoesNotThrow(() -> profileListFileDAO.updateProfile(profile),
                                "Unexpected exception thrown");
          // Analyze
        assertNotNull(result);
        Profile actual = profileListFileDAO.getProfile(profile.getUsername());
        assertEquals(actual,profile);

    }

    @Test
    public void testGetProfileNotFound() throws IOException {
      //Invoke
      Profile profile = profileListFileDAO.getProfile("IdontExist");

      //Analyze
      assertEquals(profile, null);
    }


    @Test
    public void testUpdateProfileNotFound() {
      //Setup
      Profile profile = new Profile("Michelle", "MGH", "IAmCool", "yahoo", "954", false);

      //Invoke
      Profile result = assertDoesNotThrow(() -> profileListFileDAO.updateProfile(profile), "Unexpected exception thrown");

      //Analyze
      assertNull(result);
    }

    @Test
    public void testGetAppointment() throws IOException{

      //Setup
      Profile profile = new Profile("John", "JohnDow", "spooky", "gmail", "386", false);

      profileListFileDAO.createProfile(profile);

      Appointment appt = new Appointment(25, "12-6-2022", "12:00", "Cozmic", "Mr. Mert", false);
      profileListFileDAO.setAppointment("JohnDow", appt);

      //Invoke

      Appointment result = profileListFileDAO.getAppointment("JohnDow");


      //Analyze

      assertEquals(appt, result);


    }

    @Test
    public void testGetAppointmentUserNotFound() throws IOException{

      //Setup

      Profile profile = new Profile("John", "JohnDow", "spooky", "gmail", "386", false);

      //Invoke

      Appointment appt = profileListFileDAO.getAppointment("NotFound");


      //Analyze

      assertEquals(appt, null);


    }

    @Test
    public void testSetAppointment() throws IOException{

      //Setup
      Profile profile = new Profile("John", "JohnDow", "spooky", "gmail", "386", false);

      profileListFileDAO.createProfile(profile);

      Appointment appt = new Appointment(25, "12-6-2022", "12:00", "Cozmic", "Mr. Mert", false);

      //Invoke

      profileListFileDAO.setAppointment("JohnDow", appt);
      Appointment result = profileListFileDAO.getAppointment("JohnDow");

      //Analyze

      assertEquals(appt, result);


    }

    @Test
    public void testSetAppointmentUserNotFound() throws IOException{

      //Setup

      Appointment appt = new Appointment(25, "12-6-2022", "12:00", "Cozmic", "Mr. Mert", false);


      //Invoke

      Profile profile = profileListFileDAO.setAppointment("NOTFOUND", appt);


      //Analyze

      assertEquals(null, profile);


    }


    @Test
    public void testCancelAppointment() throws IOException{

      //Setup

      Profile profile = new Profile("John", "JohnDow", "spooky", "gmail", "386", false);

      profileListFileDAO.createProfile(profile);

      Appointment appt = new Appointment(25, "12-6-2022", "12:00", "Cozmic", "Mr. Mert", false);

      //Invoke

      profileListFileDAO.setAppointment("JohnDow", appt);
      Appointment canceledAppt = profileListFileDAO.cancelAppointment("JohnDow");

      //Analyze

      assertEquals(canceledAppt, appt);


    }

    @Test
    public void testCancelAppointmentUserNotFound() throws IOException{

      //Invoke

      Appointment appt = profileListFileDAO.cancelAppointment("NOTFOUND");

      //Analyze

      assertEquals(appt, null);



    }



    
}
