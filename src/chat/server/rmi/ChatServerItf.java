package chat.server.rmi;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import chat.client.AbstractClient;
import chat.client.rmi.ChatClientRMI;
import chat.client.rmi.RemotableChatClientItf;
import chat.protocol.Message;

public interface ChatServerItf extends Remote
{

	/**
	 * Envoie le Message msg � tout les clients connect�s
	 * @param msg le message � envoyer aux clients
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	void sendMessageToAll(Message msg) throws RemoteException, NotBoundException;

	/**
	 * Ajoute le client user � la liste des clients
	 * Si ce client existe d�j�, il sera de nouveau ajout�
	 * @param user un objet impl�mentant l'interface RemotableChatClientItf et qui repr�sente un client du chat
	 * @throws RemoteException
	 */
	void addChatClient(RemotableChatClientItf user) throws RemoteException;

	/**
	 * Supprime le client user du chat
	 * Ne supprime pas les doublons
	 * Ne change rien si user n'existe pas
	 * @param user un objet impl�mentant l'interface RemotableChatClientItf et qui repr�sente un client du chat
	 * @throws RemoteException
	 */
	void removeChatClient(RemotableChatClientItf user) throws RemoteException;
}
