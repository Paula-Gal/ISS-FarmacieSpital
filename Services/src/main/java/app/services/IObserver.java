package app.services;

import app.model.Order;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IObserver extends Remote {
    void orderFinished(Order order) throws PharmaException, RemoteException;
}
