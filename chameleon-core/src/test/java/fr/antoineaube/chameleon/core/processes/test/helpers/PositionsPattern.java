package fr.antoineaube.chameleon.core.processes.test.helpers;

import fr.antoineaube.chameleon.core.configurations.ConcealmentPattern;
import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.pictures.structures.Position;
import fr.antoineaube.chameleon.core.processes.ConcreteConcealmentPattern;

public class PositionsPattern implements ConcealmentPattern {

    private final Position[] positions;

    public PositionsPattern(Position... positions) {
        this.positions = positions;
    }

    @Override
    public String getName() {
        return "POSITIONS";
    }

    @Override
    public ConcreteConcealmentPattern createPattern(Picture hideout, boolean reversed) {
        return new ConcreteConcealmentPattern(hideout) {
            int index = 1;

            @Override
            protected Position initialPosition() {
                return positions[0];
            }

            @Override
            protected Position nextPositionWithInitializedPosition() {
                return positions[index++];
            }

            @Override
            protected boolean hasNextWithInitializedPosition() {
                return index != positions.length;
            }
        };
    }

    @Override
    public int countUsedPixels(Picture hideout) {
        return positions.length;
    }
}
