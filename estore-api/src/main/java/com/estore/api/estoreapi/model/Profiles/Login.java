package com.estore.api.estoreapi.model.Profiles;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a user currently logged in to help assist the login component
 * 
 * @author kmc1191 (add your username to this list if you happen to work on this file.)
 */
public class Login {

    private static final Logger LOG = Logger.getLogger(Login.class.getName());

    private static String ownerUser = "admin";
    private static String ownerPassword = "ownerPassword";

    @JsonProperty("username") private String username;
    @JsonProperty("password") private String password;
    @JsonProperty("profile") private Profile currentUser;

    @JsonProperty("Owner") private boolean isOwner;


    //instantiate as null
    public Login(){
        this.username = null;
        this.password = null;
        this.currentUser = null;

        this.isOwner = false;
    }

    /**
     * Retrieves the username of the current user
     * 
     * @return The username of the user logged in, null if no profile present
     */
    public String getUsername() {return username;}

    /**
     * Retrieves the password of the current user
     * 
     * @return The password of the user logged in, null if no profile present
     */
    public String getPassword() {return password;}

    /**
     * Retrieves the the current user profile
     * 
     * @return The profile logged in, null if no profile present
     */
    public Profile getProfile() {return currentUser;}

    /**
     * Retrieves the username of the owner
     * 
     * @return The username of the owner
     */
    public String getOwnerUser(){return ownerUser;}

     /**
     * Returns if current user is the owner.
     * 
     * @return true if current user is the owner, else false
     */
    public boolean checkOwner() {return isOwner;}

    /**
     * Sets the status of the owner user being logged in
     * 
     * @param ifOwner if the current user is the owner or not
     */
    public void setOwner(boolean ifOwner){ isOwner = ifOwner;}

    /**
     * Sets the username of the currentUser
     * 
     * @param username The username of the currentUser
     */
    public void setUsername(String username) {this.username = username;}

    /**
     * Sets the password of the currentUser
     * 
     * @param password The password of the currentUser
     */
    public void setPassword(String password) {this.password = password;}

    /**
     * Sets the profile of the currentUser
     * 
     * @param currentUser The Profile object for the currentUser
     */
    public void setProfile(Profile currentUser) {this.currentUser = currentUser;}
  
}
