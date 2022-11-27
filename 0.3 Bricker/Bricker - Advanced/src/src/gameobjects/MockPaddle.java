package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * A class that implements the object of the MockPaddle type
 */

public class MockPaddle extends Paddle {

    public static boolean isInstantiated;
    private GameObjectCollection gameObjectCollection;
    private int numCollisionsToDisappear;
    private Counter collisions;

    /**
     * Construct a new MockPaddle instance.
     *  @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param inputListener An object which helps to get from the user his actions in the game
     * @param windowDimensions The dimension (height and wight) of the game window
     * @param gameObjectCollection A collection that contains all the existing objects in the game
     * @param minDistanceFromEdge The minimum distance from the wall in which the paddle can be
     * @param numCollisionsToDisappear The number of collisions after which the pedal disappeared
     */

    public MockPaddle(Vector2 topLeftCorner,
                      Vector2 dimensions,
                      Renderable renderable,
                      UserInputListener inputListener,
                      Vector2 windowDimensions,
                      GameObjectCollection gameObjectCollection,
                      int minDistanceFromEdge,
                      int numCollisionsToDisappear) {

        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions, minDistanceFromEdge);
        this.gameObjectCollection = gameObjectCollection;
        this.numCollisionsToDisappear = numCollisionsToDisappear;
        this.collisions = new Counter();
    }

    /**
     * Increases the collisions counter whenever the paddle hits an object and removes the paddle after it
     * reaches the required number of collisions
     * @param other The object in which the ball collided
     * @param collision An object that helps define actions during a collision
     */

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other instanceof Ball) {
            this.collisions.increment();
            if (this.collisions.value() >= this.numCollisionsToDisappear) {
                gameObjectCollection.removeGameObject(this);
                MockPaddle.isInstantiated = false;
            }
        }
    }


}
