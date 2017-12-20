package hust.projectair.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import hust.projectair.customize.JButton;
import hust.projectair.game.AirGame;
import hust.projectair.objectGame.Client;
import hust.projectair.utils.AssetsLoader;


public class WaittingScreen implements Screen, InputProcessor {
    private OrthographicCamera camera;

    private SpriteBatch batch;
    private SpriteBatch batchListView;

    private AirGame game;
    private AssetsLoader AssetsLoader;
    private Texture backgroundImage;
    private Texture backgroundList;

    private JButton backButton;


    private TextureRegion logo;
    private int logoX;
    private int logoY;
    private JButton[] soundButtons;
    private boolean soundOn;

    private ShapeRenderer shapeRendererBackground;


    private List<String> listCLient;
    private Skin skin;



    public WaittingScreen(AirGame game) {
        super();
        this.game = game;

    }

    public void show() {

        java.util.List<Client> clientList = game.getClients();

  
        
        
        backgroundImage = AssetsLoader.getBackground2();
        backgroundList = AssetsLoader.getBackgroundList();
        TextureRegion buttonBg = AssetsLoader.getButton();

        soundButtons = new JButton[2];
        soundButtons[0] = new JButton(AssetsLoader.getButton(), AssetsLoader.getSoundImage(false));
        soundButtons[1] = new JButton(AssetsLoader.getButton(), AssetsLoader.getSoundImage(true));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, AssetsLoader.getScreenWidth(), AssetsLoader.getScreenHeight());
        batch = new SpriteBatch();
        batchListView = new SpriteBatch();

        backButton = new JButton(AssetsLoader.getBack());

        shapeRendererBackground = new ShapeRenderer();
        shapeRendererBackground.setProjectionMatrix(camera.combined);

        skin = new Skin(Gdx.files.internal("images/data/uiskin.json"));
        listCLient = new List<String>(skin);



        listCLient.setX(AssetsLoader.getScreenWidth() / 2 - 160);
        listCLient.setY(AssetsLoader.getScreenHeight() / 2 - 100);
        listCLient.setWidth(300);
        listCLient.setHeight(200);


        logo = AssetsLoader.getListMember();
        logoX = (AssetsLoader.getScreenWidth() - logo.getRegionWidth()) / 2;
        logoY = (AssetsLoader.getScreenHeight() - logo.getRegionHeight() - 10);


        soundButtons[0].setPos(10, 10);
        soundButtons[1].setPos(10, 10);
        backButton.setPos(10, 400);

        Gdx.input.setInputProcessor(this);


    }


    public void checkStartGame()
    {
        if(game.isStart())
        {
            game.gotoStartGame();
        }
    }

    public void updateListClient()
    {
        listCLient.getItems().clear();

        for (int i = 1; i < game.getClients().size(); i++) {
            Client client = game.getClients().get(i);

            if (!listCLient.getItems().contains(client.getIp(), false)) {
                listCLient.getItems().add(client.getIp());
            }

        }

    }

    @Override
    public void render(float delta) {

        updateListClient();
        checkStartGame();




        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(backgroundImage, 0, 0);
        batch.draw(logo, logoX, logoY);

        backButton.draw(batch);


        batch.end();


        batchListView.setProjectionMatrix(camera.combined);
        batchListView.begin();
        batchListView.draw(backgroundList, AssetsLoader.getScreenWidth() / 2 - 200, AssetsLoader.getScreenHeight() / 2 - 120, 400, 250);

        listCLient.draw(batchListView, 1.f);




        batchListView.end();



//        for(int i=0;i<buttons.length;i++) {
//        	buttons[i].draw(batch);
//        }
        // helpButton.draw(batch);


    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.BACK) {
            Gdx.app.exit();
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPos = new Vector3();
        touchPos.set(screenX, screenY, 0);
        camera.unproject(touchPos);


        Gdx.app.log("", listCLient.getSelectedIndex() + "");



        if (backButton.isPressed(touchPos)) {


            for (int i = 1; i < game.getClients().size(); i++) {

                try
                {
                    Client client = game.getClients().get(i);
                    client.getGameManagement().notifiyClientQuitGame(game.getCurrentIP());
                }
                catch (Exception e)
                {

                }
            }

            game.resetClients();
            game.gotoMenuScreen();


        }
        if (soundOn && soundButtons[0].isPressed(touchPos)) {

        } else if (!soundOn && soundButtons[1].isPressed(touchPos)) {

        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        // TODO Auto-generated method stub
        return false;
    }


    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
        }
    }
}


