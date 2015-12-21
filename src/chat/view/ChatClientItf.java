package chat.view;

import chat.protocol.Message;

public interface ChatClientItf
// Interface permettant la mise en place du pattern MVC.
// Elle definie toutes les methodes qu'un client du chat doit fournir.
{
	public void join();								// Se connecter au chat
	public void leave();							// Se deconnecter du chat
	public void sendMessage(String msg);			// Envoyer un message
	public void printMessage(Message msg);			// Afficher un message
	public void setUsername(String newUsername);	// Changer de pseudo
}
