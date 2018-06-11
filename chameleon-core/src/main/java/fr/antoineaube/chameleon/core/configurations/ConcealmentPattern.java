package fr.antoineaube.chameleon.core.configurations;

import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.processes.ConcreteConcealmentPattern;

public interface ConcealmentPattern {

    String getName();
    ConcreteConcealmentPattern createPattern(Picture hideout, boolean reversed);
    int countUsedPixels(Picture hideout);
}
