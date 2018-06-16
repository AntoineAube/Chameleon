package fr.antoineaube.chameleon.patterns.linear.horizontal;

import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.pictures.structures.Position;
import fr.antoineaube.chameleon.patterns.linear.TooledConcreteConcealmentPattern;

public class HorizontalLinearConcretePattern extends TooledConcreteConcealmentPattern {

    public HorizontalLinearConcretePattern(Picture picture) {
        super(picture);
    }

    @Override
    protected Position initialPosition() {
        return new Position(0, 0);
    }

    @Override
    protected Position nextPositionWithInitializedPosition() {
        if (isAtEndOfLine()) {
            return new Position(0, getLastPosition().getY() + 1);
        }

        return new Position(getLastPosition().getX() + 1, getLastPosition().getY());
    }

    @Override
    protected boolean hasNextWithInitializedPosition() {
        return !(isAtEndOfLine() && isAtEndOfColumn());
    }
}
