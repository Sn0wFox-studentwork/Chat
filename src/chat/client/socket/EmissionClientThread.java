package chat.client.socket;

import java.io.*;
import java.net.*;
import java.lang.*;

/* NOTES :
 * ... implements Java.lang.Runnable
	Avantages : L'heritage reste possible.
	Inconvenients : Les attributs de votre classe sont
	partages par tous les threads qui y sont bases (parfois un atout)
 *
 * ... extends Java.lang.Thread
	Avantages : Chaque thread a ses donnees qui lui sont propres.
	Inconvenients : On ne peut plus heriter d'une autre classe.
 */

public class EmissionClientThread extends Thread
{
	// ---------------------------------------------------- Attributs
	private Socket echoSocket;

	// ---------------------------------------------------- Constructeur
	/**
	 *
	 * @param echoSocket Socket d'émission de message
	 */
	EmissionClientThread(Socket echoSocket)
	{
		this.echoSocket = echoSocket;
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
