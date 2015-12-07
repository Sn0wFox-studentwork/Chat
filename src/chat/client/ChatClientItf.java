package chat.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import chat.protocol.Message;

public interface ChatClientItf extends Remote
{
	void printMessage(Message msg) throws RemoteException;
}