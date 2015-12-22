package chat.client.socket;

import java.io.*;
import java.net.*;
import java.lang.*;

/* NOTES :
 * ... implements Java.lang.Runnable
	� Avantages : L'h�ritage reste possible.
	� Inconv�nients : Les attributs de votre classe sont
	partag�s par tous les threads qui y sont bas�s (parfois un atout)
 *
 * ... extends Java.lang.Thread
	� Avantages : Chaque thread a ses donn�es qui lui sont propres.
	� Inconv�nients : On ne peut plus h�riter d'une autre classe.
 */

public class EmissionClientThread extends Thread
{
	// ---------------------------------------------------- Attributs
	private ChatClientSocket chatClientSocket;
	private Socket echoSocket;

	// ---------------------------------------------------- Constructeur
	EmissionClientThread(ChatClientSocket chatClientSocket, Socket echoSocket)
	{
		this.echoSocket = echoSocket;
		this.chatClientSocket = chatClientSocket;
	}

	// ---------------------------------------------------- Methodes publiques
	public void run()
	{
		try
		{
			PrintStream socOut = new PrintStream(echoSocket.getOutputStream());
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

			String line;
			while (true)
			{
				line = stdIn.readLine(); // Bloquant jusqu'a ligne entree
				if (line.equals("."))
					break;
				socOut.println(line);
			}

			socOut.close();
			stdIn.close();
			echoSocket.close();

		} catch (Exception e)
		{
			System.err.println("Error in EmissionClientThread:" + e);
		}

	}

}
