package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class RemoveBrickStrategy implements CollisionStrategy {
    private GameObjectCollection objects;
    private Counter bricksCounter;

    public RemoveBrickStrategy(GameObjectCollection objects, Counter bricksCounter){
        this.objects = objects;
        this.bricksCounter = bricksCounter;
    }
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        objects.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
        bricksCounter.decrement();
    }

    protected GameObjectCollection getObjects() {
        return objects;
    }


}
