package brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;


/**
 * A class that defines what action will be taken during a collision with an object
 */

public class CollisionStrategy {

    private GameObjectCollection gameObjectCollection;

    /**
     * Constructor for the class
     * @param gameObjectCollection all the objects of the game
     */

    public CollisionStrategy(GameObjectCollection gameObjectCollection){

        this.gameObjectCollection = gameObjectCollection;
    }

    /**
     * Defines what will happen to the ball when it collides with another object
     * @param thisObj The object itself
     * @param otherObj The object which collided with it
     * @param bricksCounter Counter which holds the number of bricks left in the game
     */

    public void onCollision(GameObject thisObj, GameObject otherObj, Counter bricksCounter) {
        this.gameObjectCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
        bricksCounter.decrement();
    }
}
