package app.client.controllers;

import app.model.Order;
import app.model.Patient;
import app.services.IObserver;
import app.services.IServices;
import app.services.PharmaException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PatientController extends UnicastRemoteObject implements IObserver, Serializable {

    public PatientController() throws RemoteException {
        this.tableHidden = new SimpleBooleanProperty(false);
    }

    @Override
    public void orderFinished(Order order) throws PharmaException, RemoteException {

    }

    private final BooleanProperty tableHidden;

    private Patient loggedPatient;

    private IServices server;

    @FXML
    private Label patientName;

    @FXML
    private Button myRet;

    @FXML
    private Label detailsLabel;

    @FXML
    private TableView stocksTable;


    public void setPatient(Patient patient) {
        this.loggedPatient = patient;
    }

    public void setServer(IServices server) {
        this.server = server;
    }

    public void start() {
        patientName.setText(loggedPatient.getFirstName() + " " + loggedPatient.getLastName());
    }

    public final BooleanProperty tableHiddenProperty()
    {
        return this.tableHidden;
    }

    public void initialize() {
        this.stocksTable.visibleProperty().bind(this.tableHiddenProperty());
        this.stocksTable.managedProperty().bind(this.tableHiddenProperty().not());
        myRet.setVisible(false);
        detailsLabel.setVisible(false);
    }

    public void showRets(ActionEvent actionEvent) {
        this.stocksTable.visibleProperty().bind(this.tableHiddenProperty().not());
        this.stocksTable.managedProperty().bind(this.tableHiddenProperty());
        myRet.setVisible(true);
        detailsLabel.setVisible(true);
    }

    public void handleLogout(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
        FXMLLoader loginView = new FXMLLoader();
        loginView.setLocation(getClass().getResource("/views/LoginView.fxml"));
        Parent loginRoot = null;
        try {
            loginRoot = loginView.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LoginController loginController = loginView.getController();

        Scene main = new Scene(loginRoot);
        loginController.setServer(server, main);
        server.logoutPatient(loggedPatient);
        Stage mainStage = new Stage();
        mainStage.setScene(main);
        mainStage.show();
    }
}
