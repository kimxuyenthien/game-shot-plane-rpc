package hust.projectair.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**
 * Created by hieutrinh on 10/1/15.
 */
public class AssetsLoader {


    public static Texture texture, background_mini, textureMini;
    public static TextureRegion background, airOne, airTwo, airThree;
    public static Animation explosiveAnimation;
    public static TextureRegion  airEnemyStand,
           airAlliedStand,explo1, explo2, explo3;

    private static int SCREEN_WIDTH = 800;
    private static int SCREEN_HEIGHT = 480;

    private static TextureAtlas atlas;
    private static TextureAtlas textAtlas;
    private static TextureAtlas atlasExplo;
    private static Texture textureRada;


    private static Texture positionGun;
    private static Texture background3;
    private static Texture background2;
    private static Texture background4;

    private static Texture backgroundList;
    private static Texture buttonShoot;


    public static TextureRegion airAlliedMini1, airAlliedMini2, airEnemyMini1, airEnemyMini2;


    public static Sound soundGun;
    public static Sound soundExplosive;
    public static Sound soundStartGame;
    public static Sound soundWin;
    public static Sound soundLose;

    /**
     * load data for game
     */
    public static void load() {
        texture = new Texture(Gdx.files.internal("images/texture.png"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        textureMini = new Texture(Gdx.files.internal("images/notation.png"));

        background_mini = new Texture(Gdx.files.internal("images/texture.png"));


        atlas = new TextureAtlas(Gdx.files.internal("images/game.atlas"));


        textAtlas  = new TextureAtlas(Gdx.files.internal("images/text-images.atlas"));


        atlasExplo = new TextureAtlas(Gdx.files.internal("images/explosition.atlas"));


        background3 = new Texture(Gdx.files.internal("images/background3.jpg"));
        background2= new Texture(Gdx.files.internal("images/background2.jpg"));
        background4 = new Texture(Gdx.files.internal("images/background4.jpg"));
        backgroundList = new Texture(Gdx.files.internal("images/background_list.png"));

        positionGun = new Texture(Gdx.files.internal("images/positiongun.png"));
        buttonShoot = new Texture(Gdx.files.internal("images/shoot_button.png"));


        textureRada = new Texture(Gdx.files.internal("images/map_rada.jpg"));


        soundGun = Gdx.audio.newSound(Gdx.files.internal("images/audio/sound_gun.ogg"));
        soundExplosive = Gdx.audio.newSound(Gdx.files.internal("images/audio/explosive.ogg"));
        soundStartGame = Gdx.audio.newSound(Gdx.files.internal("images/audio/sound_background.mp3"));
        soundWin = Gdx.audio.newSound(Gdx.files.internal("images/audio/sound_you_win.mp3"));
        soundLose = Gdx.audio.newSound(Gdx.files.internal("images/audio/sound_you_lose.mp3"));
       // soundStartGame = Gdx.audio.newSound(Gdx.files.internal("soundStartGame.wav"));

        background = new TextureRegion(texture, 0, 0, 128, 128);
        airAlliedStand = new TextureRegion(texture, 128, 32, 72, 96);
        airEnemyStand= new TextureRegion(texture, 200, 32, 72, 96);

        airAlliedMini1 = new TextureRegion(textureMini, 257, 31, 96, 96);
        airAlliedMini2 = new TextureRegion(textureMini, 129, 1, 126, 126);

        airEnemyMini1 = new TextureRegion(textureMini, 355, 31, 96, 96);
        airAlliedMini2 = new TextureRegion(textureMini, 1, 1, 126, 126);

        explo1 = new TextureRegion(texture, 128, 0, 32, 32);
        explo2 = new TextureRegion(texture, 446, 88, 40, 40);
        explo3 = new TextureRegion(texture, 392, 76, 54, 52);

        TextureRegion[] backgrounds = {explo1, explo2, explo3};
        explosiveAnimation = new Animation(0.06f, backgrounds);
        explosiveAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }


    public TextureRegion getTextureGun()
    {
        return atlasExplo.findRegion("explo1");
    }

    public static TextureRegion getBullet()
    {
        TextureAtlas atlasExplo = new TextureAtlas(Gdx.files.internal("images/explosition.atlas"));

        return atlasExplo.findRegion("bullet");
    }


    public static TextureRegion getTextureExplosition()
    {
        return atlasExplo.findRegion("explo4");
    }

    public TextureRegion[] getAnimationGunMatch()
    {
        TextureRegion[] gunMatch = new TextureRegion[5];


        gunMatch[0] = atlasExplo.findRegion("explo1");
        gunMatch[1] = atlasExplo.findRegion("explo3");
        gunMatch[2] = atlasExplo.findRegion("explo4");
        gunMatch[3] = atlasExplo.findRegion("explo5");
        gunMatch[4] = atlasExplo.findRegion("shotting");


        return gunMatch;
    }


    public TextureRegion[] getAnimationGunNotMatch()
    {
        TextureRegion[] gunNotMatch = new TextureRegion[2];

        gunNotMatch[0] = atlasExplo.findRegion("explo1");
        gunNotMatch[1] = atlasExplo.findRegion("shotting");

        return gunNotMatch;

    }

    public static  int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }



    public static Texture getShotting() {
        return buttonShoot;
    }


    public static Texture getBackground4()
    {
        return background4;
    }
    public static Texture getBackground3()
    {
        return background3;
    }
    public static Texture getBackground2() {
        return background2;
    }
    public static Texture getBackgroundList()
    {
        return backgroundList;
    }



    public static TextureRegion getHelp() {
        return atlas.findRegion("Help");
    }

    public static TextureRegion getButton() {
        return atlas.findRegion("button");
    }

    public static TextureRegion getBack()
    {
        return atlas.findRegion("back");
    }

    public static TextureRegion getGrey2() {
        return atlas.findRegion("number_two_grey");
    }

    public static TextureRegion getGrey3() {
        return atlas.findRegion("number_three_grey");
    }

    public static TextureRegion getNumber(int number) {
        return atlas.findRegion("number", number);
    }

    public static TextureRegion getBasket() {
        return atlas.findRegion("basket");
    }

    public static TextureRegion getClockBase() {
        return atlas.findRegion("base");
    }

    public static TextureRegion getSecond(int second) {
        return atlas.findRegion("second", second);
    }

    public static TextureRegion getSecondRed(int second) {
        return atlas.findRegion("second_red", second);
    }

    public static TextureRegion getPause() {
        return atlas.findRegion("player_pause");
    }

    public static TextureRegion getSoundImage(boolean on) {
        if (on) {
            return atlas.findRegion("sound_on");
        }
        return atlas.findRegion("sound_off");
    }



    public static TextureRegion getExploAsitionAir()
    {
        return atlasExplo.findRegion("explo4");
    }



    public static TextureRegion getBadAppleFrame(int frame) {
        return atlas.findRegion("bad_apple", frame);
    }

    public static TextureRegion getStarFrame(int frame) {
        return atlas.findRegion("star", frame);
    }

    public static TextureRegion getMinusSign() {
        return atlas.findRegion("minus");
    }

    public static TextureRegion getRestart() {
        return atlas.findRegion("restart");
    }

    public static TextureRegion getTimes2() {
        return atlas.findRegion("x2");
    }
    public static TextureRegion getLogo() {
        return textAtlas.findRegion("air-fight-game");
    }

    public static TextureRegion getFight() {
        return textAtlas.findRegion("fight");
    }

    public static TextureRegion getOne() {
        return textAtlas.findRegion("1");
    }

    public static TextureRegion getTwo() {
        return textAtlas.findRegion("2");
    }

    public static TextureRegion getThree() {
        return textAtlas.findRegion("3");
    }

    public static TextureRegion getAbout() {
        return textAtlas.findRegion("about");
    }

    public static TextureRegion getCreateRoom() {
        return textAtlas.findRegion("create-room");
    }

    public static TextureRegion getExitGame() {
        return textAtlas.findRegion("exit-game");
    }

    public static TextureRegion getGuideGame() {
        return textAtlas.findRegion("guide-game");
    }

    public static TextureRegion getJoinGame() {
        return textAtlas.findRegion("join-room");
    }

    public static TextureRegion getJoin() {
        return textAtlas.findRegion("join");
    }

    public static TextureRegion getOk() {
        return textAtlas.findRegion("ok");
    }

    public static TextureRegion getPower() {
        return textAtlas.findRegion("power");
    }
    public static TextureRegion getScan(){
        return textAtlas.findRegion("scan");
    }

    public static TextureRegion getProtect() {
        return textAtlas.findRegion("protect");
    }


    public static TextureRegion getCancel(){

        return textAtlas.findRegion("cancel");
    }
    public TextureRegion getReject() {
        return textAtlas.findRegion("reject");
    }

    public static TextureRegion getYouLose() {
        return textAtlas.findRegion("you-lose");
    }

    public static TextureRegion getYouWin() {
        return textAtlas.findRegion("you-win");
    }
    public static TextureRegion getListMember() {
        return textAtlas.findRegion("list-member");
    }


    public static void dispose() {
        if (texture != null) {
            texture.dispose();
        }

    }

}
