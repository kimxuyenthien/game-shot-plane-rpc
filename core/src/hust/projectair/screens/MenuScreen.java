package hust.projectair.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import hust.projectair.customize.JButton;
import hust.projectair.game.AirGame;
import hust.projectair.utils.AssetsLoader;


/**
 * Created by hieutrinh on 10/4/15.
 */
public class MenuScreen implements Screen,InputProcessor {


    private AirGame game;

    private OrthographicCamera camera;

    private SpriteBatch batch;


    private Texture backgroundImage;

    private JButton[] buttons;

    private JButton helpButton;

    private TextureRegion logo;

    private int logoX;

    private int logoY;

    private JButton[] soundButtons;

    private boolean soundOn;

    //private long soundButtonTimePressed = 0;

    public MenuScreen(AirGame game) {
        super();
        this.game = game;

//    	soundOn = State.isSoundOn();
//    	game.getSoundManager().setSoundOn(soundOn);
    }

    @Override
    public void show() {
        
        
        backgroundImage = AssetsLoader.getBackground4();
        TextureRegion buttonBg = AssetsLoader.getButton();
        buttons = new JButton[3];
        buttons[0] = new JButton(buttonBg, AssetsLoader.getCreateRoom());
        buttons[1] = new JButton(buttonBg, AssetsLoader.getJoinGame());
        buttons[2] = new JButton(buttonBg, AssetsLoader.getExitGame());
        helpButton = new JButton(AssetsLoader.getHelp());

        soundButtons = new JButton[2];
        soundButtons[0] = new JButton(AssetsLoader.getSoundImage(false));
        soundButtons[1] = new JButton(AssetsLoader.getSoundImage(true));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, AssetsLoader.getScreenWidth(), AssetsLoader.getScreenHeight());
        batch = new SpriteBatch();

        logo = AssetsLoader.getLogo();
        logoX = (AssetsLoader.getScreenWidth() - logo.getRegionWidth())/2;
        logoY = (AssetsLoader.getScreenHeight() - logo.getRegionHeight() - 10)-50;

        int buttonMargin = 15;
        int buttonsHeight = 3*buttonMargin;
        for(int i=0; i<buttons.length; i++) {
            buttonsHeight += buttons[i].getRegionHeight();
        }

        for(int i=buttons.length-1;i>=0;i--) {
            int x, y;
            x = (AssetsLoader.getScreenWidth() - buttons[i].getRegionWidth())/2;
            if (i == buttons.length - 1) {
                y = ((AssetsLoader.getScreenHeight() - buttonsHeight) / 2) - 10;
            }
            else {
                y = ((int) buttons[i+1].getPosY()) +
                        buttons[i+1].getRegionHeight() + buttonMargin;
            }
            buttons[i].setPos(x, y);
        }

        float x = AssetsLoader.getScreenWidth() - helpButton.getRegionWidth() - 10;
        float y = 10;
        helpButton.setPos(x, y);

        soundButtons[0].setPos(10, 10);
        soundButtons[1].setPos(10, 10);

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(backgroundImage, 0, 0);
        batch.draw(logo, logoX, logoY);
        for(int i=0;i<buttons.length;i++) {
            buttons[i].draw(batch);
        }
        helpButton.draw(batch);

        if (!soundOn) {
            soundButtons[0].draw(batch);
        }
        else {
            soundButtons[1].draw(batch);
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height) {


    }

    @Override
    public void hide() {


    }

    @Override
    public void pause() {


    }

    @Override
    public void resume() {


    }

    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            Gdx.app.exit();
            return true;
        }
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

        for(int i=0;i<buttons.length;i++) {
            if (buttons[i].isPressed(touchPos)) {
                if (i == 0) {
                    game.gotoCreateRoomScreen();
                }
                else if (i == 1) {
                    game.gotoSearchRoomScreen();
                    //game.gotoGameScreen(null);
                }
                else if (i == 2) {
                    //	game.showHighscores();
                    Gdx.app.exit();
                }
                break;
            }
        }
        if (helpButton.isPressed(touchPos)) {
            //game.showHelp();
        }
        if (soundOn && soundButtons[0].isPressed(touchPos)) {
            soundOn = false;
            game.onGameSound();

//        	State.setSoundOn(soundOn);
//        	game.getSoundManager().setSoundOn(soundOn);
        }
        else if (!soundOn && soundButtons[1].isPressed(touchPos)) {
            soundOn = true;
            game.silenGameSound();
//        	State.setSoundOn(soundOn);
//        	game.getSoundManager().setSoundOn(soundOn);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        return true;
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
