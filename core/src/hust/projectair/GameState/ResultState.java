package hust.projectair.GameState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import hust.projectair.customize.JButton;
import hust.projectair.game.AirGame;
import hust.projectair.utils.AssetsLoader;
import hust.projectair.utils.ImageProvider;

/**
 * Created by hieutrinh on 10/28/15.
 */
public class ResultState extends Stage{



    private SpriteBatch batch;
    private JButton buttonState;
    private ImageProvider imageProvider;
    private int state;

    private int YOU_LOSE = 1;
    private int YOU_WIN = 2;
    private int positionX, positionY;


    private OrthographicCamera camera;
    private AirGame game;

    public ResultState(AirGame mGame, OrthographicCamera mCamera, int mState)
    {

        camera = mCamera;
        game = mGame;

        imageProvider = new ImageProvider("");
        imageProvider.load();

        batch = new SpriteBatch();

        setState(mState);

        Gdx.input.setInputProcessor(this);


    }

    public void setState(int mState)
    {
        state = mState;

        if(state == 1)
        {
            buttonState = new JButton(AssetsLoader.getYouLose());
        }
        else
        {
            buttonState = new JButton(AssetsLoader.getYouWin());
        }

        positionX = MainGameState.SCREEN_WIDTH/2-buttonState.getRegionWidth()/2;
        positionY = MainGameState.SCREEN_HEIGHT/2-buttonState.getRegionHeight()/2;

        buttonState.setPos(positionX, positionY);

    }

    @Override
    public void draw() {


        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        buttonState.draw(batch);


        batch.end();

        super.draw();



    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {



        Vector3 touchPos = new Vector3();
        touchPos.set(screenX, screenY, 0);
        camera.unproject(touchPos);


        if(buttonState.isPressed(touchPos))
        {

            game.gotoMenuScreen();
        }

        return super.touchDown(screenX, screenY, pointer, button);



    }
}
