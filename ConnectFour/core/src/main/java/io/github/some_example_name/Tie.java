package io.github.some_example_name;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen; // Screens handle processing and render one component of your game(ex. game screen, setting screen, main menu screen...)
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

public class Tie implements Screen{
    final Drop setUp;
    Texture Tie = new Texture("TieScreen.png");
    public Tie (final Drop game){
        this.setUp = game; //allows us to call Drop class's methods
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        setUp.viewport.apply();
        setUp.batch.setProjectionMatrix(setUp.viewport.getCamera().combined);
        ScreenUtils.clear(Color.BLACK);

        setUp.batch.begin();

        setUp.batch.draw(Tie, 0,0,setUp.worldWidth, setUp.worldHeight);

        setUp.batch.end();
        if(Gdx.input.justTouched()){
            setUp.setScreen(new GameScreen(setUp));
            dispose();

        }
    }

    @Override
    public void resize(int width, int height) {
        setUp.viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
