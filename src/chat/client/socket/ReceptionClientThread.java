package chat.client.socket;

import java.io.*;
import java.net.*;
import java.lang.*;

public class ReceptionClientThread extends Thread
{

	private ChatClientSocket chatClientSocket;
	private Socket echoSocket;

	ReceptionClientThread(ChatClientSocket chatClientSocket, Socket echoSocket)
	{
		this.echoSocket = echoSocket;
		this.chatClientSocket = chatClientSocket;
	}

	/**
	 * receives a request from client then sends an echo to the client
	 * 
	 * @param clientSocket
	 *            the client socket
	 **/
	public void run()
	{
		try
		{
			BufferedReader socIn = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));

			while (true)
			{
				String line = socIn.readLine();
				if (line.length() != 0)
				{
					System.out.println("Echo:" + line);
				}
			}

		} catch (Exception e)
		{
			System.err.println("Error in ChatServerSocket:" + e);
		}

	}

}
