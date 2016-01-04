package chat.view;

import chat.protocol.Message;

public interface Observer
{
	/**
	 * Mettre à jour un message
	 * @param msg Message à mettre à jour
     */
	public void update(String msg);
}
