package hust.projectair.common;



import java.io.Serializable;


public interface GameManagement extends Serializable {



	public void startGame(String ipServer);

	public void notifiyClientQuitGame(String ipClient);

	public void notifyClientJoinGame(String ipClient);

	public void passInformationClient(String ipClient, String username, int typeClient, int statusClient, int typeAir, String description, float x, float y, int power, int protect);

	public void notifyChangeStatus(String ipClient, int status);

	public void notifyChangeRotation(String ipClient, float rotation, float x, float y);

	public void notifyChangeVelocity(String ipClient, float velocityX, float velocityY, float x, float y);

	public void notifyChangePosition(String ipClient, float rotation, float velocityX, float velocityY, float x, float y);

	public void notifyChangePower(String ipClient, int power);

	public void notifyChangeProtect(String ipClient, int protect);

	public boolean notifyFire(String ipClient);


}

