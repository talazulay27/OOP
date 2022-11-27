package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.Ball;
import src.gameobjects.BallCollisionCountdownAgent;
import src.gameobjects.Puck;

/**
 * The class implements the strategy in which the camera focuses on the main ball when hitting the brick
 */

public class ChangeCameraStrategy extends RemoveBrickStrategyDecorator {

    private static final int COUNT_DOWN_VALUE = 4;
    private WindowController windowController;
    private BrickerGameManager gameManager;
    private BallCollisionCountdownAgent ballCollisionCountdownAgent;

    /**
     * The constructor of the class
     * @param toBeDecorated The basic RemoveBrickStrategy to which another action is added
     * @param windowController An object that helps control the game window
     * @param gameManager gameManager-type object which is responsible for starting and managing the game
     */
    public ChangeCameraStrategy(CollisionStrategy toBeDecorated,
                                WindowController windowController,
                                BrickerGameManager gameManager) {
        super(toBeDecorated);
        this.windowController = windowController;
        this.gameManager = gameManager;
    }

    /**
     * Changes camera position and makes it focuses on the main ball of the game
     * @param thisObj The object itself
     * @param otherObj The object which collided with it
     * @param counter Global counter which holds the number of bricks that exist in the game
     */

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        if (this.gameManager.getCamera() == null) {
            Ball ballToFollow = null;
            for (GameObject object : this.getGameObjectCollection()) {
                if (object instanceof Ball && !(object instanceof Puck)) {
                    ballToFollow = (Ball) object;
                    break;
                }
            }
            this.gameManager.setCamera(new Camera(ballToFollow, Vector2.ZERO,
                    this.windowController.getWindowDimensions().mult(1.2f),
                    this.windowController.getWindowDimensions()));
            this.ballCollisionCountdownAgent = new BallCollisionCountdownAgent(
                    ballToFollow, this, ballToFollow.getCollisionCount() +
                    ChangeCameraStrategy.COUNT_DOWN_VALUE + 1);
            this.getGameObjectCollection().addGameObject(this.ballCollisionCountdownAgent, Layer.BACKGROUND);
        }
    }

    /**
     * Returns camera to normal ground position.
     */

    public void turnOffCameraChange() {
        this.gameManager.setCamera(null);
        this.getGameObjectCollection().removeGameObject(this.ballCollisionCountdownAgent, Layer.BACKGROUND);
    }
}

