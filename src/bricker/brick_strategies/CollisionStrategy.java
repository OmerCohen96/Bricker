package bricker.brick_strategies;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;

public class CollisionStrategy {
    private GameObjectCollection objects;

    public CollisionStrategy (GameObjectCollection objects){
        this.objects = objects;
    }
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        objects.removeGameObject(thisObj);

    }
}