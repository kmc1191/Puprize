package com.estore.api.estoreapi.model.Profiles;

import java.io.File;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a PetProfile entity
 * 
 * @author kmc1191 (add your username to this list if you happen to work on this file.)
 */
public class PetProfile {

    private static final Logger LOG = Logger.getLogger(PetProfile.class.getName());

    public static final String STRING_FORMAT = "Pet Profile [name=%s, age=%d, breed=%s, temperament=%s, extraInfo=%s, nails=%b, earClean=%b, bath=%b, style=%s, cut=%b";

    @JsonProperty("name") private String name; 
    @JsonProperty("age") private int age;
    @JsonProperty("breed") private String breed;
    @JsonProperty("temperament") private String temperament;
    //To cover anything not listed here
    @JsonProperty("extraInfo") private String extraInfo;

    //SERVICES STUFF
    @JsonProperty("nails") private boolean nails; 
    @JsonProperty("earClean") private boolean earClean;
    @JsonProperty("bath") private boolean bath;
   
    @JsonProperty("style") private String style;
    @JsonProperty("cut") private boolean cut;

    @JsonProperty("username") private String username;



    /**
     * Create a pet profile with the given parameters
     * 
     * @param name          Name of the dog
     * @param age           Age of the dog
     * @param breed         Breed of the dog
     * @param temperament   Temperament of the dog
     * @param extraInfo     Extra information about the dog
     * @param nails         If the dog is getting their nails cut
     * @param earClean      If the dog is getting their ears cleaned
     * @param bath          If the dog is getting a bath
     * @param haircut       What style haircut the dog gets
     * @param cut           If the dog is getting a haircut
     * @param username      Username of owner profile
     */
    public PetProfile(@JsonProperty("name") String name, @JsonProperty("age") int age, 
    @JsonProperty("breed") String breed, @JsonProperty("temperament") String temperament, 
    @JsonProperty("extraInfo") String extraInfo, @JsonProperty("nails") boolean nails, 
    @JsonProperty("earClean") boolean earClean, @JsonProperty("bath") boolean bath, 
    @JsonProperty("style") String style, @JsonProperty("cut") boolean cut, @JsonProperty("username") String username) {
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.temperament = temperament;
        this.extraInfo = extraInfo;
        this.nails = nails;
        this.earClean = earClean;
        this.bath = bath;
        this.style = style;
        this.cut = cut;
        this.username = username;
    }

     /**
     * Retrieves the name of the pet
     * 
     * @return The name of the pet
     */
    public String getName() {return this.name;}

     /**
     * Retrieves the username of the pet owner
     * 
     * @return The username of the pet owner
     */
    public String getUsername() {return this.username;}
    

     /**
     * Retrieves the age of the pet
     * 
     * @return The age of the pet
     */
    public int getAge() {return this.age;}

     /**
     * Retrieves the breed of the pet
     * 
     * @return The breed of the pet
     */
    public String getBreed() {return this.breed;}

     /**
     * Retrieves the temperament of the pet
     * 
     * @return The temperament of the pet
     */
    public String getTemperament() {return this.temperament;}

     /**
     * Retrieves the extra info of the pet
     * 
     * @return The extra info of the pet
     */
    public String getExtraInfo() {return this.extraInfo;}

     /**
     * Retrieves whether the pet is indicated to get their nails trimmed
     * 
     * @return Whether the owner wants the service or not
     */
    public boolean getNails() {return this.nails;}

    /**
     * Retrieves whether the pet is indicated to get their ears cleaned
     * 
     * @return Whether the owner wants the service or not
     */
    public boolean getEar() {return this.earClean;}

    /**
     * Retrieves whether the pet is indicated to get a bath
     * 
     * @return Whether the owner wants the service or not
     */
    public boolean getBath() {return this.bath;}

    /**
     * Retrieves what style haircut for the dog
     * 
     * @return What style the owner wants for their dog.
     */
    public String getStyle() {return this.style;}

    /**
     * Retrieves whether the pet is indicated to get their hair cut
     * 
     * @return Whether the owner wants the service or not.
     */
    public boolean getCut() {return this.cut;}


    // SETTER METHODS

    /**
     * Sets the name of the pet profile
     * 
     * @param name to change to
     */
    public void setName(String name) {this.name = name;}

    /**
     * Sets the username of the pet profile
     * 
     * @param username to change to
     */
    public void setUsername(String username) {this.username = username;}



    /**
     * Sets the age of the pet profile
     * 
     * @param age to change to
     */
    public void setAge(int age) {this.age = age;}

    /**
     * Sets the breed of the pet profile
     * 
     * @param breed to change to
     */
    public void setBreed(String breed) {this.breed = breed;}

    /**
     * Sets the temperament of the pet profile
     * 
     * @param temperament to change to
     */
    public void setTemperament(String temperament) {this.temperament = temperament;}

    /**
     * Sets the extra indo of the pet profile
     * 
     * @param extraInfo to change to
     */
    public void setExtraInfo(String extraInfo) {this.extraInfo = extraInfo;}

    /**
     * Sets the nail cut boolean of the pet profile
     * 
     * @param nails to change to
     */
    public void setNail(boolean nails) {this.nails = nails;}

    /**
     * Sets the ear clean boolean of the pet profile
     * 
     * @param earClean to change to
     */
    public void setEarClean(boolean earClean) {this.earClean = earClean;}

    /**
     * Sets the bath boolean of the pet profile
     * 
     * @param bath to change to
     */
    public void setBath(boolean bath) {this.bath = bath;}

    /**
     * Sets the style of the pet profile
     * 
     * @param style to change to
     */
    public void setStyle(String style) {this.style = style;}

    /**
     * Sets the cut boolean of the pet profile
     * 
     * @param cut to change to
     */
    public void setCut(boolean cut) {this.cut = cut;}



    /**
     * Generates string for a profile
     * 
     * @return String representation of the profile object
     */
    public String toString(){
        return String.format(STRING_FORMAT, name, age, breed, temperament, 
        extraInfo, nails, earClean, bath, style, cut);
    }


}
