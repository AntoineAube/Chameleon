package fr.antoineaube.chameleon.patterns.linear.horizontal;

import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.pictures.structures.Position;
import fr.antoineaube.chameleon.core.processes.ConcreteConcealmentPattern;

public class HorizontalLinearConcretePattern extends ConcreteConcealmentPattern {

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
        return !(isAtEndOfLine() && getLastPosition().getY() == getHeight() - 1);
    }

    private boolean isAtEndOfLine() {
        return getLastPosition().getX() == getWidth() - 1;
    }
}
