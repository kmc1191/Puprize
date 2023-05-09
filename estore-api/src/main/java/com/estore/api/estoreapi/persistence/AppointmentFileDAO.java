package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component; 

import com.estore.api.estoreapi.model.Appointments.Appointment;

/**
 * Implements the functionality for JSON file-based persistence for Appointments
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of this
 * class and injects the instance into other classes as needed
 * 
 * @author hab1466 phc6515 (add your username to this list if you happen to work on this file.)
 */
@Component
public class AppointmentFileDAO implements AppointmentDAO {
    private static final Logger LOG = Logger.getLogger(AppointmentFileDAO.class.getName()); // currently unused
    Map<Integer, Appointment> appointments;     // Provides a local map/cache of the Appointment objects so
                                                // that we don't have to read from the file every time
    private ObjectMapper objectMapper;          // provides conversion between Appointment objects and JSON text format
                                                // written to the file
    private static int nextId;                  // the next id that will be given to a newly created hero
    private String filename;


    /**
     * creates an Appointment File Data Access Object
     * 
     * @param filename Filename to read from and write to
     * @param objectmapper procides JSON object to/from Java Object serialization
     *                     and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public AppointmentFileDAO(@Value("${appointments.file}") String filename, ObjectMapper objectmapper) throws IOException {
        this.filename = filename; 
        this.objectMapper = objectmapper;
        load();     // loads the appointments from the file into appointments, the local cache
    }

    /**
     * Generates the next id for a new {@linkplain Appointment appointment}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Saves the {@linkplain Appointment appointment} from the map into the file as an array of JSON objects
     * 
     * @return true if the {@link Appointment appointment} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Appointment[] appointmentArray = getAppointmentArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), appointmentArray);
        return true;
    }

    /**
     * Loads {@linkplain Apppointment appointment} from the JSON file into the map
     * <br>
     * Also sets nextId to be equal to one more than the largest idea found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when the file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        appointments = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of appointments
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Appointment[] appointmentArray = objectMapper.readValue(new File(filename),Appointment[].class);

        // Add each appointment to the tree map and keep track of the greatest id
        for (Appointment appointment : appointmentArray) {
            appointments.put(appointment.getId(), appointment);
            if (appointment.getId() > nextId)
                nextId = appointment.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

     /**
     * Finds all {@linkplain Appointment appointments} whose date and time equals the given text
     * 
     * @param date The date to match against
     * @param time The time to match against
     * 
     * @return An array of {@link Appointment appointments} whose date and time equals the given text, may be empty
     */
    private Appointment[] getAppointmentArray(String date, String time) { // if containsText == null, no filter
        ArrayList<Appointment> appointmentArrayList = new ArrayList<>();

        for (Appointment appointment : appointments.values()) {
            if (date == null && time == null || (appointment.getDate().contains(date) && appointment.getTime().contains(time))) {
                appointmentArrayList.add(appointment);
            }
        }

        Appointment[] appointmentArray = new Appointment[appointmentArrayList.size()];
        appointmentArrayList.toArray(appointmentArray);
        return appointmentArray;
    }

    /**
     * Retrieves all appointments, including all {@linkplain Appointment appointments}
     * Will be used for the master calendar and displaying appointments, should decide to display based on reserved or not
     * 
     * @return An array of {@link Appointment appointments}, may be empty
     * 
     * @throws IOException if an issue with underlying storage
     */
    private Appointment[] getAppointmentArray() {
        return getAppointmentArray(null, null);
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public Appointment[] getAppointments() {
        synchronized(appointments) {
            return getAppointmentArray();
        }
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public Appointment[] findAppointments(String date, String time) {
        ArrayList<Appointment> appointmentArrayList = new ArrayList<>();

        for (Appointment currAppointment : appointments.values()) {
            String appointmentDate = currAppointment.getDate();
            String appointmentTime = currAppointment.getTime();

            if (date.equals("") && time.equals("")){
                // adds all appointments
                appointmentArrayList.add(currAppointment);
            } else if (date.equals("") && (time.equals("") == false)) {
                // adds appointments with the given time regardless of date
                if (appointmentTime.equals(time)){
                    appointmentArrayList.add(currAppointment);
                }
            } else if ((date.equals("") == false) && time.equals("")) {
                // adds appointments with the given date but at any time
                if (appointmentDate.equals(date)){
                    appointmentArrayList.add(currAppointment);
                }
            } else {
                // adds appointments that match the given date and time
                if (appointmentTime.equals(time) && appointmentDate.equals(date)){
                    appointmentArrayList.add(currAppointment);
                }
            }
        }
        
        if ((appointmentArrayList == null) || (appointmentArrayList.size() == 0)){
            Appointment[] matchArray = new Appointment[0];
            return matchArray;
        } else {
            Appointment[] matchArray = new Appointment[appointmentArrayList.size()];
            appointmentArrayList.toArray(matchArray);
            return matchArray;
        }
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public Appointment getAppointment(int appointmentId) {
        synchronized(appointments) {
            if (appointments.containsKey(appointmentId)){
                return appointments.get(appointmentId);
            }
            else {
                return null;
            }
        }
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public Appointment createAppointment(Appointment appointment) throws IOException {
        synchronized(appointments) {
            // We create a new appointment object because the id field is immutable
            // and we need to assign the next unique id
            Appointment newAppointment = new Appointment(nextId(), appointment.getDate(), appointment.getTime(), appointment.getUser(),
            appointment.getGroomer(), appointment.getReserved());
            appointments.put(newAppointment.getId(), newAppointment);
            save(); // may throw an IOException
            return newAppointment;
        }
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public Appointment updateAppointment(Appointment appointment) throws IOException {
        synchronized(appointments) {
            if(appointments.containsKey(appointment.getId()) == false){
                return null; // appointment does not exists and therefore cannot be updated
            }
            appointments.put(appointment.getId(), appointment);
            save(); // may throw an IOException
            return appointment; 
        } 
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public Appointment reopenAppointment(int appointmentId) throws IOException {
        synchronized(appointments) {
            if (appointments.containsKey(appointmentId)){
                Appointment appointment = appointments.get(appointmentId);
                appointment.setUser("") ;
                appointment.setReserved(false);
                return updateAppointment(appointment);
            }
            else {
                return null;
            }
        }
    }

    /**
     * {@inheritDoc}}
     */
    @Override
    public boolean deleteAppointment(int appointmentId) throws IOException {
        synchronized(appointments) {
            if (appointments.containsKey(appointmentId)) {
                appointments.remove(appointmentId);
                return save();
            }
            else {
                return false;
            }
        }
    }
    
}
