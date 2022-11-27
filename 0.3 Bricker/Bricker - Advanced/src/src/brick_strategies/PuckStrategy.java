package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Ball;
import src.gameobjects.Puck;

/**
 * The class implements the strategy in which 3 Pucks are added to the game
 */

public class PuckStrategy extends RemoveBrickStrategyDecorator{

    private static final String MOCK_BALL_IMAGE_PATH = "assets/mockBall.png";
    private static final String COLLISION_SOUND_PATH = "assets/blop_cut_silenced.wav";
    private static final int NUM_OF_PUCKS_TO_ADD = 3;
    private Sound puckSound;
    private ImageReader imageReader;
    private SoundReader soundReader;

    /**
     * The constructor of the class
     * @param toBeDecorated The basic RemoveBrickStrategy to which another action is added
     * @param imageReader An object that helps import images for the objects
     * @param soundReader An object that helps import sounds for the objects
     */
    public PuckStrategy(CollisionStrategy toBeDecorated,
                        ImageReader imageReader,
                        SoundReader soundReader) {
        super(toBeDecorated);
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.puckSound = soundReader.readSound(PuckStrategy.COLLISION_SOUND_PATH);
    }

    /**
     * Adds another 3 Pucks in the position of the brick to the GameObjects Collection
     * Adds another 3 Pucks in the position of the brick to the GameObjects Collection
     * @param thisObj The object itself
     * @param otherObj The object which collided with it
     * @param counter Global counter which holds the number of bricks that exist in the game
     */

    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter){
        super.onCollision(thisObj, otherObj, counter);
        for (int i = 0; i < NUM_OF_PUCKS_TO_ADD; i++) {
            Vector2 ballPosition = new Vector2(
                    thisObj.getTopLeftCorner().x() + (i * (thisObj.getDimensions().x() / 3)),
                    thisObj.getCenter().y());
            Vector2 ballSize = new Vector2(
                    thisObj.getDimensions().x()/3,
                    thisObj.getDimensions().x()/3);
            Ball mockBall = new Puck(ballPosition, ballSize,
                    imageReader.readImage(PuckStrategy.MOCK_BALL_IMAGE_PATH, true),
                    this.puckSound);
            mockBall.setVelocity(otherObj.getVelocity().flipped(Vector2.DOWN));
            mockBall.setCenter(ballPosition);
            this.toBeDecorated.getGameObjectCollection().addGameObject(mockBall);
        }

    }

}
