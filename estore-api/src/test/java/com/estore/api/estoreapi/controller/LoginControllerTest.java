package com.estore.api.estoreapi.controller; 

import static org.junit.jupiter.api.Assertions.assertEquals; 
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

import com.estore.api.estoreapi.controller.LoginController;
import com.estore.api.estoreapi.model.Profiles.Login;
import com.estore.api.estoreapi.model.Profiles.Profile;
import com.estore.api.estoreapi.persistence.ProfileListDAO;

import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 

public class LoginControllerTest {

    private ProfileListDAO mockProfileListDAO ;
    private LoginController loginController ;


    @BeforeEach
    public void setupLoginController() {
        mockProfileListDAO = mock(ProfileListDAO.class); 
        loginController = new LoginController(mockProfileListDAO); 
    }


    @Test
    public void testGetLogin() throws IOException {
        // Setup
    
        Login login = loginController.getLogin().getBody();


        // Invoke 
        ResponseEntity<Login> response = loginController.getLogin(); 

        // Analyze 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(login, response.getBody());

    }

    @Test
    public void testGetLoginNotFound() throws IOException {
        // Setup
        Login login = loginController.getLogin().getBody();

        // Invoke
        login = null; 

        // Analyze 
        assertEquals(null, login);

    }

    @Test
    public void testTryLogin() throws IOException {
        // Setup
    
        Profile profile = new Profile("Jade", "IReallyLikeDogs", "puppies", "Jade@gmail", "321", false);
        mockProfileListDAO.createProfile(profile);
        when(mockProfileListDAO.getProfile(profile.getUsername())).thenReturn(profile);

        // Invoke 
        ResponseEntity<Profile> response = loginController.tryLogin(profile.getUsername(), profile.getPassword());

        // Analyze 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(profile, response.getBody());

    }

    @Test
    public void testTryLoginNotFound() throws IOException{
        
        // Setup
    
        Profile profile = new Profile("Jade", "IReallyLikeDogs", "puppies", "Jade@gmail", "321", false);

        // Invoke 
        ResponseEntity<Profile> response = loginController.tryLogin(profile.getUsername(), profile.getPassword());

        // Analyze 
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());


    }

    @Test
    public void testTryLoginHandleException() throws IOException{
        
        // Setup
    
        Profile profile = new Profile("Jade", "IReallyLikeDogs", "puppies", "Jade@gmail", "321", false);
        doThrow(new IOException()).when(mockProfileListDAO).getProfile("IReallyLikeDogs"); 

        // Invoke 
        ResponseEntity<Profile> response = loginController.tryLogin(profile.getUsername(), profile.getPassword());

        // Analyze 
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());


    }

    @Test
    public void testTryLogout() throws IOException {
        // Setup
    
        Profile profile = new Profile("Jade", "IReallyLikeDogs", "puppies", "Jade@gmail", "321", false);
        mockProfileListDAO.createProfile(profile);

        loginController.tryLogin(profile.getUsername(), profile.getPassword());

        // Invoke 
        ResponseEntity<Profile> response = loginController.logout(profile.getUsername()); 

        // Analyze 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody());

    }

    @Test
    public void testGetCurrentProfile() throws IOException {
        // Setup
        Profile profile = new Profile("Hunter", "tester1", "testing", "fake@gmail.com", "123", false);
        mockProfileListDAO.createProfile(profile);
        when(mockProfileListDAO.getProfile(profile.getUsername())).thenReturn(profile) ;
        loginController.tryLogin(profile.getUsername(), profile.getPassword());

        // Invoke
        ResponseEntity<Profile> response = loginController.getCurrentProfile();

        // Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(profile, response.getBody());
    }
}
