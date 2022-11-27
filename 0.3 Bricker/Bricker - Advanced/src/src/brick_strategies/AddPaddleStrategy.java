package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.MockPaddle;
import src.gameobjects.Paddle;

/**
 * The class implements the strategy in which a paddle is added to the game when hitting the brick
 */

public class AddPaddleStrategy extends RemoveBrickStrategyDecorator{

    private static final String PADDLE_IMAGE_PATH ="assets/paddle.png";
    private static final Vector2 PADDLE_DIMENSIONS = new Vector2(100, 15);
    private final int NUM_COLLISIONS_TO_DISAPPEAR = 3;
    private final ImageReader imageReader;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;

    /**
     * The constructor of the class
     * @param toBeDecorated The basic RemoveBrickStrategy to which another action is added
     * @param imageReader An object that helps import images for the objects
     * @param inputListener An object which helps to get from the user his actions in the game
     * @param windowDimensions The dimension (height and wight) of the game window
     */

    public AddPaddleStrategy(CollisionStrategy toBeDecorated,
                             ImageReader imageReader,
                             UserInputListener inputListener,
                             Vector2 windowDimensions)
 {
     super(toBeDecorated);
     this.imageReader = imageReader;
     this.inputListener = inputListener;
     this.windowDimensions = windowDimensions;
 }

    /**
     * Adds another paddle in the center of the screen and to the GameObjects Collection
     * @param thisObj The object itself
     * @param otherObj The object which collided with it
     * @param counter Global counter which holds the number of bricks that exist in the game
     */

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        if (!MockPaddle.isInstantiated){
            Paddle mockPaddle = new MockPaddle(this.windowDimensions.mult(0.5f),
                    AddPaddleStrategy.PADDLE_DIMENSIONS,
                    this.imageReader.readImage(AddPaddleStrategy.PADDLE_IMAGE_PATH,
                            true),
                    this.inputListener,
                    this.windowDimensions,
                    this.gameObjectCollection,
                    BrickerGameManager.BORDER_WIDTH,
                    NUM_COLLISIONS_TO_DISAPPEAR);
            mockPaddle.setCenter(this.windowDimensions.mult(0.5f));
            this.gameObjectCollection.addGameObject(mockPaddle);
            MockPaddle.isInstantiated = true;
        }

    }
}
