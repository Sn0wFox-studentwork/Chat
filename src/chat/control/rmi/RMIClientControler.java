package chat.control.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import chat.client.AbstractClient;
import chat.client.rmi.ChatClientRMI;
import chat.client.rmi.RemotableChatClientItf;
import chat.control.AbstractClientControler;
import chat.view.ViewClient;

public class RMIClientControler extends AbstractClientControler
{
	
	// ---------------------------------------------------- Constructeur
	public RMIClientControler()
	{
		super();
	}
	
	// ---------------------------------------------------- Surcharges methodes publiques
	@Override
	public AbstractClient addClient(String username, String hostIP,
									int port, ViewClient view)
	{
		ChatClientRMI c = new ChatClientRMI(username);
		setClient(c);
		client.addObserver(view);
		RemotableChatClientItf remoteClientInterface;
		try
		{
			remoteClientInterface = (RemotableChatClientItf) UnicastRemoteObject.exportObject(client, 0);
			client.join(hostIP, port);
			view.getConnectionButton().setEnabled(false);
		}
		catch (RemoteException e)
		{
			System.out.println("RMIClientControler addClient error : " + e);
			e.printStackTrace();
		}
		return client;
	}

	@Override
	public void initEnable(ViewClient view)
	{
		view.getHostField().setEnabled(false);
		view.getPortField().setEnabled(false);
		view.setTitle("RMI chat");
	}

}
