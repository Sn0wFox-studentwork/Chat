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

	/**
	 *
	 * @param pseudo Nom d'utilisateur du client
     */
	public AbstractClient(String pseudo)
	{
		username = pseudo;
		observersList = new LinkedList<Observer>();
	}

    /**
     * Recupere la liste d'observateurs du client
     * @return liste d'observateurs
     */
	public LinkedList<Observer> getObserversList()
	{
		return observersList;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	// ---------------------------------------------------- Methodes publiques abstraites
    /**
     * Cette methode permet au client de rejoindre un serveur
     * @param hostIP IP de l'hôte
     * @param port Port choisi pour la communication
     */
    public abstract void join(String hostIP, int port);		// Se connecter au chat

    /**
     * Cette méthode arrête la communication du client avec le serveur
     */
    public abstract void leave();							// Se deconnecter du chat

    /**
     * Envoie un message à partir du client
     * @param msg Message à envoyer
     */
    public abstract void sendMessage(Message msg);			// Envoyer un message

    /**
     * Affiche un message dans le client
     * @param msg Message à afficher
     */
    public abstract void printMessage(String msg);			// Afficher un message

    /**
     * Change le nom de l'utilisateur
     * @param newUsername Nouveau nom d'utilisateur
     */
    public abstract void setUsername(String newUsername);	// Changer de pseudo
	
	// ---------------------------------------------------- Implementations interfaces
    /**
     * Ajoute un observateur à la liste d'observateurs du client
     * @param obs Observateur à ajouter
     */
    @Override
	public void addObserver(Observer obs)
	{
		observersList.add(obs);
	}

    /**
     * Supprime un observateur de la liste d'observateurs du client
     * @param obs Observateur à supprimer
     */
	@Override
	public void removeObserver(Observer obs)
	{
		observersList.remove(obs);
	}

    /**
     * Notifie la liste d'observateurs d'un changement de message
     * @param msg Message à notifier
     */
	@Override
	public void notifyObserver(String msg)
	{
		for(Observer obs : observersList)
		{
			obs.update(msg);
		}
	}
}
