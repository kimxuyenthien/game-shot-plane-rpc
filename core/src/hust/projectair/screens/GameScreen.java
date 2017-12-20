package hust.projectair.screens;


import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import hust.projectair.GameState.MainGameState;
import hust.projectair.customize.JButton;
import hust.projectair.game.AirGame;
import hust.projectair.objectGame.Client;
import hust.projectair.utils.AssetsLoader;

import java.util.List;


/**
 * Created by hieutrinh on 10/1/15.
 */
public class GameScreen implements Screen, InputProcessor {

    private AirGame game;
    private MainGameState stateGame;

    private OrthographicCamera camera;
    private JButton backButton;
    private AssetsLoader AssetsLoader;
    private SpriteBatch batch;


    public GameScreen(AirGame mGame, List<Client> clients)
    {
        game = mGame;
        stateGame = new MainGameState(clients, game);
        camera = new OrthographicCamera(MainGameState.SCREEN_WIDTH,MainGameState.SCREEN_HEIGHT);
        camera.setToOrtho(false, MainGameState.SCREEN_WIDTH,MainGameState.SCREEN_HEIGHT);

        batch = new SpriteBatch();

        backButton = new JButton(AssetsLoader.getBack());
        backButton.setPos(10, MainGameState.SCREEN_HEIGHT-100);



        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(this);
        inputMultiplexer.addProcessor(stateGame);

        Gdx.input.setInputProcessor(inputMultiplexer);
    }


    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {

    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {

        stateGame.draw();
        stateGame.act(delta);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        backButton.draw(batch);


        batch.end();

    }

    /**
     * @param width
     * @param height
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        Gdx.app.log("GameScreen","resize");
    }

    /**
     * @see ApplicationListener#pause()
     */
    @Override
    public void pause() {
        Gdx.app.log("GameScreen","pause");
    }

    /**
     * @see ApplicationListener#resume()
     */
    @Override
    public void resume() {
        Gdx.app.log("GameScreen","resume");
    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {
        Gdx.app.log("GameScreen","hide");
    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        Gdx.app.log("GameScreen","dispose");
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {


        Vector3 touchPos = new Vector3();
        touchPos.set(screenX, screenY, 0);
        camera.unproject(touchPos);

        if(backButton.isPressed(touchPos))
        {
            stateGame.dispose();
            game.resetClients();
            game.gotoMenuScreen();
        }

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
