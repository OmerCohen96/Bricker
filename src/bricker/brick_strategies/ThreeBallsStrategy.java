package bricker.brick_strategies;

import bricker.brick_strategies.collision_objects.MockBall;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

public class ThreeBallsStrategy extends RemoveBrickStrategy {
    private WindowController windowController;
    private Random random;

    public ThreeBallsStrategy(GameObjectCollection objects,
                              Counter bricksCounter, WindowController windowController) {
        super(objects, bricksCounter);
        this.windowController = windowController;
    }
    @Override
    public void onCollision (GameObject thisObj, GameObject otherObj){
        super.onCollision(thisObj, otherObj);
        createBalls(thisObj);
    }

    private void createBalls(GameObject thisObj) {
        Renderable mockBallImg =
                new ImageReader(windowController).readImage("assets/mockBall.png", true);
        Sound mockBallSound =
                new SoundReader(windowController).readSound("assets/blop_cut_silenced.wav");
        random = new Random();
        for (int i = 0; i < 1; i++) {
            GameObject mock =
                    new MockBall(Vector2.ZERO, new Vector2(23,23),mockBallImg, mockBallSound, windowController,getObjects());
            mock.setTopLeftCorner(thisObj.getCenter().add(new Vector2(-17,0)));
            mock.setVelocity(
                    new Vector2(random.nextFloat(-1,1),1).mult(250));
            getObjects().addGameObject(mock);
        }
    }
}
