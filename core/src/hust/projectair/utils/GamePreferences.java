package hust.projectair.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import hust.projectair.config.ConfigGame;


/**
 * Created by hieutrinh on 10/2/15.
 */
public class GamePreferences {

    private static GamePreferences instance;
    public boolean sound;
    public boolean music;
    public float volSound;
    public float volMusic;
    public int charSkin;
    public boolean showCounter;

    private Preferences preferences;


    public GamePreferences()
    {
        preferences = Gdx.app.getPreferences(ConfigGame.PREFERENCE);
    }

    public static GamePreferences getInstance()
    {
        if(instance == null)
        {
            instance = new GamePreferences();
        }

        return instance;
    }



}
