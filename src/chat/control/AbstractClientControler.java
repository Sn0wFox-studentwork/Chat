package chat.control;

import java.io.Serializable;

import chat.client.AbstractClient;
import chat.protocol.Message;
import chat.view.ViewClient;

public abstract class AbstractClientControler implements Serializable
{
	// ---------------------------------------------------- Attributs
	protected AbstractClient client;
	
	// ---------------------------------------------------- Constructeur
	public AbstractClientControler()
	{
		client = null;
	}
	
	// ---------------------------------------------------- Methodes publiques
	public void updateChat(Message msg)
	{
		client.printMessage(msg);
	}
	
	public void removeClient()
	{
		client.leave();
		client = null;
	}
	
	public void sendMessageToClient(Message msg)
	{
		System.out.println(client.getUsername());
		System.out.println("sendMessageToClient recois le message : " + msg);
		System.out.println("on lenvoit a client.sendMessage");
		client.sendMessage(msg);
		// TODO : fonctionne avec notifyObserver
	}
	
	// ---------------------------------------------------- Methodes publiques abstraites
	public abstract AbstractClient addClient(	String username,
												String hostIP,
												int port,
												ViewClient view);

	
	// ---------------------------------------------------- Methodes protegees
	protected void setClient(AbstractClient c)
	{
		client = c;
	}
	
}
