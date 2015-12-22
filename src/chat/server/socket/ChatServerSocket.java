package chat.server.socket;

import java.io.*;
import java.net.*;
import java.util.LinkedList;

import chat.client.rmi.RemotableChatClientItf;
import chat.protocol.Message;

public class ChatServerSocket
{
	// ---------------------------------------------------- Attributs
	private LinkedList<Socket> clientList;
	private ServerSocket listenSocket;
	
	// ---------------------------------------------------- Constructeur
	public ChatServerSocket(int port)
	{
		clientList = new LinkedList<Socket>();
		try
		{
			listenSocket = new ServerSocket(port);
			System.out.println("Server ready on port " + port);
		}
		catch (IOException e)
		{
			System.err.println("Eroor while creating server : " + e); 
			e.printStackTrace();
		}
	}
	
	
	// ---------------------------------------------------- Methodes publiques
	public void sendMessageToAll(String msg)
	{
		System.out.println("sendMessageToAll recoit le message : " + msg);
		try
		{
			PrintStream socOut = null;
			System.out.println("Il va etre transfere a " + clientList.size() + " clients");
			for (Socket cs : clientList)
			{
				try
				{
					socOut = new PrintStream(cs.getOutputStream());
					System.out.println("On transmet le message a un client via ecriture dans sa socket");
					System.out.println("C'est le ReceptionClientThread qui devrait le recevoir");
					socOut.println(msg);
				}
				catch (Exception e)
				{
					System.err.println("Error in sendMessageToAll : " + e);
					e.printStackTrace();
				}
			}
		}
		catch (Exception e)
		{
			System.err.println("Error in sendMessageToAll : " + e);
			e.printStackTrace();
		}
	}
	
	public void addChatClient()
	{
		try
		{
			while (true)
			{
				Socket clientSocket = listenSocket.accept(); // Bloquant jusqu'a nouveau client
				clientList.add(clientSocket);
				System.out.println("Connexion from : " + clientSocket.getInetAddress());
				ReceptionServerThread rst = new ReceptionServerThread(this, clientSocket);
				rst.start();
			}
		} catch (Exception e)
		{
			System.err.println("Error in addChatClient : " + e);
			e.printStackTrace();
		}
	}
	
	public void removeChatClient(Socket user)
	{
		clientList.remove(user);
	}
	
	
	// ---------------------------------------------------- Fonction principale
	public static void main(String args[])
	{
		ChatServerSocket es;

		if (args.length != 1)
		{
			System.out.println("Usage: java ChatServerSocket <ChatServerSocket port>");
			System.exit(1);
		}

		es = new ChatServerSocket(Integer.parseInt(args[0]));
		es.addChatClient();

	}

}
