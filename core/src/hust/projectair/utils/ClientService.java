package hust.projectair.utils;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import hust.projectair.common.ClientManagement;
import hust.projectair.common.GameManagement;
import hust.projectair.config.ConfigGame;
import hust.projectair.game.AirGame;
import hust.projectair.utils.network.NetWorkUtils;
import lipermi.handler.CallHandler;
import lipermi.handler.filter.GZipFilter;
import lipermi.net.Client;

import java.io.IOException;
import java.util.List;

/**
 * Created by hieutrinh on 10/9/15.
 */
public class ClientService {

    private AirGame airGame;
    private String currentIp;

    public ClientService(AirGame mAirGame)
    {
        airGame = mAirGame;

        if(Gdx.app.getType() == Application.ApplicationType.Android)
        {
            currentIp = NetWorkUtils.getCurrentIpAndroid();
        }
        else
        {
            currentIp = NetWorkUtils.getCurrentIp();
        }
    }




    public ClientManagement joinGame( String ipRemote, String ipClient)
    {

        CallHandler callHandler = new CallHandler();
        try {

            Client client = new Client(ipRemote, ConfigGame.PORT_CLIENTMANAGEMENT, callHandler, new GZipFilter());

            ClientManagement clientController = (ClientManagement) client.getGlobal(ClientManagement.class);

            clientController.joinGame(ipClient);
            // code process

            return clientController;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }

    public static GameManagement registryClient(String ipClient)
    {
        CallHandler callHandler = new CallHandler();
        try {

            Client client = new Client(ipClient, ConfigGame.PORT_GAMEMANAGEMENT, callHandler, new GZipFilter());

            GameManagement gameManagement = (GameManagement) client.getGlobal(GameManagement.class);


            // code process

            return gameManagement;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }



    public void quitGame(ClientManagement clientManagement,String ipClient)
    {
        clientManagement.quitGame(ipClient);
    }

    public void setStatusForClient(ClientManagement clientManagement,String ipClient, int status)
    {
        clientManagement.setStatusForClient(ipClient,status);
    }

    public hust.projectair.objectGame.Client getClient(List<hust.projectair.objectGame.Client> clients, String ipClient)
    {
        for(hust.projectair.objectGame.Client client : clients)
        {
            if(client.getIp().equalsIgnoreCase(ipClient))
            {
                return client;
            }
        }

        return null;
    }




    public void passInformationClient(String ipClient, String username, int typeClient, int statusClient, int typeAir, String description, float x, float y, int power, int protect)
    {
        hust.projectair.objectGame.Client client = getClient(airGame.getClients(), ipClient);
        client.getGameManagement().passInformationClient(currentIp,username,typeClient,statusClient,typeAir,description,x,y,power,protect);

    }

    public void notifyChangeStatus(String ipClient, int status)
    {
        hust.projectair.objectGame.Client client = getClient(airGame.getClients(), ipClient);
        try
        {
            client.getGameManagement().notifyChangeStatus(currentIp,status);
        }
        catch (Exception e)
        {

        }


    }

    public void notifyChangeRotation(String ipClient, float rotation, float x, float y)
    {
        hust.projectair.objectGame.Client client = getClient(airGame.getClients(), ipClient);

        try
        {
            client.getGameManagement().notifyChangeRotation(currentIp,rotation,x,y);
        }
        catch (Exception e)
        {

        }

    }

    public void notifyChangeVelocity(String ipClient, float velocityX, float velocityY, float x, float y)
    {
        hust.projectair.objectGame.Client client = getClient(airGame.getClients(), ipClient);

        try {
            client.getGameManagement().notifyChangeVelocity(currentIp,velocityX,velocityY,x,y);
        }
        catch (Exception e)
        {

        }


    }

    public void notifyChangePower(String ipClient, int power)
    {
        hust.projectair.objectGame.Client client = getClient(airGame.getClients(), ipClient);
        try {
            client.getGameManagement().notifyChangePower(currentIp, power);
        }
        catch (Exception e)
        {

        }


    }

    public void notifyChangeProtect(String ipClient, int protect)
    {
        hust.projectair.objectGame.Client client = getClient(airGame.getClients(), ipClient);

        try {
            client.getGameManagement().notifyChangeProtect(currentIp,protect);
        }
        catch (Exception e)
        {

        }


    }

    public void notifyFire(String ipClient)
    {
        hust.projectair.objectGame.Client client = getClient(airGame.getClients(), ipClient);

        try {
            client.getGameManagement().notifyFire(currentIp);
        }
        catch (Exception e)
        {

        }


    }

}
