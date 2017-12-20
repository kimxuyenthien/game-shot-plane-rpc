package hust.projectair.utils;

import com.badlogic.gdx.Gdx;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import hust.projectair.common.ClientManagement;
import hust.projectair.common.GameManagement;
import hust.projectair.config.ConfigGame;
import hust.projectair.game.AirGame;
import lipermi.handler.CallHandler;
import lipermi.handler.filter.GZipFilter;
import lipermi.net.IServerListener;
import lipermi.net.Server;

/**
 * Created by hieutrinh on 10/6/15.
 */
public class Service{

    private CallHandler callHandler;
    private ClientManagement clientManagement;
    private GameManagement gameManagement;
    private Server serverManageClient;
    private Server serverManageGame;
    private AirGame game;

    private IServerListener manageClientListener;
    private IServerListener manageGameListener;

    private ServerSocket serverSocket;

    private boolean isOnline = true;


    public Service()
    {

        manageClientListener = new IServerListener() {
            public void clientConnected(Socket socket) {
                Gdx.app.log("Client connected: " + socket.getInetAddress(),"");


                try {
                    socket.setTcpNoDelay(true);

                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public void clientDisconnected(Socket socket) {
                Gdx.app.log("Client disconnected: " + socket.getInetAddress(),"");
            }
        };

        manageGameListener = new IServerListener() {
            public void clientConnected(Socket socket) {
                Gdx.app.log("Client connected: " + socket.getInetAddress(),"");

            }

            public void clientDisconnected(Socket socket) {
                Gdx.app.log("Client disconnected: " + socket.getInetAddress(),"");
            }
        };

    }

    public Service(AirGame mAirGame)
    {
        game = mAirGame;
    }


    public void stopServerGameManager()
    {
        if(serverManageGame != null)
        {
            serverManageGame.removeServerListener(manageClientListener);
            serverManageGame.close();
        }
    }

    public void stopServerClientManager()
    {

        if(serverManageClient != null)
        {
            serverManageClient.removeServerListener(manageClientListener);
            serverManageClient.close();
        }
    }


    public void startGameManagement(GameManagement mGameManagement)
    {
        gameManagement = mGameManagement;


        if(serverManageGame == null)
        {
            serverManageGame = new Server();
            CallHandler callHandler = new CallHandler();
            try
            {
                callHandler.registerGlobal(GameManagement.class, gameManagement);

                serverManageGame.addServerListener(manageGameListener);

                serverManageGame.bind(ConfigGame.PORT_GAMEMANAGEMENT, callHandler, new GZipFilter());


            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }


    }

    public void startClientManagement(ClientManagement mClientManagement)
    {

        clientManagement = mClientManagement;

        if(serverManageClient == null)
        {
            serverManageClient = new Server();
            CallHandler callHandler = new CallHandler();
            try
            {
                callHandler.registerGlobal(ClientManagement.class, clientManagement);

                serverManageClient.addServerListener(manageClientListener);
                serverManageClient.bind(ConfigGame.PORT_CLIENTMANAGEMENT, callHandler, new GZipFilter());


            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }


    public void stopListenerRoom() throws IOException {
        isOnline = false;
        if(serverSocket != null && !serverSocket.isClosed())
        {
            serverSocket.close();
        }
    }

    public void restartListenerRoom()
    {
        isOnline = true;
    }

    public void createGroupForGame() throws IOException {

        serverSocket = new ServerSocket(ConfigGame.RMI_PORT);

        while(isOnline)
        {
            Socket clientSocket = serverSocket.accept();
            PrintWriter out =
                    new PrintWriter(clientSocket.getOutputStream(), true);

            out.print(AirGame.currentIP);

        }


    }

}
