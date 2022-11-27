package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.WidenOrNarrowPaddle;
import src.gameobjects.StatusDefiner;
import java.util.Random;

/**
 * The class implements the strategy in which WideOrNarrow StatusDefiner is added to the game
 */

public class WideOrNarrowPaddleStrategy extends RemoveBrickStrategyDecorator{

    private static final String BUFF_WIDEN_IMAGE_PATH = "assets/buffWiden.png";
    private static final String BUFF_NARROW_IMAGE_PATH = "assets/buffNarrow.png";
    private static final float WIDEN = 2;
    private static final float NARROW = 0.5f;
    private ImageReader imageReader;
    private Vector2 windowDimensions;

    /**
     * The constructor of the class
     * @param toBeDecorated The basic RemoveBrickStrategy to which another action is added
     * @param imageReader An object that helps import images for the objects
     * @param windowDimensions The dimension (height and wight) of the game window
     */

    public WideOrNarrowPaddleStrategy(CollisionStrategy toBeDecorated,
                                      ImageReader imageReader,
                                      Vector2 windowDimensions) {
        super(toBeDecorated);
        this.imageReader = imageReader;
        this.windowDimensions = windowDimensions;
    }

    /**
     * @return A random factor that determines whether the paddle will become wider or narrower
     */

    private float randomFactor(){
        Random random = new Random();
        if(random.nextBoolean()){
            return WideOrNarrowPaddleStrategy.WIDEN;
        }
        else {
            return WideOrNarrowPaddleStrategy.NARROW;
        }
    }

    /**
     * Adds WideOrNarrowPaddle status definer in the position of the brick and to the GameObjects Collection
     * @param thisObj The object itself
     * @param otherObj The object which collided with it
     * @param counter Global counter which holds the number of bricks that exist in the game
     */

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        Renderable imageRenderable;
        float factor = this.randomFactor();
        if (factor == WideOrNarrowPaddleStrategy.WIDEN){
            imageRenderable = this.imageReader.readImage(WideOrNarrowPaddleStrategy.BUFF_WIDEN_IMAGE_PATH,
                    true);
        }
        else{
            imageRenderable = this.imageReader.readImage(WideOrNarrowPaddleStrategy.BUFF_NARROW_IMAGE_PATH,
                    true);
        }
        StatusDefiner wideOrNarrowPaddle = new WidenOrNarrowPaddle(thisObj.getTopLeftCorner(),
                    thisObj.getDimensions(),
                    imageRenderable,
                    this.gameObjectCollection,
                    windowDimensions,
                    factor,
                    this.windowDimensions.x() - (BrickerGameManager.BORDER_WIDTH * 2),
                    otherObj.getVelocity().flipped(Vector2.DOWN).y());
        this.gameObjectCollection.addGameObject(wideOrNarrowPaddle);
        }
}
