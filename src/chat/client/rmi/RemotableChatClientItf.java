package chat.client.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import chat.protocol.Message;

public interface RemotableChatClientItf extends Remote
{
	/**
	 * Affiche un message
	 * @param msg Message à afficher
	 * @throws RemoteException
     */
	void printMessage(String msg) throws RemoteException;
}