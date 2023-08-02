package bricker.main;

import danogl.GameManager;
import danogl.util.Vector2;

public class BrickerGameManager extends GameManager {

    public BrickerGameManager (String windowTitle, Vector2 windowsDimension){
        super(windowTitle, windowsDimension);
    }
    public static void main(String[] args) {
        new BrickerGameManager("Bricker", new Vector2(700, 500)).run();

//        new GameManager("Bricker",
//                new Vector2(700, 500)).run();


    }
}
