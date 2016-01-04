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
		this.setDaemon(true);
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
				if (line == null) break;		// C'est qu'on a engage la deconnexion
				System.out.println("ReceptionServerThread recoit le message : " + line);
				System.out.println("On l'envoit a sendMessageToAll");
				es.sendMessageToAll(line);
			}
			// Procédure normale de déconnexion
			socIn.close();
			socOut.close();
			// Fermeture du thread en fermant les sockets
			es.removeChatClient(clientSocket);
			clientSocket.close();
			System.out.println("Fin du thread de reception serveur");
		}
		catch (SocketException e)
		{
			System.out.println("Fermeture forcee du ReceptionServerThread");
		}
		catch (Exception e)
		{
			System.err.println("Error in ReceptionServerThread : " + e);
			e.printStackTrace();
		}
		// Procédure en cas de fermeture forcée
		finally
		{
			if(clientSocket != null)
			{
				try
				{
					clientSocket.close();
				} catch (IOException e)
				{
					System.err.println("Error in ReceptionServerThread while trying to close socket : " + e);
					e.printStackTrace();
				}
			}
		}
	}

}
