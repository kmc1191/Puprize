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

import com.estore.api.estoreapi.model.Profiles.Login;
import com.estore.api.estoreapi.model.Profiles.PetProfile;
import com.estore.api.estoreapi.model.Profiles.Profile;
 
/**
* Implements the functionality for JSON file-based persistence for Pet Profiles
*
* {@literal @}Component Spring annotation instantiates a single instance of this
* class and injects the instance into other classes as needed
*
* @author kmc1191 (add your username to this list if you happen to work on this file.)
*/
@Component
public class PetProfileListFileDAO implements PetProfileListDAO {

    private static final Logger LOG = Logger.getLogger(PetProfileListFileDAO.class.getName()); // currently unused
    Map<String, PetProfile> petProfileList;     // Provides a local map/cache of the pet profile objects so
                                                // that we don't have to read from the file every time
    private ObjectMapper objectMapper;          // provides conversion between Pet Profile objects and JSON text format
                                                // written to the file
    private String filename;                    // the file to be read from and to write to


     /**
     * creates an PetProfileList File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectmapper procides JSON object to/from Java Object serialization
     *                      and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public PetProfileListFileDAO(@Value("${petprofiles.file}") String filename, ObjectMapper objectmapper) throws IOException {
        this.filename = filename; 
        this.objectMapper = objectmapper;
        load(); // loads the petprofile from the file into petprofileList, the local cache
    }

    /**
     * Saves the {@linkplain PetProfile petprofile} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@linkplain PetProfile petprofile} were written successfully
     * 
     * @throws IOException when the file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        PetProfile[] petProfileArray = getPetProfiles();

        // Serialization of the java objects into JSON objects. 
        // writeValue will throw an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), petProfileArray);
        return true;
    }


    /**
     * Loads {@linkplain PetProfile petprofile} from the JSON file into the map
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when the file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        petProfileList = new TreeMap<>();

        // Deserialization of the JSON objects from the file into an array of petprofiles. 
        // readValue will throw an IOException if there is an issue
        // with the file or reading from the file
        PetProfile[] petProfileArray = objectMapper.readValue(new File(filename), PetProfile[].class);


        for (PetProfile petProfile : petProfileArray) {
            petProfileList.put(petProfile.getUsername(), petProfile);
        }

        return true;
    }



    /**
     * {@inheritDoc}}
     */
    @Override
    public PetProfile[] getPetProfiles() throws IOException{
        
        ArrayList<PetProfile> petProfileArrayList = new ArrayList<>();

        for (PetProfile petProfile : petProfileList.values()){
            petProfileArrayList.add(petProfile);
        }

        if(petProfileArrayList.size() == 0) {
            return new PetProfile[ 0 ] ;
        }

        PetProfile[] petProfileArray = new PetProfile[petProfileArrayList.size()];
        petProfileArrayList.toArray(petProfileArray) ;
        return petProfileArray;


    }


    /**
     * {@inheritDoc}}
     */
    @Override
    public PetProfile getPetProfile(String username) throws IOException{

        synchronized(petProfileList){

            // checks to see if the profileList contains the desired profile, by username
            if(petProfileList.containsKey(username)){
                return petProfileList.get(username);
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
    public PetProfile createPetProfile(PetProfile petProfile) throws IOException{
        // Create a new Product 
            PetProfile newPetProfile = new PetProfile(petProfile.getName(), petProfile.getAge(), 
            petProfile.getBreed(), petProfile.getTemperament(), petProfile.getExtraInfo(), 
            petProfile.getNails(), petProfile.getEar(), petProfile.getBath(), 
            petProfile.getStyle(), petProfile.getCut(), petProfile.getUsername());

            petProfileList.put(newPetProfile.getUsername(), newPetProfile); 

            save(); // may throw IOException
            return newPetProfile; 

    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public PetProfile updatePetProfile(PetProfile petProfile) throws IOException{
        synchronized(petProfileList) {
            if(petProfileList.containsKey(petProfile.getUsername()) == false){
                return null; // product does not exists and therefore cannot be updated
            }
            petProfileList.put(petProfile.getUsername(), petProfile);
            save(); // may throw an IOException
            return petProfile;
        } 

    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public boolean deletePetProfile(String username) throws IOException{ 
        synchronized(petProfileList) {
            if(petProfileList.containsKey(username)) {
                
                petProfileList.remove(username);
                
                return save();

            }
            else{
                return false; 
            }
        } 
    }



  
}