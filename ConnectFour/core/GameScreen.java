package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;



public class GameScreen implements Screen {
    final Drop setUp;
    Vector2 touchPos = new Vector2(); // By creating a single instance variable rather than a local variable we can prevent the garbage collector from triggering, which prevents lag spikes
    Texture backgroundTexture;
    Texture yellowCoinTexture;
    Sprite yellowCoin;
    Texture redCoinTexture;
    Sprite redCoin;
    Texture scoreBoardYellowTexture;
    Sprite scoreBoardYellow;
    Texture scoreBoardRedTexture;
    Sprite scoreBoardRed;
    SpriteBatch spriteBatch;
    FitViewport viewport;
    private boolean allEqual = true;
    private static int redScore = 0;
    private static int yellowScore = 0;
    private float inputCooldown = 0.3f;
    private static final float COOLDOWN_TIME = 0.3f;
    public int[][] gameBoard = new int[6][7];
    private boolean redTurn = true;

    public GameScreen(final Drop game) {
        this.setUp = game;

        viewport = new FitViewport(7f, 7f);
        backgroundTexture = new Texture("board2.png");
        yellowCoinTexture = new Texture("YellowCoin.png");
        redCoinTexture = new Texture("RedCoin.png");
        scoreBoardYellowTexture = new Texture("Scoreboard_Yellow.png");
        scoreBoardYellow = new Sprite(scoreBoardYellowTexture);
        scoreBoardRedTexture = new Texture("Scoreboard_Red.png");
        scoreBoardRed = new Sprite(scoreBoardRedTexture);
        yellowCoin = new Sprite(yellowCoinTexture);
        redCoin = new Sprite(redCoinTexture);
        redCoin.setSize(1.8f,1.8f);
        yellowCoin.setSize(1.8f,1.8f);
        scoreBoardYellow.setSize(2f, 1f);
        scoreBoardRed.setSize(2f, 1f);
        spriteBatch = new SpriteBatch();
    }
    @Override
    public void render(float delta) {
        String result;
        float deltaTime = Gdx.graphics.getDeltaTime(); // Time since last frame
        if (inputCooldown > 0) {
            inputCooldown -= deltaTime;
        }
        play();
        draw();
        result = updateScore();
        if(result.equals("RedWins")){
            dispose();
            setUp.setScreen(new RedWins(setUp));
        }
        else if(result.equals("YellowWins")){
            dispose();
            setUp.setScreen(new YellowWins(setUp));
        }
    }

    private void input() {
    }

    private void play(){
        if (Gdx.input.justTouched() && inputCooldown <= 0){
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
        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight-1.1f);
        if(redTurn) spriteBatch.draw(scoreBoardRedTexture, worldWidth/2-1, worldHeight-1, 2, 1 );
        else spriteBatch.draw(scoreBoardYellowTexture, worldWidth/2-1, worldHeight-1, 2, 1 );
        for(int r =0 ; r<6; r++){
            for(int c =0;c<7; c++){
                if(gameBoard[r][c] ==0) continue;
                else if(gameBoard[r][c] == 2){
                    redCoin.setX(c);
                    redCoin.setY(5-r-0.5f);
                    redCoin.draw(spriteBatch);
                }
                else{
                    yellowCoin.setX(c);
                    yellowCoin.setY(5-r-0.5f);
                    yellowCoin.draw(spriteBatch);
                }
            }
        }
        spriteBatch.end();
    }

    private String updateScore() {
        int tempRedScore = redScore;
        int tempYellowScore = yellowScore;
        for (int r = 0; r < gameBoard.length; r++) {
            for (int c = 0; c < gameBoard[0].length; c++) {
                horizontal(r,c);
                if(redScore > tempRedScore) return "RedWins";
                else if(yellowScore > tempYellowScore) return "YellowWins";
                vertical(r,c);
                if(redScore > tempRedScore) return "RedWins";
                else if(yellowScore > tempYellowScore) return "YellowWins";
                pDiagonal(r,c);
                if(redScore > tempRedScore) return "RedWins";
                else if(yellowScore > tempYellowScore) return "YellowWins";
                nDiagonal(r,c);
                if(redScore > tempRedScore) return "RedWins";
                else if(yellowScore > tempYellowScore) return "YellowWins";
            }
        }
        return "";
    }

    private void horizontal(int r, int c){ //checks for horizontal connect 4s
        if (gameBoard[r][c] != 0 && c < gameBoard[0].length-3) {
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
    }
    private void vertical(int r, int c){ //checks for vertical connect 4s
        if (gameBoard[r][c] != 0 && r < gameBoard.length-3) {
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
    }
    private void pDiagonal(int r, int c){ //checks positive diagonal connect 4s
        if(gameBoard[r][c] != 0 && (r >= 3 && c < 4)){
            for(int i = 1; i <= 3; i++){
                if(gameBoard[r][c] == gameBoard[r-i][c+i]){
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
    private void nDiagonal(int r, int c){
        if(gameBoard[r][c] != 0 && (r < 3 && c < 4)){ //checks negative diagonal connect 4s
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
    @Override
    public void dispose() {
         backgroundTexture.dispose();
         yellowCoinTexture.dispose();
         redCoinTexture.dispose();
         spriteBatch.dispose();
    }

    @Override
    public void show() {

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

    @Override
    public void hide() {

    }
}
