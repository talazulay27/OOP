package src.gameobjects;

import src.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * A class that represent the brick object in the game
 */

public class Brick extends GameObject {

    private CollisionStrategy collisionStrategy;
    private Counter bricksCounter;

    /**
     * Construct a new Brick instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param collisionStrategy  The action will be taken during a collision with an object
     * @param counter Counter which holds the number of bricks left in the game
     */

    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy, Counter counter) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        this.bricksCounter = counter;
    }

    /**
     * Defines what will happen to the brick when it collides with another object
     * @param other The object in which the brick collided
     * @param collision An object that helps define actions during a collision
     */

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.collisionStrategy.onCollision(this, other, this.bricksCounter);

    }
}
