package hust.projectair.common;


import java.io.Serializable;


public interface ClientManagement extends Serializable {


    public void joinGame(String ipClient);

    public void quitGame(String ipClient);

    public void startGame(String ipServer);

    public void setStatusForClient(String ipClient, int status);

}
