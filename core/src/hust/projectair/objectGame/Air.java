package hust.projectair.objectGame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import hust.projectair.GameState.MainGameState;
import hust.projectair.config.ConfigGame;
import hust.projectair.enums.TypeAir;
import hust.projectair.utils.AssetsLoader;


/**
 * Created by hieutrinh on 10/1/15.
 */
public class Air {

    public static final int SIZE_AIR_MINI = 100;
    public static final int PROTECT_AIR = 100;
    public static final int POWER_AIR = 6;

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 positionLocal;

    private float rotation;
    private int width;
    private int height;

    private int power;
    private int protect;

    private TextureRegion regionAir;
    private TextureRegion regionAirMini;
    private TypeAir typeAir;
    private Circle bound;

    public static int MAX_VELOCITY = 3;


    public Air(float x, float y, int width, int height)

    {
        this.width = width;
        this.height = height;
        this.rotation = 0;

        position = new Vector2(x, y);
        positionLocal = new Vector2(x, y);
        velocity = new Vector2(0, 0);


        regionAir = AssetsLoader.airAlliedStand;
        regionAirMini = AssetsLoader.airAlliedMini1;

        bound = new Circle(positionLocal.x, positionLocal.y,regionAir.getRegionHeight()/2);
    }



    public void resetNewAir()
    {

        this.rotation = 0;

        long x = (long) (Math.random()* ConfigGame.MAP_WIDTH);
        long y = (long) (Math.random()*ConfigGame.MAP_HEIGHT);

        setPosition(x,y);
        setPositionLocal(MainGameState.SCREEN_WIDTH / 2, MainGameState.SCREEN_HEIGHT / 2);
        setVelocity(0,0);
        setProtect(PROTECT_AIR);


        regionAir = AssetsLoader.airAlliedStand;
        regionAirMini = AssetsLoader.airAlliedMini1;

        bound = new Circle(positionLocal.x, positionLocal.y,regionAir.getRegionHeight()/2);
    }


    public TypeAir getTypeAir() {
        return typeAir;
    }

    public void setTypeAir(TypeAir typeAir) {
        this.typeAir = typeAir;
        if(typeAir == TypeAir.ENEMY)
        {
            regionAirMini = AssetsLoader.airEnemyMini1;
            regionAir = AssetsLoader.airEnemyStand;
        }
        else
        {
            regionAirMini = AssetsLoader.airAlliedMini1;
            regionAir = AssetsLoader.airAlliedStand;
        }

    }

    public void resetAir()
    {


        long x = (long) (Math.random()* ConfigGame.MAP_WIDTH);
        long y = (long) (Math.random()*ConfigGame.MAP_HEIGHT);

        setPosition(x,y);
        setPositionLocal(MainGameState.SCREEN_WIDTH / 2, MainGameState.SCREEN_HEIGHT / 2);
        setVelocity(0,0);

    }


    public void setRotation(float mRotation) {
        rotation = mRotation;
    }

    public void update(float x, float y, float tempRotation) {


        rotation += tempRotation;

        if (x != 0 || y != 0) {
            velocity.set(x, y);
        }


        position.add(x, y);


        if(protect < 0)
        {
            setStateDestroy();
            resetAir();
            protect++;
        }


        //Gdx.app.log("MyTag", position.x + " : " + position.y);
    }


    public boolean isCollision(Circle circleBullet)
    {
        return Intersector.overlaps(circleBullet, bound);
    }


    public void render(SpriteBatch spriteBatchsAir)
    {
        spriteBatchsAir.draw(getCurrentTextureRegion(), getPositionLocal().x, getPositionLocal().y,
                getWidth() / 2.0f, getHeight() / 2.0f,
                getWidth(), getHeight(), 1, 1, getRotation());

    }

    public void rendderMini(SpriteBatch spriteBatchMini)
    {
        spriteBatchMini.draw(regionAirMini, getPositionX(), getPositionY(),
                SIZE_AIR_MINI / 2.0f, SIZE_AIR_MINI / 2f,
                SIZE_AIR_MINI, SIZE_AIR_MINI, 1, 1, 0);

    }



    public void setVelocity(float speedX, float speedY) {
        velocity = new Vector2(speedX, speedY);
    }


    public Vector2 getPositionLocal() {
        return positionLocal;
    }

    public void setPositionLocal(float x, float y)
    {
        this.positionLocal = new Vector2(x, y);
        bound.setPosition(positionLocal.x, positionLocal.y);
    }

    public void setPosition(float x, float y) {
        position = new Vector2(x, y);

    }


    public TextureRegion getCurrentTextureRegionMini(String currentIp, String ip) {

       return regionAirMini;

    }

    public TextureRegion getCurrentTextureRegion() {


       return regionAir;


    }


    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getProtect() {
        return protect;
    }

    public void setProtect(int protect) {
        this.protect = protect;
    }


    public float getPositionX() {
        return position.x;
    }

    public float getPositionY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getVelocityX() {
        return velocity.x;
    }

    public float getVelocityY() {
        return velocity.y;
    }

    public float getRotation() {
        return rotation;
    }

    public class Scrollable {

        // Protected is similar to private, but allows inheritance by subclasses.
        protected Vector2 position;
        protected Vector2 velocity;
        protected int width;
        protected int height;
        protected boolean isScrolledLeft;

        public Scrollable(float x, float y, int width, int height, float scrollSpeed) {
            position = new Vector2(x, y);
            velocity = new Vector2(scrollSpeed, 0);
            this.width = width;
            this.height = height;
            isScrolledLeft = false;
        }

        public void update(float delta) {
            position.add(velocity.cpy().scl(delta));

            // If the Scrollable object is no longer visible:
            if (position.x + width < 0) {
                isScrolledLeft = true;
            }
        }

        // Reset: Should Override in subclass for more specific behavior.
        public void reset(float newX) {
            position.x = newX;
            isScrolledLeft = false;
        }



        public float getX() {
            return position.x;
        }

        public float getY() {
            return position.y;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

    }


    public void setStateDestroy()
    {
        regionAir = AssetsLoader.getExploAsitionAir();
    }

    public void isAttacked(int power) {
        protect -= power;

        if (protect < 0) {
            protect = 0;
        }
    }

    public void playSounDestroy() {
        if (protect <= 0) {
            AssetsLoader.soundExplosive.play();
        }

    }

    public void playSoundGun() {
        if (protect > 0) {
            AssetsLoader.soundGun.play();
        }

    }

}
