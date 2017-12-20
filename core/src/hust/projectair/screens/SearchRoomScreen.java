package hust.projectair.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Random;

import hust.projectair.GameState.SearchGameStage;
import hust.projectair.game.AirGame;
import hust.projectair.utils.AssetsLoader;


public class SearchRoomScreen implements Screen, InputProcessor
{
    private OrthographicCamera camera;

    private AirGame game;


    private SpriteBatch batch;

    private List<String> listCLient;
    private Skin skin;
    private Skin skinCheckbok;



    private SearchGameStage searchGameStage;

    private Stage stage;

    private Table groupSetting;
    private Label labelChooseTeam;
    private Label labelChooseAir;

    private CheckBox checkBoxAirStrong;
    private CheckBox checkBokAirProtect;

    private boolean soundOn;
    private CheckBox checkBoxTeamVn;
    private CheckBox checkBoxTeamChina;

    private boolean isTeamVn;
    private boolean isAirProtect;

    private static final int COUNTER_UPDATE = 20;
    private int counter = 0;

    public SearchRoomScreen(AirGame game) {
        super();
        this.game = game;

        

        camera = new OrthographicCamera();
        camera.setToOrtho(false, AssetsLoader.getScreenWidth(), AssetsLoader.getScreenHeight());

        skin = new Skin(Gdx.files.internal("images/data/uiskin.json"));
        skinCheckbok = new Skin(Gdx.files.internal("images/data/uiskin.json"));

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        listCLient = new List<String>(skin);

        skinCheckbok.getFont("default-font").getData().setScale(1.5f);
        groupSetting = new Table(skinCheckbok);

        // init value for item
        labelChooseAir = new Label("Choose air : ", skinCheckbok);
        labelChooseTeam = new Label("Choose team : ",skinCheckbok);

        checkBokAirProtect = new CheckBox("Type A (power = 6, protect = 100)", skinCheckbok);
        checkBoxAirStrong = new CheckBox("Type B (power = 8, protect = 70)", skinCheckbok);
        checkBoxTeamVn = new CheckBox("Vietnam Team",skinCheckbok);
        checkBoxTeamChina = new CheckBox("China Team", skinCheckbok);

        checkBokAirProtect.setChecked(true);
        checkBoxAirStrong.setChecked(false);



        if(new Random().nextBoolean())
        {
            checkBoxTeamVn.setChecked(true);
            checkBoxTeamChina.setChecked(false);
            isTeamVn = true;
        }
        else
        {
            checkBoxTeamVn.setChecked(false);
            checkBoxTeamChina.setChecked(true);
            isTeamVn = false;
        }


        groupSetting.setFillParent(true);

        groupSetting.add(labelChooseTeam).left();
        groupSetting.row();
        groupSetting.add(checkBoxTeamChina).left().padLeft(30);
        groupSetting.row();
        groupSetting.add(checkBoxTeamVn).left().padLeft(30);

        groupSetting.row();

        groupSetting.add(labelChooseAir).left();

        groupSetting.row();

        groupSetting.add(checkBokAirProtect).left().padLeft(30);
        groupSetting.row();
        groupSetting.add(checkBoxAirStrong).left().padLeft(30);

        isTeamVn = true;
        isAirProtect = true;

        if(Gdx.app.getType() == Application.ApplicationType.Android)
        {
            skin.getFont("default-font").getData().setScale(2);
            listCLient.setX(AssetsLoader.getScreenWidth() / 2 - 80);
            listCLient.setY(AssetsLoader.getScreenHeight()/2+10);

        }
        else
        {
            skin.getFont("default-font").getData().setScale(2);
            listCLient.setX(AssetsLoader.getScreenWidth() / 2 - 160);
            listCLient.setY(AssetsLoader.getScreenHeight() / 2 -10);

        }

        listCLient.setWidth(Gdx.graphics.getWidth()/3);
        listCLient.setHeight(300);



        searchGameStage = new SearchGameStage(camera, game, listCLient, checkBoxTeamVn, checkBokAirProtect);
        ScreenViewport screenViewport = new ScreenViewport();
        screenViewport.setScreenBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        searchGameStage.setViewport(screenViewport);

        groupSetting.right();
        groupSetting.padRight(50);

        stage = new Stage(screenViewport);
        stage.addActor(listCLient);
        stage.addActor(groupSetting);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(searchGameStage);

        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public void show()
    {

    }

    public void update()
    {

        if(checkBokAirProtect.isChecked() && isAirProtect == false)
        {
            checkBoxAirStrong.setChecked(false);
            isAirProtect = true;
        }

        if(checkBoxAirStrong.isChecked() && isAirProtect == true)
        {
            checkBokAirProtect.setChecked(false);
            isAirProtect = false;
        }

        if(checkBoxTeamChina.isChecked() && isTeamVn == true)
        {
            checkBoxTeamVn.setChecked(false);
            isTeamVn = false;
        }

        if(checkBoxTeamVn.isChecked() && isTeamVn == false)
        {
            checkBoxTeamChina.setChecked(false);
            isTeamVn = true;
        }
    }

    @Override
    public void render(float delta) {



        update();

        camera.update();

        searchGameStage.act(Gdx.graphics.getDeltaTime());
        stage.act(Gdx.graphics.getDeltaTime());
        searchGameStage.draw();
        stage.draw();


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

    }
}


