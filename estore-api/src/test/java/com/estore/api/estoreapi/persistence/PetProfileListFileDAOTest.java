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
 * Test the PetProfileList File DAO class
 * 
 * @author kmc1191
 */
@Tag("Persistence-tier")
public class PetProfileListFileDAOTest {


    PetProfileListFileDAO petProfileListFileDAO;
    //ProfileListFileDAO profileListFileDAO;
    PetProfile[] testPetProfiles;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    public void PetProfileListFileDAO() throws IOException{

        mockObjectMapper = mock(ObjectMapper.class);
        testPetProfiles = new PetProfile[3];

        testPetProfiles[0] = new PetProfile("Sam", 11, "Lhasa Apso", "friendly", "Be gentle", true, true, true, "short", true, "Cozmic");
        testPetProfiles[1] = new PetProfile("Max", 12, "Lhasa Poo", "Mean", "", true, false, true, "short", true, "biddy");
        testPetProfiles[2] = new PetProfile("Belle", 2, "Shih Tzu", "nice", "Will lick you a lot", true, true, true, "medium", true, "helpwantedsigns");

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the hero array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"), PetProfile[].class))
                .thenReturn(testPetProfiles);
                petProfileListFileDAO = new PetProfileListFileDAO("doesnt_matter.txt", mockObjectMapper);



    }

    @Test
    public void testSave() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class), any(PetProfile[].class));

        PetProfile petProfile = new PetProfile("Sam", 11, "Lhasa Apso", "friendly", "Be gentle", true, true, true, "short", true, "Cozmic");

        assertThrows(IOException.class,
                        () -> petProfileListFileDAO.createPetProfile(petProfile),
                        "IOException not thrown");

    }

    @Test
    public void testLoad() throws IOException { 
      //Setup
      ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
      doThrow(new IOException())
        .when(mockObjectMapper)
          .readValue(new File("doesnt_matter.txt"), PetProfile[].class);
      
      //Invoke & Analyze
      assertThrows(IOException.class, 
                      () -> new PetProfileListFileDAO("doesnt_matter.txt", mockObjectMapper), 
                      "IOException not thrown");
    }

    @Test
    public void testGetPetProfiles() throws IOException {
        // Invoke
        PetProfile[] petprofiles = petProfileListFileDAO.getPetProfiles();

        // Analyze
        assertEquals(petprofiles.length, testPetProfiles.length);
        for (int i = 0; i < testPetProfiles.length; i++) {
            assertEquals(petprofiles[i], testPetProfiles[i]);
        }
    }


    @Test
    public void testGetPetProfile() throws IOException {
             // Invoke
             PetProfile petprofile = petProfileListFileDAO.getPetProfile("Cozmic");

             // Analzye
             assertEquals(petprofile,testPetProfiles[0]);
    }


    @Test
    public void testGetPetProfileNotFound() throws IOException {
             // Invoke
             PetProfile petprofile = petProfileListFileDAO.getPetProfile("NOTFOUND");

             // Analzye
             assertEquals(petprofile,null);
    }

    @Test
    public void testCreatePetProfile() throws IOException {
      //Setup
      PetProfile petprofile = new PetProfile("Zuko", 9, "Shih Tzu", "friendly", "", true, true, true, "short", true, "echo");

      //Invoke
      PetProfile result = assertDoesNotThrow(() -> petProfileListFileDAO.createPetProfile(petprofile), "Unexpected exception thrown");

      //Analyze
      assertNotNull(result); 
      PetProfile actual = petProfileListFileDAO.getPetProfile(petprofile.getUsername()); 
      assertEquals(actual.getName(), result.getName());
      assertEquals(actual.getAge(), result.getAge());
      assertEquals(actual.getBreed(), result.getBreed());
      assertEquals(actual.getTemperament(), result.getTemperament());
      assertEquals(actual.getExtraInfo(), result.getExtraInfo());
      assertEquals(actual.getNails(), result.getNails());
      assertEquals(actual.getEar(), result.getEar());
      assertEquals(actual.getBath(), result.getBath());
      assertEquals(actual.getStyle(), result.getStyle());
      assertEquals(actual.getCut(), result.getCut());
      assertEquals(actual.getUsername(), result.getUsername());
      

    }

    @Test
    public void testUpdatePetProfile() throws IOException {
        //Setup
        PetProfile petprofile = new PetProfile("Zuko", 9, "Shih Tzu", "friendly", "", true, true, true, "short", true, "Cozmic");

        //Invoke
        PetProfile result = assertDoesNotThrow(() -> petProfileListFileDAO.updatePetProfile(petprofile),
                                "Unexpected exception thrown");
        // Analyze
        assertNotNull(result);
        PetProfile actual = petProfileListFileDAO.getPetProfile(petprofile.getUsername());
        assertEquals(actual,petprofile);

    }


    @Test
    public void testUpdateProfileNotFound() {
      //Setup
      PetProfile petprofile = new PetProfile("Zuko", 9, "Shih Tzu", "friendly", "", true, true, true, "short", true, "echo");

      //Invoke
      PetProfile result = assertDoesNotThrow(() -> petProfileListFileDAO.updatePetProfile(petprofile), "Unexpected exception thrown");

      //Analyze
      assertNull(result);
    }


    @Test
    public void testDeletePet() {
    //Invoke
    boolean result = assertDoesNotThrow(() -> petProfileListFileDAO.deletePetProfile("Cozmic"),
    "Unexpected exception thrown");
    // Analzye
    assertEquals(result,true);
    assertEquals(petProfileListFileDAO.petProfileList.size(),testPetProfiles.length-1);
    }


    @Test
    public void testDeletePetNotFound() {
      //Invoke
      boolean result = assertDoesNotThrow(() -> petProfileListFileDAO.deletePetProfile("NOTFOUND"), "Unexpected exception thrown");

      //Analyze
      assertEquals(result, false);
      assertEquals(petProfileListFileDAO.petProfileList.size(), testPetProfiles.length);
    }



    
}
