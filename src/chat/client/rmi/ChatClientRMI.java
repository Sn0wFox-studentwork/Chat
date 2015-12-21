package chat.client.rmi;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

import chat.client.AbstractClient;
import chat.protocol.Message;
import chat.server.rmi.ChatServerItf;
import chat.view.Observable;
import chat.view.Observer;

public class ChatClientRMI	extends AbstractClient
						
/* Role :
 * Modelise le comportement d'un client du chat dans sa forme RMI.
 */
{
	// ---------------------------------------------------- Attributs
	private Registry registry;
	public static final String SERVER_JREFERENCE = "server";

	// ---------------------------------------------------- Constructeur
	public ChatClientRMI(String username)
	{
		super(username);
		
		try
		{
			registry = LocateRegistry.getRegistry();
		} catch (RemoteException e)
		{
			System.out.println("Failed to locate RMI registry in ChatClientRMI constructor");
			e.printStackTrace();
		}
	}

	// ---------------------------------------------------- Methodes publiques
	public Registry getRegistry()
	{
		return registry;
	}

	public String getUsername()
	{
		return username;
	}
	

	// ---------------------------------------------------- Implementations interfaces

	@Override
	public void join(String hostIP, int port)
	{
		try
		{
			// On recupere le serveur pour s'enregistrer aupres de la liste des clients
			ChatServerItf stubServ = (ChatServerItf) registry.lookup(SERVER_JREFERENCE);
			stubServ.addChatClient(this);
			
		} catch (RemoteException e)
		{
			System.err.println("Client error : " + e);
			e.printStackTrace();
		} catch (NotBoundException e)
		{
			System.err.println("ChatServerRMI not found bound at " + SERVER_JREFERENCE);
			e.printStackTrace();
		}
	}	//------ Fin de join()

	@Override
	public void leave()
	{
		// On recupere le serveur RMI courant
		try
		{
			// On recupere le serveur pour se desenregistrer
			ChatServerItf stubServ = (ChatServerItf) registry.lookup(SERVER_JREFERENCE);
			stubServ.removeChatClient(this);
		} catch (RemoteException e)
		{
			System.err.println("Failed to locate RMI-Registry");
			e.printStackTrace();
		} catch (NotBoundException e)
		{
			System.err.println("ChatServerRMI not found bound at " + SERVER_JREFERENCE);
			e.printStackTrace();
		}
	}	//------ Fin de leave()

	@Override
	public void sendMessage(Message msg)
	{
		System.out.println("sendMessage recois le message : " + msg);
		try
		{
			// On recupere le serveur pour se desenregistrer
			ChatServerItf stubServ = (ChatServerItf) registry.lookup(SERVER_JREFERENCE);
			System.out.println("on l'envoie a serveur.sendMessageToAll en tant que : " + msg);
			stubServ.sendMessageToAll(msg);
		}
		catch (RemoteException e)
		{
			System.err.println("Failed to locate RMI-Registry in sendMessage");
			e.printStackTrace();
		}
		catch (NotBoundException e)
		{
			System.err.println("ChatServerRMI not found bound at " + SERVER_JREFERENCE);
			e.printStackTrace();
		}
	}	//------ Fin de sendMessage()

	@Override
	public void printMessage(Message msg)
	{
		System.out.println(username + " recois le message :");
		System.out.println(msg);
		
		notifyObserver(msg);
	}
	
	@Override
	public void setUsername(String newPseudo)
	{
		username = newPseudo;
	}
	
	@Override
	public void addObserver(Observer obs)
	{
		observersList.add(obs);
	}

	@Override
	public void removeObserver(Observer obs)
	{
		observersList.remove(obs);
	}

	@Override
	public void notifyObserver(Message msg)
	{
		System.out.println(observersList.size() + " vues vont etre mises a jour pour " + username);
		for(Observer obs : observersList)
		{
			obs.update(msg);
		}
	}

	
	// ---------------------------------------------------- Fonction principale
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		System.out.println("Entrez votre pseudo :");
		String s = sc.nextLine();

		ChatClientRMI client = new ChatClientRMI(s);
		RemotableChatClientItf remoteClientInterface;
		try
		{
			remoteClientInterface = (RemotableChatClientItf) UnicastRemoteObject.exportObject(client, 0);
			client.join("127.0.0.1", 1099);
			try
			{
				ChatServerItf csi = (ChatServerItf) client.getRegistry().lookup(SERVER_JREFERENCE);
			} catch (RemoteException | NotBoundException e)
			{
				System.out.println("Error while looking for server.");
				e.printStackTrace();
			}

			while (true)
			{
				s = null;
				s = sc.nextLine();
				if (s.contains("exit"))
				{
					break;
				}
				client.sendMessage(new Message(s, client.getUsername()));
			}
			System.out.println("Leave procedure engaged");
			client.leave();
			System.exit(0);
		} catch (RemoteException e1)
		{
			System.out.println("Client main error : " + e1);
			e1.printStackTrace();
		}

		sc.close();
	}	//------ Fin de main()

}
