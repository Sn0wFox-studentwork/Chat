package chat.protocol;

import java.io.Serializable;
import java.sql.Timestamp;

public class Message implements Serializable {

	public String message;
	public String username;
	public Timestamp time;
	
	public Message(String m, String u) {
		this.message = m;
		this.username = u;
		this.time = new Timestamp(System.currentTimeMillis());
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String toString()
	{
		return "[" + time.getHours() + ":" + time.getMinutes() + "] " + username + " : " + message;
	}

}
