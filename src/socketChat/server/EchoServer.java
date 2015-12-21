package socketChat.server;

import java.io.*;
import java.net.*;
import java.util.LinkedList;

public class EchoServer  {
    public static LinkedList<Socket> clientList;

    /**
    * main method
    * @param EchoServer port
    * 
    **/
    public static void main(String args[]) { 
      ServerSocket listenSocket;
      clientList = new LinkedList<Socket>();
      EchoServer es = new EchoServer();
        
      if (args.length != 1) {
            System.out.println("Usage: java EchoServer <EchoServer port>");
            System.exit(1);
      }

      try {
        listenSocket = new ServerSocket(Integer.parseInt(args[0])); //port
        System.out.println("Server ready..."); 
        while (true) {
          Socket clientSocket = listenSocket.accept(); // Bloquant jusqu'à nouveau client
          clientList.add(clientSocket);
          System.out.println("Connexion from:" + clientSocket.getInetAddress());
          ReceptionServeurThread rst = new ReceptionServeurThread(es, clientSocket);
          rst.start();
        }
      }      
      catch (Exception e) {
            System.err.println("Error in EchoServer:" + e);
      }
    }

    public void sendMessage(String line) {
      try {
        PrintStream socOut = null;
        for (Socket cs : EchoServer.clientList) {
          try {  
            socOut = new PrintStream(cs.getOutputStream());
            socOut.println(line + "\n");
          }
          catch (Exception e) {
                System.err.println("Error in sendMessageToAll:" + e);
          }
        }
      }
      catch (Exception e) {
        System.err.println("Error in EchoServer:" + e); 
      }
    }

}

  
