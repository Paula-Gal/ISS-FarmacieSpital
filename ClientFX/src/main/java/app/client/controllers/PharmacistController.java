package app.client.controllers;

import app.model.Order;
import app.model.Pharmacist;
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

public class PharmacistController extends UnicastRemoteObject implements IObserver, Serializable {
    
    public PharmacistController() throws RemoteException {
        this.tableHidden = new SimpleBooleanProperty(false);
    }

    @Override
    public void orderFinished(Order order) throws PharmaException, RemoteException {

    }

    private Pharmacist loggedPharmacist;

    private IServices server;

    @FXML
    private Button medListLabel;

    @FXML
    private Label seacrhMedLabel;

    @FXML
    private TextField searchMedTextField;

    @FXML
    private TableView stocksTable;

    @FXML
    private Button logoutBtn;

    @FXML
    private Label pharmaName;

    private final BooleanProperty tableHidden;

    public void setPharma(Pharmacist pharmacist) {
        this.loggedPharmacist = pharmacist;
    }

    public void setServer(IServices server) {
        this.server = server;

    }

    public void start() {
        pharmaName.setText(loggedPharmacist.getFirstName() + " " + loggedPharmacist.getLastName());
    }

    public final BooleanProperty tableHiddenProperty()
    {
        return this.tableHidden;
    }


    public void initialize() {
        this.stocksTable.visibleProperty().bind(this.tableHiddenProperty());
        this.stocksTable.managedProperty().bind(this.tableHiddenProperty().not());
        medListLabel.setVisible(false);
        seacrhMedLabel.setVisible(false);
        searchMedTextField.setVisible(false);
    }

    public void stocksMenu(ActionEvent actionEvent) {
        this.stocksTable.visibleProperty().bind(this.tableHiddenProperty().not());
        this.stocksTable.managedProperty().bind(this.tableHiddenProperty());
        medListLabel.setVisible(true);
        seacrhMedLabel.setVisible(true);
        searchMedTextField.setVisible(true);
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
        server.logoutPharma(loggedPharmacist);
        Stage mainStage = new Stage();
        mainStage.setScene(main);
        mainStage.show();
    }
}
