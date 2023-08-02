package bricker.main;

import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class BrickerGameManager extends GameManager {

    public BrickerGameManager (String windowTitle, Vector2 windowsDimension){
        super(windowTitle, windowsDimension);
    }
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        Renderable ballImage =
                new ImageReader(windowController).readImage("assets/ball.png", true);

        GameObject ball = new GameObject(Vector2.ZERO,new Vector2(20, 20), ballImage);

        gameObjects().addGameObject(ball);
    }

    public static void main(String[] args) {
        new BrickerGameManager("Bricker", new Vector2(700, 500)).run();

//        new GameManager("Bricker",
//                new Vector2(700, 500)).run();


    }
}
