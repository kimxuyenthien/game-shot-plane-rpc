package hust.projectair.objectGame;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

import hust.projectair.common.GameManagement;
import hust.projectair.config.ConfigGame;
import hust.projectair.enums.StatusClient;
import hust.projectair.enums.TypeAir;
import hust.projectair.enums.TypeClient;
import hust.projectair.game.AirGame;
import hust.projectair.utils.ClientService;

public class Client {

    private AirGame game;
    private String ip;
    private String userName;
    private TypeClient type;
    private String description;
    private StatusClient status;
    private Air air;
    private GameManagement gameManagement;

    public Client(AirGame game) {
        this.game = game;
    }

    public GameManagement getGameManagement()
    {
        return gameManagement;
    }

    public void setGameManagement(GameManagement gameManagement) {
        this.gameManagement = gameManagement;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public StatusClient getStatus() {
        return status;
    }

    public void setStatus(StatusClient status) {
        this.status = status;
    }

    public Air getAir() {
        return air;
    }


    public void setAir(Air air) {
        this.air = air;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public TypeClient getType() {
        return type;
    }

    public void setType(TypeClient type) {
        this.type = type;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub

        if (obj instanceof Client) {
            if (((Client) obj).getIp().equalsIgnoreCase(ip)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    public void resetValueAir()
    {
        air.resetAir();
        status = StatusClient.DESTROY;
    }

    public void update(float x, float y, float tempRotation) {

        if (air != null &&  status != StatusClient.DESTROY) {

            if(air.getProtect() == 0)
            {
                status = StatusClient.DESTROY;

            }

            air.update(x, y, tempRotation);
        }


    }

    public void render(SpriteBatch spriteBatchAir) {



        if (air != null && status != StatusClient.DESTROY) {
            air.render(spriteBatchAir);
        }

    }

    public void renderMini(SpriteBatch spriteBatchMini) {

        if (air != null && status != StatusClient.DESTROY) {
            air.rendderMini(spriteBatchMini);
        }


    }


    public void resetNewClient()
    {
        status =StatusClient.PLAYING;
        air.resetNewAir();
    }

    public void startGame(String ipServer) {

        game.setStatusStart(true);
    }

    public void notifiyClientQuitGame(String ipClient) {
        Client client = findClient(game.getClients(), ipClient);
        game.getClients().remove(client);


        List<Client> c = game.getClients();
    }


    public void notifyClientJoinGame(String ipClient)
    {
        Client client = findClient(game.getClients(), ipClient);
        Client original = game.getClients().get(0);

        if (client == null)
        {
            GameManagement gameManagement = ClientService.registryClient(ipClient);

            Client tempClient = new Client(game);
            tempClient.setIp(ipClient);
            tempClient.setGameManagement(gameManagement);
            tempClient.setAir(null);
            game.getClients().add(tempClient);

            gameManagement.passInformationClient(original.getIp(), original.getUserName(), original.getType().getValue(), original.getStatus().getValue(), original.getAir().getTypeAir().getValue(), original.getDescription(), original.getAir().getPositionX(), original.getAir().getPositionY(), original.getAir().getPower(), original.getAir().getProtect());
        }
    }


    public void passInformationClient(String ipClient, String username, int typeClient, int statusClient, int typeAir, String description, float x, float y, int power, int protect) {


        final Client original = game.getClients().get(0);
        Client client = findClient(game.getClients(), ipClient);

        if (client == null) {
            client = new Client(game);
            client.setDescription(description);
            client.setIp(ipClient);
            client.setStatus(StatusClient.parseStatusClient(statusClient));
            client.setType(TypeClient.parseType(typeClient));
            client.setUserName(username);

            Air air = new Air(x, y, ConfigGame.AIR_WIDTH, ConfigGame.AIR_HEIGHT);

            air.setPower(power);
            air.setProtect(protect);
            air.setTypeAir(TypeAir.parseTypeAir(typeAir));

            //air.setPositionLocal(x-this.air.getPositionX(), y-this.air.getPositionY());

            client.setAir(air);
            client.setGameManagement(ClientService.registryClient(ipClient));


            if (client.getType() == original.getType()) {
                client.getAir().setTypeAir(TypeAir.ALLIED);
            } else {
                client.getAir().setTypeAir(TypeAir.ENEMY);
            }

            game.getClients().add(client);


            final GameManagement gameManagement = client.getGameManagement();

            new Thread(new Runnable() {
                @Override
                public void run() {

                    gameManagement.passInformationClient(original.getIp(), original.getUserName(), original.getType().getValue(), original.getStatus().getValue(), original.getAir().getTypeAir().getValue(), original.getDescription(), original.getAir().getPositionX(), original.getAir().getPositionY(), original.getAir().getPower(), original.getAir().getProtect());

                }
            }).start();

        } else {
            client.setDescription(description);
            client.setIp(ipClient);
            client.setStatus(StatusClient.parseStatusClient(statusClient));
            client.setType(TypeClient.parseType(typeClient));


            Air air = new Air(x, y, ConfigGame.AIR_WIDTH, ConfigGame.AIR_HEIGHT);
            air.setPower(power);
            air.setProtect(protect);

            client.setAir(air);

            if (client.getType() == original.getType()) {
                client.getAir().setTypeAir(TypeAir.ALLIED);
            } else {
                client.getAir().setTypeAir(TypeAir.ENEMY);
            }

        }


    }

    public void notifyChangeStatus(String ipClient, int status) {
        Client client = findClient(game.getClients(), ipClient);
        client.setStatus(StatusClient.parseStatusClient(status));
    }


    public void notifyChangePosition(String ipClient, float rotation, float velocityX, float velocityY, float x, float y) {

        Client client = findClient(game.getClients(), ipClient);


        if (client != null && client.getStatus() != StatusClient.DESTROY) {

            client.getAir().setRotation(rotation);
            client.getAir().setVelocity(velocityX, velocityY);
            client.getAir().setPosition(x + velocityX, y + velocityY);
        }


    }

    public void notifyChangeRotation(String ipClient, float rotation, float x, float y) {
        Client client = findClient(game.getClients(), ipClient);
        client.getAir().setRotation(rotation);
        client.getAir().setPosition(x, y);
    }


    public void notifyChangeVelocity(String ipClient, float velocityX, float velocityY, float x, float y) {
        Client client = findClient(game.getClients(), ipClient);
        client.getAir().setVelocity(velocityX, velocityY);
        client.getAir().setPosition(x, y);
    }


    public void notifyChangePower(String ipClient, int power) {

        Client client = findClient(game.getClients(), ipClient);
        client.getAir().setPower(power);
    }

    public void notifyChangeProtect(String ipClient, int protect) {

        Client client = findClient(game.getClients(), ipClient);
        client.getAir().setProtect(protect);
    }


    public boolean notifyFire(String ipClient) {

        final Client client = game.getCurrentClient();

        Client clientGun = findClient(game.getClients(), ipClient);

        int power = clientGun.getAir().getPower();
        int protect = client.getAir().getProtect();

        protect -= power;

        if (protect <= 0)
        {
            protect = ConfigGame.CODE_DESTROY_AIR;
            client.setStatus(StatusClient.DESTROY);
            // send notify change status to all client
        }

        client.getAir().setProtect(protect);

        new Thread(new Runnable() {
            @Override
            public void run() {

                for(int i = 1; i < game.getClients().size(); i++)
                {

                    Client tempClient = game.getClients().get(i);
                    try
                    {
                        tempClient.getGameManagement().notifyChangeProtect(client.getIp(),client.getAir().getProtect());
                    }
                    catch (Exception e)
                    {

                    }
                 }

            }
        }).start();

        return true;
    }


    public Client findClient(List<Client> clients, String ipClient)
    {
        for (Client client : clients)
        {
            if (client.getIp().equalsIgnoreCase(ipClient))
            {
                return client;
            }
        }

        return null;
    }


}
