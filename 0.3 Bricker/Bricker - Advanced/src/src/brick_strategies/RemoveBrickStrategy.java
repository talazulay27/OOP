package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

/**
 * A class that implements the removal of a brick from the game when it is collided with
 */

public class RemoveBrickStrategy implements CollisionStrategy{

    private GameObjectCollection gameObjectCollection;

    /**
     * The constructor of the class
     * @param gameObjectCollection An object that holds all the objects that exist in the game
     */

    public RemoveBrickStrategy(GameObjectCollection gameObjectCollection) {

        this.gameObjectCollection = gameObjectCollection;
    }

    /**
     * Removes the brick from the game and updates the brick counter
     * @param thisObj The object itself
     * @param otherObj The object which collided with it
     * @param counter Global counter which holds the number of bricks that exist in the game
     */

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        if(gameObjectCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS)){
            counter.decrement();
        }

    }

    /**
     * @return global game object collection whose reference is held in object.
     */

    @Override
    public GameObjectCollection getGameObjectCollection() {
        return this.gameObjectCollection;
    }

}
