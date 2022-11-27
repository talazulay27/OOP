package src.gameobjects;

import danogl.GameObject;
import src.brick_strategies.ChangeCameraStrategy;

/**
 * A class that implements the feature that the camera focuses the ball until the ball reaches
 * a certain number of hits
 */

public class BallCollisionCountdownAgent extends GameObject {

    private Ball ball;
    private ChangeCameraStrategy owner;
    private int countDownValue;

    /**
     * The constructor of the class
     * @param ball The ball for which the number of hits is counted
     * @param owner The ChangeCameraStrategy which holds the feature
     * @param countDownValue The number of hits needed to stop the camera from tracking the ball
     */

    public BallCollisionCountdownAgent(Ball ball, ChangeCameraStrategy owner, int countDownValue){
        super(ball.getTopLeftCorner(), ball.getDimensions(), null);
        this.ball = ball;
        this.owner = owner;
        this.countDownValue = countDownValue;
    }

    /**
     * Checks at all times whether the number of hits of the ball has reached the desired value
     * and calls the turnOffCameraChange method if so
     * @param deltaTime The deltaTime at which the function operates
     */

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.ball.getCollisionCount() >= this.countDownValue){
            this.owner.turnOffCameraChange();
            this.owner.getGameObjectCollection().removeGameObject(this);
        }

    }
}
