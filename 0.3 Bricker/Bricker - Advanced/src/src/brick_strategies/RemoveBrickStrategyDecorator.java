package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

/**
 * Abstract class that implements the strategy in which the brick disappears and another
 * action(s) takes place in the game
 */

public abstract class RemoveBrickStrategyDecorator implements CollisionStrategy {

    protected CollisionStrategy toBeDecorated;
    protected GameObjectCollection gameObjectCollection;

    /**
     * The constructor of the game
     * @param toBeDecorated The basic RemoveBrickStrategy to which another action is added
     */
    public RemoveBrickStrategyDecorator(CollisionStrategy toBeDecorated){
        this.toBeDecorated = toBeDecorated;
        this.gameObjectCollection = this.toBeDecorated.getGameObjectCollection();
    }

    /**
     * @return global game object collection whose reference is held in object.
     */

    @Override
    public GameObjectCollection getGameObjectCollection(){
        return this.gameObjectCollection;
    }

    /**
     * Calls the onCollision method of the toBeDecorated object.
     * @param thisObj The object itself
     * @param otherObj The object which collided with it
     * @param counter Global counter which holds the number of bricks that exist in the game
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        this.toBeDecorated.onCollision(thisObj, otherObj, counter);
    }
}
