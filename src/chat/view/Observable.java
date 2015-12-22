package chat.view;

import chat.protocol.Message;

public interface Observable
// Permet la mise en place du Pattern MVC.
// Rend un objet surveillable.
{
	public void addObserver(Observer obs);
	public void removeObserver(Observer obs);
	public void notifyObserver(String msg);
}
