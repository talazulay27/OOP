package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * A class that implements the graphical representation of the disqualifications left to the user
 */

public class GraphicLifeCounter extends GameObject {

    private Counter livesCounter;
    private GameObjectCollection gameObjectsCollection;
    private int numOfLives;
    private GameObject[] hearts;

    /**
     * Construct a new GameObject instance.
     *
     * @param widgetTopLeftCorner Position of the object, in window coordinates (pixels).
     *                            Note that (0,0) is the top-left corner of the window.
     * @param widgetDimensions Width and height in window coordinates.
     * @param livesCounter Counter which holds the number of disqualifications left for the player
     * @param widgetRenderable The renderable representing the object. Can be null, in which case
     * @param gameObjectsCollection A collection that includes all the existing objects in the game
     * @param numOfLives The number of disqualifications of the player at the beginning of the game
     */
    public GraphicLifeCounter(Vector2 widgetTopLeftCorner,
                              Vector2 widgetDimensions,
                              Counter livesCounter,
                              Renderable widgetRenderable,
                              GameObjectCollection gameObjectsCollection,
                              int numOfLives) {
        super(widgetTopLeftCorner, widgetDimensions, null);
        this.livesCounter = livesCounter;
        this.gameObjectsCollection = gameObjectsCollection;
        this.numOfLives = numOfLives;
        this.hearts = new GameObject[this.numOfLives];
        for (int i = 0; i < this.numOfLives; i++) {
            Vector2 position = new Vector2(
                    widgetTopLeftCorner.x() + (i * widgetDimensions.x()),
                    widgetTopLeftCorner.y());
            GameObject tempHeart = new GameObject(position, widgetDimensions, widgetRenderable);
            this.gameObjectsCollection.addGameObject(tempHeart, Layer.BACKGROUND);
            this.hearts[i] = tempHeart;
        }
    }

    /**
     * Updates the number of hearts on the screen according to the number of disqualifications left
     * for the player
     * @param deltaTime The deltaTime at which the function operates
     */

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.livesCounter.value() < this.hearts.length) {
            GameObject[] tempHearts = new GameObject[this.livesCounter.value()];
            if (this.livesCounter.value() >= 0)
                System.arraycopy(this.hearts, 0, tempHearts, 0, this.livesCounter.value());
                this.gameObjectsCollection.removeGameObject(this.hearts[this.livesCounter.value()],
                                                        Layer.BACKGROUND);
                this.hearts = tempHearts;
        }
    }
}
