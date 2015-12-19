/***
 * Request
 */

public class Request  {
  private String user;
  private String message;
  private Status status;

  // Constructeur minimal -> La requête est identifiée
  public Request(String u, String s) {
    this.user = u;
    this.status = s;
  }

  // Constructeur pour un message
  public Request(String u, String s, String m) {
    super(u, s);
    this.message = m;
  }

public enum STATUS {
  MESSAGE, JOIN, LEAVE
}

  
