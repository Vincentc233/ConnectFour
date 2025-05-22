package io.github.some_example_name;

import com.badlogic.gdx.Input;
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
    Vector2 touchPos; // By creating a single instance variable rather than a local variable we can prevent the garbage collector from triggering, which prevents lag spikes

    Texture backgroundTexture;
    Texture blueCoin;
    Texture redCoin;
    SpriteBatch spriteBatch;
    FitViewport viewport;

    @Override //overrides its superclass method
    public void create() {
        backgroundTexture = new Texture("background.png");
        blueCoin = new Sprite("blue.png");
        redCoin = new Sprite("red.png");
        redCoin.setSize(1,1);
        blueCoin.setSize(1,1);
        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);
        touchPos = new Vector2();

    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }
    private void input() {
        float speed = .25;
        float delta = Gdx.graphics.getDeltaTime(); // returns time between frames(delta)

        if(Gdx.input.isKeyPress(Input.Keys.RIGHT)) {
            bucketSprite.translateX(speed*delta); // Moves the sprite
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            bucketSprite.translateX(-speed * delta);
        }

        if(Gdx.input.isTouched()){ // If user clicks the screen
            touchPos.set(Gdx.input.getX(), Gdx.input.getY()); // gets where the user clicked
            viewport.unproject(touchPos); // Covert the U
            bucketSprite.setCenterX(touchPos.x);
        }
    }

    private void logic() {

    }

    private void draw() {
        ScreenUtils.clear(Color.TAN);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined); //Makes sure that the images are shown in the right place
        spriteBatch.begin(); //drawing starts here

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        blueCoin.draw(spriteBatch);
        redCoin.draw(spriteBatch);

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
