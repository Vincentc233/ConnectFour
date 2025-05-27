package io.github.some_example_name;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Main implements ApplicationListener{
    Vector2 touchPos = new Vector2(); // By creating a single instance variable rather than a local variable we can prevent the garbage collector from triggering, which prevents lag spikes
    Texture backgroundTexture;
    Texture yellowCoinTexture;
    Sprite yellowCoin;
    Texture redCoinTexture;
    Sprite redCoin;
    SpriteBatch spriteBatch;
    FitViewport viewport;
    int redScore = 0;
    int yellowScore = 0;
    private float inputCooldown = 0f;
    private static final float COOLDOWN_TIME = 0.3f;
    public int[][] gameBoard = new int[6][7];
    private boolean redTurn = true;
    public void create() {

        viewport = new FitViewport(7, 6);
        backgroundTexture = new Texture("background.png");
        yellowCoinTexture = new Texture("blue.png");
        redCoinTexture = new Texture("red.png");
        yellowCoin = new Sprite(yellowCoinTexture);
        redCoin = new Sprite(redCoinTexture);
        redCoin.setSize(1f,1f);
        yellowCoin.setSize(1f,1f);
        spriteBatch = new SpriteBatch();
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime(); // Time since last frame
        if (inputCooldown > 0) {
            inputCooldown -= deltaTime;
        }
        play();
        draw();
        updateScore();
    }

    private void input() {

    }

    private void play(){
        if (Gdx.input.isTouched() && inputCooldown <= 0){
            touchPos.set(Gdx.input.getX(), Gdx.input.getY()); // gets where the user clicked
            viewport.unproject(touchPos); // Convert the units to the world units of the viewport
            for (int c = 0; c < 7; c++) {
                if (touchPos.x >= c && touchPos.x < c+1) {
                    for (int r = 5; r > -1; r--) {
                        if (gameBoard[r][c] == 0) {
                            if(redTurn) {
                                gameBoard[r][c] = 2; //sets it to a redCoin
                                redTurn = false;

                            }
                            else {
                                gameBoard[r][c] = 1; //sets it to a blueCoin
                                redTurn = true;

                            }
                            inputCooldown = COOLDOWN_TIME;
                            break;
                        }
                    }

                }

            }
            System.out.println(touchPos.x);
        }

    }
    private void logic() {
        float worldWidth = viewport.getWorldWidth();
        float worldLength = viewport.getWorldHeight();

        float coinWidth = yellowCoin.getWidth();
        float coinHeight = yellowCoin.getHeight();
    }

    private void draw() {
        ScreenUtils.clear(Color.TAN);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined); //Makes sure that the images are shown in the right place
        spriteBatch.begin(); //drawing starts here

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        for(int r =0 ; r<6; r++){
            for(int c =0;c<7; c++){
                if(gameBoard[r][c] ==0) continue;
                else if(gameBoard[r][c] == 2){
                    redCoin.setX(c);
                    redCoin.setY(5-r);
                    redCoin.draw(spriteBatch);
                }
                else{
                    yellowCoin.setX(c);
                    yellowCoin.setY(5-r);
                    yellowCoin.draw(spriteBatch);
                }
            }
        }
        spriteBatch.end();
    }

    private void updateScore() {
        boolean allEqual = true;
        for (int r = 0; r < gameBoard.length; r++) {
            for (int c = 0; c < gameBoard[0].length; c++) {
                if (gameBoard[r][c] != 0 && c < gameBoard[0].length-4) {//checks for horizontal connect 4s
                    for (int i = 1; i <= 3; i++) {
                        if (gameBoard[r][c] == gameBoard[r][c + i]) {
                            continue;
                        }
                        else allEqual = false;
                    }
                    if (allEqual && gameBoard[r][c] == 2){
                        redScore++;
                        System.out.println("RedScore:" + redScore);
                    }
                    else if (allEqual && gameBoard[r][c] == 1){
                        yellowScore++;
                        System.out.println("YellowScore:" + yellowScore);
                    }
                    allEqual = true;
                }
                if (gameBoard[r][c] != 0 && r < gameBoard.length-3) {//checks for vertical connect 4s
                    for (int i = 1; i <= 3; i++) {
                        if (gameBoard[r][c] == gameBoard[r+i][c]) {
                            continue;
                        }
                        else allEqual = false;
                    }
                    if (allEqual && gameBoard[r][c] == 2){
                        redScore++;
                        System.out.println("RedScore:" + redScore);
                    }
                    else if (allEqual && gameBoard[r][c] == 1){
                        yellowScore++;
                        System.out.println("YellowScore:" + yellowScore);
                    }
                    allEqual = true;
                }
                if(gameBoard[r][c] != 0 && (r <= 3 && c > 3)){ //checks diagonal connect 4s
                    for(int i = 1; i <= 3; i++){
                        if(gameBoard[r][c] == gameBoard[r+i][c+i]){
                            continue;
                        }
                        else allEqual = false;
                    }
                    if (allEqual && gameBoard[r][c] == 2){
                        redScore++;
                        System.out.println("RedScore:" + redScore);
                    }
                    else if (allEqual && gameBoard[r][c] == 1){
                        yellowScore++;
                        System.out.println("YellowScore:" + yellowScore);
                    }
                    allEqual = true;
                }
            }
        }
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        redCoinTexture.dispose();
        yellowCoinTexture.dispose();
        spriteBatch.dispose();
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width, height, true); //centers the camera
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}

