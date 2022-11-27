import bricker.brick_strategies.CollisionStrategy;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import gameobjects.AiPaddle;
import gameobjects.Ball;
import gameobjects.Brick;
import gameobjects.UserPaddle;
import java.awt.*;
import java.util.Random;

public class BouncingBallGameManager extends GameManager{

    private static final float BALL_SPEED = 200;
    private static final float NUM_OF_BRICK_ROWS = 3;
    private static final float NUM_OF_BRICK_IN_SINGLE_ROW = 4;
    private static final float ONE_PIXEL_SPACE = 1;
    private static final float BORDERS_SIZE = 10;
    private static final float BRICK_HEIGHT = 15;
    private Ball ball;
    private Vector2 windowDimensions;
    private WindowController windowControler;
    private Counter countBricks;

    public BouncingBallGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener,
                               WindowController windowController) {

        this.windowControler = windowController;
        this.countBricks = new Counter();
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        windowDimensions = windowController.getWindowDimensions();
        this.ball = createBall(imageReader, soundReader, windowDimensions);
        createUserPaddle(imageReader, windowDimensions, inputListener);
//      createAiPaddle(imageReader, windowDimensions, ball);
        createBorders(windowDimensions);
        createBackgrounds(imageReader, windowController);
        createBricks(windowDimensions, imageReader);

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();

        }

    private void checkForGameEnd() {
        float ball_height = ball.getCenter().y();
        String massage = "";
        if (this.countBricks.value() == 0) {
            massage = "You win!";
        }
        if (ball_height > windowDimensions.y()) {
            massage = "You lose!";
        }
        if (!massage.isEmpty()) {
            massage += " Play again?";
            if (windowControler.openYesNoDialog(massage)) {
                windowControler.resetGame();
            } else {
                windowControler.closeWindow();
            }

        }
    }

    private Ball createBall(ImageReader imageReader, SoundReader soundReader, Vector2 windowDimensions){

        Renderable ballImage = imageReader.readImage("assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        Ball ball = new Ball(new Vector2(0, 0), new Vector2(20, 20), ballImage, collisionSound);
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean())
        {
            ballVelX *= -1;
        }
        if (rand.nextBoolean())
        {
            ballVelY *= -1;
        }
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
        ball.setCenter(windowDimensions.mult(0.5F));
        gameObjects().addGameObject(ball);
        return ball;
    }

    private void createUserPaddle(ImageReader imageReader, Vector2 windowDimensions,
                                  UserInputListener inputListener){

        Renderable paddleImage = imageReader.readImage("assets/paddle.png", true);
        GameObject userPaddle = new UserPaddle(Vector2.ZERO, new Vector2(100, 15),
                                                paddleImage, inputListener, windowDimensions);
        userPaddle.setCenter(new Vector2(windowDimensions.x() / 2, (int) windowDimensions.y() - 30));
        gameObjects().addGameObject(userPaddle);
    }

    private void createAiPaddle(ImageReader imageReader, Vector2 windowDimensions, Ball ball){

        Renderable paddleImage = imageReader.readImage("assets/paddle.png", true);
        GameObject aiPaddle = new AiPaddle(Vector2.ZERO, new Vector2(100, 15),
                                            paddleImage, ball, windowDimensions);
        aiPaddle.setCenter(new Vector2(windowDimensions.x() / 2, 30));
        gameObjects().addGameObject(aiPaddle);
    }

    private void createBackgrounds(ImageReader imageReader, WindowController windowController){

        GameObject background = new GameObject(Vector2.ZERO, windowController.getWindowDimensions(),
                imageReader.readImage("assets/background.png", false));
        gameObjects().addGameObject(background, Layer.BACKGROUND);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
    }

    private void createBorders(Vector2 windowDimensions) {

        gameObjects().addGameObject(new GameObject(Vector2.ZERO, new Vector2(BORDERS_SIZE, windowDimensions.y()),
                new RectangleRenderable(Color.BLACK)));
        gameObjects().addGameObject(new GameObject(new Vector2(windowDimensions.x() - BORDERS_SIZE, 0),
                new Vector2(BORDERS_SIZE, windowDimensions.y()), new RectangleRenderable(Color.BLACK)));
        gameObjects().addGameObject(new GameObject(Vector2.ZERO, new Vector2(windowDimensions.x(), BORDERS_SIZE),
                new RectangleRenderable(Color.BLACK)));

    }
    private void createBricks(Vector2 windowDimensions, ImageReader imageReader) {

        Renderable brickImage = imageReader.readImage("assets/brick.png", false);
        CollisionStrategy collisionStrategy = new CollisionStrategy(gameObjects(), this.countBricks);
        float brick_wight = windowDimensions.x() - BORDERS_SIZE * 2 - ONE_PIXEL_SPACE * 2;
        brick_wight = brick_wight / NUM_OF_BRICK_IN_SINGLE_ROW;
        float current_positionY = BORDERS_SIZE + ONE_PIXEL_SPACE;
        for (int i = 0; i < NUM_OF_BRICK_ROWS; i++) {
            float current_positionX = BORDERS_SIZE + ONE_PIXEL_SPACE;
            for (int j = 0; j < NUM_OF_BRICK_IN_SINGLE_ROW; j++) {
                GameObject brick = new Brick(new Vector2(current_positionX, current_positionY), new Vector2(brick_wight, BRICK_HEIGHT), brickImage, collisionStrategy);
                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
                this.countBricks.increment();
                current_positionX += brick_wight;
            }
            current_positionY += BRICK_HEIGHT;
        }

    }

    public static void main(String[] args) {
        new BouncingBallGameManager("bouncing ball", new Vector2(700,500)).run();
    }
}
