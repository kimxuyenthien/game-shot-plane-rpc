package hust.projectair.GameState;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import hust.projectair.config.ConfigGame;
import hust.projectair.customize.JButton;
import hust.projectair.enums.TypeClient;
import hust.projectair.game.AirGame;
import hust.projectair.utils.AssetsLoader;
import hust.projectair.utils.network.NetWorkUtils;

/**
 * Created by hieutrinh on 10/18/15.
 */
public class SearchGameStage extends Stage {



    private AirGame game;

    private boolean isScan;
    private Texture backgroundImage;
    private JButton scanJButton;
    private JButton backJButton;
    private JButton joinJButton;


    // private JButton helpButton;
    private TextureRegion logo;
    private int logoX;
    private int logoY;
    private JButton[] soundJButtons;


    private  Thread threadScan;

    private ShapeRenderer shapeRendererBackground;
    private boolean soundOn;
    private CheckBox checkBoxVnTeam;
    private CheckBox checkBoxAirProtect;
    private Label titleWaitting;
    private List<String> listCLient;
    private Skin skin;
    private Texture backgroundList;

    private AssetsLoader AssetsLoader;
    private SpriteBatch batch;
    private SpriteBatch batchListView;
    private OrthographicCamera camera;


    public SearchGameStage(OrthographicCamera mCamera, AirGame mGame, List<String> mClient, CheckBox mCheckBoxVnTeam, CheckBox mCheckBoxAirProtect) {


        game = mGame;
        listCLient = mClient;
        checkBoxVnTeam = mCheckBoxVnTeam;
        checkBoxAirProtect = mCheckBoxAirProtect;

        isScan = true;
        
        backgroundImage = AssetsLoader.getBackground3();
        backgroundList = AssetsLoader.getBackgroundList();
        TextureRegion buttonBg = AssetsLoader.getButton();


        soundJButtons = new JButton[2];


        soundJButtons[0] = new JButton(AssetsLoader.getButton(), AssetsLoader.getSoundImage(false));
        soundJButtons[1] = new JButton(AssetsLoader.getButton(), AssetsLoader.getSoundImage(true));

        camera = mCamera;
        batch = new SpriteBatch();


        scanJButton = new JButton(buttonBg, AssetsLoader.getScan());
        joinJButton = new JButton(buttonBg, AssetsLoader.getJoin());
        backJButton = new JButton(AssetsLoader.getBack());

        shapeRendererBackground = new ShapeRenderer();
        shapeRendererBackground.setProjectionMatrix(camera.combined);


        logo = AssetsLoader.getLogo();
        logoX = (AssetsLoader.getScreenWidth() - logo.getRegionWidth()) / 2;
        logoY = (AssetsLoader.getScreenHeight() - logo.getRegionHeight() - 10);

        soundJButtons[0].setPos(10, 10);
        soundJButtons[1].setPos(10, 10);
        backJButton.setPos(10, 400);

        float scanButtonX = AssetsLoader.getScreenWidth() / 2 - scanJButton.getRegionWidth() - 20;
        float scanButtonY = AssetsLoader.getScreenHeight() / 10 - 20;
        float joinButtonX = AssetsLoader.getScreenWidth() / 2 + 20;
        float joinButtonY = AssetsLoader.getScreenHeight() / 10 - 20;

        scanJButton.setPos(scanButtonX, scanButtonY);
        joinJButton.setPos(joinButtonX, joinButtonY);


        backgroundList = AssetsLoader.getBackgroundList();


        batch = new SpriteBatch();
        batchListView = new SpriteBatch();

        skin = new Skin(Gdx.files.internal("images/data/uiskin.json"));





        titleWaitting = new Label("Scanning ...", skin);
        titleWaitting.setX(10);
        titleWaitting.setY(10);

        titleWaitting.setVisible(false);
        titleWaitting.setScale(2.0f);

        addActor(titleWaitting);


    }



    @Override
    public void draw() {


        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(backgroundImage, 0, 0);
        batch.draw(logo, logoX, logoY);

        scanJButton.draw(batch);
        joinJButton.draw(batch);
        backJButton.draw(batch);


        batch.end();


      /*  batchListView.setProjectionMatrix(camera.combined);
        batchListView.begin();
        batchListView.draw(backgroundList, AssetsLoader.getScreenWidth() / 2 - 300, AssetsLoader.getScreenHeight() / 2 - 110, 600, 250);



        batchListView.end();*/

        super.draw();

    }

    /**
     * Calls {@link #act(float)} with {@link Graphics#getDeltaTime()}.
     */
    @Override
    public void act() {
        super.act();
    }

    /**
     * Applies a touch down event to the stage and returns true if an actor in the scene {@link Event#handle() handled} the event.
     *
     * @param screenX
     * @param screenY
     * @param pointer
     * @param button
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPos = new Vector3();
        touchPos.set(screenX, screenY, 0);
        camera.unproject(touchPos);


        if (scanJButton.isPressed(touchPos) && isScan)
        {


            if(game.getCurrentIP().length() == 0)
            {
                String currentIP = "";


                if(Gdx.app.getType() == Application.ApplicationType.Android)
                {
                    currentIP = NetWorkUtils.getCurrentIpAndroid();
                }
                else
                {
                    currentIP = NetWorkUtils.getCurrentIp();
                }

                game.setCurrentIP(currentIP);

            }


            listCLient.getItems().clear();
            titleWaitting.setText("Scanning ...");
            titleWaitting.setVisible(true);
            isScan = false;



            threadScan = new Thread(new Runnable() {
                @Override
                public void run() {
                    java.util.List<String> ipClients = NetWorkUtils.listIpOpenPort(NetWorkUtils.getQuickListIpLan(game.getCurrentIP()), ConfigGame.RMI_PORT);

                    for(String ip : ipClients)
                    {
                        if(!listCLient.getItems().contains(ip,false))
                        {
                            listCLient.getItems().add(ip);
                        }
                    }
                    titleWaitting.setVisible(false);
                    isScan = true;
                }
            });

            threadScan.start();

        }

        if (joinJButton.isPressed(touchPos))
        {

            String ipServer = "";

            if(listCLient.getSelected() != null)
            {
                ipServer = listCLient.getSelected();
            }
            if(ipServer != null && ipServer.length() > 0)
            {

                if(checkBoxVnTeam.isChecked())
                {
                    game.getClients().get(0).setType(TypeClient.A);
                }
                else
                {
                    game.getClients().get(0).setType(TypeClient.B);
                }

                if(checkBoxAirProtect.isChecked())
                {
                    game.getClients().get(0).getAir().setProtect(100);
                    game.getClients().get(0).getAir().setPower(6);
                }
                else
                {
                    game.getClients().get(0).getAir().setProtect(70);
                    game.getClients().get(0).getAir().setPower(8);
                }

                game.onInitRemotForUser(ipServer);
                game.gotoWaitingScreen();
            }
            else
            {
                titleWaitting.setText("Please choose room to connect !");


                titleWaitting.setVisible(true);
                titleWaitting.setScale(2.0f);
            }


        }

        if (backJButton.isPressed(touchPos))
        {
            game.resetClients();
            game.gotoMenuScreen();
        }
        if (soundOn && soundJButtons[0].isPressed(touchPos))
        {
            soundOn = false;

        } else if (!soundOn && soundJButtons[1].isPressed(touchPos))
        {
            soundOn = true;

        }
        return true;
    }
}
