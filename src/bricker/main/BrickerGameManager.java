package bricker.main;

import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class BrickerGameManager extends GameManager {

    public BrickerGameManager(String windowTitle, Vector2 windowsDimension) {
        super(windowTitle, windowsDimension);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        //creat ball

        Renderable ballImage =
                new ImageReader(windowController).readImage("assets/ball.png", true);

        GameObject ball = new GameObject(Vector2.ZERO, new Vector2(30, 30), ballImage);

        ball.setVelocity(Vector2.DOWN.mult(100));

        ball.setCenter(windowController.getWindowDimensions().mult(0.5f));

        gameObjects().addGameObject(ball);

        // create paddle

        Renderable paddleImage = new ImageReader(windowController)
                .readImage("assets/paddle.png", true);

        GameObject paddle = new GameObject(Vector2.ZERO ,
                new Vector2(150, 15), paddleImage);

        paddle.setCenter(new Vector2(windowController.getWindowDimensions().x()/2,
                windowController.getWindowDimensions().y()-8));

        gameObjects().addGameObject(paddle);


    }

    public static void main(String[] args) {
        new BrickerGameManager("Bricker", new Vector2(700, 500)).run();

//        new GameManager("Bricker",
//                new Vector2(700, 500)).run();


    }
}
