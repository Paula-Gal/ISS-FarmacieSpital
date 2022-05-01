package app.client.controllers;

import app.model.Doctor;
import app.model.Order;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import javafx.scene.control.TableView;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class DoctorController extends UnicastRemoteObject implements IObserver, Serializable {

    private Doctor loggedDoctor;

    private Stage primaryStage;

    private IServices server;

    private Scene loginScene;

    @FXML
    private Button medListLabel;

    @FXML
    private Label seacrhMedLabel;

    @FXML
    private TextField searchMedTextField;

    @FXML
    private Label cantLabel;

    @FXML
    private TextField cantTextField;

    @FXML
    private Button addMedBtn;

    @FXML
    private TableView allMedTable;

    @FXML
    private TableView seelctedMedTable;

    @FXML
    private Button selectedMedLabel;

    @FXML
    private Button finishOrderBtn;

    private final BooleanProperty tableHidden;

    @FXML
    private Label doctorName;


    @Override
    public void orderFinished(Order order) throws PharmaException, RemoteException {

    }

    public final BooleanProperty tableHiddenProperty()
    {
        return this.tableHidden;
    }

    public DoctorController() throws RemoteException {

        this.tableHidden = new SimpleBooleanProperty(false);
    }


    public void setDoctor(Doctor doctor) {
        this.loggedDoctor = doctor;
    }

    public void setServer(IServices server) {
        this.server = server;
//        this.primaryStage = primaryStage;
//        this.loginScene = loginScene;

    }

    public void start() {
        doctorName.setText(loggedDoctor.getFirstName() + " " + loggedDoctor.getLastName());
    }

    public final void setTableHidden(final boolean value)
    {
        this.tableHiddenProperty().set(value);
    }

    public void initialize() {
        this.allMedTable.visibleProperty().bind(this.tableHiddenProperty());
        this.allMedTable.managedProperty().bind(this.tableHiddenProperty().not());
        this.seelctedMedTable.visibleProperty().bind(this.tableHiddenProperty());
        this.seelctedMedTable.managedProperty().bind(this.tableHiddenProperty().not());
        medListLabel.setVisible(false);
        seacrhMedLabel.setVisible(false);
        searchMedTextField.setVisible(false);
        cantLabel.setVisible(false);
        cantTextField.setVisible(false);
        addMedBtn.setVisible(false);
        selectedMedLabel.setVisible(false);
        finishOrderBtn.setVisible(false);
    }

    public void medMenu(ActionEvent actionEvent) {
        this.allMedTable.visibleProperty().bind(this.tableHiddenProperty().not());
        this.allMedTable.managedProperty().bind(this.tableHiddenProperty());
        this.seelctedMedTable.visibleProperty().bind(this.tableHiddenProperty().not());
        this.seelctedMedTable.managedProperty().bind(this.tableHiddenProperty());
        medListLabel.setVisible(true);
        seacrhMedLabel.setVisible(true);
        searchMedTextField.setVisible(true);
        cantLabel.setVisible(true);
        cantTextField.setVisible(true);
        addMedBtn.setVisible(true);
        selectedMedLabel.setVisible(true);
        finishOrderBtn.setVisible(true);
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
        server.logoutDoctor(loggedDoctor);
        Stage mainStage = new Stage();
        mainStage.setScene(main);
        mainStage.show();
    }
}
