package fr.antoineaube.chameleon.patterns.linear.horizontal;

import fr.antoineaube.chameleon.core.configurations.ConcealmentPattern;
import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.processes.ConcreteConcealmentPattern;

public class HorizontalLinearPattern implements ConcealmentPattern {

    @Override
    public String getName() {
        return "HORIZONTAL_LINEAR";
    }

    @Override
    public ConcreteConcealmentPattern createPattern(Picture hideout, boolean reversed) {
        if (reversed) {
            return new ReversedHorizontalLinearConcretePattern(hideout);
        }

        return new HorizontalLinearConcretePattern(hideout);
    }

    @Override
    public int countUsedPixels(Picture hideout) {
        return hideout.getHeight() * hideout.getWidth();
    }
}
