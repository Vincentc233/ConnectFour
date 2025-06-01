package io.github.some_example_name;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen; // Screens handle processing and render one component of your game(ex. game screen, setting screen, main menu screen...)
public class MainMenuScreen implements Screen{
    final Drop setUp;
    public MainMenuScreen(final Drop game){
        this.setUp = game; //allows us to call Drop class's methods
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        setUp.viewport.apply();
        setUp.batch.setProjectionMatrix(setUp.viewport.getCamera().combined);

        setUp.batch.begin();

        //setUp.batch.draw(setUp.backgroundTexture, 0, 0, setUp.worldWidth, setUp.worldHeight);
        setUp.font.draw(setUp.batch, "Connect 4", 1, 1.5f);
        setUp.font.draw(setUp.batch, "Click to begin playing", 1, 1);

        setUp.batch.end();

        if(Gdx.input.isTouched()){
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
