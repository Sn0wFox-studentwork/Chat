package socketChat;

import java.io.*;
import java.net.*;
import java.lang.*;

public class EmissionClientThread extends Thread {
  
  private EchoClient echoClient;
  private EchoSocket echoSocket;
  
  EmissionThread(EchoClient echoClient, Socket echoSocket) {
    this.echoSocket = echoSocket;
    this.echoClient = echoClient;
  }

  /**
    * receives a request from client then sends an echo to the client
    * @param clientSocket the client socket
    **/
  public void run() {
        try {
        PrintStream socOut = null;
        BufferedReader stdIn = null;
        BufferedReader socIn = null;

        socIn = new BufferedReader(
                      new InputStreamReader(.getInputStream()));    
        socOut= new PrintStream(echoSocket.getOutputStream());
        stdIn = new BufferedReader(new InputStreamReader(System.in));
        socIn = new BufferedReader(
          new InputStreamReader(echoSocket.getInputStream()));
        
      } catch (Exception e) {
          System.err.println("Error in EchoServer:" + e); 
      }

      String line;
      while (true) {
        line=stdIn.readLine(); // Bloquant jusqu'à ligne entrée
        if (line.equals(".")) break;
        socOut.println(line);
        System.out.println("ECHO:" + socIn.readLine());
      }

      socOut.close();
      socIn.close();
      stdIn.close();
      echoSocket.close();


  }
  
}

  
