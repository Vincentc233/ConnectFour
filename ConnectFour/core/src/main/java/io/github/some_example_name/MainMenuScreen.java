package io.github.some_example_name;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen; // Screens handle processing and render one component of your game(ex. game screen, setting screen, main menu screen...)
public class MainMenuScreen implements Screen{
    final Drop game;

    public MainMenuScreen(final Drop game){
        this.game = game; //allows us to call Drop class's methods
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();

        game.font.draw(game.batch, "Connect 4", 1, 1.5f);
        game.font.draw(game.batch, "Click to begin playing", 1, 1);
        game.batch.end();

        if(Gdx.input.isTouched()){
            game.setScreen(new GameScreen(game));
            dispose();

        }
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
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
