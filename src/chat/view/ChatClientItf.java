package chat.view;

import chat.protocol.Message;

/**
 * Interface permettant la mise en place du pattern MVC.
 * Elle definit toutes les methodes qu'un client du chat doit fournir.
 */
public interface ChatClientItf
{
	/**
	 *  Se connecter au chat
	 */
	public void join();

	/**
	 * Se deconnecter du chat
	 */
	public void leave();

	/**
	 * Envoyer un message
	 * @param msg Message à envoyer
     */
	public void sendMessage(String msg);

	/**
	 * Afficher un message
	 * @param msg Message à afficher
     */
    public void printMessage(Message msg);

	/**
	 * Changer de pseudo
	 * @param newUsername Pseudo à changer
     */
    public void setUsername(String newUsername);
}
