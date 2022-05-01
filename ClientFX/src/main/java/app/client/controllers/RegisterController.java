package app.client.controllers;

import app.model.User;
import app.services.IServices;
import app.services.UserType;
import app.validators.UserValidator;
import app.validators.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RegisterController extends UnicastRemoteObject implements Serializable {

    public RegisterController() throws RemoteException {
    }

    private IServices server;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passField;

    @FXML
    private Button registerBtn;

    @FXML
    private ComboBox combo;

    public void setServer(IServices server) {
        this.server = server;
    }

    ObservableList<UserType> options =
            FXCollections.observableArrayList(
                    UserType.DOCTOR,
                    UserType.PHARMACIST,
                    UserType.PATIENT
            );


    public void start() {
        //combo box init
        combo.getItems().clear();
        combo.getItems().addAll(options);
    }

    public void register(ActionEvent actionEvent) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = passField.getText();
        UserType userType = (UserType) combo.getValue();
        try {
            try {
                User user = new User(firstName, lastName, email, password);
                UserValidator userValidator = new UserValidator();
                userValidator.validate(user);
            }
            catch (ValidationException ex){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
            if (userType != null) {
                server.registerUser(firstName, lastName, email, password, userType);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Nu s-a selectat niciun rol!");
                alert.showAndWait();
            }
            ((Node) actionEvent.getSource()).getScene().getWindow().hide();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
