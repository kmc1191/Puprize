package com.estore.api.estoreapi.persistence;
 
import java.io.IOException;
import java.util.ArrayList;

import com.estore.api.estoreapi.model.Profiles.PetProfile;
import com.estore.api.estoreapi.model.Profiles.Profile;
 
 
/**
* Defines the interface for the Petprofile which tracks PetProfile object persistence
*
* @author kmc1191 (add your name to this list if you happen to work on this file.)
*/
public interface PetProfileListDAO {

    /**
     * Retrieves the overall pet profile list, including all {@linkplain PetProfile petprofiles}
     * 
     * @return An ArrayList of {@link PetProfile petprofiles}, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    PetProfile[] getPetProfiles() throws IOException;


    /**
     * Retrieves the {@linkplain PetProfile petprofile} with the given username
     * 
     * @param username The username of the {@link PetProfile petprofile} associated with it to get
     * 
     * @return A {@link PetProfile petprofile} with the matching associated username, null if no {@link PetProfile petprofile} is found
     * 
     * @throws IOException if an issue with underlying storage
     */
    PetProfile getPetProfile(String username) throws IOException;

    /**
     * Creates and saves a {@linkplain PetProfile petprofile}
     * 
     * @param profile {@linkplain PetProfile petprofile} object to be created and saved
     *
     * @return new {@linkplain PetProfile profile} if successful, false otherwise 
     * 
     * @throws IOException if an issue with underlying storage
     */
    PetProfile createPetProfile(PetProfile petprofile) throws IOException;


    /**
     * Updates and saves a {@linkplain PetProfile petprofile}
     * 
     * @param profile {@linkplain PetProfile petprofile} object to be updated and saved
     * 
     * @return updated {@linkplain PetProfile petprofile} if successful, null if {@link PetProfile petprofile} could not be found
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    PetProfile updatePetProfile(PetProfile petprofile) throws IOException;


     /**
     * Deletes a {@linkplain PetProfile petProfile} with the given username
     * 
     * @param username The username associated with the {@link PetProfile petProfile}
     * 
     * @return true if the {@linkplain PetProfile petProfile} was deleted, false if the {@link PetProfile petProfile} with the given username does not exist
     * 
     * @throws IOException if underlying storage cannot be accessed
     */
    boolean deletePetProfile(String username) throws IOException; 


  
}


