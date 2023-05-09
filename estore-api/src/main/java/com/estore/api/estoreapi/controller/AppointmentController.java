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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.estore.api.estoreapi.model.Appointments.*;
import com.estore.api.estoreapi.persistence.AppointmentDAO;

/*
 * Handles the REST API Requests for the Appointment resources 
 * 
 * @author hab1466 (with the help of ProductController code) jbc9236 (for docs)
 */
@RestController
@RequestMapping("appointments")
public class AppointmentController {
    /* The log for status messages and error messages */
    private static final Logger LOG = Logger.getLogger(AppointmentController.class.getName());
    /* The DAO for accessing an appointment object */
    private AppointmentDAO appointmentDAO; 

    /**
     * Constructs the appointment controller
     * @param appointmentDAO The DAO object for accessing an appointment
     */
    public AppointmentController(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }
    
    /**
     * Responds to the PUT request for a {@Linkplain Appointment appointment } given a new appointment
     * @param Appointment The appointment to be updated
     * @return  ResponseEntity with {@link Appointment appointment} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Appointment> updateAppointment(@RequestBody Appointment Appointment) {
        LOG.info("PUT /appointments " + Appointment);
        try {
            Appointment uAppointment = appointmentDAO.updateAppointment(Appointment);
            if (uAppointment != null) {
                return new ResponseEntity<Appointment>(uAppointment, HttpStatus.OK);
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
     * Responds to the GET request for a {@linkplain Appointment appointment} for the given id
     * 
     * @param id The id used to locate the {@link Appointment appointment}
     * 
     * @return ResponseEntity with {@link Appointment appointment} object and HTTP status of OK if found<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointment(@PathVariable int id) {
        LOG.info("GET /appointments/" +id);
        try{
            Appointment Appointment = appointmentDAO.getAppointment(id);
            if (Appointment != null){
                return new ResponseEntity<Appointment>(Appointment, HttpStatus.OK);
            } else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(IOException e){
                LOG.log(Level.SEVERE, e.getLocalizedMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

    /**
     * Responds to the GET request for all {@linkplain Appointment appointment}
     * 
     * @return ResponseEntity with ArrayList of {@link Appointment appointment} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Appointment[]> getAppointments() {
        LOG.info("GET /appointments");
        try{
            Appointment[] appointments = appointmentDAO.getAppointments();
            return new ResponseEntity<Appointment[]>(appointments, HttpStatus.OK);
        } catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the GET request for all {@linkplain Appointment appointment} whose date equals
     * the text in date (with "any" as the time or with the given time) OR 
     * whose time equals the text in time (with "any" as the date or with the given date)
     * 
     * @param date The date parameter which contains the text used to find the {@link Appointment appointments}
     * @param time The time parameter which contains the text used to find the {@link Appointment appointments}
     * 
     * @return ResponseEntity with ArrayList of {@link Appointment appointments} objects (may be empty) and
     * HTTP status of OK<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/")
    public ResponseEntity<Appointment[]> findAppointments(@RequestParam String date, @RequestParam String time){
        LOG.info("GET /appointment/?date=" + date + "&time=" + time);
        try{
            Appointment[] foundAppointments = appointmentDAO.findAppointments(date, time);
            return new ResponseEntity<Appointment[]>(foundAppointments, HttpStatus.OK);
        } catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Creates a {@linkplain Appointment appointment} with the provided Appointment object
     * 
     * @param Appointment - The {@link Appointment appointment} to create
     * 
     * @return ResponseEntity with created {@link Appointment appointment} object and HTTP status of CREATED<br>
     * ResponseEntity with HTTP status of CONFLICT if {@link Appointment appointment} object already exists<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PostMapping("")
    public ResponseEntity<Appointment> createAppointment(@RequestBody Appointment appointment) {
        LOG.info("POST /appointments " + appointment);

        try{
            int appointmentId = appointment.getId();
            String date = appointment.getDate();
            String time = appointment.getTime();
            String user = appointment.getUser();
            String groomer = appointment.getGroomer();
            boolean reserved = appointment.getReserved();
            boolean noMatches = (appointmentDAO.findAppointments(date, time).length == 0);
            //noMatches has to be true in order to create
            if(appointmentDAO.getAppointment(appointmentId) == null && noMatches){
                Appointment newAppointment = appointmentDAO.createAppointment(appointment);
                return new ResponseEntity<Appointment>(newAppointment,HttpStatus.CREATED);
            }
            else{
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Deletes a {@linkplain Appointment appointment} with the given id
     * 
     * @param id The id of the {@link Appointment appointment} to deleted
     * 
     * @return ResponseEntity HTTP status of OK if deleted<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Appointment> deleteAppointment(@PathVariable int id) {
        LOG.info("DELETE /appointments/" + id);

        try{

            if(appointmentDAO.deleteAppointment(id)){
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
    }
}