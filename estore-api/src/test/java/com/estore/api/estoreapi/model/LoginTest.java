package com.estore.api.estoreapi.model;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
 
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Profiles.Login;
import com.estore.api.estoreapi.model.Profiles.Profile;
 
@Tag("Model-tier")
public class LoginTest {
 
 
   public void testCtor(){
       //Setup
       String expected_username = "scubaby";
       String expected_password = "ILoveSkateboarding";
       Profile expected_profile = new Profile("Yazz", "scubaby",
       "ILoveSkateboarding", "yazzmiller@gmail.com", "401-222-333", false);
 
       //Invoke
       Login login = new Login();
 
       login.setUsername(expected_username);
       login.setPassword(expected_password);
       login.setProfile(expected_profile);
 
       //Analyze
       assertEquals(expected_username, login.getUsername());
       assertEquals(expected_password, login.getPassword());
       assertEquals(expected_profile, login.getProfile());
 
 
   }
 
 
   @Test
   public void testUsername(){
 
       //Setup
       String expected_name = "skateboardLover";
       Login login = new Login();
 
       //Invoke
       login.setUsername(expected_name);
 
       //Analyze
       assertEquals(expected_name, login.getUsername());
 
   }
 
   @Test
   public void testPassword(){
 
       //Setup
       String expected_password = "IHateSkateboarding";
       Login login = new Login();
 
       //Invoke
       login.setPassword(expected_password);
 
       //Analyze
       assertEquals(expected_password, login.getPassword());
 
 
   }
 
   @Test
   public void testProfile(){
 
       //Setup
       Profile expected_profile = new Profile("Yazz", "scubaby",
       "ILoveSkateboarding", "yazzmiller@gmail.com", "401-222-333", false);
       Login login = new Login();
 
       //Invoke
       login.setProfile(expected_profile);
 
       //Analyze
       assertEquals(expected_profile, login.getProfile());
 
   }


   @Test
   public void testOwner(){
 
       //Setup
       Login login = new Login();
 
       //Invoke
       boolean owner = login.checkOwner();
 
       //Analyze
       assertEquals(false, owner);
 
   }
 
 
}
 


