package chat.client.socket;

import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;

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

	public void run()
	{
		try
		{
			BufferedReader socIn = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			System.out.println("Un ReceptionClientThread a ete lance");
			while (true)
			{
				String line = socIn.readLine();
				System.out.println("ReceptionClientThread recoit le message : " + line);
				System.out.println("On le transmet a client.printMessage");
				chatClientSocket.printMessage(line);
				if (line.length() != 0)
				{
					System.out.println("Echo:" + line);
				}
			}

		}
		catch (Exception e)
		{
			System.err.println("Le serveur a ete deconnecte");
			JOptionPane.showMessageDialog(null, "Le serveur a ete deconnecter. "
					+ "Veuillez le reconnecter puis vous reconnecter.",
					"Deconnexion serveur",
					JOptionPane.YES_OPTION);
			System.exit(1);
		}

	}

}
