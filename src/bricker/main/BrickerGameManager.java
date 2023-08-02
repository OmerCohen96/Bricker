package bricker.main;

import danogl.GameManager;
import danogl.util.Vector2;

public class BrickerGameManager {
    public static void main(String[] args) {
        new GameManager("Bricker",
                new Vector2(700, 500)).run();


    }
}
