package fr.antoineaube.chameleon.patterns.linear.vertical;

import fr.antoineaube.chameleon.core.configurations.ConcealmentPattern;
import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.processes.ConcreteConcealmentPattern;

public class VerticalLinearPattern implements ConcealmentPattern {

    @Override
    public String getName() {
        return "VERTICAL_LINEAR";
    }

    @Override
    public ConcreteConcealmentPattern createPattern(Picture hideout, boolean reversed) {
        if (reversed) {
            return new ReversedVerticalLinearConcretePattern(hideout);
        }

        return new VerticalLinearConcretePattern(hideout);
    }

    @Override
    public int countUsedPixels(Picture hideout) {
        return hideout.getHeight() * hideout.getWidth();
    }
}
