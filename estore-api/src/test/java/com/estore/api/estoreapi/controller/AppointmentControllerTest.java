package com.estore.api.estoreapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import com.estore.api.estoreapi.model.Appointments.Appointment;
import com.estore.api.estoreapi.persistence.AppointmentDAO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * The unit test suite for the AppointmentController class
 * 
 * @author hab1466
 */
@Tag("Controller-tier")
public class AppointmentControllerTest {
    private AppointmentController appointmentController ;
    private AppointmentDAO mockAppointmentDAO ;

    /**
     * Before each test, create a new AppointmentController object and inject
     * a mock Appointments DAO 
     */
    @BeforeEach
    public void setupAppointmentController() {
        mockAppointmentDAO = mock(AppointmentDAO.class); 
        appointmentController = new AppointmentController(mockAppointmentDAO); 
    }

    @Test
    public void testGetAppointment() throws IOException {
        // Setup
        Appointment appointment = new Appointment(25, "12_21_22", "2_00", 
        "Hunter", "Paul", false);
        when(mockAppointmentDAO.getAppointment(appointment.getId())).thenReturn(appointment); 

        // Invoke 
        ResponseEntity<Appointment> response = appointmentController.getAppointment(appointment.getId()); 

        // Analyze 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(appointment, response.getBody());

    }

    @Test
    public void testGetAppointmentNotFound() throws IOException {
        // Setup
        int appointmentId = 25;
        when(mockAppointmentDAO.getAppointment(appointmentId)).thenReturn(null); 

        // Invoke 
        // when no Appointment is found for the id, null is returned with an error code of
        // "not found"
        ResponseEntity<Appointment> response = appointmentController.getAppointment(appointmentId); 

        // Analyze 
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    public void testGetAppointmentHandleException() throws Exception {
        // Setup
        int appointmentId = 25;
        doThrow(new IOException()).when(mockAppointmentDAO).getAppointment(appointmentId); 

        // Invoke 
        // testing the statement that throws an IOException given a file error
        ResponseEntity<Appointment> response = appointmentController.getAppointment(appointmentId); 

        // Analyze 
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    @Test
    public void testGetAppointments() throws IOException {
        // Setup
        Appointment Appointment1 = new Appointment(25, "12_21_22", "2_00", "Hunter", "Paul", false);
        Appointment Appointment2 = new Appointment(26, "12_24_22", "4_00", "Jade", "Rafael", true);
        Appointment[] appointmentsArray = new Appointment[2] ;
        appointmentsArray[0] = Appointment1;
        appointmentsArray[1] = Appointment2;
        when(mockAppointmentDAO.getAppointments()).thenReturn(appointmentsArray); 

        // Invoke
        ResponseEntity<Appointment[]> response = appointmentController.getAppointments();  

        // Analyze 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(appointmentsArray, response.getBody());

    }

    @Test
    public void testGetAppointmentsHandleException() throws IOException {
        // Setup
        doThrow(new IOException()).when(mockAppointmentDAO).getAppointments(); 

        // Invoke
        ResponseEntity<Appointment[]> response = appointmentController.getAppointments();  

        // Analyze 
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    @Test
    public void testCreateAppointment() throws IOException {
        // Setup
        Appointment appointment = new Appointment(25, "12_21_22", "2_00", "Hunter", "Paul", false);
        when(mockAppointmentDAO.createAppointment(appointment)).thenReturn(appointment);

        Appointment[] list = new Appointment[0];
        when(mockAppointmentDAO.findAppointments(appointment.getDate(), appointment.getTime())).thenReturn(list);

        //Invoke
        ResponseEntity<Appointment> response = appointmentController.createAppointment(appointment);
        
        //Analyze
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertEquals(appointment,response.getBody());

    }

    @Test
    public void testCreateAppointmentFailed() throws IOException {
        // Setup
        Appointment appointment = new Appointment(25, "12_21_22", "2_00", "Hunter", "Paul", false);

        Appointment[] list = new Appointment[1];
        when(mockAppointmentDAO.findAppointments(appointment.getDate(), appointment.getTime())).thenReturn(list);

        //Invoke
        when(mockAppointmentDAO.getAppointment(appointment.getId())).thenReturn(appointment);
        ResponseEntity<Appointment> response = appointmentController.createAppointment(appointment);
        //Analyze
        assertEquals(HttpStatus.CONFLICT,response.getStatusCode());
        
    }

    @Test
    public void testCreateAppointmentHandleException() throws IOException {
        // Setup
        Appointment appointment= new Appointment(25, "12_21_22", "2_00", "Hunter", "Paul", false);

        doThrow(new IOException()).when(mockAppointmentDAO).createAppointment(appointment);
        //Invoke
        Appointment[] list = new Appointment[0];
        when(mockAppointmentDAO.findAppointments(appointment.getDate(), appointment.getTime())).thenReturn(list);
        ResponseEntity<Appointment> response = appointmentController.createAppointment(appointment);
        //Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

        
    }

    @Test
    public void testUpdateAppointment() throws IOException {
        // Setup
        Appointment appointment= new Appointment(25, "12_21_22", "2_00", "Hunter", "Paul", false);
        when(mockAppointmentDAO.updateAppointment(appointment)).thenReturn(appointment);
        ResponseEntity<Appointment> response = appointmentController.updateAppointment(appointment);
        appointment.setGroomer("Groomer #5");
        //Invoke
        response = appointmentController.updateAppointment(appointment);
        //Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(appointment,response.getBody());

    }

    @Test
    public void testUpdateAppointmentFailed() throws IOException {
        // Setup
        Appointment appointment = new Appointment(25, "12_21_22", "2_00", "Hunter", "Paul", false);
        when(mockAppointmentDAO.updateAppointment(appointment)).thenReturn(null);
        //Invoke
        ResponseEntity<Appointment> response = appointmentController.updateAppointment(appointment);

        //Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());


    }

    @Test
    public void testUpdateAppointmentHandleException() throws IOException {
        // Setup
        Appointment appointment = new Appointment(25, "12_21_22", "2_00", "Hunter", "Paul", false);
        doThrow(new IOException()).when(mockAppointmentDAO).updateAppointment(appointment);
        //Invoke
        ResponseEntity<Appointment> response = appointmentController.updateAppointment(appointment);

        //Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

    }

    @Test
    public void testFindAppointmentsWithDateAndTime() throws IOException {
        // Setup
        String desiredDate = "12_21_22";
        String desiredTime = "2_00";
        Appointment[] appointments = new Appointment[2] ;
        appointments[0] = new Appointment(25, "12_21_22", "2_00", "Hunter", "Paul", false);
        appointments[1] = new Appointment(26, "12_24_22", "4_00", "Jade", "Rafael", false);
        when(mockAppointmentDAO.findAppointments(desiredDate, desiredTime)).thenReturn(appointments);
        //Invoke
        ResponseEntity<Appointment[]> response = appointmentController.findAppointments(desiredDate, desiredTime);

        //Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(appointments,response.getBody());
    }

    @Test
    public void testFindAppointmentsWithOnlyDate() throws IOException {
        // Setup
        String desiredDate = "12_21_22";
        String desiredTime = "";
        Appointment[] appointments = new Appointment[2] ;
        appointments[0] = new Appointment(25, "12_21_22", "2_00", "Hunter", "Paul", false);
        appointments[1] = new Appointment(26, "12_24_22", "4_00", "Jade", "Rafael", false);
        when(mockAppointmentDAO.findAppointments(desiredDate, desiredTime)).thenReturn(appointments);
        //Invoke
        ResponseEntity<Appointment[]> response = appointmentController.findAppointments(desiredDate, desiredTime);

        //Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(appointments,response.getBody());
    }

    @Test
    public void testFindAppointmentsWithOnlyTime() throws IOException {
        // Setup
        String desiredDate = "";
        String desiredTime = "2_00";
        Appointment[] appointments = new Appointment[2] ;
        appointments[0] = new Appointment(25, "12_21_22", "2_00", "Hunter", "Paul", false);
        appointments[1] = new Appointment(26, "12_24_22", "4_00", "Jade", "Rafael", false);
        when(mockAppointmentDAO.findAppointments(desiredDate, desiredTime)).thenReturn(appointments);
        //Invoke
        ResponseEntity<Appointment[]> response = appointmentController.findAppointments(desiredDate, desiredTime);

        //Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(appointments,response.getBody());
    }

    @Test
    public void testFindAppointmentsWithNeither() throws IOException {
        // Setup
        String desiredDate = "";
        String desiredTime = "";
        Appointment[] appointments = new Appointment[2] ;
        appointments[0] = new Appointment(25, "12_21_22", "2_00", "Hunter", "Paul", false);
        appointments[1] = new Appointment(26, "12_24_22", "4_00", "Jade", "Rafael", false);
        when(mockAppointmentDAO.findAppointments(desiredDate, desiredTime)).thenReturn(appointments);
        //Invoke
        ResponseEntity<Appointment[]> response = appointmentController.findAppointments(desiredDate, desiredTime);

        //Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(appointments,response.getBody());
    }

    @Test
    public void testFindAppointmentsHandleException() throws IOException {
        // Setup
        String desiredDate = "testDate";
        String desiredTime = "testTime";
        doThrow(new IOException()).when(mockAppointmentDAO).findAppointments(desiredDate, desiredTime);
        //Invoke
        ResponseEntity<Appointment[]> response = appointmentController.findAppointments(desiredDate, desiredTime);

        //Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    public void testDeleteAppointment() throws IOException {
        // Setup
        int appointmentId = 25;
        when(mockAppointmentDAO.deleteAppointment(appointmentId)).thenReturn(true);
        //Invoke
        ResponseEntity<Appointment> response = appointmentController.deleteAppointment(appointmentId);

        //Analyze
        assertEquals(HttpStatus.OK,response.getStatusCode());

    }

    @Test
    public void testDeleteAppointmentNotFound() throws IOException {
        // Setup
        int appointmentId = 25;
        when(mockAppointmentDAO.deleteAppointment(appointmentId)).thenReturn(false);

        //Invoke
        ResponseEntity<Appointment> response = appointmentController.deleteAppointment(appointmentId);

        //Analyze
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());

    }

    @Test
    public void testDeleteAppointmentHandleException() throws IOException {
        // Setup
        int appointmentId = 25;
        doThrow(new IOException()).when(mockAppointmentDAO).deleteAppointment(appointmentId);

        //Invoke
        ResponseEntity<Appointment> response = appointmentController.deleteAppointment(appointmentId);


        //Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());

    }

}
