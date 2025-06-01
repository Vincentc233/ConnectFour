package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
public class Drop extends Game{
    public SpriteBatch batch;
    public BitmapFont font;
    public FitViewport viewport;
    Texture backgroundTexture;
    Sprite background;

    float worldWidth;
    float worldHeight;
    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        viewport = new FitViewport(7,6);
        backgroundTexture = new Texture("background.png");
        background = new Sprite(backgroundTexture);
        worldWidth = viewport.getWorldWidth();
        worldHeight = viewport.getWorldHeight();
        //font has 15pt, but we need to scale it to our viewport by ratio of viewport height to screen height
        font.setUseIntegerPositions(false);
        font.getData().setScale(viewport.getWorldHeight()/Gdx.graphics.getHeight());

        this.setScreen(new MainMenuScreen(this));
    }

    public void render(){
        super.render();
    }

    public void dispose(){
        batch.dispose();
        font.dispose();
    }
}
