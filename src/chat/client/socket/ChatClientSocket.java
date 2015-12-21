package chat.client.socket;

import java.io.*;
import java.net.*;

import chat.client.AbstractClient;
import chat.protocol.Message;

public class ChatClientSocket extends AbstractClient
{

	public ChatClientSocket(String username)
	{
		super(username);
	}

	/**
	 * main method accepts a connection, receives a message from client then
	 * sends an echo to the client
	 **/
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
			ect = new EmissionClientThread(chatClientSocket, echoSocket);
			rct = new ReceptionClientThread(chatClientSocket, echoSocket);
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

	@Override
	public void join(String hostIP, int port)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void leave()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(Message msg)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void printMessage(Message msg)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUsername(String newUsername)
	{
		// TODO Auto-generated method stub
		
	}
}
