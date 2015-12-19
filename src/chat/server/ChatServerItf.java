package chat.server;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import chat.client.ChatClientItf;
import chat.protocol.Message;

public interface ChatServerItf extends Remote {
    void sendMessageToAll(Message msg) throws RemoteException, NotBoundException;
    void addChatClient(ChatClientItf user) throws RemoteException;
    void removeChatClient(ChatClientItf user) throws RemoteException;
}
