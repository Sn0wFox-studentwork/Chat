package socketChat.server;

import java.io.*;
import java.net.*;
import java.lang.*;

public class ReceptionServeurThread extends Thread {
  
  private Socket clientSocket;
  private EchoServer es;
  
  ReceptionServeurThread(EchoServer es, Socket s) {
    this.clientSocket = s;
    this.es = es;
  }

  /**
    * receives a request from client then sends an echo to the client
    * @param clientSocket the client socket
    **/
  public void run() {
        try {
        BufferedReader socIn = null;
        socIn = new BufferedReader(
          new InputStreamReader(clientSocket.getInputStream()));    
        PrintStream socOut = new PrintStream(clientSocket.getOutputStream());
        while (true) {
          String line = socIn.readLine(); // Bloquant jusqu'à ligne entrée
          es.sendMessage(line);
        }
      } catch (Exception e) {
          System.err.println("Error in EchoServer:" + e); 
        }
  }
  
}

  
