package hust.projectair.objectGame;


import hust.projectair.common.ClientManagement;
import hust.projectair.game.AirGame;

/**
 * Created by hieutrinh on 10/9/15.
 */
public class Admin implements ClientManagement {


    private AirGame airGame;

    public Admin(AirGame mGame)
    {
        airGame = mGame;
    }


    @Override
    public void joinGame(String ipClient) {
        airGame.joinGame(ipClient);
    }


    @Override
    public void quitGame(String ipClient) {
        airGame.quitGame(ipClient);
    }

    @Override
    public void startGame(String ipServer) {

    }


    @Override
    public void setStatusForClient(String ipClient, int status) {
        airGame.setStatusForClient(ipClient, status);
    }
}
