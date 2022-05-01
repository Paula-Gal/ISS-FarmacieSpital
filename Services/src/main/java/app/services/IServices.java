package app.services;

import app.model.Doctor;
import app.model.Patient;
import app.model.Pharmacist;
import app.model.User;

public interface IServices {

    Pharmacist loginPharmacist(String email, String password) throws PharmaException;

    Doctor loginDoctor(String email, String password, IObserver client) throws PharmaException;

    User loginUser(String email, String password) throws PharmaException;

    Patient loginPatient(String email, String password);

    void logoutDoctor(Doctor doctor);

    void logoutPharma(Pharmacist pharmacist);

    void logoutPatient(Patient patient);

    void registerUser(String firstName, String lastName, String email, String password, UserType userType) throws PharmaException;
}
