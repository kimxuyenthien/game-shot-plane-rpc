package hust.projectair.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

/**
 * Created by hieutrinh on 10/4/15.
 */
public class TouchControl {

    private Stage stage;
    private Touchpad touchpad;


    public TouchControl(Stage mStage)
    {

        stage = mStage;
        create();
    }

    public void create() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        touchpad = new Touchpad(20, skin);
        touchpad.setBounds(15, 15, 100, 100);
        stage.addActor(touchpad);
    }


    public void dispose() {

    }


    public void update()
    {

    }

    public float getCurrentSpeedX()
    {
        return touchpad.getKnobPercentX();
    }

    public float getCurrentSpeedY()
    {
        return touchpad.getKnobPercentY();
    }

    public void render() {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }


}
