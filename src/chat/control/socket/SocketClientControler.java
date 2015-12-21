package chat.control.socket;

import chat.client.AbstractClient;
import chat.client.socket.ChatClientSocket;
import chat.control.AbstractClientControler;
import chat.protocol.Message;
import chat.view.ViewClient;

public class SocketClientControler extends AbstractClientControler
{

	// ---------------------------------------------------- Surcharges methodes publiques
	@Override
	public AbstractClient addClient(String username, String hostIP, int port, ViewClient view)
	{
		ChatClientSocket client = new ChatClientSocket(username);
		setClient(client);
		return client;
	}

	@Override
	public void sendMessageToClient(Message msg)
	{
		// TODO Auto-generated method stub
	}

}
