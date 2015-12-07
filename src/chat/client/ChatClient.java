package chat.client;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import chat.protocol.Message;
import chat.server.ChatServer;
import chat.server.ChatServerItf;

public class ChatClient implements ChatClientItf {
	// Attributs
	private String username;
	private Registry registry;
	public static final String SERVER_JREFERENCE = "server";
	
	public ChatClient(String s) throws RemoteException {
		registry = LocateRegistry.getRegistry();
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
		// TODO :	Mettre le code principal du ChatClient
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		ChatClient client = null;
		try {
			client = new ChatClient(s);
			client.join();
		}
		catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while (s != "exit") {
			s = sc.nextLine();
			try {
				ChatServerItf csi = (ChatServerItf) client.getRegistry().lookup(SERVER_JREFERENCE);
			} catch (RemoteException | NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void join()
	{
		// On recupere le serveur RMI courant
		try {			
			// On bind l'objet
			ChatClientItf stub = (ChatClientItf) UnicastRemoteObject.exportObject(this, 0);
			registry.bind(username, stub);
			
			// On recupere le serveur pour s'enregistrer aupres de la liste des serveurs
			ChatServerItf stubServ = (ChatServerItf) registry.lookup(SERVER_JREFERENCE);
			stubServ.addChatClient(username);
		}
		catch (RemoteException e) {
			System.err.println("Failed to locate RMI-Registry");
			e.printStackTrace();
		}
		catch (NotBoundException e) {
			System.err.println("ChatServer not found bound at " + SERVER_JREFERENCE);
			e.printStackTrace();
		}
		catch (java.rmi.AlreadyBoundException e) {
			System.err.println(username + " already bound");
			e.printStackTrace();
		}
	}
	
	public void leave()
	{
		// On recupere le serveur RMI courant
		try {			
			// On recupere le serveur pour se desenregistrer
			ChatServer stubServ = (ChatServer) registry.lookup(SERVER_JREFERENCE);
			stubServ.removeChatClient(username);
			registry.unbind(username);
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
	
	// Implments from RemotableChatClient
	public void printMessage(Message msg)
	{
		System.out.println(msg.getMessage());
	}
}
