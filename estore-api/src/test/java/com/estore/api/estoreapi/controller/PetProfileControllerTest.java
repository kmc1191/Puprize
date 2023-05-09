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
import com.estore.api.estoreapi.persistence.PetProfileListDAO;
import com.estore.api.estoreapi.persistence.ProfileListDAO;


import com.estore.api.estoreapi.model.Appointments.Appointment;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import com.estore.api.estoreapi.persistence.ProfileListFileDAO;

@Tag("Controller-tier")
public class PetProfileControllerTest {

    private PetProfileController petProfileController ;
    private PetProfileListDAO mockPetProfileListDAO ;

    @BeforeEach
    public void setupLoginController() {
        mockPetProfileListDAO = mock(PetProfileListDAO.class); 
        petProfileController = new PetProfileController(mockPetProfileListDAO); 
    }

    @Test
    public void testUpdatePetProfile() throws IOException {
        // Setup
        PetProfile petProfile = new PetProfile("Axel", 1, "Cocker Spaniel", "Mild-Aggressive", "N/A", false, false, true, "Short", true, "Cozmic");
        when(mockPetProfileListDAO.updatePetProfile(petProfile)).thenReturn(petProfile);

        //Invoke
        ResponseEntity<PetProfile> response = petProfileController.updatePetProfile(petProfile);
        //Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(petProfile,response.getBody());

    }

    @Test
    public void testUpdatePetProfileNullResponse() throws IOException {
        // Setup
        PetProfile petProfile = new PetProfile("Axel", 1, "Cocker Spaniel", "Mild-Aggressive", "N/A", false, false, true, "Short", true, "Cozmic");
        when(mockPetProfileListDAO.updatePetProfile(petProfile)).thenReturn(null);

        //Invoke
        ResponseEntity<PetProfile> response = petProfileController.updatePetProfile(petProfile);
        //Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());

    }

    @Test
    public void testUpdatePetProfileHandleException() throws IOException {
        // Setup
        PetProfile petProfile = new PetProfile("Axel", 1, "Cocker Spaniel", "Mild-Aggressive", "N/A", false, false, true, "Short", true, "Cozmic");
        doThrow(new IOException()).when(mockPetProfileListDAO).updatePetProfile(petProfile) ;

        //Invoke
        ResponseEntity<PetProfile> response = petProfileController.updatePetProfile(petProfile);
        //Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

    }

    @Test
    public void testGetPetProfile() throws IOException {
        // Setup
        PetProfile petProfile = new PetProfile("Axel", 1, "Cocker Spaniel", "Mild-Aggressive", "N/A", false, false, true, "Short", true, "Cozmic");
        when(mockPetProfileListDAO.getPetProfile(petProfile.getUsername())).thenReturn(petProfile);

        //Invoke
        ResponseEntity<PetProfile> response = petProfileController.getPetProfile(petProfile.getUsername());
        //Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(petProfile,response.getBody());

    }

    @Test
    public void testGetPetProfileeNullResponse() throws IOException {
        // Setup
        PetProfile petProfile = new PetProfile("Axel", 1, "Cocker Spaniel", "Mild-Aggressive", "N/A", false, false, true, "Short", true, "Cozmic");
        when(mockPetProfileListDAO.getPetProfile(petProfile.getUsername())).thenReturn(null);

        //Invoke
        ResponseEntity<PetProfile> response = petProfileController.getPetProfile(petProfile.getUsername());
        //Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());

    }

    @Test
    public void testGetPetProfileHandleException() throws IOException {
        // Setup
        PetProfile petProfile = new PetProfile("Axel", 1, "Cocker Spaniel", "Mild-Aggressive", "N/A", false, false, true, "Short", true, "Cozmic");
        doThrow(new IOException()).when(mockPetProfileListDAO).getPetProfile(petProfile.getUsername()) ;

        //Invoke
        ResponseEntity<PetProfile> response = petProfileController.getPetProfile(petProfile.getUsername());
        //Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

    }

    @Test
    public void testGetPetProfiles() throws IOException {
        // Setup
        PetProfile petProfile = new PetProfile("Axel", 1, "Cocker Spaniel", "Mild-Aggressive", "N/A", false, false, true, "Short", true, "Cozmic");
        PetProfile[] petList = new PetProfile[1] ;
        petList[0] = petProfile ;
        when(mockPetProfileListDAO.getPetProfiles()).thenReturn(petList);

        //Invoke
        ResponseEntity<PetProfile[]> response = petProfileController.getPetProfiles();

        //Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(petList,response.getBody());

    }

    @Test
    public void testGetPetProfilesHandleException() throws IOException {
        //Setup
        doThrow(new IOException()).when(mockPetProfileListDAO).getPetProfiles();

        //Invoke
        ResponseEntity<PetProfile[]> response = petProfileController.getPetProfiles();

        //Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

    }

    @Test
    public void testCreatePetProfile() throws IOException {
        //Setup
        PetProfile petProfile = new PetProfile("Axel", 1, "Cocker Spaniel", "Mild-Aggressive", "N/A", false, false, true, "Short", true, "Cozmic");
        when(mockPetProfileListDAO.getPetProfile(petProfile.getUsername())).thenReturn(null);
        when(mockPetProfileListDAO.createPetProfile(petProfile)).thenReturn(petProfile);

        //Invoke
        ResponseEntity<PetProfile> response = petProfileController.createPetProfile(petProfile);

        //Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(petProfile,response.getBody());

    }

    @Test
    public void testCreatePetProfileFailure() throws IOException {
        //Setup
        PetProfile petProfile = new PetProfile("Axel", 1, "Cocker Spaniel", "Mild-Aggressive", "N/A", false, false, true, "Short", true, "Cozmic");
        when(mockPetProfileListDAO.getPetProfile(petProfile.getUsername())).thenReturn(petProfile);

        //Invoke
        ResponseEntity<PetProfile> response = petProfileController.createPetProfile(petProfile);

        //Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());

    }

    @Test
    public void testCreatePetProfileHandleException() throws IOException {
        //Setup
        PetProfile petProfile = new PetProfile("Axel", 1, "Cocker Spaniel", "Mild-Aggressive", "N/A", false, false, true, "Short", true, "Cozmic");
        doThrow(new IOException()).when(mockPetProfileListDAO).createPetProfile(petProfile) ;

        //Invoke
        ResponseEntity<PetProfile> response = petProfileController.createPetProfile(petProfile);

        //Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

    }


    @Test
    public void testDeletePetProfile() throws IOException{

        //PetProfile petProfile = new PetProfile("Axel", 1, "Cocker Spaniel", "Mild-Aggressive", "N/A", false, false, true, "Short", true, "Cozmic");

        when(mockPetProfileListDAO.deletePetProfile("Cozmic")).thenReturn(true);

        ResponseEntity<PetProfile> response = petProfileController.deletePetProfile("Cozmic");

        assertEquals(HttpStatus.OK,response.getStatusCode());


    } 

    @Test
    public void testDeletePetProfileNotFound() throws IOException {
        // Setup
        String username = "Cozmic";
        when(mockPetProfileListDAO.deletePetProfile(username)).thenReturn(false);

        //Invoke
        ResponseEntity<PetProfile> response = petProfileController.deletePetProfile(username);

        //Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());

    }

    @Test
    public void testDeletePetProfileHandleException() throws IOException{

        //Setup
        String username = "Cozmic";
        doThrow(new IOException()).when(mockPetProfileListDAO).deletePetProfile(username);

        //Invoke
        ResponseEntity<PetProfile> response = petProfileController.deletePetProfile(username);


        //Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());


    }



    
}
