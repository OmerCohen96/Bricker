package bricker.main;

import bricker.gameobjects.Ball;
import bricker.gameobjects.Paddle;
import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

public class BrickerGameManager extends GameManager {
    private static final float BALL_SPEED = 250f;
    private static final float BALL_SIZE = 30f;
    private static final float PADDLE_HEIGHT = 30f;
    private static final float PADDLE_WIDTH = 150f;

    public BrickerGameManager(
            String windowTitle, Vector2 windowsDimension) {
        super(windowTitle, windowsDimension);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        windowController.setTargetFramerate(120);

        //creat ball
//        Renderable ballImage =
//                new ImageReader(windowController).readImage("assets/ball.png", true);
//        Sound ballSound = soundReader.readSound("assets/blop_cut_silenced.wav");
//        GameObject ball = new Ball(Vector2.ZERO, new Vector2(30, 30), ballImage, ballSound);
//        ball.setVelocity(Vector2.DOWN.mult(500));
//        ball.setCenter(windowController.getWindowDimensions().mult(0.5f));
//        gameObjects().addGameObject(ball);

        creatBoarders(imageReader, windowController);
        creatPaddle(imageReader, inputListener, soundReader, windowController);
//        GameObject paddle = new Paddle(Vector2.ZERO,
//                new Vector2(150, 15), paddleImage, inputListener, windowController.getWindowDimensions());
//        paddle.setCenter(
//                new Vector2(windowController.getWindowDimensions().x() / 2,
//                windowController.getWindowDimensions().y() - 20));
//        gameObjects().addGameObject(paddle);
        creatBall(imageReader, soundReader, windowController);

        // paddle up for test sound
//        Renderable paddleImage = new ImageReader(windowController)
//                .readImage("assets/paddle.png", true);
//        GameObject paddletest =
//                new GameObject(Vector2.LEFT, new Vector2(150, 15), paddleImage);
//        paddletest.setCenter(
//                new Vector2(windowController.getWindowDimensions().x() / 2, 20));
//        gameObjects().addGameObject(paddletest);

    }

    private void creatBoarders(ImageReader imageReader, WindowController windowController) {
        Vector2 windowDimension = windowController.getWindowDimensions();

        Vector2 verticalPaddleDimension =
                new Vector2(1, windowDimension.y());
        Vector2 horizontalPaddleDimension = new Vector2(windowDimension.x(), 1);

        GameObject leftBoarder = new GameObject(Vector2.ZERO, verticalPaddleDimension, null);
        GameObject rightBoarder =
                new GameObject(Vector2.RIGHT.mult(windowDimension.x()), verticalPaddleDimension, null);
        GameObject upBoarder = new GameObject(Vector2.UP, horizontalPaddleDimension, null);
        GameObject buttonBoarder =
                new GameObject(Vector2.DOWN.mult(windowDimension.y()), horizontalPaddleDimension, null);


        gameObjects().addGameObject(leftBoarder);
        gameObjects().addGameObject(rightBoarder);
        gameObjects().addGameObject(upBoarder);
        gameObjects().addGameObject(buttonBoarder);
    }

    private void creatPaddle(
            ImageReader imageReader, UserInputListener inputListener,
            SoundReader soundReader, WindowController windowController) {

        Vector2 windowDimension = windowController.getWindowDimensions();
        Renderable paddleImage = new ImageReader(windowController)
                .readImage("assets/paddle.png", true);
        GameObject paddle = new Paddle(Vector2.ZERO,
                new Vector2(150, 15), paddleImage, inputListener, windowDimension);

        paddle.setCenter(
                new Vector2(windowDimension.x() / 2, windowDimension.y() - 20));

        gameObjects().addGameObject(paddle);
    }

    private void creatBall(ImageReader imageReader, SoundReader soundReader, WindowController windowController) {
        Renderable ballImage =
                new ImageReader(windowController).readImage("assets/ball.png", true);
        Sound collisionSound =
                new SoundReader(windowController).readSound("assets/blop_cut_silenced.wav");

        GameObject ball = new Ball(
                Vector2.ZERO, new Vector2(BALL_SIZE, BALL_SIZE), ballImage, collisionSound);
        ball.setCenter(windowController.getWindowDimensions().mult(0.5f));
        super.gameObjects().addGameObject(ball);

        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;

        Random random = new Random();

        if (random.nextBoolean())
            ballVelX = -BALL_SPEED;
        if (random.nextBoolean())
            ballVelY = -BALL_SPEED;

            ball.setVelocity(new Vector2(ballVelX, ballVelY));

        }


        public static void main (String[]args){

            new BrickerGameManager("Bricker", new Vector2(700, 515)).run();

        }
    }


