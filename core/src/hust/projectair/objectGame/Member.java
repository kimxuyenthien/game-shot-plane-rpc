package hust.projectair.objectGame;


import hust.projectair.common.GameManagement;

/**
 * Created by hieutrinh on 10/10/15.
 */
public class Member implements GameManagement {


    private Client client;

    public Member(Client client)
    {
        this.client = client;
    }


    @Override
    public void startGame(String ipServer) {
        client.startGame(ipServer);
    }

    @Override
    public void notifiyClientQuitGame(String ipClient) {
        client.notifiyClientQuitGame(ipClient);
    }

    @Override
    public void notifyClientJoinGame(String ipClient) {
        client.notifyClientJoinGame(ipClient);
    }

    @Override
    public void passInformationClient(String ipClient, String username, int typeClient, int statusClient, int typeAir, String description, float x, float y, int power, int protect) {
        client.passInformationClient(ipClient,username,typeClient,statusClient,typeAir,description,x,y,power,protect);
    }

    @Override
    public void notifyChangeStatus(String ipClient, int status) {
        client.notifyChangeStatus(ipClient, status);
    }

    @Override
    public void notifyChangeRotation(String ipClient, float rotation, float x, float y) {
        client.notifyChangeRotation(ipClient, rotation,x,y);
    }

    @Override
    public void notifyChangeVelocity(String ipClient, float velocityX, float velocityY, float x, float y) {
        client.notifyChangeVelocity(ipClient, velocityX, velocityY,x,y);
    }

    @Override
    public void notifyChangePosition(String ipClient, float rotation, float velocityX, float velocityY, float x, float y) {
        client.notifyChangePosition(ipClient,rotation, velocityX, velocityY,x,y);
    }

    @Override
    public void notifyChangePower(String ipClient, int power) {
        client.notifyChangePower(ipClient, power);
    }

    @Override
    public void notifyChangeProtect(String ipClient, int protect) {
        client.notifyChangeProtect(ipClient, protect);
    }

    @Override
    public boolean notifyFire(String ipClient) {
        return client.notifyFire(ipClient);
    }
}
