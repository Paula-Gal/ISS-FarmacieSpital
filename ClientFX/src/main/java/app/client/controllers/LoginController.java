package app.client.controllers;

import app.model.Doctor;
import app.model.Patient;
import app.model.Pharmacist;
import app.services.IServices;
import app.services.PharmaException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class LoginController {

    private IServices server;

    private Scene primaryScene;

    private Parent parent;

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passTextField;

    @FXML
    private Button loginAsDoctorBtn;

    private DoctorController doctorController;
    private Parent doctorParent;

    private PharmacistController pharmacistController;
    private Parent pharmaParent;

    private PatientController patientController;
    private Parent patientParent;

    private RegisterController registerController;
    private Parent registerParent;

    public void setServer(IServices server, Scene main) {
        this.server = server;
        this.primaryScene = main;
//        this.primaryStage = primaryStage;
    }

    public void loginDoctor(ActionEvent actionEvent) {
        System.out.println("Login as Doctor");
        String email = emailTextField.getText();
        String password = passTextField.getText();

        try {
            loadDoctorController();
            Doctor doctor = server.loginDoctor(email, password, doctorController);
            if (doctor != null) {
                Stage doctorStage = loadDoctorView(doctor);
                doctorStage.show();
                ((Node) actionEvent.getSource()).getScene().getWindow().hide();
            }
        } catch (PharmaException | IOException ex) {
            if (ex.getMessage().equals("Authentication failed!")) {
                System.out.println("Credentials are not correct");
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    private void loadDoctorController() throws IOException {
        FXMLLoader doctorLoader = new FXMLLoader();
        doctorLoader.setLocation(getClass().getResource("/views/DoctorView.fxml"));
        this.doctorParent = doctorLoader.load();
        this.doctorController = doctorLoader.getController();
    }

    private Stage loadDoctorView(Doctor doctor) throws IOException {
        doctorController.setDoctor(doctor);
        doctorController.setServer(this.server);
        doctorController.start();
        Stage doctorStage = new Stage();
        doctorStage.setScene(new Scene(doctorParent));

        return doctorStage;
    }


    public void loginAsPharma(ActionEvent actionEvent) {
        System.out.println("Login as Pharmacist");
        String email = emailTextField.getText();
        String password = passTextField.getText();
        try {
            loadPharmaController();
            Pharmacist pharmacist = server.loginPharmacist(email, password);
            if (pharmacist != null) {
                Stage pharmaView = loadPharmaView(pharmacist);
                pharmaView.show();
                ((Node) actionEvent.getSource()).getScene().getWindow().hide();
            }
        } catch (PharmaException | IOException ex) {
            if (ex.getMessage().equals("Authentication failed!")) {
                System.out.println("Credentials are not correct");
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    private Stage loadPharmaView(Pharmacist pharmacist) {
        pharmacistController.setPharma(pharmacist);
        pharmacistController.setServer(this.server);
        pharmacistController.start();
        Stage pharmaStage = new Stage();
        pharmaStage.setScene(new Scene(pharmaParent));

        return pharmaStage;
    }

    private void loadPharmaController() throws IOException {
        FXMLLoader pharmaLoader = new FXMLLoader();
        pharmaLoader.setLocation(getClass().getResource("/views/PharmacistView.fxml"));
        this.pharmaParent = pharmaLoader.load();
        this.pharmacistController = pharmaLoader.getController();
    }


    public void loginAsPatient(ActionEvent actionEvent) {
        System.out.println("Login as Patient");
        String email = emailTextField.getText();
        String password = passTextField.getText();
        try {
            loadPatientController();
            Patient patient = server.loginPatient(email, password);
            if (patient != null) {
                Stage patientView = loadPatientView(patient);
                patientView.show();
                ((Node) actionEvent.getSource()).getScene().getWindow().hide();
            }
        } catch (IOException ex) {
            if (ex.getMessage().equals("Authentication failed!")) {
                System.out.println("Credentials are not correct");
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    private Stage loadPatientView(Patient patient) {
        patientController.setPatient(patient);
        patientController.setServer(this.server);
        patientController.start();
        Stage patientStage = new Stage();
        patientStage.setScene(new Scene(patientParent));

        return patientStage;
    }

    private void loadPatientController() throws IOException {
        FXMLLoader patientLoader = new FXMLLoader();
        patientLoader.setLocation(getClass().getResource("/views/PatientView.fxml"));
        this.patientParent = patientLoader.load();
        this.patientController = patientLoader.getController();
    }

    public void register(ActionEvent actionEvent) {
        System.out.println("Register");
        try {
            loadRegisterController();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        Stage registerView = loadRegisterView();
        registerView.show();

    }

    private Stage loadRegisterView() {
        registerController.start();
        Stage registerStage = new Stage();
        registerController.setServer(this.server);
        registerStage.setScene(new Scene(registerParent));

        return registerStage;
    }

    private void loadRegisterController() throws IOException {
        FXMLLoader registerLoader = new FXMLLoader();
        registerLoader.setLocation(getClass().getResource("/views/RegisterView.fxml"));
        this.registerParent = registerLoader.load();
        this.registerController = registerLoader.getController();
    }
}
