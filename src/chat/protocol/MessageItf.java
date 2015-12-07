package chat.protocol;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MessageItf extends Remote {
	String getMessage() throws RemoteException;
	String getUsername() throws RemoteException;
}
