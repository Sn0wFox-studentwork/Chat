package chat.protocol;

import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;

public class Message implements MessageItf {

	public String message;
	public String username;
	// TODO
	// Add timestamp to every message
	
	public Message(String m, String u, int t) {
		this.message = m;
		this.username = u;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getUsername() {
		return username;
	}

}
