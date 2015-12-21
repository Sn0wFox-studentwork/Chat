package socketChat.client;

import java.io.*;
import java.net.*;
import java.lang.*;

public class ReceptionClientThread extends Thread {
  
  private EchoClient echoClient;
  private Socket echoSocket;
  
  ReceptionClientThread(EchoClient echoClient, Socket echoSocket) {
    this.echoSocket = echoSocket;
    this.echoClient = echoClient;
  }

  /**
    * receives a request from client then sends an echo to the client
    * @param clientSocket the client socket
    **/
  public void run() {
        try {
        BufferedReader socIn = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));

        while (true) {
          String line = socIn.readLine();
          if (line.length() != 0)
          {
            System.out.println("Echo:" + line);
          }
        }

        } catch (Exception e) {
            System.err.println("Error in EchoServer:" + e); 
        }


  }
  
}

  
