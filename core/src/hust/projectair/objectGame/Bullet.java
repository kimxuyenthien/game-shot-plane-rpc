package hust.projectair.objectGame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import hust.projectair.GameState.MainGameState;
import hust.projectair.utils.AssetsLoader;

/**
 * Created by hieutrinh on 10/28/15.
 */
public class Bullet
{

    public static int STAND_BULLET_X;
    public static int STAND_BULLET_Y;

    public static float STAND_VELOCITY = 14f;

    private Vector2 position;
    private Vector2 velocity;

    private TextureRegion regionBullet;
    private Circle bound;

    private boolean isShow;


    public Bullet()
    {

        STAND_BULLET_X = MainGameState.SCREEN_WIDTH/2+34;
        STAND_BULLET_Y = MainGameState.SCREEN_HEIGHT/2+46;

        position = new Vector2(STAND_BULLET_X,STAND_BULLET_Y);
        velocity = new Vector2(0,0);

        regionBullet = getBullet();
        bound = new Circle(STAND_BULLET_X,STAND_BULLET_Y,regionBullet.getRegionHeight()/2);

        isShow = false;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setIsShow(boolean isShow) {
        this.isShow = isShow;
    }

    public Circle getBound()
    {
        return bound;
    }

    public void update()
    {

        if(velocity.x == 0 && velocity.y == 0)
        {
            isShow = false;
        }
        else
        {
            isShow = true;
        }

        float x = position.x;
        float y = position.y;

        position.set(x+velocity.x, y+velocity.y);
        bound.setPosition(position.x , position.y );

    }


    public void render(SpriteBatch batch)
    {
        if(isShow)
        {
            batch.draw(regionBullet,position.x, position.y);
        }

    }


    public void setRotation(float anphaInput)
    {


        double anpha = (anphaInput*Math.PI)/180;

        float velocityX = (float) (STAND_VELOCITY*Math.cos(anpha));
        float velocityY = (float) (STAND_VELOCITY*Math.sin(anpha));

        velocity.x = velocityX;
        velocity.y = velocityY;


    }


    public void resetBullet()
    {
        regionBullet = getBullet();
        position.set(STAND_BULLET_X, STAND_BULLET_Y);
        bound.setPosition(STAND_BULLET_X, STAND_BULLET_Y );
        velocity.set(0, 0);

        isShow = false;
    }

    public void setPosition(float x, float y)
    {
        position = new Vector2(x,y);
    }

    public void setStateGun()
    {
        regionBullet = getBullet();
    }

    public void setExplosive()
    {
        regionBullet = getExplosion();
        velocity.set(0, 0);

    }

    public TextureRegion getExplosion()
    {
        return AssetsLoader.getTextureExplosition();
    }

    public TextureRegion getBullet()
    {
        return AssetsLoader.getBullet();
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Vector2 getPosition() {
        return position;
    }
}
