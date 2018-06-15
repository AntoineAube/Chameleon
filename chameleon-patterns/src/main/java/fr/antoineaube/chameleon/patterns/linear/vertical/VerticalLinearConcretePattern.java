package fr.antoineaube.chameleon.patterns.linear.vertical;

import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.pictures.structures.Position;
import fr.antoineaube.chameleon.core.processes.ConcreteConcealmentPattern;
import fr.antoineaube.chameleon.patterns.linear.TooledConcreteConcealmentPattern;

public class VerticalLinearConcretePattern extends TooledConcreteConcealmentPattern {

    public VerticalLinearConcretePattern(Picture picture) {
        super(picture);
    }

    @Override
    protected Position initialPosition() {
        return new Position(0, 0);
    }

    @Override
    protected Position nextPositionWithInitializedPosition() {
        if (isAtEndOfColumn()) {
            return new Position(getLastPosition().getX() + 1, 0);
        }

        return new Position(getLastPosition().getX(), getLastPosition().getY() + 1);
    }

    @Override
    protected boolean hasNextWithInitializedPosition() {
        return !(isAtEndOfLine() && isAtEndOfColumn());
    }
}
