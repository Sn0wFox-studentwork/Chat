/***
 * ClientThread
 * Example of a TCP server
 * Date: 14/12/08
 * Authors:
 */

import java.io.*;
import java.net.*;
import java.lang.*;

public class EmissionThread extends Thread {
  
  private Socket clientSocket;
  
  ClientThread(Socket s) {
    this.clientSocket = s;
  }

  public void sendMessageToAll(String m) {
    PrintStream socOut;
    for (Socket cs : EchoServer.clientList) {
      try {
        socOut = new PrintStream(cs.getOutputStream());
        socOut.println(m);
      }    
      catch (Exception e) {
            System.err.println("Error in sendMessageToAll:" + e);
      }
    }
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
          String line = socIn.readLine();
          System.out.println(line);
          socOut.println(line);
        }
      } catch (Exception e) {
          System.err.println("Error in EchoServer:" + e); 
        }
       }
  
  }

  
