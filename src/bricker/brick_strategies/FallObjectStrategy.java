package bricker.brick_strategies;


import bricker.brick_strategies.collision_objects.FallObject;
import bricker.brick_strategies.collision_objects.NarroWidenPaddle;
import bricker.brick_strategies.collision_objects.SlowFastMotion;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.WindowController;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

public class FallObjectStrategy extends RemoveBrickStrategy {
    private static final int NUM_OF_OBJECTS = 2;
    private Random random = new Random();
    private WindowController windowController;
    private boolean widenEffect;

    public FallObjectStrategy(GameObjectCollection objects, Counter bricksCounter, WindowController windowController) {
        super(objects, bricksCounter);
        this.windowController = windowController;
        widenEffect = random.nextBoolean();
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        super.onCollision(thisObj, otherObj);
        FallObject randObject = fallObjectFactory();
        randObject.setCenter(thisObj.getCenter());
        getObjects().addGameObject(randObject);
        randObject.setVelocity(Vector2.DOWN.multY(200));
    }

    private FallObject fallObjectFactory() {
        int rand = random.nextInt(0, NUM_OF_OBJECTS);
        FallObject randObject = null;
        switch (rand) {
            case 0:
                randObject = createSlowFast();
                break;
            case 1:
                randObject = createNarroWiden();
                break;
        }
        return randObject;
    }

    private FallObject createSlowFast() {
        FallObject slowFast;
        if (SlowFastMotion.getAttribute()){
            slowFast = new SlowFastMotion(getObjects(), windowController,
                    new ImageReader(windowController).readImage("assets/quicken.png",true));
        } else {
            slowFast = new SlowFastMotion(getObjects(), windowController,
                    new ImageReader(windowController).readImage("assets/slow.png",true));
        }
        return slowFast;
    }

    private FallObject createNarroWiden() {
        if (widenEffect){
            return new NarroWidenPaddle(getObjects(), windowController,
                    new ImageReader(windowController).readImage("assets/buffWiden.png",true),
                    widenEffect);
        } else {
            return new NarroWidenPaddle(getObjects(), windowController,
                    new ImageReader(windowController).readImage("assets/buffNarrow.png",true),
                    widenEffect);
        }
    }

}
