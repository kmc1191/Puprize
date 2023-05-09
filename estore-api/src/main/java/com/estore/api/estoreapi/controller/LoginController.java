package com.estore.api.estoreapi.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
//Unsure if i have to change to .Products.Login or no. -kaitlynn
import com.estore.api.estoreapi.model.Products.*;
import com.estore.api.estoreapi.model.Profiles.Login;
import com.estore.api.estoreapi.model.Profiles.Profile;
import com.estore.api.estoreapi.persistence.ProfileListDAO;
/*
* Handles the REST API Requests for the login resource
*
* @author kmc119 jbc9236 (for docs)
*/
@RestController
@RequestMapping("login")
public class LoginController {

    /*The log for status messages and errors*/
    private static final Logger LOG = Logger.getLogger(ProfileController.class.getName());
    /* The DAO for accessing the profile list */
    private ProfileListDAO profileListDAO;
    /* The login object for the profile */
    private Login login; 

    /**
     * Constructs the login controller using the profile list
     * @param profileListDAO The DAO for accessing the list of profiles
     */
    public LoginController(ProfileListDAO profileListDAO)
    {
        Login loginObj = new Login();

        this.login = loginObj;

        
        this.profileListDAO = profileListDAO;
    }

    /**
     * Responds to the GET request for a {@linkplain Login login}
     * 
     * @return ResponseEntity with {@link Login login} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Login> getLogin() {
        LOG.info("GET /login/");

        if (login != null){
            return new ResponseEntity<Login>(login, HttpStatus.OK);
        } 
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    /**
     * Attempts a log in from the user and sets login values if user is found
     * 
     * @param username entered username from the login
     * @param password entered password from the login
     * @return Profile that was logged in, null if not found
     */
    @PutMapping("")
    public ResponseEntity<Profile> tryLogin(@RequestParam String username, @RequestParam String password){

        username = username.strip();
        password = password.strip();

        try {

            Profile profile = profileListDAO.getProfile(username);
            if(profile != null ){
            
                String correct_password = profile.getPassword();
                if (correct_password.equals(password)){
                    
                    //Check for owner
                    if(username.equals(login.getOwnerUser())){
                        login.setOwner(true);
                    }

                    login.setUsername(username);
                    login.setPassword(password);
                    login.setProfile(profile);

                    return new ResponseEntity<Profile>(profile, HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }


            }
            else{
                return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
            }

        }
        catch(IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }

    /**
     * Logs out the current user and sets login values to null
     * 
     * @return profile of user logged out
     */
    @PutMapping("/")
    public ResponseEntity<Profile> logout(@RequestParam String username){

        LOG.info("PUT /login/-");
        login.setUsername(null);
        login.setPassword(null);
        login.setProfile(null);

        //Make sure owner is set back to default false
        login.setOwner(false);

        return new ResponseEntity<Profile>(login.getProfile(), HttpStatus.OK);
    }

    //Added throws IO Exception along with diff way of accessing current profile

    /**
     * Gets the current user profile logged in
     * 
     * @return profile of user currently logged in
     * @throws IOException
     */
    @GetMapping("/*")
    public ResponseEntity<Profile> getCurrentProfile() throws IOException {
        //Jade
        LOG.info("GET /login/*");

        String username = login.getUsername();
        Profile tempProfile = profileListDAO.getProfile(username);

        return new ResponseEntity<Profile>(tempProfile, HttpStatus.OK);

        //Instead of getting the actual login.getProfile which is static until a login or log out call is made,
        //we use the username stored since this can not be changed to get updated profile information without
        //having to log out then log back in !!
        //-Kaitlynn

        //return new ResponseEntity<Profile>(login.getProfile(), HttpStatus.OK);


    }




  
}
 
