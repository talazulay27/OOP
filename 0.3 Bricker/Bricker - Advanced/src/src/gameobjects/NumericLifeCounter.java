package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * A class that implements the numeric representation of the disqualifications left to the user
 */

public class NumericLifeCounter extends GameObject {

    private TextRenderable textRenderable;
    private Counter livesCounter;

    /**
     * Construct a new GameObject instance.
     *
     * @param livesCounter Counter which holds the number of disqualifications left for the player
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions Width and height in window coordinates.
     * @param gameObjectCollection A collection that includes all the existing objects in the game
     */

    public NumericLifeCounter(Counter livesCounter,
                              Vector2 topLeftCorner,
                              Vector2 dimensions,
                              GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, null);
        this.livesCounter = livesCounter;
        this.textRenderable = new TextRenderable(Integer.toString(livesCounter.value()));
        GameObject textRenderableObject = new GameObject(topLeftCorner, dimensions,
                this.textRenderable);
        gameObjectCollection.addGameObject(textRenderableObject, Layer.BACKGROUND);
    }

    /**
     * Writes on the screen the current number of disqualifications left for the player
     * @param deltaTime The deltaTime at which the function operates
     */

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        this.textRenderable.setString(Integer.toString(this.livesCounter.value()));
    }
}
