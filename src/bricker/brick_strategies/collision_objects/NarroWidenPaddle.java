package bricker.brick_strategies.collision_objects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class NarroWidenPaddle extends FallObject {
    private static Vector2 objectDimension = new Vector2(50,30);
    private boolean isWiden;

    public NarroWidenPaddle(GameObjectCollection objects, WindowController windowController,Renderable img, boolean isWiden){
        super(Vector2.ZERO, objectDimension, img, windowController, objects);
        this.isWiden = isWiden;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (isWiden){
            other.setDimensions(other.getDimensions().add(Vector2.RIGHT.multX(15)));
        }else {
            other.setDimensions(other.getDimensions().add(Vector2.LEFT.multX(15)));
        }
    }

    private static Renderable img (WindowController windowController, boolean isWiden){
        if (isWiden)
            return  new ImageReader(windowController).readImage("assets/buffWiden.png",true);
         else
             return  new ImageReader(windowController).readImage("assets/buffNarrow.png",true);

    }
}
