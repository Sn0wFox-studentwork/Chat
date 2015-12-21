package chat.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import chat.client.rmi.RemotableChatClientItf;
import chat.protocol.Message;
import chat.view.Observable;
import chat.view.Observer;

public abstract class AbstractClient implements Observable, RemotableChatClientItf, Serializable
{
	// ---------------------------------------------------- Attributs
	protected String username;
	protected LinkedList<Observer> observersList;
	
	// ---------------------------------------------------- Constructeur
	public AbstractClient(String pseudo)
	{
		username = pseudo;
		observersList = new LinkedList<Observer>();
	}
	
	public LinkedList<Observer> getObserversList()
	{
		return observersList;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	// ---------------------------------------------------- Methodes publiques abstraites
	public abstract void join(String hostIP, int port);		// Se connecter au chat
	public abstract void leave();							// Se deconnecter du chat
	public abstract void sendMessage(Message msg);			// Envoyer un message
	public abstract void printMessage(Message msg);			// Afficher un message
	public abstract void setUsername(String newUsername);	// Changer de pseudo
	
	// ---------------------------------------------------- Implementations interfaces
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
		for(Observer obs : observersList)
		{
			obs.update(msg);
		}
	}
}
