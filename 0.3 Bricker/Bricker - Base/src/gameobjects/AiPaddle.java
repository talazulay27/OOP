package gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class AiPaddle extends GameObject {

    private static final float PADDLE_SPEED = 400 ;
    private static final float MIN_DISTANCE_FROM_SCREEN_EDGE = 3;
    private final Vector2 dimensions;
    private GameObject objectToFollow;
    private Vector2 windowDimensions;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public AiPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, GameObject objectToFollow, Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable);
        this.dimensions = dimensions;
        this.objectToFollow = objectToFollow;
        this.windowDimensions = windowDimensions;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movemntDir = Vector2.ZERO;
        if (objectToFollow.getCenter().x() + 10 < getCenter().x()){
            movemntDir = Vector2.LEFT;
        }
        if (objectToFollow.getCenter().x() - 10 > getCenter().x()){
            movemntDir = Vector2.RIGHT;
        }
        if (objectToFollow.getCenter().x() == getCenter().x()){
            movemntDir = Vector2.ZERO;
        }
        setVelocity(movemntDir.mult(PADDLE_SPEED));
        if (getTopLeftCorner().x() <= MIN_DISTANCE_FROM_SCREEN_EDGE){
            setTopLeftCorner(new Vector2(MIN_DISTANCE_FROM_SCREEN_EDGE, 30));
        }
        if (getTopLeftCorner().x() >= windowDimensions.x() - MIN_DISTANCE_FROM_SCREEN_EDGE - dimensions.x()){
            setTopLeftCorner(new Vector2(windowDimensions.x() - MIN_DISTANCE_FROM_SCREEN_EDGE - dimensions.x(), 30));
        }
    }
}
