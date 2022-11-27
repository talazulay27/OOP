package gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;

/**
 * A class that represent the paddle object in the game
 */

public class Paddle extends GameObject {
    private static final float MOVEMENT_SPEED = 400;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;
    private int minDistanceFromEdge;

    /**
     * Construct a new Paddle instance.
     *  @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param inputListener An object which helps to get from the user his actions in the game
     * @param windowDimensions The dimension (height and wight) of the game window
     * @param minDistanceFromEdge The minimum distance from the wall in which the paddle can be
     */

    public Paddle(Vector2 topLeftCorner,
                  Vector2 dimensions,
                  Renderable renderable,
                  UserInputListener inputListener,
                  Vector2 windowDimensions,
                  int minDistanceFromEdge) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.minDistanceFromEdge = minDistanceFromEdge;
    }

    /**
     * The function defines how the paddle behaves according to the buttons that the user presses on the keyboard
     * @param deltaTime The deltaTime at which the function operates
     */

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if(this.inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDir = movementDir.add(Vector2.LEFT);
        }
        if(this.inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
        if (getTopLeftCorner().x() <= this.minDistanceFromEdge){
            transform().setTopLeftCornerX(this.minDistanceFromEdge);
        }
        if (getTopLeftCorner().x() >= this.windowDimensions.x() -
                this.minDistanceFromEdge - getDimensions().x()){
            transform().setTopLeftCornerX(this.windowDimensions.x() -
                    this.minDistanceFromEdge - getDimensions().x());
        }
    }
}
