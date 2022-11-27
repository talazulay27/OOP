package src.brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import src.BrickerGameManager;
import java.util.Random;

/**
 * The class randomly returns an object that represents
 * a particular action that occurs during a collision with a brick
 */

public class BrickStrategyFactory {
    private GameObjectCollection gameObjectCollection;
    private BrickerGameManager gameManager;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private UserInputListener inputListener;
    private WindowController windowController;
    private Vector2 windowDimensions;

    /**
     * The constructor of the class
     * @param gameObjectCollection A collection that contains all the existing objects in the game
     * @param gameManager gameManager-type object which is responsible for starting and managing the game
     * @param imageReader An object that helps import images for the objects
     * @param soundReader An object that helps import sounds for the objects
     * @param inputListener An object which helps to get from the user his actions in the game
     * @param windowController An object that helps control the game window
     * @param windowDimensions The dimension (height and wight) of the game window
     */

    public BrickStrategyFactory(GameObjectCollection gameObjectCollection,
                                BrickerGameManager gameManager,
                                ImageReader imageReader,
                                SoundReader soundReader,
                                UserInputListener inputListener,
                                WindowController windowController,
                                Vector2 windowDimensions) {

        this.gameObjectCollection = gameObjectCollection;
        this.gameManager = gameManager;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowDimensions = windowDimensions;

    }

    /**
     * @param chooseStrategyNumber A random number which represents the strategy which will be returned
     * Returns one of the 4 usual strategies according to the number which is received as input
     */
    private CollisionStrategy removeBrakeStrategy(int chooseStrategyNumber){
        if (chooseStrategyNumber == 0) {
            return new AddPaddleStrategy(new RemoveBrickStrategy(this.gameObjectCollection),
                    this.imageReader, this.inputListener, this.windowDimensions);
        } else if (chooseStrategyNumber == 1) {
            return new PuckStrategy(new RemoveBrickStrategy(this.gameObjectCollection),
                    this.imageReader, this.soundReader);
        } else if (chooseStrategyNumber == 2) {
            return new ChangeCameraStrategy(new RemoveBrickStrategy(this.gameObjectCollection),
                    this.windowController, this.gameManager);

        } else {
            return new WideOrNarrowPaddleStrategy(new RemoveBrickStrategy(this.gameObjectCollection),
                    this.imageReader, this.windowDimensions);
        }
    }

    /**
     * A method that creates a double strategy - randoms 2 strategies and ensures that the total number
     * of strategies does not exceed 3
     * @return Random double Strategy
     */

    private CollisionStrategy makeDoubleStrategy(){
        CollisionStrategy firstCollisionStrategy;
        CollisionStrategy secondCollisionStrategy;
        Random random = new Random();
        int chooseStrategy = random.nextInt(5);
        if (chooseStrategy == 4){
            CollisionStrategy doubleFirstCollisionStrategy = removeBrakeStrategy(random.nextInt(4));
            CollisionStrategy doubleSecondCollisionStrategy = removeBrakeStrategy(random.nextInt(4));
            firstCollisionStrategy = new DoubleStrategy(doubleFirstCollisionStrategy,
                    doubleSecondCollisionStrategy);
        }
        else{
            firstCollisionStrategy = removeBrakeStrategy(random.nextInt(4));
        }
        secondCollisionStrategy = removeBrakeStrategy(random.nextInt(4));
        return new DoubleStrategy(firstCollisionStrategy, secondCollisionStrategy);
    }

    /**
     * @return randomly returns a CollisionStrategy object that represents
     * a particular action that occurs during a collision with a brick
     */

    public CollisionStrategy getStrategy() {
        Random random = new Random();
        int chooseStrategyNumber = random.nextInt(6);
        if (chooseStrategyNumber < 4){
            return removeBrakeStrategy(chooseStrategyNumber);
        }
        else if (chooseStrategyNumber == 5){
            return makeDoubleStrategy();
        }
        else {
            return new RemoveBrickStrategy(this.gameObjectCollection);
        }
    }
}
