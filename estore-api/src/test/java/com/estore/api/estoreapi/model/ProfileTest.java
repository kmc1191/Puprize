package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Appointments.Appointment;
import com.estore.api.estoreapi.model.Profiles.Profile;

@Tag("Model-tier")
public class ProfileTest {

    public void testCtor(){
        //Setup
        String expected_name = "Yazz Miller";
        String expected_user = "scubaby";
        String expected_password = "ILoveSkateboarding";
        String expected_email = "yazzMiller22198@gmail.com";
        String expected_phone = "401-222-333";

        //Invoke
        Profile profile = new Profile(expected_name, expected_user, expected_password, expected_email, expected_phone, false);

        // Analyze
        assertEquals(expected_name, profile.getName());
        assertEquals(expected_user, profile.getUsername());
        assertEquals(expected_password, profile.getPassword());
        assertEquals(expected_email, profile.getEmail());
        assertEquals(expected_phone, profile.getPhone());
    }

    @Test
    public void testName(){
        //Setup
        String name = "Yazz Miller";
        String expected_user = "scubaby";
        String expected_password = "ILoveSkateboarding";
        String expected_email = "yazzMiller22198@gmail.com";
        String expected_phone = "401-222-333";

        String expected_name = "Yazmin Miller";

        Profile profile = new Profile(name, expected_user, expected_password, expected_email, expected_phone, false);
    
        //Invoke
        profile.setName(expected_name);

        //Analyze
        assertEquals(expected_name,profile.getName());
    
    }

    @Test
    public void testUsername(){
        //Setup
        String expected_name = "Yazz Miller";
        String user = "scubaby";
        String expected_password = "ILoveSkateboarding";
        String expected_email = "yazzMiller22198@gmail.com";
        String expected_phone = "401-222-333";

        String expected_user = "scubaby123";

        Profile profile = new Profile(expected_name, user, expected_password, expected_email, expected_phone, false);
    
        //Invoke
        profile.setUsername(expected_user);

        //Analyze
        assertEquals(expected_user,profile.getUsername());
    }

    @Test
    public void testPassword(){

        //Setup
        String expected_name = "Yazz Miller";
        String expected_user = "scubaby";
        String password = "ILoveSkateboarding";
        String expected_email = "yazzMiller22198@gmail.com";
        String expected_phone = "401-222-333";

        String expected_password = "IHateSkateboarding";

        Profile profile = new Profile(expected_name, expected_user, password, expected_email, expected_phone, false);
    
        //Invoke
        profile.setPassword(expected_password);

        //Analyze
        assertEquals(expected_password,profile.getPassword());

    }

    @Test
    public void testEmail(){

        //Setup
        String expected_name = "Yazz Miller";
        String expected_user = "scubaby";
        String expected_password = "ILoveSkateboarding";
        String email = "yazzMiller22198@gmail.com";
        String expected_phone = "401-222-333";

        String expected_email = "yazzMiller123@gmail.com";

        Profile profile = new Profile(expected_name, expected_user, expected_password, email, expected_phone, false);
    
        //Invoke
        profile.setEmail(expected_email);

        //Analyze
        assertEquals(expected_email,profile.getEmail());

    }

    @Test
    public void testPhone(){

        //Setup
        String expected_name = "Yazz Miller";
        String expected_user = "scubaby";
        String expected_password = "ILoveSkateboarding";
        String expected_email = "yazzMiller22198@gmail.com";
        String phone = "401-222-333";

        String expected_phone = "401-123-456";

        Profile profile = new Profile(expected_name, expected_user, expected_password, expected_email, phone, false);
    
        //Invoke
        profile.setPhone(expected_phone);

        //Analyze
        assertEquals(expected_phone,profile.getPhone());

    }


    @Test
    public void testIsOwner(){

        //Setup
        String expected_name = "Yazz Miller";
        String expected_user = "scubaby";
        String expected_password = "ILoveSkateboarding";
        String expected_email = "yazzMiller22198@gmail.com";
        String expected_phone = "401-222-333";
        boolean isOwner = false;

        boolean expectedOwner = true;

        Profile profile = new Profile(expected_name, expected_user, expected_password, expected_email, expected_phone, isOwner);
    
        //Invoke
        profile.setOwner(expectedOwner);

        //Analyze
        assertEquals(expectedOwner,profile.getIsOwner());

    }

    @Test
    public void testAppointment(){

        //Setup
        String expected_name = "Yazz Miller";
        String expected_user = "scubaby";
        String expected_password = "ILoveSkateboarding";
        String expected_email = "yazzMiller22198@gmail.com";
        String expected_phone = "401-222-333";
        boolean expected_Owner = false;

        Profile profile = new Profile(expected_name, expected_user, expected_password, expected_email, expected_phone, expected_Owner);
    
        //Invoke

        int expected_id = 25;
        String expected_date = "12_6_2022";
        String expected_time = "12_00";
        //String expected_user = "Dog Lover 123";
        String expected_groomer = "W.L. Mert";
        boolean expected_status = false;

        Appointment appt = new Appointment(expected_id, expected_date, expected_time, expected_user, expected_groomer, expected_status);
        profile.setAppointment(appt);

        //Analyze
        assertEquals(appt,profile.getAppointment());

    }


    @Test
    public void testToString(){

        //Setup
        String name = "Yazz Miller";
        String user = "scubaby";
        String password = "ILoveSkateboarding";
        String email = "yazzMiller22198@gmail.com";
        String phone = "401-222-333";

        Profile profile = new Profile(name, user, password, email, phone, false);

        String expected_string = String.format(Profile.STRING_FORMAT,name, user, password, email, phone, false);
        
        //Invoke
        String actual_string = profile.toString();

        //Analyze
        assertEquals(expected_string,actual_string);

    }

    
}
