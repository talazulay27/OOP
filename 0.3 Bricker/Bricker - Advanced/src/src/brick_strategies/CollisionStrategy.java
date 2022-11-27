package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

/**
 * An interface that defines what action will be taken during a collision with an object
 */

public interface CollisionStrategy {

    /**
     * @return global game object collection whose reference is held in object.
     */

    GameObjectCollection getGameObjectCollection();

    /**
     * Defines what will happen to the ball when it collides with another object
     * @param thisObj The object itself
     * @param otherObj The object which collided with it
     * @param counter Global counter which holds the number of bricks that exist in the game
     */

    void onCollision(GameObject thisObj, GameObject otherObj, Counter counter);
}
