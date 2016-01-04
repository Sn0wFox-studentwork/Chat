package chat.client.socket;

import java.io.*;
import java.net.*;

import chat.client.AbstractClient;
import chat.protocol.Message;
import chat.server.socket.ReceptionServerThread;
import chat.view.Observer;

public class ChatClientSocket extends AbstractClient
{

	// ---------------------------------------------------- Attributs
    private Socket echoSocket;
	private EmissionClientThread ect;
	private ReceptionClientThread rct;

	// ---------------------------------------------------- Constructeur
    /**
     * Constructeur du ChatClientSocket
     * @param username Nom d'utilisateur du client
     */
    public ChatClientSocket(String username)
	{
		super(username);
		echoSocket = null;
		ect= null;
		rct = null;
	}

	// ---------------------------------------------------- Implementations interfaces publiques
	@Override
	public void join(String hostIP, int port)
	{
		try
		{
			// creation socket ==> connexion
			echoSocket = new Socket(hostIP, port);
			rct = new ReceptionClientThread(echoSocket);
			System.out.println("Un ReceptionClientThread a ete cree");
			rct.start();
		}
		catch (UnknownHostException e)
		{
			System.err.println("Don't know about host:" + hostIP);
			System.exit(1);
		}
		catch (IOException e)
		{
			System.err.println("Couldn't get I/O for the connection to:" + hostIP);
			System.exit(1);
		}
	}

	@Override
	public void leave()
	{
		rct.stop();				// TODO : deprecated
		try
		{
			echoSocket.close();
		}
		catch (IOException e)
		{
			System.out.println("Error while trying to close the socket of the client " + username);
			e.printStackTrace();
		}
	}

	@Override
	public void sendMessage(Message msg)
	{
		System.out.println("Le client " + username + " recois le message : " + msg);
		try
		{
			System.out.println("On l'ecrit dans sa socket pour le transferer au ReceptionServerThread");
			PrintStream socOut = new PrintStream(echoSocket.getOutputStream());
			if(socOut.checkError())
			{
				
			}
			socOut.println(msg);
		}
		catch (IOException e)
		{
			System.err.println("Error in ChatClientSocket sendMessage : " +e);
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void printMessage(String msg)
	{
		System.out.println("client.printMessage recoit le message :");
		System.out.println(msg);
		System.out.println("On le tansmet a client.notifyObserver");
		notifyObserver(msg);
	}

	@Override
	public void setUsername(String newUsername)
	{
		username = newUsername;
	}

	@Override
	public void addObserver(Observer obs)
	{
		observersList.add(obs);
	}

	@Override
	public void removeObserver(Observer obs)
	{
		observersList.remove(obs);
	}

	@Override
	public void notifyObserver(String msg)
	{
		System.out.println("client.notifyObserver recoit le message : " + msg);
		System.out.println(observersList.size() + " vues vont etre mises a jour pour " + username);
		for(Observer obs : observersList)
		{
			obs.update(msg);
		}
	}

	// ---------------------------------------------------- Fonction principale
	public static void main(String[] args) throws IOException
	{
		ChatClientSocket chatClientSocket = new ChatClientSocket("TODO");
		Socket echoSocket = null;
		EmissionClientThread ect = null;
		ReceptionClientThread rct = null;

		if (args.length != 2)
		{
			System.out.println("Usage: java ChatClientSocket <ChatServerSocket host> <ChatServerSocket port>");
			System.exit(1);
		}

		try
		{
			// creation socket ==> connexion
			echoSocket = new Socket(args[0], new Integer(args[1]).intValue());
			ect = new EmissionClientThread(echoSocket);
			rct = new ReceptionClientThread(echoSocket);
			ect.start();
			rct.start();
		} catch (UnknownHostException e)
		{
			System.err.println("Don't know about host:" + args[0]);
			System.exit(1);
		} catch (IOException e)
		{
			System.err.println("Couldn't get I/O for the connection to:" + args[0]);
			System.exit(1);
		}

	}
}
