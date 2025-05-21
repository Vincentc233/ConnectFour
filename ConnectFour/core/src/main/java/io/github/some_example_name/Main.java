package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    Texture backgroundTexture;
    Texture blueCoin;
    Texture redCoin;
    SpriteBatch spriteBatch;
    FitViewport viewport;

    @Override //overrides its superclass method
    public void create() {
        backgroundTexture = new Texture("background.png");
        blueCoin = new Texture("blue.png");
        redCoin = new Texture("red.png");
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);
    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }
    private void input() {

    }

    private void logic() {

    }

    private void draw() {
        ScreenUtils.clear(Color.TAN);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined); //Makes sure that the images are shown in the right place
        spriteBatch.begin(); //drawing starts here

        spriteBatch.draw(blueCoin, 0,0,1,1);
        spriteBatch.draw(redCoin, 1,0,1,1);

        spriteBatch.end();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void resize(int width, int height){
        viewport.update(width, height, true); //centers the camera
    }
}
