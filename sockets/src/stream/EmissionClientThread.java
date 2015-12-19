/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

import java.io.*;
import java.net.*;
import java.lang.*;

public class EmissionClientThread extends Thread {
  
  private EchoClient echoClient;
  private Socket echoSocket;
  
  EmissionClientThread(EchoClient echoClient, Socket echoSocket) {
    this.echoSocket = echoSocket;
    this.echoClient = echoClient;
  }

  /**
    * receives a request from client then sends an echo to the client
    * @param clientSocket the client socket
    **/
  public void run() {
      try {
      PrintStream socOut = new PrintStream(echoSocket.getOutputStream());
      BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

      String line;
      while (true) {
        line=stdIn.readLine(); // Bloquant jusqu'à ligne entrée
        if (line.equals(".")) break;
        socOut.println(line);
      }

      socOut.close();
      stdIn.close();
      echoSocket.close();

      } catch (Exception e) {
          System.err.println("Error in EchoServer:" + e); 
      }


  }
  
}

  
