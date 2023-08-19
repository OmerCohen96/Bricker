package bricker.brick_strategies.collision_objects;

import bricker.gameobjects.Ball;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class MockBall extends Ball {
    private WindowController windowController;
    private Vector2 windowDimension;
    private GameObjectCollection objects;



    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public MockBall(Vector2 topLeftCorner, Vector2 dimensions
            , Renderable renderable, Sound soundCollision,
                    WindowController windowController, GameObjectCollection objects) {
        super(topLeftCorner, dimensions, renderable, soundCollision);
        this.windowController = windowController;
        windowDimension = windowController.getWindowDimensions();
        this.objects = objects;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (getTopLeftCorner().y() > windowDimension.y())
            objects.removeGameObject(this);
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        if (other instanceof Ball)
            return false;
        return true;
    }
}
