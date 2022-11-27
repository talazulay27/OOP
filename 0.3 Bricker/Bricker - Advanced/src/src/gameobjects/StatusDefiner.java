package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * An abstract class that implements a status-definer object
 */

public class StatusDefiner extends GameObject {

    private boolean isCollied;
    private GameObjectCollection gameObjectCollection;
    private Vector2 windowDimensions;

    /**
     * Construct a new StatusDefiner instance.
     *  @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param gameObjectCollection A collection that contains all the existing objects in the game
     * @param windowDimensions The dimension (height and wight) of the game window
     * @param velocity The speed of the status definer
     */

    public StatusDefiner(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                         GameObjectCollection gameObjectCollection, Vector2 windowDimensions, float velocity){
        super(topLeftCorner, dimensions, renderable);
        this.gameObjectCollection = gameObjectCollection;
        this.windowDimensions = windowDimensions;
        this.isCollied = false;
        this.setVelocity(new Vector2(0, velocity));
    }

    /**
     * @param other The object in which the ball collided
     * @return True if the object that hit the paddle is of a Paddle type, False otherwise.
     */

    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other instanceof Paddle;
    }

    /**
     * Change the value of isCollied to true
     * @param other The object in which the ball collided
     * @param collision An object that helps define actions during a collision
     */

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.isCollied = true;
    }

    /**
     * At all times checks if the Status Definer hit an object, if so removes it from the game
     * @param deltaTime The deltaTime at which the function operates
     */

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.isCollied || this.getDimensions().y()>this.windowDimensions.y()){
            this.gameObjectCollection.removeGameObject(this);
        }
    }
}
