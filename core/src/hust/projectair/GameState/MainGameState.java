package hust.projectair.GameState;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.util.List;

import hust.projectair.enums.StatusClient;
import hust.projectair.enums.TypeAir;
import hust.projectair.game.AirGame;
import hust.projectair.game.GameRenderer;
import hust.projectair.game.GameUpdate;
import hust.projectair.objectGame.Air;
import hust.projectair.objectGame.Bullet;
import hust.projectair.objectGame.Client;
import hust.projectair.objectGame.IconImage;
import hust.projectair.utils.AssetsLoader;


/**
 * Created by hieutrinh on 10/4/15.
 */
public class MainGameState extends Stage implements InputProcessor {


    public static final float SCALE_MINI_ANDROID = 8f;
    public static final float SCALE_MINI_DESKTOP = 10f;
    public static final int SCREEN_WIDTH = Gdx.graphics.getWidth();
    public static final int SCREEN_HEIGHT = Gdx.graphics.getHeight();
    public static int MAX_BULLET = 7;


    public float scaleMiniMap;

    public OrthographicCamera camera;
    public OrthographicCamera cameraMiniMap;

    public static int TIME_GUN = 5000;
    private GameUpdate gameUpdate;
    private GameRenderer gameRenderer;
    private int runTimeCounter = 0;
    // touchpad
    private SpriteBatch batch;
    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;
    private Texture blockTexture;
    private Sprite blockSprite;
    private float blockSpeed;
    private Label titlePower;
    private Label titleHeath;
    private Skin skin;
    private AirGame game;


    private IconImage imageResultGame;
    private IconImage imageGun;
    private List<Client> clients;
    private Client clientIsGuned;
    private Bullet[] bullets;
    private int currentBullet;

    private boolean isGun;



    public MainGameState(List<Client> mClients, AirGame mGame) {
        Gdx.app.log("GameScreen", "Attached");

        game = mGame;
        initTouchPad();
        initComponent();

        clients = game.getClients();

        camera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
        camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);

        cameraMiniMap = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);

        if (Gdx.app.getType() == Application.ApplicationType.Android) {
            cameraMiniMap.setToOrtho(false, SCREEN_WIDTH * SCALE_MINI_ANDROID, SCREEN_HEIGHT * SCALE_MINI_ANDROID);
            scaleMiniMap = SCALE_MINI_ANDROID;
        } else {
            cameraMiniMap.setToOrtho(false, SCREEN_WIDTH * SCALE_MINI_DESKTOP, SCREEN_HEIGHT * SCALE_MINI_DESKTOP);
            scaleMiniMap = SCALE_MINI_DESKTOP;
        }

        gameUpdate = new GameUpdate(this);
        gameRenderer = new GameRenderer(gameUpdate, this);

        gameUpdate.setTouchPad(touchpad);

        skin = new Skin(Gdx.files.internal("images/data/uiskin.json"));
        titleHeath = new Label("Protect : " + mClients.get(0).getAir().getProtect(), skin);
        titlePower = new Label("Power : " + mClients.get(0).getAir().getPower(), skin);

        titleHeath.setY(50);
        titleHeath.setX(10);

        titlePower.setX(10);
        titlePower.setY(10);

        addActor(titleHeath);
        addActor(titlePower);

    }


    private void initComponent()
    {
        imageGun = new IconImage(new TextureRegion(AssetsLoader.getShotting()));

        imageGun.setPosition(MainGameState.SCREEN_WIDTH-200, MainGameState.SCREEN_HEIGHT-200);
        imageGun.setShow(false);

        imageResultGame = new IconImage(AssetsLoader.getYouLose());
        imageResultGame.setPosition(MainGameState.SCREEN_WIDTH/2-imageResultGame.getRegionImage().getRegionWidth()/2,MainGameState.SCREEN_HEIGHT/3);
        imageResultGame.setShow(false);


        bullets = new Bullet[MAX_BULLET];

        currentBullet = 0;

        for (int i = 0; i < MAX_BULLET; i++) {
            Bullet bullet = new Bullet();

            bullets[i] = bullet;

        }


    }

    public void goToYouLose()
    {
        imageResultGame.setRegionImage(AssetsLoader.getYouLose());
        imageResultGame.setShow(true);
    }

    public void gotoYouWin()
    {
        imageResultGame.setRegionImage(AssetsLoader.getYouWin());
        imageResultGame.setShow(true);
    }

    public IconImage getImageResultGame()
    {
        return imageResultGame;
    }

    public IconImage getImageGun() {
        return imageGun;
    }

    public int getCurrentBullet() {
        return currentBullet;
    }

    public void setCurrentBullet(int mCurrentBullet) {
        currentBullet = mCurrentBullet;
    }

    public Client getClientIsGuned() {
        return clientIsGuned;
    }


    public void updateStatusAir() {

        int protect = game.getCurrentClient().getAir().getProtect();

        if(protect < 0)
        {
            protect = 0;
        }
        titleHeath.setText("Protect : " +protect);
    }


    public float getScaleMiniMap() {
        return scaleMiniMap;
    }

    @Override
    public void act(float delta) {
        runTimeCounter++;
        updateStatusAir();
        gameUpdate.setTouchPad(touchpad);
        gameUpdate.update(runTimeCounter);
        super.act(delta);
    }

    @Override
    public void draw() {

        renderTouchPad();
        gameRenderer.render(runTimeCounter);

        super.draw();
    }


    public void renderTouchPad() {

        //Move blockSprite with TouchPad
        blockSprite.setX(blockSprite.getX() + touchpad.getKnobPercentX() * blockSpeed);
        blockSprite.setY(blockSprite.getY() + touchpad.getKnobPercentY() * blockSpeed);

        //Draw
        batch.begin();
        blockSprite.draw(batch);
        batch.end();

    }


    public Client getOriginalClient() {
        return clients.get(0);
    }

    public void initTouchPad() {
        batch = new SpriteBatch();
        //Create camera
        float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();

        //Create a touchpad skin
        touchpadSkin = new Skin();
        //Set background image
        touchpadSkin.add("touchBackground", new Texture("images/touchBackground.png"));
        //Set knob image
        touchpadSkin.add("touchKnob", new Texture("images/touchKnob.png"));
        //Create TouchPad Style
        touchpadStyle = new Touchpad.TouchpadStyle();
        //Create Drawable's from TouchPad skin
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");
        //Apply the Drawables to the TouchPad Style
        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;
        //Create new TouchPad with the created style
        touchpad = new Touchpad(10, touchpadStyle);

        //setBounds(x,y,width,height)
        touchpad.setBounds(Gdx.graphics.getWidth() - Gdx.graphics.getHeight() / 3 - 50, 50, Gdx.graphics.getHeight() / 3, Gdx.graphics.getHeight() / 3);

        //Create a Stage and add TouchPad
        addActor(touchpad);
        Gdx.input.setInputProcessor(this);

        //Create block sprite
        blockTexture = new Texture(Gdx.files.internal("images/block.png"));
        blockSprite = new Sprite(blockTexture);
        //Set position to centre of the screen
        blockSprite.setPosition(Gdx.graphics.getWidth() / 2 - blockSprite.getWidth() / 2, Gdx.graphics.getHeight() / 2 - blockSprite.getHeight() / 2);

        blockSpeed = 5;

        addActor(touchpad);
    }





    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {


        boolean touch = super.touchDown(screenX, screenY, pointer, button);


        if (!touch) {

            final int xTouch = screenX;
            final int yTouch = SCREEN_HEIGHT - screenY;

            isGun = true;

            for (int i = 0; i < gameUpdate.getListClient().size(); i++) {

                Air air = gameUpdate.getListClient().get(i).getAir();


                if (air.getTypeAir() == TypeAir.ENEMY) {
                    if (xTouch <= air.getPositionLocal().x + air.getWidth() && xTouch >= air.getPositionLocal().x &&
                            yTouch <= air.getPositionLocal().y + air.getHeight() && yTouch >= air.getPositionLocal().y) {

                        game.getClients().get(i).setStatus(StatusClient.ISSHOOTING);

                        clientIsGuned = game.getClients().get(i);

                        imageGun.setShow(true);

                      /*  for (Bullet bullet : bullets)
                        {
                            bullet.resetBullet();
                        }*/

                       /* Timer timerStatus = new Timer();
                        final int finalI = i;
                        TimerTask taskRecovery = new TimerTask() {
                            @Override
                            public void run() {
                                game.getClients().get(finalI).setStatus(StatusClient.PLAYING);

                                imageGun.setShow(false);

                                for (Bullet bullet : bullets)
                                {
                                    bullet.setIsShow(false);
                                }
                            }
                        };

                        timerStatus.schedule(taskRecovery, TIME_GUN);*/


                    }
                }


            }
        }


        return touch;


    }

    public boolean getGunning()
    {
        return imageGun.getIsShow();
    }

    public List<Client> getClients() {
        return clients;
    }

    public Bullet[] getBullets() {
        return bullets;
    }

    public boolean getStatusGun()
    {
        return isGun;
    }

    public void setStateGun(boolean stateGun)
    {
        isGun = stateGun;
    }

}
