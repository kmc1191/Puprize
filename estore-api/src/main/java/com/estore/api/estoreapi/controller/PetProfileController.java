package com.estore.api.estoreapi.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.estore.api.estoreapi.model.Products.*;
import com.estore.api.estoreapi.model.Profiles.PetProfile;
import com.estore.api.estoreapi.persistence.PetProfileListDAO;
/*
* Handles the REST API Requests for the PetProfile resources
*
* @author kmc119 jbc9236 (for doc)
*/
@RestController
@RequestMapping("petProfiles")
public class PetProfileController {
    /*The log for messages and error statuses*/
    private static final Logger LOG = Logger.getLogger(PetProfileController.class.getName());
    /* The DAO object for the pet profile lists */
    private PetProfileListDAO petProfileListDAO; 

    /**
     * Constructs the controller for pet profiles
     * @param petProfileListDAO the DAO for the pet profile list
     */
    public PetProfileController(PetProfileListDAO petProfileListDAO) {
        this.petProfileListDAO = petProfileListDAO;
    }



    
    /**
     * Updates the given {@linkplain PetProfile petProfile} 
     * 
     * @param profile The {@link PetProfile petProfile} to update
     * 
     * @return ResponseEntity with {@link PetProfile petProfile} object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<PetProfile> updatePetProfile(@RequestBody PetProfile petProfile) {
        LOG.info("PUT /petProfiles " + petProfile);
        try {
            PetProfile uPetProfile= petProfileListDAO.updatePetProfile(petProfile);
            if (uPetProfile != null) {
                return new ResponseEntity<PetProfile>(uPetProfile, HttpStatus.OK);
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
     * Responds to the GET request for a {@linkplain PetProfile petProfile} for the given username
     * 
     * @param username The username used to locate the {@link PetProfile petProfile}
     * 
     * @return ResponseEntity with {@link PetProfile petProfile} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{username}")
    public ResponseEntity<PetProfile> getPetProfile(@PathVariable String username) {
        LOG.info("GET /petProfiles/" +username);
        try{
            PetProfile petProfile = petProfileListDAO.getPetProfile(username);
            if (petProfile != null){
                return new ResponseEntity<PetProfile>(petProfile, HttpStatus.OK);
            } else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(IOException e){
                LOG.log(Level.SEVERE, e.getLocalizedMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }



    /**
     * Responds to the GET request for all {@linkplain PetProfile petProfiles}
     * 
     * @return ResponseEntity with ArrayList of {@link PetProfile petProfile} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<PetProfile[]> getPetProfiles() {
        //Jade
        LOG.info("GET /petProfile");
        try{
            PetProfile[] petProfileList = petProfileListDAO.getPetProfiles();
            return new ResponseEntity<PetProfile[]>(petProfileList, HttpStatus.OK);
        } catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Creates a {@linkplain PetProfile petProfile} with the provided petProfile object
     * 
     * @param product - The {@link PetProfile petProfile} to create
     * 
     * @return ResponseEntity with created {@link PetProfile petProfile} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link PetProfile petProfile} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<PetProfile> createPetProfile(@RequestBody PetProfile petProfile) {

        LOG.info("POST /petProfiles " + petProfile);

        try{

            String username = petProfile.getUsername();
            
            //Functionality that only allows 1 pet profile, the if statement
            if(petProfileListDAO.getPetProfile(username) == null){
                PetProfile petProfile1 = petProfileListDAO.createPetProfile(petProfile);
                return new ResponseEntity<PetProfile>(petProfile1,HttpStatus.CREATED);
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


     /**
     * Deletes a {@linkplain PetProfile petProfile} with the given username
     * 
     * @param username The username of the {@link PetProfile petProfile} to delete
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{username}")
    public ResponseEntity<PetProfile> deletePetProfile(@PathVariable String username) {

        LOG.info("DELETE /petProfiles/" + username);

        try{

            if(petProfileListDAO.deletePetProfile(username)){
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }


        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }



}