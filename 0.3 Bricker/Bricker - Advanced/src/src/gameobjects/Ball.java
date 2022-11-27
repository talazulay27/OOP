package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * A class that represent the ball object in the game
 */

public class Ball extends GameObject {

    private final Sound collisionSound;
    private Counter collisionCounter;

    /**
     * Construct a new Ball instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param collisionSound An object that determines the sound that will be heard when the ball hits
     *                       another object
     */

    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                    Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.collisionCounter = new Counter();
    }

    /**
     * Defines what will happen to the ball when it collides with another object
     * @param other The object in which the ball collided
     * @param collision An object that helps define actions during a collision
     */

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        this.collisionSound.play();
        this.collisionCounter.increment();


    }

    /**
     * @return Returns how many times the ball collided with other objects
     */

    public int getCollisionCount(){
        return this.collisionCounter.value();
    }
}
