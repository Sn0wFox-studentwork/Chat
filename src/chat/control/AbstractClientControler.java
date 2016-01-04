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

	/**
	 * Met à jour le message dans le client
	 * @param msg Message à mettre à jour
     */
	public void updateChat(Message msg)
	{
		client.printMessage(msg.toString());
	}

	/**
	 * Supprime une vue de la liste des observateurs du client
	 * @param view Vue à supprimer
     */
	public void removeClient(ViewClient view)
	{
		client.removeObserver(view);
		client.leave();
		client = null;
	}

	/**
	 * Envoie un message au client pour être envoyé au serveur
	 * @param msg Message à envoyer
     */
	public void sendMessageToClient(Message msg)
	{
		System.out.println(client.getUsername());
		System.out.println("AbstractClientControler.sendMessageToClient recois le message : " + msg);
		System.out.println("on l'envoit a client.sendMessage");
		client.sendMessage(msg);
	}
	
	// ---------------------------------------------------- Methodes publiques abstraites
	/**
	 * Construit une instance de AbstractClient
	 * @param username Nom d'utilisateur du client
	 * @param hostIP IP de l'hôte
	 * @param port Port de communication
	 * @param view Vue (graphique) du client
	 * @return instance de AbstractClient
	 */
	public abstract AbstractClient addClient(	String username,
												String hostIP,
												int port,
												ViewClient view);

	/**
	 * Intiliase les composants de la vue du controleur
	 * @param view
     */
	public abstract void initEnable(ViewClient view);

	
	// ---------------------------------------------------- Methodes protegees

	/**
	 * Assigne un client au controleur
	 * @param c
     */
	protected void setClient(AbstractClient c)
	{
		client = c;
	}
	
}
