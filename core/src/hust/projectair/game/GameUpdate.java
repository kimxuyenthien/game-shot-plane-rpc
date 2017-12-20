package hust.projectair.game;


import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import hust.projectair.GameState.MainGameState;
import hust.projectair.enums.GameState;
import hust.projectair.enums.StatusClient;
import hust.projectair.enums.TypeAir;
import hust.projectair.objectGame.Air;
import hust.projectair.objectGame.Bullet;
import hust.projectair.objectGame.Client;

/**
 * Created by hieutrinh on 10/1/15.
 */
public class GameUpdate {


    public static final int TIME_RECONNECT = 5000;
    public static final int TIME_REPORT = 40;
    public static final int TIME_DELAY_GUN = 15;


    private MainGameState mainGameState;
    private List<Client> clients;
    private GameState gameState;
    private Touchpad touchControl;
    private Client originalClient;
    private boolean youWin;


    public GameUpdate(MainGameState mMainGameState) {

        mainGameState = mMainGameState;
        gameState = GameState.READY;
        clients = mMainGameState.getClients();
        originalClient = clients.get(0);

    }


    public void setTouchPad(Touchpad mTouchPad) {
        touchControl = mTouchPad;
    }

    public void update(int runtimeCounter) {
        switch (gameState) {
            case READY:
                updateReady(runtimeCounter);
                break;

            case RUNNING:
                updateRunning(runtimeCounter);
                break;
        }
    }

    /**
     * update when status is running
     *
     * @param runtimeCounter
     */
    private void updateRunning(int runtimeCounter) {



        float x = touchControl.getKnobPercentX() * Air.MAX_VELOCITY;
        float y = touchControl.getKnobPercentY() * Air.MAX_VELOCITY;

        float tempRotation = (float) (Math.atan2(x, y) * 180 / Math.PI);

        if (tempRotation == 0) {
            tempRotation = 0;
        } else if (tempRotation < 0) {
            tempRotation = -tempRotation;
            tempRotation -= originalClient.getAir().getRotation();
        } else {
            tempRotation = 360 - tempRotation;
            tempRotation -= originalClient.getAir().getRotation();
        }

        originalClient.update(x, y, tempRotation);
        mainGameState.camera.translate(originalClient.getAir().getVelocityX(), originalClient.getAir().getVelocityY());
        originalClient.getAir().setPosition(mainGameState.camera.position.x, mainGameState.camera.position.y);


        // check state original client
        boolean isLose = true;

        for(Client client : clients)
        {
            if(client.getAir().getTypeAir() == TypeAir.ALLIED && client.getAir().getProtect() > 0)
            {
                isLose = false;
            }
        }

        if(isLose)
        {
            mainGameState.goToYouLose();
        }

        if (runtimeCounter % TIME_REPORT == 0)
        {
            notifyChangePosition();
        }

        if (mainGameState.getStatusGun() && runtimeCounter % TIME_DELAY_GUN == 0)
        {
            fire();

            mainGameState.setStateGun(false);
        }


        youWin = true;

        for (int i = 1; i < clients.size(); i++)
        {
            Client client = clients.get(i);
            client.getAir().setPositionLocal(client.getAir().getPositionX() + MainGameState.SCREEN_WIDTH / 2 - mainGameState.camera.position.x, client.getAir().getPositionY() + MainGameState.SCREEN_HEIGHT / 2 - mainGameState.camera.position.y);

            if(client.getAir().getProtect() <= 0)
            {
                client.resetValueAir();
            }
            if(client.getAir().getProtect() > 0 && client.getAir().getTypeAir() == TypeAir.ENEMY)
            {
                youWin = false;
            }

        }

        if(youWin)
        {
            mainGameState.gotoYouWin();
        }

        for (int i = 0; i < MainGameState.MAX_BULLET; i++)
        {
            Bullet bullet = mainGameState.getBullets()[i];
            bullet.update();

            if (bullet.isShow()) {


                for(final Client client : mainGameState.getClients())
                {
                    if(client.getAir().getTypeAir() == TypeAir.ENEMY)
                    {
                        if (client != null) {
                            boolean result = client.getAir().isCollision(bullet.getBound());

                            if (result)
                            {
                                bullet.setExplosive();

                                client.getAir().setProtect(client.getAir().getProtect() - originalClient.getAir().getPower());

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {


                                        try
                                        {
                                            client.getGameManagement().notifyFire(originalClient.getIp());
                                        }
                                        catch (Exception e)
                                        {

                                        }
                                    }
                                }).start();

                            }
                        }
                    }
                }


            }
        }

    }

    public void fire()
    {

        for (Bullet bullet : mainGameState.getBullets())
        {
            if (bullet.getVelocity().x == 0 && bullet.getVelocity().y == 0)
            {
                bullet.setRotation(originalClient.getAir().getRotation()+90);
                bullet.setStateGun();
                originalClient.getAir().playSoundGun();

                return;
            }
        }

        for(Bullet bullet : mainGameState.getBullets())
        {
            bullet.resetBullet();
        }

    }

    public void notifyChangePosition() {


        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 1; i < clients.size(); i++) {
                    try {

                        Client client = clients.get(i);

                        if (client.getStatus() != StatusClient.DESTROY) {
                            client.getGameManagement().notifyChangePosition(originalClient.getIp(), originalClient.getAir().getRotation(), originalClient.getAir().getVelocityX(), originalClient.getAir().getVelocityY(), originalClient.getAir().getPositionX(), originalClient.getAir().getPositionY());
                        }

                    } catch (Exception e) {

                        //clients.get(i).setStatus(StatusClient.DISCONNECT);
                        //checkDisconnect(finalI);

                    }
                }
            }
        }).start();


    }

    public void checkDisconnect(final int index) {

        Timer timer = new Timer();

        TimerTask taskCheck = new TimerTask() {
            @Override
            public void run() {


                Client client = clients.get(index);

                if (client.getStatus() == StatusClient.DISCONNECT) {
                    client.setStatus(StatusClient.DESTROY);
                }

            }
        };


        timer.schedule(taskCheck, TIME_RECONNECT);

    }


    /**
     * update ready for status is ready
     *
     * @param delta
     */
    private void updateReady(float delta) {
        gameState = GameState.RUNNING;
    }


    public boolean checkColisition(Bullet bullet, Client client) {

        boolean result = false;


        if (client == null || bullet == null) {
            return false;
        }


        float x1 = bullet.getPosition().x;
        float y1 = bullet.getPosition().y;
        float w1 = bullet.getBullet().getRegionWidth();
        float h1 = bullet.getBullet().getRegionHeight();
        float x2 = client.getAir().getPositionLocal().x;
        float y2 = client.getAir().getPositionLocal().y;
        float w2 = client.getAir().getCurrentTextureRegion().getRegionWidth();
        float h2 = client.getAir().getCurrentTextureRegion().getRegionHeight();


        float distanceX = Math.abs(x1 + w1 / 2 - x2 + w2 / 2);
        float distanceY = Math.abs(y1 + h1 / 2 - y2 + h2 / 2);


        if (distanceX <= w1 / 2 + w2 / 2 && distanceY <= h1 / 2 + h2 / 2) {
            return true;
        }

        return false;

    }


    public List<Client> getListClient() {
        return clients;
    }

    public Client getClient(String ipClient) {
        for (Client client : clients) {
            if (client.getIp().equalsIgnoreCase(ipClient)) {
                return client;
            }
        }

        return null;
    }
}
