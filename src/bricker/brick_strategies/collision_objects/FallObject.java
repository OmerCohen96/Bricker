package bricker.brick_strategies.collision_objects;

import bricker.gameobjects.Paddle;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;


public abstract class FallObject extends GameObject {
    private Sound collisionSound;
    protected WindowController windowController;
    protected GameObjectCollection objects;

    public FallObject(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                            WindowController windowController, GameObjectCollection objects) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound =
                new SoundReader(windowController).readSound("assets/Bubble5_4.wav");
        this.windowController = windowController;
        this.objects = objects;
    }

    @Override
    public boolean shouldCollideWith(GameObject other) {
        if (other instanceof Paddle)
            return super.shouldCollideWith(other);
        return false;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionSound.play();
        objects.removeGameObject(this);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.getTopLeftCorner().y() > windowController.getWindowDimensions().y()){
            objects.removeGameObject(this);
        }
    }
}
