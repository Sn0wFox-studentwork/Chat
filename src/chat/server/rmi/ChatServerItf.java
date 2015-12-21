package chat.server.rmi;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import chat.client.AbstractClient;
import chat.client.rmi.ChatClientRMI;
import chat.client.rmi.RemotableChatClientItf;
import chat.protocol.Message;

public interface ChatServerItf extends Remote
{
	void sendMessageToAll(Message msg) throws RemoteException, NotBoundException;

	void addChatClient(RemotableChatClientItf user) throws RemoteException;

	void removeChatClient(RemotableChatClientItf user) throws RemoteException;
}
