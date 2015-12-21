package chat.server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;

import chat.client.ChatClient;
import chat.client.ChatClientItf;
import chat.protocol.Message;

public class ChatServer implements ChatServerItf {
	private LinkedList<ChatClientItf> clientList;
	private Registry registry;

	public ChatServer() throws RemoteException {
		clientList = new LinkedList<ChatClientItf>();
    	LocateRegistry.createRegistry(1099);
        registry = LocateRegistry.getRegistry();
	}
	
	public static void main(String[] args) {
		try {
			ChatServer cs = new ChatServer();
			
			// Launching and getting RMI register
            ChatServerItf chi = (ChatServerItf) UnicastRemoteObject.exportObject(cs, 0);
            cs.registry.bind("server", chi);
           
            System.out.println("Server ready");
        }
		catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
	}

	@Override
	public void sendMessageToAll(Message msg) throws RemoteException, NotBoundException {
		for ( ChatClientItf user : clientList ) {
			user.printMessage(msg);
		}
	}

	@Override
	public void addChatClient(ChatClientItf user) {
		clientList.add(user);
	}

	@Override
	public void removeChatClient(ChatClientItf user) {
		clientList.remove(user);
	}

}
