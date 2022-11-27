package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * The class implements Status Definer which can expand or narrow the pedal
 */

public class WidenOrNarrowPaddle extends StatusDefiner{

    private float factor;
    private float maxWight;


    /**
     * Construct a new GameObject instance.
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param gameObjectCollection A collection that contains all the existing objects in the game
     * @param windowDimensions The dimension (height and wight) of the game window
     * @param factor The factor which will determine whether the object will increase or decrease the width
     *              of the paddle
     * @param maxWight The maximum width that the paddle can reach
     * @param velocity The speed which will be set to the object
     */

    public WidenOrNarrowPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                               GameObjectCollection gameObjectCollection, Vector2 windowDimensions,
                               float factor, float maxWight, float velocity) {
        super(topLeftCorner, dimensions, renderable, gameObjectCollection, windowDimensions, velocity);
        this.factor = factor;
        this.maxWight = maxWight;
    }

    /**
     * Changes the width of the paddle if and when it hits
     * @param other The object in which the brick collided
     * @param collision An object that helps define actions during a collision
     */

    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (shouldCollideWith(other)){
            if (other.getDimensions().multX(this.factor).x() >= this.maxWight){
                other.setDimensions(new Vector2(this.maxWight, other.getDimensions().y()));
            }
            else{
                other.setDimensions(other.getDimensions().multX(this.factor));
            }
        }
    }

}
