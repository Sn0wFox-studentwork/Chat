package chat.server.socket;

import java.io.*;
import java.net.*;
import java.lang.*;

public class ReceptionServerThread extends Thread
{
	// ---------------------------------------------------- Attributs
	private Socket clientSocket;
	private ChatServerSocket es;

	// ---------------------------------------------------- Constructeur
	ReceptionServerThread(ChatServerSocket es, Socket s)
	{
		this.clientSocket = s;
		this.es = es;
	}

	// ---------------------------------------------------- Methodes publiques
	public void run()
	{
		try
		{
			System.out.println("Un receptionServerThread est lance");
			BufferedReader socIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
			while (true)
			{
				String line = socIn.readLine(); // Bloquant jusqu'a ligne entree
				System.out.println("ReceptionServerThread recoit le message : " + line);
				System.out.println("On l'envoit a sendMessageToAll");
				es.sendMessageToAll(line);
			}
		}
		catch (Exception e)
		{
			System.err.println("Error in ReceptionServerThread : " + e);
			e.printStackTrace();
		}
	}

}
