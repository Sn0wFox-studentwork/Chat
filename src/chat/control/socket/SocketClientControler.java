package chat.control.socket;

import chat.client.AbstractClient;
import chat.client.socket.ChatClientSocket;
import chat.control.AbstractClientControler;
import chat.protocol.Message;
import chat.view.ViewClient;

public class SocketClientControler extends AbstractClientControler
{
	// ---------------------------------------------------- Constructeur
	public SocketClientControler()
	{
		super();
	}
	
	// ---------------------------------------------------- Surcharges methodes publiques
	@Override
	public AbstractClient addClient(String username, String hostIP, int port, ViewClient view)
	{
		ChatClientSocket c = new ChatClientSocket(username);
		setClient(c);
		client.addObserver(view);
		client.join(hostIP, port);
		return client;
	}

	@Override
	public void initEnable(ViewClient view)
	{
		view.getHostField().setEnabled(true);
		view.getHostField().setText("127.0.0.1");
	}

}
