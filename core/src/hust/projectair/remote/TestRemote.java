package hust.projectair.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TestRemote extends Remote{

	public boolean login(String username) throws RemoteException;
}
