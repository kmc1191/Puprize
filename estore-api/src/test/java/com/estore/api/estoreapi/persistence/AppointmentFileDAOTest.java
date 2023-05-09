package com.estore.api.estoreapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.estore.api.estoreapi.model.Appointments.Appointment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

@Tag("Persistence-Tier")
public class AppointmentFileDAOTest {
    AppointmentFileDAO appointmentFileDAO;
    Appointment[] testAppointments;
    ObjectMapper mockObjectMapper;

    /**
     * Before each test, we will create and inject a Mock Object Mapper to
     * isolate the tests from the underlying file
     * @throws IOException
     */
    @BeforeEach
    public void setupHeroFileDAO() throws IOException {
        mockObjectMapper = mock(ObjectMapper.class);
        testAppointments = new Appointment[3];
        testAppointments[0] = new Appointment(99, "12_13_2022", "5_00", "Mr.Curcio", "Mr.Martinez", false);
        testAppointments[1] = new Appointment(100, "12_13_2022", "4_00", "Mr.Boggan", "Mr.DogLover", true);
        testAppointments[2] = new Appointment(101,"12_14_2022", "5_00", "Ms.Condez", "Mr.Martinez", false);

        // When the object mapper is supposed to read from the file
        // the mock object mapper will return the appointment array above
        when(mockObjectMapper
            .readValue(new File("doesnt_matter.txt"),Appointment[].class))
                .thenReturn(testAppointments);
        appointmentFileDAO = new AppointmentFileDAO("doesnt_matter.txt",mockObjectMapper);
    }

    @Test
    public void testGetAppointments() {
        // Invoke
        Appointment[] appointments = appointmentFileDAO.getAppointments();
        
        // Analyze
        assertEquals(appointments.length,testAppointments.length);
        for (int i = 0; i < testAppointments.length;++i)
            assertEquals(appointments[i],testAppointments[i]);
    }

    @Test
    public void testFindAppointments() {
        // Invoke
        Appointment[] appointments = appointmentFileDAO.findAppointments("12_13_2022", "");

        // Analyze
        assertEquals(appointments.length, 2);
        assertEquals(appointments[0],testAppointments[0]);
        assertEquals(appointments[1],testAppointments[1]);
    }

    @Test
    public void testGetAppointment() {
        // Invoke
        Appointment appointment = appointmentFileDAO.getAppointment(99);

        // Analzye
        assertEquals(appointment,testAppointments[0]);
    }

    @Test
    public void testDeleteAppointment() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> appointmentFileDAO.deleteAppointment(99),
                            "Unexpected exception thrown");

        // Analzye
        assertEquals(result,true);
        // We check the internal tree map size against the length
        // of the test heroes array - 1 (because of the delete)
        // Because heroes attribute of HeroFileDAO is package private
        // we can access it directly
        assertEquals(appointmentFileDAO.appointments.size(), testAppointments.length-1);
    }

    @Test
    public void testReopenAppointment() throws IOException{
        // Setup
        Appointment appt = new Appointment(200, "","", "", "", true);
        Appointment result = appointmentFileDAO.createAppointment(appt);
        Appointment test = assertDoesNotThrow(() -> appointmentFileDAO.reopenAppointment(result.getId()), "Unexpected exception thrown");
        
        // Analyze
        assertEquals(test.getReserved(), false);
    }

    @Test
    public void testCreateAppointment() {
        // Setup
        Appointment appointment = new Appointment(102,"12_13_2022", "3_00", "Mr.Rivera", "Mr.DogLover", true);

        // Invoke
        Appointment result = assertDoesNotThrow(() -> appointmentFileDAO.createAppointment(appointment),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Appointment actual = appointmentFileDAO.getAppointment(appointment.getId());
        assertEquals(actual.getId(), appointment.getId());
        assertEquals(actual.getDate(), appointment.getDate());
        assertEquals(actual.getTime(), appointment.getTime());
        assertEquals(actual.getUser(), appointment.getUser());
        assertEquals(actual.getGroomer(), appointment.getGroomer());
        assertEquals(actual.getReserved(), appointment.getReserved());
    }

    @Test
    public void testUpdateAppointment() {
        // Setup
        Appointment appointment = new Appointment(99,"12_13_2022", "3_00", "Mr.Boggan", "Mr.DogLover", true);

        // Invoke
        Appointment result = assertDoesNotThrow(() -> appointmentFileDAO.updateAppointment(appointment),
                                "Unexpected exception thrown");

        // Analyze
        assertNotNull(result);
        Appointment actual = appointmentFileDAO.getAppointment(appointment.getId());
        assertEquals(actual, appointment);
    }

    @Test
    public void testSaveException() throws IOException{
        doThrow(new IOException())
            .when(mockObjectMapper)
                .writeValue(any(File.class),any(Appointment[].class));

        Appointment appointment = new Appointment(102,"12_14_2022", "4_00", "Ms.Clement", "Mr.Martinez", false);

        assertThrows(IOException.class,
                        () -> appointmentFileDAO.createAppointment(appointment),
                        "IOException not thrown");
    }

    @Test
    public void testGetAppointmentNotFound() {
        // Invoke
        Appointment appointment = appointmentFileDAO.getAppointment(98);

        // Analyze
        assertEquals(appointment,null);
    }

    @Test
    public void testDeleteAppointmentNotFound() {
        // Invoke
        boolean result = assertDoesNotThrow(() -> appointmentFileDAO.deleteAppointment(98),
                                                "Unexpected exception thrown");

        // Analyze
        assertEquals(result,false);
        assertEquals(appointmentFileDAO.appointments.size(), testAppointments.length);
    }

    @Test
    public void testUpdateAppointmentNotFound() {
        // Setup
        Appointment appointment = new Appointment(98,"12_12_2022", "12_00", "Mr.Curcio", "Mr.DogLover", true);

        // Invoke
        Appointment result = assertDoesNotThrow(() -> appointmentFileDAO.updateAppointment(appointment),
                                                "Unexpected exception thrown");

        // Analyze
        assertNull(result);
    }

    @Test
    public void testConstructorException() throws IOException {
        // Setup
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        // We want to simulate with a Mock Object Mapper that an
        // exception was raised during JSON object deseerialization
        // into Java objects
        // When the Mock Object Mapper readValue method is called
        // from the HeroFileDAO load method, an IOException is
        // raised
        doThrow(new IOException())
            .when(mockObjectMapper)
                .readValue(new File("doesnt_matter.txt"),Appointment[].class);

        // Invoke & Analyze
        assertThrows(IOException.class,
                        () -> new AppointmentFileDAO("doesnt_matter.txt",mockObjectMapper),
                        "IOException not thrown");
    }
}
