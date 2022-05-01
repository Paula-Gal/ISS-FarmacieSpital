package app.server;

import app.model.Doctor;
import app.model.Patient;
import app.model.Pharmacist;
import app.model.User;
import app.persistence.repo.*;
import app.services.IObserver;
import app.services.IServices;
import app.services.PharmaException;
import app.services.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class Services implements IServices {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DoctorRepo doctorRepo;

    @Autowired
    private PharmacistRepo pharmacistRepo;

    @Autowired
    private PatientRepo patientRepo;

    private final Map<Long, IObserver> loggedDoctors;

    public Services() {
        loggedDoctors = new ConcurrentHashMap<>();
    }

    @Override
    public Pharmacist loginPharmacist(String email, String password) throws PharmaException {
        return pharmacistRepo.findByEmailAndPassword(email, password);
    }

    @Override
    public synchronized Doctor loginDoctor(String email, String password, IObserver client) throws PharmaException {
        Doctor doctor = doctorRepo.findByEmailAndPassword(email, password);
        if (doctor != null) {
            if (loggedDoctors.get(doctor.getId()) != null) {
                throw new PharmaException("The doctor is already logged in app!");
            }
            loggedDoctors.put(doctor.getId(), client);
        } else throw new PharmaException("Login failed!");
        return doctor;
    }

    @Override
    public User loginUser(String email, String password) throws PharmaException {
        return userRepo.findByEmailAndPassword(email, password);
    }

    @Override
    public Patient loginPatient(String email, String password) {
        return patientRepo.findByEmailAndPassword(email, password);
    }

    @Override
    public void logoutDoctor(Doctor doctor) {
        loggedDoctors.remove(doctor.getId());
    }

    @Override
    public void logoutPharma(Pharmacist pharmacist) {
        loggedDoctors.remove(pharmacist.getId());
    }

    @Override
    public void logoutPatient(Patient patient) {
        loggedDoctors.remove(patient.getId());
    }

    @Override
    public void registerUser(String firstName, String lastName, String email, String password, UserType userType) throws PharmaException {
        switch (userType) {
            case DOCTOR -> {
                try {
                    registerDoctor(firstName, lastName, email, password);
                } catch (PharmaException e) {
                    e.printStackTrace();
                }
            }
            case PHARMACIST -> {
                try {
                    registerPharma(firstName, lastName, email, password);
                } catch (PharmaException e) {
                    e.printStackTrace();
                }
            }
            case PATIENT -> {
                try {
                    registerPatient(firstName, lastName, email, password);
                } catch (PharmaException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void registerPatient(String firstName, String lastName, String email, String password) throws PharmaException {
        Patient patient = new Patient(firstName, lastName, email, password);
        patientRepo.save(patient);
    }

    private void registerPharma(String firstName, String lastName, String email, String password) throws PharmaException {
        Pharmacist pharmacist = new Pharmacist(firstName, lastName, email, password);
        pharmacistRepo.save(pharmacist);
    }

    private void registerDoctor(String firstName, String lastName, String email, String password) throws PharmaException {
        Doctor doctor = new Doctor(firstName, lastName, email, password);
        doctorRepo.save(doctor);
    }
}
