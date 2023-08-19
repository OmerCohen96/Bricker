package bricker.main;

import bricker.brick_strategies.BrickStrategyFactory;
import bricker.brick_strategies.CollisionStrategy;
import bricker.brick_strategies.RemoveBrickStrategy;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Brick;
import bricker.gameobjects.Paddle;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.sql.Time;
import java.util.Random;

public class BrickerGameManager extends GameManager {
    private static final int DISQUALIFICATIONS = 3; // if we want multiple disqualifications until the game end
    private static final float BALL_SPEED = 250f;
    private static final float BALL_SIZE = 30f;
    private static final float PADDLE_HEIGHT = 15f;
    private static final float PADDLE_WIDTH = 150f;
    private static final float BRICK_HEIGHT = 20f;
    private static final int BRICK_PER_ROW = 8;
    private static final int BRICKS_PER_COL = 5;
    private static final float MIN_DISTANCE_FROM_SCREEN_EDGE = 10f;
    private static final float SLOW_MOTION_FACTOR = 0.5f;
    private GameObject ball;
    private Vector2 windowDimension;
    private WindowController windowController;
    private Counter counterBricks, counterDisqualifications;
    private GameObject[] disqualificationsHearts;
    private long start_time;
    private boolean slowMotion = false;

    public BrickerGameManager(
            String windowTitle, Vector2 windowsDimension) {
        super(windowTitle, windowsDimension);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowDimension = windowController.getWindowDimensions();
        this.windowController = windowController;

        // Create background

        Renderable backGroundImg = imageReader.readImage("assets/DARK_BG2_small.jpeg", false);
        GameObject backGround = new GameObject(Vector2.ZERO, windowDimension, backGroundImg);
        gameObjects().addGameObject(backGround, Layer.BACKGROUND);

        // Create game objects
        counterDisqualifications = new Counter();
        if (DISQUALIFICATIONS > 0)
            createDisqualifications(imageReader, this.windowDimension);

        creatBoarders(imageReader, this.windowDimension);
        createBricks(imageReader, soundReader, this.windowController);
        creatPaddle(imageReader, inputListener, this.windowController);
        creatBall(imageReader, soundReader, this.windowController);

        handleSlowMotion(true);
    }

    private void handleSlowMotion(boolean startSlowMotion) {
        if (startSlowMotion) {
            slowMotion = true;
            windowController.setTimeScale(SLOW_MOTION_FACTOR);
            start_time = System.currentTimeMillis();
        } else {
            slowMotion = false;
            windowController.setTimeScale(1);
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (slowMotion && System.currentTimeMillis() - start_time > 2000) {
            handleSlowMotion(false);
        }
        checkForGameEnd();
    }

    private void checkForGameEnd() {
        String prompt = "";
        if (ball.getTopLeftCorner().y() > windowDimension.y()) {
            if (counterDisqualifications.value() > 0){
                this.handleDisqualification();
            } else {
                prompt = "you LOSE!";
            }
        }
        if (counterBricks.value() == 0) {
            prompt = "you WIN!";
        }

        if (!prompt.isEmpty()) {
            prompt += " Play again?";
            if (windowController.openYesNoDialog(prompt)) {
                windowController.resetGame();
            } else {
                windowController.closeWindow();
            }
        }
    }

    private void createDisqualifications(ImageReader imageReader, Vector2 windowDimension) {
        Renderable heart = imageReader.readImage("assets/heart.png", true);
        Vector2 heartDimension = new Vector2(25f, 25f);
        float spacious = 5f;
        disqualificationsHearts = new GameObject[DISQUALIFICATIONS];

        for (int i = 0; i < DISQUALIFICATIONS; i++) {
            GameObject heartForDisqual = new GameObject(
                    Vector2.ZERO, heartDimension, heart);
            heartForDisqual.setTopLeftCorner(
                    new Vector2(MIN_DISTANCE_FROM_SCREEN_EDGE + (heartDimension.x() + spacious) * i,
                            windowDimension.y() - heartDimension.y() - 10));
            gameObjects().addGameObject(heartForDisqual, Layer.BACKGROUND);
            disqualificationsHearts[i] = heartForDisqual;
        }
        counterDisqualifications.increaseBy(DISQUALIFICATIONS);
    }

    private void handleDisqualification() {
        counterDisqualifications.decrement();
        this.gameObjects().removeGameObject(
                disqualificationsHearts[counterDisqualifications.value()], Layer.BACKGROUND);
        this.ball.setCenter(windowDimension.mult(0.5f));

        Random dir = new Random();
        if (dir.nextBoolean()){
            this.ball.setVelocity(ball.getVelocity().multX(-1));
        }
        handleSlowMotion(true);
    }

    private void createBricks(ImageReader imageReader, SoundReader soundReader,
                              WindowController windowController) {
        Renderable brickImage = imageReader.readImage("assets/brick.png", true);

        float brickWidth = (windowDimension.x() - (BRICK_PER_ROW - 1) - 2 * MIN_DISTANCE_FROM_SCREEN_EDGE)
                / (float) BRICK_PER_ROW;
        Vector2 brickDimension = new Vector2(brickWidth, BRICK_HEIGHT);

        counterBricks = new Counter(BRICK_PER_ROW * BRICKS_PER_COL);
        BrickStrategyFactory factory = new BrickStrategyFactory(this.gameObjects(), counterBricks, windowController, ball);

        for (int i = 0; i < BRICKS_PER_COL; i++) {
            for (int j = 0; j < BRICK_PER_ROW; j++) {
                float cordY = BRICK_HEIGHT * (float) i + MIN_DISTANCE_FROM_SCREEN_EDGE + (float) i;
                float cordX = brickWidth * (float) j + MIN_DISTANCE_FROM_SCREEN_EDGE + (float) j;

                GameObject brick = new Brick(Vector2.ZERO, brickDimension, brickImage, factory.getStrategy());
                brick.setTopLeftCorner(new Vector2(cordX, cordY));
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }
        }
    }

    private void creatBoarders(ImageReader imageReader, Vector2 windowDimension) {

        Vector2 verticalPaddleDimension =
                new Vector2(1, windowDimension.y());
        Vector2 horizontalPaddleDimension = new Vector2(windowDimension.x(), 1);

        GameObject leftBoarder = new GameObject(Vector2.LEFT, verticalPaddleDimension, null);
        GameObject rightBoarder =
                new GameObject(Vector2.RIGHT.mult(windowDimension.x() + 1), verticalPaddleDimension, null);
        GameObject upBoarder = new GameObject(Vector2.UP.mult(2), horizontalPaddleDimension, null);
        GameObject buttonBoarder =
                new GameObject(Vector2.DOWN.mult(windowDimension.y() + 1), horizontalPaddleDimension, null);

        gameObjects().addGameObject(leftBoarder);
        gameObjects().addGameObject(rightBoarder);
        gameObjects().addGameObject(upBoarder);
        //gameObjects().addGameObject(buttonBoarder);
    }

    private void creatPaddle(
            ImageReader imageReader, UserInputListener inputListener,WindowController windowController) {

        Renderable paddleImage = imageReader.readImage("assets/paddle.png", true);
        GameObject paddle = new Paddle(Vector2.ZERO,
                new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT), paddleImage, inputListener, windowDimension);
        paddle.setCenter(
                new Vector2(windowDimension.x() / 2, windowDimension.y() - 20));
        gameObjects().addGameObject(paddle);
    }

    private void creatBall(ImageReader imageReader, SoundReader soundReader, WindowController windowController) {
        Renderable ballImage =
                imageReader.readImage("assets/ball.png", true);
        Sound collisionSound =
                soundReader.readSound("assets/blop_cut_silenced.wav");

        this.ball = new Ball(
                Vector2.ZERO, new Vector2(BALL_SIZE, BALL_SIZE), ballImage, collisionSound);
        ball.setCenter(windowDimension.mult(0.5f));
        super.gameObjects().addGameObject(ball);

        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;

        Random random = new Random();

        if (random.nextBoolean())
            ballVelX = -BALL_SPEED;

        ball.setVelocity(new Vector2(ballVelX, ballVelY));

    }


    public static void main(String[] args) {

        new BrickerGameManager("Bricker", new Vector2(1000, 640)).run();


    }
}


