package fr.antoineaube.chameleon.patterns.linear;

import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.processes.ConcreteConcealmentPattern;

public abstract class TooledReversedConcreteConcealmentPattern extends ConcreteConcealmentPattern {

    public TooledReversedConcreteConcealmentPattern(Picture picture) {
        super(picture);
    }

    protected boolean isAtEndOfLine() {
        return getLastPosition().getX() == 0;
    }

    protected boolean isAtEndOfColumn() {
        return getLastPosition().getY() == 0;
    }
}
