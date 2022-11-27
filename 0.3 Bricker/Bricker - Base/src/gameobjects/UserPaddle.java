package gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class UserPaddle extends GameObject {

    private static final float MOVEMENT_SPEED = 300;
    private static final float MIN_DISTANCE_FROM_SCREEN_EDGE = 3;
    private final Vector2 topLeftCorner;
    private final Vector2 dimensions;
    private UserInputListener inputListener;
    private final Vector2 windowDimensions;

    /**
     *
     * @param topLeftCorner
     * @param dimensions
     * @param paddleImage
     * @param inputListener
     */

    public UserPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable paddleImage, UserInputListener inputListener, Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, paddleImage);
        this.topLeftCorner = topLeftCorner;
        this.dimensions = dimensions;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movemntDir = Vector2.ZERO;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)){
            movemntDir = movemntDir.add(Vector2.LEFT);
            setVelocity(movemntDir.mult(MOVEMENT_SPEED));
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)){
            movemntDir = movemntDir.add(Vector2.RIGHT);
            setVelocity(movemntDir.mult(MOVEMENT_SPEED));
        }
        if (getTopLeftCorner().x() <= MIN_DISTANCE_FROM_SCREEN_EDGE){
            setTopLeftCorner(new Vector2(MIN_DISTANCE_FROM_SCREEN_EDGE,
                    (int)this.windowDimensions.y() - 30));
        }
        if (getTopLeftCorner().x() >= windowDimensions.x() - MIN_DISTANCE_FROM_SCREEN_EDGE - dimensions.x()){
            setTopLeftCorner(new Vector2(windowDimensions.x() - MIN_DISTANCE_FROM_SCREEN_EDGE - dimensions.x(),
                    (int)this.windowDimensions.y() - 30));
        }
    }
}

