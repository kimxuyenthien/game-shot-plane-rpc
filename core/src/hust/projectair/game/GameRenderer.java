package hust.projectair.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import hust.projectair.GameState.MainGameState;
import hust.projectair.customize.JButton;
import hust.projectair.objectGame.Bullet;
import hust.projectair.objectGame.Client;
import hust.projectair.utils.AssetsLoader;

import java.util.List;

/**
 * Created by hieutrinh on 10/1/15.
 */
public class GameRenderer {


    public OrthographicCamera camera;
    public OrthographicCamera cameraMiniMap;

    private GameUpdate gameUpdate;
    private ShapeRenderer shapeRender;

    private SpriteBatch spriteBatchsAirMini;
    private SpriteBatch spriteBatchsAir;
    private List<Client> clients;
    private JButton backButton;

    private MainGameState mainGameState;
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private OrthogonalTiledMapRenderer gameMapRenderer;
    private Client originalClient;


    private static int TIME_DELAY_GUN = 1000;


    public GameRenderer(GameUpdate mGameUpdate, MainGameState mMainGameState) {


        mainGameState = mMainGameState;

        clients = mGameUpdate.getListClient();

        originalClient = clients.get(0);

        camera = mainGameState.camera;
        cameraMiniMap = mainGameState.cameraMiniMap;
        tiledMap = new TmxMapLoader().load("images/map/newmap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        gameMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        gameUpdate = mGameUpdate;

        shapeRender = new ShapeRenderer();
        shapeRender.setProjectionMatrix(camera.combined);


        initGameObject();
        initDataAssets();



        spriteBatchsAir = new SpriteBatch();
        spriteBatchsAirMini = new SpriteBatch();

        spriteBatchsAir.setProjectionMatrix(camera.combined);
        spriteBatchsAirMini.setProjectionMatrix(cameraMiniMap.combined);

        camera.position.x = clients.get(0).getAir().getPositionX();
        camera.position.y = clients.get(0).getAir().getPositionY();

        cameraMiniMap.update();
        camera.update();


        backButton = new JButton(AssetsLoader.getBack());
        backButton.setPos(10, MainGameState.SCREEN_HEIGHT);


    }




    public void render(float runTime) {


        Gdx.gl.glClearColor(10 / 255.0f, 15 / 255.0f, 230 / 255.0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Gdx.app.log("MyTag", camera.position.x + " : " + camera.position.y);
        shapeRender.begin(ShapeRenderer.ShapeType.Filled);

        // Draw Background color
        shapeRender.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
        shapeRender.rect(0, 0, MainGameState.SCREEN_WIDTH, MainGameState.SCREEN_HEIGHT);

        // End ShapeRenderer
        shapeRender.end();

        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        gameMapRenderer.setView(cameraMiniMap.combined, 100, 100, MainGameState.SCREEN_WIDTH * mainGameState.getScaleMiniMap(), MainGameState.SCREEN_HEIGHT * mainGameState.getScaleMiniMap());

        gameMapRenderer.render();


        spriteBatchsAir.begin();

        for (int i = 0; i < clients.size(); i++)
        {
            Client client = clients.get(i);
            client.render(spriteBatchsAir);

        }

        for (int i = 0; i < MainGameState.MAX_BULLET; i++)
        {
            Bullet bullet = mainGameState.getBullets()[i];
            bullet.render(spriteBatchsAir);


        }


        mainGameState.getImageGun().render(spriteBatchsAir);
        mainGameState.getImageResultGame().render(spriteBatchsAir);

        spriteBatchsAir.end();


        spriteBatchsAirMini.begin();

        for (int i = 0; i < clients.size(); i++) {

            Client client = clients.get(i);
            client.renderMini(spriteBatchsAirMini);

        }


        spriteBatchsAirMini.end();


        cameraMiniMap.update();
        camera.update();
    }




    private void initGameObject() {
        clients = gameUpdate.getListClient();
    }

    private void initDataAssets() {

    }
}
