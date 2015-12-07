package chat.server;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import chat.protocol.Message;

public interface ChatServerItf extends Remote {
    void sendMessageToAll(Message msg) throws RemoteException, NotBoundException;
    void addChatClient(String user) throws RemoteException;
    void removeChatClient(String user) throws RemoteException;
}
