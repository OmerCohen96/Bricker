package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.WindowController;
import danogl.util.Counter;

import java.util.Random;

public class BrickStrategyFactory {
    public static final int NUM_OF_STRATEGIES = 2;
    private Random random = new Random();
    private GameObjectCollection objects;
    private Counter counterBricks;
    private WindowController windowController;
    private GameObject gameBall;


    public BrickStrategyFactory(GameObjectCollection objects,
                                Counter counterBricks, WindowController windowController, GameObject gameBall) {
        this.objects = objects;
        this.counterBricks = counterBricks;
        this.windowController = windowController;
        this.gameBall = gameBall;
    }

    public CollisionStrategy getStrategy() {
        CollisionStrategy strategy = null;

        int ranInt = random.nextInt(1, NUM_OF_STRATEGIES+1);

        switch (ranInt) {
            case 1:
                strategy = new RemoveBrickStrategy(objects, counterBricks);
                break;
            case 2:
                strategy = new ThreeBallsStrategy(objects, counterBricks, windowController);
                break;
        }
        return strategy;
    }

    public static void main(String[] args) {
        for (int i = 0; i <100 ; i++) {

            System.out.println(new Random().nextInt(1, NUM_OF_STRATEGIES+1));
        }
    }
}
