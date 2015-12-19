package chat.client;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Calendar;
import java.util.Scanner;

import chat.protocol.Message;
import chat.server.ChatServer;
import chat.server.ChatServerItf;

public class ChatClient implements ChatClientItf, Serializable {
	// Attributs
	private String username;
	private Registry registry; 
	public static final String SERVER_JREFERENCE = "server";
	
	public ChatClient(String s) {
		try {
			registry = LocateRegistry.getRegistry();
		} catch (RemoteException e) {
			System.out.println("Failed to locate RMI registry in ChatClient constructor");
			e.printStackTrace();
		}
		this.username = s;
	}
	
	public Registry getRegistry()
	{
		return registry;
	}
	
	// Getters
	public String getPseudo()
	{
		return username;
	}
	
	// Setters
	public void setPseudo(String newPseudo)
	{
		// TODO :	ne pas oublier de changer le nom dans la liste de clients
		//			du serveur, et de rebind avec le nouveau username !
		//			OU : ne pas laisser la possibilité de changer de pseudo
		username = newPseudo;
	}
	
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Entrez votre pseudo :");
		String s = sc.nextLine();
		
		ChatClient client = new ChatClient(s);
		ChatClientItf remoteClientInterface;
		try {
			remoteClientInterface = (ChatClientItf)UnicastRemoteObject.exportObject(client, 0);
			remoteClientInterface.join();
			try {
				ChatServerItf csi = (ChatServerItf) client.getRegistry().lookup(SERVER_JREFERENCE);
			} catch (RemoteException | NotBoundException e) {
				System.out.println("Error while looking for server.");
				e.printStackTrace();
			}
			
			while (true) {
				s = null;
				s = sc.nextLine();
				if(s.contains("exit"))
				{
					break;
				}
				client.sendMessage(s);
			}
			System.out.println("Leave procedure engaged");
			remoteClientInterface.leave();
			UnicastRemoteObject.unexportObject(remoteClientInterface, true);
		} catch (RemoteException e1) {
			System.out.println("Client main error : " + e1);
			e1.printStackTrace();
		}

		sc.close();
		
	}
	
	public void join()
	{
		// On recupere le serveur RMI courant
		try {			
			// On recupere le serveur pour s'enregistrer aupres de la liste des serveurs
			ChatServerItf stubServ = (ChatServerItf) registry.lookup(SERVER_JREFERENCE);
			stubServ.addChatClient(this);
		}
		catch (RemoteException e) {
			System.err.println("Client error : " + e);
			e.printStackTrace();
		}
		catch (NotBoundException e) {
			System.err.println("ChatServer not found bound at " + SERVER_JREFERENCE);
			e.printStackTrace();
		}
	}
	
	public void leave()
	{
		// On recupere le serveur RMI courant
		try {			
			// On recupere le serveur pour se desenregistrer
			ChatServerItf stubServ = (ChatServerItf) registry.lookup(SERVER_JREFERENCE);
			stubServ.removeChatClient(this);
		}
		catch (RemoteException e) {
			System.err.println("Failed to locate RMI-Registry");
			e.printStackTrace();
		}
		catch (NotBoundException e) {
			System.err.println("ChatServer not found bound at " + SERVER_JREFERENCE);
			e.printStackTrace();
		}
	}
	
	public void sendMessage(String s)
	{
		try {			
			// On recupere le serveur pour se desenregistrer
			ChatServerItf stubServ = (ChatServerItf) registry.lookup(SERVER_JREFERENCE);
			stubServ.sendMessageToAll(new Message(s, username));
		}
		catch (RemoteException e) {
			System.err.println("Failed to locate RMI-Registry in sendMessage");
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.err.println("ChatServer not found bound at " + SERVER_JREFERENCE);
			e.printStackTrace();
		}
	}
	
	@Override
	public void printMessage(Message msg)
	{
		System.out.println(msg);
	}
}
