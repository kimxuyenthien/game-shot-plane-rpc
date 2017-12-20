package hust.projectair.objectGame;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by hieutrinh on 10/29/15.
 */
public class IconImage {

    private Vector2 position;
    private TextureRegion regionImage;
    private boolean isShow;

    public IconImage(TextureRegion textureRegion)
    {
        position = new Vector2(10,100);
        regionImage = textureRegion;
        isShow = true;
    }

    public void render(SpriteBatch spriteBatch)
    {
        if(isShow)
        {
            spriteBatch.draw(regionImage, position.x, position.y);
        }
    }


    public TextureRegion getRegionImage()
    {
        return regionImage;
    }

    public void setRegionImage(TextureRegion region)
    {
        regionImage = region;
    }

    public boolean getIsShow()
    {
        return isShow;
    }

    public void setShow(boolean mIsShow)
    {
        isShow = mIsShow;
    }

    public void setPosition(float x, float y)
    {
        position = new Vector2(x,y);
    }
}
