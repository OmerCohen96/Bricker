package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {
    private static float MOVEMENT_SPEED = 350f;
    private static float MIN_DISTANCE_FROM_SCREEN_EDGE = 0.5f;
    private final Vector2 windowDimensions;
    private UserInputListener inputListener;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner    Position of the object, in window coordinates (pixels).
     *                         Note that (0,0) is the top-left corner of the window.
     * @param dimensions       Width and height in window coordinates.
     * @param renderable       The renderable representing the object. Can be null, in which case
     *                         the GameObject will not be rendered.
     * @param inputListener
     * @param windowDimensions
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener, Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)){
            movementDir = movementDir.add(Vector2.LEFT);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)){
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        this.setVelocity(movementDir.mult(MOVEMENT_SPEED));

        Vector2 stayInBorder;
        float rightEdge = windowDimensions.x() - MIN_DISTANCE_FROM_SCREEN_EDGE;

        if (this.getTopLeftCorner().x() < MIN_DISTANCE_FROM_SCREEN_EDGE){
            stayInBorder = new Vector2(MIN_DISTANCE_FROM_SCREEN_EDGE, this.getTopLeftCorner().y());
            setTopLeftCorner(stayInBorder);
        } else if ((this.getTopLeftCorner().x() + this.getDimensions().x()) > rightEdge) {
            stayInBorder = new Vector2(rightEdge - this.getDimensions().x(), this.getTopLeftCorner().y());
            setTopLeftCorner(stayInBorder);
        }

    }
}
