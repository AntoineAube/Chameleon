package fr.antoineaube.chameleon.patterns.linear.vertical;

import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.pictures.structures.Position;
import fr.antoineaube.chameleon.patterns.linear.TooledReversedConcreteConcealmentPattern;

public class ReversedVerticalLinearConcretePattern extends TooledReversedConcreteConcealmentPattern {

    public ReversedVerticalLinearConcretePattern(Picture picture) {
        super(picture);
    }

    @Override
    protected Position initialPosition() {
        return new Position(getWidth() - 1, getHeight() - 1);
    }

    @Override
    protected Position nextPositionWithInitializedPosition() {
        if (isAtEndOfColumn()) {
            return new Position(getLastPosition().getX() - 1, getHeight() - 1);
        }

        return new Position(getLastPosition().getX(), getLastPosition().getY() - 1);
    }

    @Override
    protected boolean hasNextWithInitializedPosition() {
        return !(isAtEndOfLine() && isAtEndOfColumn());
    }
}
