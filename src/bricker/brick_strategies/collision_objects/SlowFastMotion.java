package bricker.brick_strategies.collision_objects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

public class SlowFastMotion extends FallObject{
    private static Vector2 objectDimension = new Vector2(100,50);
    private static boolean attribute = new Random().nextBoolean(); // true is fast , false is slow

    public SlowFastMotion
            (GameObjectCollection objects, WindowController windowController,Renderable img) {
        super(Vector2.ZERO, objectDimension, img, windowController, objects);
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (attribute) {
            windowController.setTimeScale(2);
            attribute = false;
        }
        else {
            windowController.setTimeScale(0.8f);
            attribute = true;
        }
    }

    public static boolean getAttribute (){
        return attribute;
    }
}
