package fr.antoineaube.chameleon.core.processes;

import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.pictures.structures.Position;

public abstract class ConcreteConcealmentPattern {

    private final int height;
    private final int width;
    private Position lastPosition;

    public ConcreteConcealmentPattern(Picture picture) {
        height = picture.getHeight();
        width = picture.getWidth();
        lastPosition = null;
    }

    /**
     *
     * @return The initial position in the pattern.
     */
    protected abstract Position initialPosition();

    /**
     * Computes the next position in the pattern without modifying any state.
     * It supposes that the last position is not null.
     * @return The next position in the pattern.
     */
    protected abstract Position nextPositionWithInitializedPosition();

    /**
     * Indicates if the pattern can provide one more position.
     * It supposes that the last position is not null.
     * @return True if there is a next position in the pattern.
     */
    protected abstract boolean hasNextWithInitializedPosition();

    protected int getHeight() {
        return height;
    }

    protected int getWidth() {
        return width;
    }

    public final Position nextPosition() {
        lastPosition = hasInitializedPosition() ? nextPositionWithInitializedPosition() : initialPosition();

        return lastPosition;
    }

    public final boolean hasNext() {
        return !hasInitializedPosition() || hasNextWithInitializedPosition();
    }

    private boolean hasInitializedPosition() {
        return lastPosition != null;
    }
}
