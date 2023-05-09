package com.estore.api.estoreapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.estore.api.estoreapi.model.Appointments.Appointment;

/**
 * The unit test suite for the Appointment class
 * 
 * @author hab1466
 */

@Tag("Model-tier")
public class AppointmentTest {
    @Test
    public void testCtor(){   
        // Setup
        int expected_id = 25;
        String expected_date = "12_6_2022";
        String expected_time = "12_00";
        String expected_user = "Dog Lover 123";
        String expected_groomer = "W.L. Mert";
        boolean expected_status = false;

        // Invoke
        Appointment appointment = new Appointment(expected_id, expected_date, expected_time, expected_user, expected_groomer, expected_status);

        // Analyze
        assertEquals(expected_id, appointment.getId());
        assertEquals(expected_date, appointment.getDate());
        assertEquals(expected_time, appointment.getTime());
        assertEquals(expected_user, appointment.getUser());
        assertEquals(expected_groomer, appointment.getGroomer());
        assertEquals(expected_status, appointment.getReserved());
    }

    @Test
    public void testDate(){
        // Setup
        int id = 25;
        String date = "12_6_2022";
        String time = "12_00";
        String user = "Dog Lover 123";
        String groomer = "W.L. Mert";
        boolean status = false;
        
        Appointment appointment = new Appointment(id, date, time, user, groomer, status);

        String expected_date = "12_13_2022";

        // Invoke
        appointment.setDate(expected_date);

        // Analyze
        assertEquals(expected_date, appointment.getDate());
    }

    @Test
    public void testTime(){
        // Setup
        int id = 25;
        String date = "12_6_2022";
        String time = "12_00";
        String user = "Dog Lover 123";
        String groomer = "W.L. Mert";
        boolean status = false;
        
        Appointment appointment = new Appointment(id, date, time, user, groomer, status);

        String expected_time = "1_00";

        // Invoke
        appointment.setTime(expected_time);

        // Analyze
        assertEquals(expected_time, appointment.getTime());
    }

    @Test
    public void testUser(){
        // Setup
        int id = 25;
        String date = "12_6_2022";
        String time = "12_00";
        String user = "Dog Lover 123";
        String groomer = "W.L. Mert";
        boolean status = false;
        
        Appointment appointment = new Appointment(id, date, time, user, groomer, status);

        String expected_user = "Cat Lover 123";

        // Invoke
        appointment.setUser(expected_user);

        // Analyze
        assertEquals(expected_user, appointment.getUser());
    }

    @Test
    public void testGroomer(){
        // Setup
        int id = 25;
        String date = "12_6_2022";
        String time = "12_00";
        String user = "Dog Lover 123";
        String groomer = "W.L. Mert";
        boolean status = false;
        
        Appointment appointment = new Appointment(id, date, time, user, groomer, status);

        String expected_groomer = "Mr. Boggan";

        // Invoke
        appointment.setGroomer(expected_groomer);

        // Analyze
        assertEquals(expected_groomer, appointment.getGroomer());
    }

    @Test
    public void testStatus(){
        // Setup
        int id = 25;
        String date = "12_6_2022";
        String time = "12_00";
        String user = "Dog Lover 123";
        String groomer = "W.L. Mert";
        boolean status = false;
        
        Appointment appointment = new Appointment(id, date, time, user, groomer, status);

        boolean expected_status = true;

        // Invoke
        appointment.setReserved(expected_status);

        // Analyze
        assertEquals(expected_status, appointment.getReserved());
    }

    @Test
    public void testToString(){
        // Setup
        int id = 25;
        String date = "12_6_2022";
        String time = "12_00";
        String user = "Dog Lover 123";
        String groomer = "W.L. Mert";
        boolean status = false;
        
        String expected_String = String.format(Appointment.STRING_FORMAT, id, date, time, user, groomer, status);
        
        Appointment appointment = new Appointment(id, date, time, user, groomer, status);

        // Invoke
        String actual_String = appointment.toString();

        // Analyze
        assertEquals(expected_String, actual_String);
    }

}
