package fr.antoineaube.chameleon.patterns.linear;

import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.processes.ConcreteConcealmentPattern;

public abstract class TooledConcreteConcealmentPattern extends ConcreteConcealmentPattern {

    public TooledConcreteConcealmentPattern(Picture picture) {
        super(picture);
    }

    protected boolean isAtEndOfLine() {
        return getLastPosition().getX() == getWidth() - 1;
    }

    protected boolean isAtEndOfColumn() {
        return getLastPosition().getY() == getHeight() - 1;
    }
}
