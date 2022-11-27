package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class CollisionStrategy {

    private final Counter countBricks;
    private GameObjectCollection gameObjectCollection;

    public CollisionStrategy(GameObjectCollection gameObjectCollection, Counter countBricks){
        this.countBricks = countBricks;
        this.gameObjectCollection = gameObjectCollection;
    }

    public void onCollision(GameObject thisObj) {
        gameObjectCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
        countBricks.decrement();
    }
}
