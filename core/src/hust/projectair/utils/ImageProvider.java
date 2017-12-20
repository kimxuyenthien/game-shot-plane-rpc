package hust.projectair.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class ImageProvider {

	private int SCREEN_WIDTH = 800;

	private int SCREEN_HEIGHT = 480;

	private TextureAtlas atlas;

	private TextureAtlas textAtlas;

	private TextureAtlas atlasExplo;

	private Texture textureRada;



	private Texture positionGun;


	private Texture background1;

	private Texture background2;

	private Texture backgroundList;

	private Texture buttonShoot;

	private String locale;


	private Animation animationGunMatch;
	private Animation animationGunNotMatch;



	public ImageProvider(String locale) {
		this.locale = locale;
	}
	public void load() {
		atlas = new TextureAtlas(Gdx.files.internal("images/game.atlas"));

		if (locale.equals("de")) {
			textAtlas  = new TextureAtlas(Gdx.files.internal("images/text_images_de.atlas"));
		}
		else {
			textAtlas  = new TextureAtlas(Gdx.files.internal("images/text-images.atlas"));
		}

		atlasExplo = new TextureAtlas(Gdx.files.internal("images/explosition.atlas"));


		background1 = new Texture(Gdx.files.internal("images/background1.jpg"));
		background2= new Texture(Gdx.files.internal("images/background2.jpg"));
		backgroundList = new Texture(Gdx.files.internal("images/background_list.png"));

		positionGun = new Texture(Gdx.files.internal("images/positiongun.png"));
		buttonShoot = new Texture(Gdx.files.internal("images/shoot_button.png"));


		textureRada = new Texture(Gdx.files.internal("images/map_rada.jpg"));
	}
	public void dispose()
	{
		atlas.dispose();
		textAtlas.dispose();
		textureRada.dispose();

	}


}
