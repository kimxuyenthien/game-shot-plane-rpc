package hust.projectair.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hust.projectair.GameState.MainGameState;
import hust.projectair.common.ClientManagement;
import hust.projectair.common.GameManagement;
import hust.projectair.config.ConfigGame;
import hust.projectair.enums.StatusClient;
import hust.projectair.enums.TypeAir;
import hust.projectair.enums.TypeClient;
import hust.projectair.objectGame.Admin;
import hust.projectair.objectGame.Air;
import hust.projectair.objectGame.Client;
import hust.projectair.objectGame.Member;
import hust.projectair.screens.CreateRoomScreen;
import hust.projectair.screens.GameScreen;
import hust.projectair.screens.MenuScreen;
import hust.projectair.screens.SearchRoomScreen;
import hust.projectair.screens.WaittingScreen;
import hust.projectair.utils.AssetsLoader;
import hust.projectair.utils.ClientService;
import hust.projectair.utils.Service;
import hust.projectair.utils.network.NetWorkUtils;

/**
 * Created by hieutrinh on 10/1/15.
 */
public class AirGame extends Game{


    private static List<Client> clients;
    private static Client currentClient;
    private GameManagement currentGameManagement;

    private GameScreen gameScreen;
    private MenuScreen menuScreen;

    private SearchRoomScreen searchRoomScreen;
    private CreateRoomScreen createRoomScreen;
    private WaittingScreen waittingScreen;

    private ClientManagement admin;

    private Service service;
    private ClientService clientService;

    public static String currentIP;

    private long idSoundBackground;
    private Sound soundBackground;
    private Random random;


    private boolean isStart;

    /**
     * Called when the {@link Application} is first created.
     */

    @Override
    public void create()
    {

        clients = new ArrayList<Client>();

        if(Gdx.app.getType() == Application.ApplicationType.Android)
        {
            currentIP = NetWorkUtils.getCurrentIpAndroid();
        }
        else
        {
            currentIP = NetWorkUtils.getCurrentIp();
        }

        service = new Service();

        clientService = new ClientService(this);

        AssetsLoader.load();

        initUser();

        gotoMenuScreen();

        startGameSound();

    }


    public boolean isStart() {
        return isStart;
    }

    public void setStatusStart(boolean isStart) {
        this.isStart = isStart;
    }

    public void onInitRemotForUser(final String ipServer)
    {

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                clientService.joinGame(ipServer,currentIP);
            }
        }).start();


    }
    public void onCreateRoom()
    {

        resetClients();

        if(admin == null)
        {
            admin = new Admin(this);
            service.stopServerClientManager();
            service.startClientManagement(admin);
        }



        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    service.createGroupForGame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> mClients) {
        clients = mClients;
    }

    @Override
    public void dispose() {
        super.dispose();

        AssetsLoader.dispose();
    }

    public void setCurrentIP(String ip)
    {
        currentIP = ip;
        currentClient.setIp(ip);
    }

    public String getCurrentIP()
    {
        return currentIP;
    }

    public Service getService()
    {
        return service;
    }

    private void initUser()
    {

        clients = new ArrayList<Client>();

        random = new Random();

        long x = (long) (Math.random()*ConfigGame.MAP_WIDTH);
        long y = (long) (Math.random()*ConfigGame.MAP_HEIGHT);

        Air tempAir = new Air(x, y, 75, 100);

        tempAir.setPositionLocal(MainGameState.SCREEN_WIDTH/2, MainGameState.SCREEN_HEIGHT/2);
        tempAir.setTypeAir(TypeAir.ALLIED);
        tempAir.setProtect(100);
        tempAir.setPower(6);

        currentClient = new Client(this);

        if(Gdx.app.getType() == Application.ApplicationType.Android)
        {
            currentClient.setIp(NetWorkUtils.getCurrentIpAndroid());
        }
        else
        {
            currentClient.setIp(NetWorkUtils.getCurrentIp());
        }

        currentClient.setDescription("");
        currentClient.setStatus(StatusClient.PLAYING);
        currentClient.setType(TypeClient.A);

        currentClient.setUserName(NetWorkUtils.getNamePC());
        currentClient.setAir(tempAir);

        currentGameManagement = new Member(currentClient);
        currentClient.setGameManagement(currentGameManagement);
        service.startGameManagement(currentGameManagement);

        clients.add(currentClient);

    }

    public void resetClients()
    {
        clients = new ArrayList<Client>();
        currentClient.resetNewClient();
        clients.add(currentClient);
    }

    public void joinGame(final String ipClient) {

        Client client = getClient(clients, ipClient);

        if(client == null)
        {
            client = new Client(this);
            client.setIp(ipClient);
            client.setGameManagement(ClientService.registryClient(ipClient));
            client.setAir(null);

            clients.add(client);
        }


        for(int i = 1; i < clients.size()-1; i++)
        {

            final int finalI = i;
            new Thread(new Runnable()
            {
                @Override
                public void run() {
                    try
                    {
                        clients.get(finalI).getGameManagement().notifyClientJoinGame(ipClient);
                    }
                    catch (Exception e)
                    {

                    }
                }
            }).start();

        }

        final GameManagement gameManagement = client.getGameManagement();

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {

                gameManagement.passInformationClient(currentIP, currentClient.getUserName(), currentClient.getType().getValue(), currentClient.getStatus().getValue(), currentClient.getAir().getTypeAir().getValue(), currentClient.getDescription(), currentClient.getAir().getPositionX(), currentClient.getAir().getPositionY(), currentClient.getAir().getPower(), currentClient.getAir().getProtect());

            }
        }).start();

    }


    public void quitGame(String ipClient) {

        Client client = getClient(clients, ipClient);
        clients.remove(client);
    }

    public void setStatusForClient(String ipClient, int status) {

        Client client = getClient(clients,ipClient);
        client.setStatus(StatusClient.parseStatusClient(status));
    }


    public Client getClient(List<Client> clients, String ipClient)
    {
        for(Client client : clients)
        {
            if(client.getIp().equalsIgnoreCase(ipClient))
            {
                return client;
            }
        }

        return null;
    }




    public void gotoWaitingScreen()
    {
        waittingScreen = new WaittingScreen(this);
        setScreen(waittingScreen);
    }

    public void gotoCreateRoomScreen()
    {

        currentClient.getAir().setTypeAir(TypeAir.ALLIED);
        currentClient.setType(TypeClient.A);

        onCreateRoom();
        Gdx.app.log("Admin", admin.toString());
        createRoomScreen = new CreateRoomScreen(this);
        setScreen(createRoomScreen);
    }
    public void gotoSearchRoomScreen()
    {
        searchRoomScreen = new SearchRoomScreen(this);
        setScreen(searchRoomScreen);
    }

    public void gotoMenuScreen()
    {

        isStart = false;
        try {
            service.stopListenerRoom();
        } catch (IOException e) {
            e.printStackTrace();
        }


        menuScreen = new MenuScreen(this);
        setScreen(menuScreen);
    }

    public void gotoStartGame()
    {

        silenGameSound();
        // setStatePlayGame();
        gameScreen = new GameScreen(this, clients);
        setScreen(gameScreen);
    }

    public void startGameSound()
    {
        soundBackground = AssetsLoader.soundStartGame;
        idSoundBackground = soundBackground.loop();

    }

    public void setStatePlayGame()
    {
        for(Client client : clients)
        {
            client.setStatus(StatusClient.PLAYING);
        }
    }


    public void onGameSound()
    {
        soundBackground.resume(idSoundBackground);
    }
    public void silenGameSound()
    {
        soundBackground.pause(idSoundBackground);
    }

    public Client getCurrentClient() {
        return currentClient;
    }
}
