package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

/**
 * The class implements double strategy in which 2 strategies occur together
 */

public class DoubleStrategy implements CollisionStrategy {

    private CollisionStrategy firstCollisionStrategy;
    private CollisionStrategy secondCollisionStrategy;
    private GameObjectCollection gameObjectCollection;

    /**
     * The constructor of the class
     * @param firstCollisionStrategy The first strategy which occurs when hitting the brick
     * @param secondCollisionStrategy The second strategy which occurs when hitting the brick
     */

    public DoubleStrategy(CollisionStrategy firstCollisionStrategy, CollisionStrategy secondCollisionStrategy) {
        this.firstCollisionStrategy = firstCollisionStrategy;
        this.secondCollisionStrategy = secondCollisionStrategy;
        this.gameObjectCollection = firstCollisionStrategy.getGameObjectCollection();
    }

    /**
     * @return global game object collection whose reference is held in object.
     */

    @Override
    public GameObjectCollection getGameObjectCollection() {
        return this.gameObjectCollection;
    }

    /**
     * Calls the OnCollision method of the 2 Strategies
     * @param thisObj The object itself
     * @param otherObj The object which collided with it
     * @param counter Global counter which holds the number of bricks that exist in the game
     */

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        this.firstCollisionStrategy.onCollision(thisObj, otherObj, counter);
        this.secondCollisionStrategy.onCollision(thisObj, otherObj, counter);

    }
}
