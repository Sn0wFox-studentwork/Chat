package chat.view;

import chat.protocol.Message;

public interface Observable
// Permet la mise en place du Pattern MVC.
// Rend un objet surveillable.
{
	/**
	 * Ajoute un observateur
	 * @param obs Observateur à ajouter
     */
	public void addObserver(Observer obs);

	/**
	 * Supprime un observateur
	 * @param obs Observateur à supprimer
     */
    public void removeObserver(Observer obs);

	/**
	 * Notifie un observateur d'un nouveau message
	 * @param msg Message à notifier
     */
    public void notifyObserver(String msg);
}
