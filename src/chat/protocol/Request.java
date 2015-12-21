package chat.protocol;

import javax.net.ssl.SSLEngineResult.Status;

public class Request
{
	private String user;
	private String message;
	private String status;

	// Constructeur minimal -> La requête est identifiée
	public Request(String u, String s)
	{
		this.user = new String(u);
		this.status = new String(s);
	}

	// Constructeur pour un message
	public Request(String u, String s, String m)
	{
		this(u, s);
		this.message = m;
	}

	public enum STATUS {
		MESSAGE, JOIN, LEAVE
	}

}
