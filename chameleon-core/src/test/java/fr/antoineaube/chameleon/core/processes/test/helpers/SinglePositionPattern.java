package fr.antoineaube.chameleon.core.processes.test.helpers;

import fr.antoineaube.chameleon.core.configurations.ConcealmentPattern;
import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.pictures.structures.Position;
import fr.antoineaube.chameleon.core.processes.ConcreteConcealmentPattern;

public class SinglePositionPattern implements ConcealmentPattern {

    private final Position target;

    public SinglePositionPattern(Position target) {
        this.target = target;
    }

    @Override
    public String getName() {
        return "SINGLE_POSITION";
    }

    @Override
    public ConcreteConcealmentPattern createPattern(Picture hideout, boolean reversed) {
        return new ConcreteConcealmentPattern(hideout) {
            @Override
            protected Position initialPosition() {
                return target;
            }

            @Override
            protected Position nextPositionWithInitializedPosition() {
                return null;
            }

            @Override
            protected boolean hasNextWithInitializedPosition() {
                return false;
            }
        };
    }
}
