import brick_strategies.CollisionStrategy;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import gameobjects.*;
import java.awt.*;
import java.util.Random;

/**
 * this class is responsible for game initialization,
 * holding references for game objects and calling update methods for every update iteration.
 * Entry point for code should be in a main method in this class.
 */

public class BrickerGameManager extends GameManager {

    public static final String REAL_BALL_IMAGE_PATH = "assets/ball.png";
    public static final String COLLISION_SOUND_PATH = "assets/blop_cut_silenced.wav";
    public static final String PADDLE_IMAGE_PATH ="assets/paddle.png";
    public static final String BRICK_IMAGE_PATH = "assets/brick.png";
    public static final String BACKGROUND_IMAGE_PATH = "assets/DARK_BG2_small.jpeg";
    public static final String HEART_IMAGE_PATH = "assets/heart.png";
    public static final String WIN_MASSAGE = "You win!";
    public static final String LOSE_MASSAGE = "You lose!";
    public static final String PLAY_AGAIN_MASSAGE = " play again?";
    public static final int BORDER_WIDTH = 20;
    private static final int PADDLE_HEIGHT = 15;
    private static final int PADDLE_WIDTH = 100;
    private static final float BRICK_HEIGHT = 15;
    private static final int BALL_RADIUS = 20;
    private static final int HEART_SIZE = 20;
    private static final int NUMBER_OF_DISQUALIFICATIONS = 4;
    private static final float BALL_SPEED = 250;
    private static final Renderable BORDER_RENDERABLE = new RectangleRenderable(Color.CYAN);
    private static final int NUM_OF_BRICKS_IN_ROW = 8;
    private static final int NUM_OF_ROWS_OF_BRICKS = 5;
    private final Vector2 windowDimensions;
    private WindowController windowController;
    private Ball ball;
    private final Counter bricksCounter;
    private final Counter livesCounter;


    /**
     * The constructor of the class
     * @param windowTitle The name of the game
     * @param windowDimensions The dimension (height and wight) of the game window
     */

    public BrickerGameManager(String windowTitle, Vector2 windowDimensions){
        super(windowTitle, windowDimensions);
        this.bricksCounter = new Counter();
        this.livesCounter = new Counter();
        this.windowDimensions = windowDimensions;

    }

    /**
     * The class initializes all the objects that are needed for the game with the help of auxiliary functions
     * and adds them to the game
     * @param imageReader An object that helps import images for the objects
     * @param soundReader An object that helps import sounds for the objects
     * @param inputListener An object which helps to get from the user his actions in the game
     * @param windowController An object that helps control the game window
     */

    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {

        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowController = windowController;

        //creates ball
        Renderable ballImage = imageReader.readImage(BrickerGameManager.REAL_BALL_IMAGE_PATH, true);
        Sound collisionSound = soundReader.readSound(BrickerGameManager.COLLISION_SOUND_PATH);
        createBall(ballImage, collisionSound, this.windowDimensions);

        //create paddle
        Renderable paddleImage = imageReader.readImage(BrickerGameManager.PADDLE_IMAGE_PATH, false);
        createPaddle(paddleImage, inputListener, this.windowDimensions);

        //create borders
        createBorders(this.windowDimensions);

        //create background
        Renderable backgroundImage = imageReader.readImage(BrickerGameManager.BACKGROUND_IMAGE_PATH, false);
        createBackground(backgroundImage, this.windowDimensions);

        //create bricks
        Renderable brickImage = imageReader.readImage(BrickerGameManager.BRICK_IMAGE_PATH, false);
        createBricks(brickImage, this.windowDimensions);

        //create graphic life counter
        Renderable graphicLifeCounterImage = imageReader.readImage(BrickerGameManager.HEART_IMAGE_PATH, true);
        this.livesCounter.increaseBy(BrickerGameManager.NUMBER_OF_DISQUALIFICATIONS);
        createGraphicLifeCounter(graphicLifeCounterImage, this.windowDimensions);

        //create numeric life counter
        createNumericLifeCounter(this.windowDimensions);
    }


    /**
     * Checks each time whether the game is over with the help of the function checkForGameEnd
     * @param deltaTime The deltaTime at which the function operates
     */

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
    }

    /**
     * The function puts the ball in the center of the screen and in a random direction
     * @param windowDimensions The dimension (height and wight) of the game window
     */

    private void ballReset(Vector2 windowDimensions) {
        this.ball.setCenter(windowDimensions.mult(0.5f));
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean())
            ballVelX *= -1;
        if (rand.nextBoolean())
            ballVelY *= -1;
        this.ball.setVelocity(new Vector2(ballVelX, ballVelY));
    }

    /**
     * The function checks whether the game ends according to the conditions defined in the game.
     * if the game ends, it shows a relevant message to the user depending on the result of the game
     */

    private void checkForGameEnd() {
        double ballHeight = this.ball.getCenter().y();
        String prompt = "";
        if(this.bricksCounter.value() == 0) {
            prompt = BrickerGameManager.WIN_MASSAGE;
        }
        if(ballHeight > this.windowDimensions.y()) {
            this.livesCounter.decrement();
            if (this.livesCounter.value() == 0){
                prompt = BrickerGameManager.LOSE_MASSAGE;
            }
            else {
                this.ballReset(this.windowDimensions);
            }
        }
        if(!prompt.isEmpty()) {
            prompt += BrickerGameManager.PLAY_AGAIN_MASSAGE;
            if(this.windowController.openYesNoDialog(prompt))
                this.windowController.resetGame();
            else
                this.windowController.closeWindow();
        }
    }

    /**
     * Creates a ball-type object and adds it to the game
     * @param ballImage An object which determined what the ball would look like
     * @param collisionSound An object that determines the sound that will be heard when the ball hits
     *                      another object
     * @param windowDimensions The dimension (height and wight) of the game window
     */

    private void createBall(Renderable ballImage, Sound collisionSound , Vector2 windowDimensions) {

        this.ball = new Ball(Vector2.ZERO,
                new Vector2(BrickerGameManager.BALL_RADIUS, BrickerGameManager.BALL_RADIUS),
                ballImage,
                collisionSound);
        this.ballReset(windowDimensions);
        this.gameObjects().addGameObject(this.ball);
    }

    /**
     * Creates a padlle-type object and adds it to the game
     * @param paddleImage An object which determined what the paddle would look like
     * @param inputListener An object which helps to get from the user his actions in the game
     * @param windowDimensions The dimension (height and wight) of the game window
     */

    private void createPaddle(Renderable paddleImage, UserInputListener inputListener,
                              Vector2 windowDimensions) {
        GameObject paddle = new Paddle(Vector2.ZERO,
                new Vector2(BrickerGameManager.PADDLE_WIDTH, BrickerGameManager.PADDLE_HEIGHT),
                paddleImage,
                inputListener,
                windowDimensions,
                BrickerGameManager.BORDER_WIDTH);

        paddle.setCenter(new Vector2(windowDimensions.x()/2, (int)windowDimensions.y()-30));
        this.gameObjects().addGameObject(paddle);
    }

    /**
     * Creates the borders and adds it to the game
     * @param windowDimensions The dimension (height and wight) of the game window
     */

    private void createBorders(Vector2 windowDimensions) {
        this.gameObjects().addGameObject(new GameObject(
                                    Vector2.ZERO,
                                    new Vector2(BrickerGameManager.BORDER_WIDTH, windowDimensions.y()),
                                    BrickerGameManager.BORDER_RENDERABLE));
        this.gameObjects().addGameObject(new GameObject(new Vector2(
                                                windowDimensions.x()-BrickerGameManager.BORDER_WIDTH, 0),
                                        new Vector2(BrickerGameManager.BORDER_WIDTH,
                                                windowDimensions.y()),
                                        BrickerGameManager.BORDER_RENDERABLE));
    }

    /**
     * Creates the background and adds it to the game
     * @param backgroundImage An object which determined what the background would look like
     * @param windowDimensions The dimension (height and wight) of the game window
     */

    private void createBackground(Renderable backgroundImage, Vector2 windowDimensions) {
        GameObject background = new GameObject(Vector2.ZERO,
                                                windowDimensions,
                                                backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        this.gameObjects().addGameObject(background, Layer.BACKGROUND);

    }

    /**
     * Creates a brick-type objects and adds them to the game
     * @param brickImage An object which determined what the bricks would look like
     * @param windowDimensions The dimension (height and wight) of the game window
     */

    private void createBricks(Renderable brickImage, Vector2 windowDimensions) {
        float brick_wight = (windowDimensions.x() - BrickerGameManager.BORDER_WIDTH * 2) /
                BrickerGameManager.NUM_OF_BRICKS_IN_ROW;
        for (int i = 0; i < BrickerGameManager.NUM_OF_ROWS_OF_BRICKS; i++) {
                float current_positionY = i * BrickerGameManager.BRICK_HEIGHT;
            for (int j = 0; j < NUM_OF_BRICKS_IN_ROW; j++) {
                float current_positionX = BrickerGameManager.BORDER_WIDTH + (j * brick_wight);
                GameObject brick = new Brick(new Vector2(current_positionX, current_positionY),
                        new Vector2(brick_wight, BrickerGameManager.BRICK_HEIGHT), brickImage,
                        new CollisionStrategy(gameObjects()), this.bricksCounter);
                this.gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
                this.bricksCounter.increment();
            }
        }
    }

    /**
     * Creates a graphicLifeCounterImage-type object and adds them to the game
     * @param graphicLifeCounterImage An object which determined what the signs of disqualification
     *                               would look like
     * @param windowDimensions The dimension (height and wight) of the game window
     */

    private void createGraphicLifeCounter(Renderable graphicLifeCounterImage, Vector2 windowDimensions) {
        Vector2 position = new Vector2(BrickerGameManager.BORDER_WIDTH, windowDimensions.y()
                - BrickerGameManager.HEART_SIZE);
        Vector2 size = new Vector2(BrickerGameManager.HEART_SIZE, BrickerGameManager.HEART_SIZE);
        GraphicLifeCounter graphicLifeCounter = new GraphicLifeCounter(position, size, this.livesCounter,
                graphicLifeCounterImage, this.gameObjects(), BrickerGameManager.NUMBER_OF_DISQUALIFICATIONS);
        gameObjects().addGameObject(graphicLifeCounter, Layer.BACKGROUND);
    }

    /**
     * Creates a createNumericLifeCounter-type object and adds them to the game
     * @param windowDimensions The dimension (height and wight) of the game window
     */

    private void createNumericLifeCounter(Vector2 windowDimensions) {

        Vector2 position = new Vector2(BrickerGameManager.BORDER_WIDTH +
                BrickerGameManager.HEART_SIZE * BrickerGameManager.NUMBER_OF_DISQUALIFICATIONS,
                windowDimensions.y() - BrickerGameManager.HEART_SIZE);
        Vector2 size = new Vector2( BrickerGameManager.HEART_SIZE,  BrickerGameManager.HEART_SIZE);
        NumericLifeCounter numericLifeCounter = new NumericLifeCounter(this.livesCounter, position, size,
                this.gameObjects());
        gameObjects().addGameObject(numericLifeCounter, Layer.BACKGROUND);
    }

    /**
     * Creates an instance of and BrickerGameManager calls the function run to start the game
     * @param args Arguments to be received from the user (the list will be empty)
     */

    public static void main(String[] args) {
        new BrickerGameManager("Bricker", new Vector2(700, 500)).run();
    }
}
