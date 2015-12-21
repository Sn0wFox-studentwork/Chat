package chat.client.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import chat.protocol.Message;

public interface RemotableChatClientItf extends Remote
{
	void printMessage(Message msg) throws RemoteException;
}