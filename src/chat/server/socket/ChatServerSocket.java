package chat.server.socket;

import java.io.*;
import java.net.*;
import java.util.LinkedList;

public class ChatServerSocket
{
	public static LinkedList<Socket> clientList;

	/**
	 * main method
	 * 
	 * @param ChatServerSocket
	 *            port
	 * 
	 **/
	public static void main(String args[])
	{
		ServerSocket listenSocket;
		clientList = new LinkedList<Socket>();
		ChatServerSocket es = new ChatServerSocket();

		if (args.length != 1)
		{
			System.out.println("Usage: java ChatServerSocket <ChatServerSocket port>");
			System.exit(1);
		}

		try
		{
			listenSocket = new ServerSocket(Integer.parseInt(args[0])); // port
			System.out.println("Server ready...");
			while (true)
			{
				Socket clientSocket = listenSocket.accept(); // Bloquant
																// jusqu'à
																// nouveau
																// client
				clientList.add(clientSocket);
				System.out.println("Connexion from:" + clientSocket.getInetAddress());
				ReceptionServerThread rst = new ReceptionServerThread(es, clientSocket);
				rst.start();
			}
		} catch (Exception e)
		{
			System.err.println("Error in ChatServerSocket:" + e);
		}
	}

	public void sendMessage(String line)
	{
		try
		{
			PrintStream socOut = null;
			for (Socket cs : ChatServerSocket.clientList)
			{
				try
				{
					socOut = new PrintStream(cs.getOutputStream());
					socOut.println(line + "\n");
				} catch (Exception e)
				{
					System.err.println("Error in sendMessageToAll:" + e);
				}
			}
		} catch (Exception e)
		{
			System.err.println("Error in ChatServerSocket:" + e);
		}
	}

}
