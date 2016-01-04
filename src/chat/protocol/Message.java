package chat.protocol;

import java.io.Serializable;
import java.sql.Timestamp;

public class Message implements Serializable
{

	public String message;
	public String username;
	public Timestamp time;

	/**
	 *
	 * @param m Contenu du message
	 * @param u Nom d'utilisateur du message
     */
	public Message(String m, String u)
	{
		this.message = m;
		this.username = u;
		this.time = new Timestamp(System.currentTimeMillis());
	}


	/**
	 * Retourne le contenu du message
	 * @return Contenu du message
     */
	public String getMessage()
	{
		return message;
	}

	/**
	 * Retourne le nom d'utilisateur
	 * @return Nom d'utilisateur
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * Spécification de toString permettant l'affichage du temps,
	 * le nom d'utilisateur et le contenu d'un message
	 * @return String affichant tout l'objet Message
     */
	public String toString()
	{
		return "[" + time.getHours() + ":" + time.getMinutes() + "] " + username + " : " + message;
	}

}
