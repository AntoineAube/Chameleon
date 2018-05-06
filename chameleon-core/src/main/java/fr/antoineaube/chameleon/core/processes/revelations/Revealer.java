package fr.antoineaube.chameleon.core.processes.revelations;

import fr.antoineaube.chameleon.core.configurations.ChameleonConfiguration;
import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.processes.ChameleonProcess;

import java.io.InputStream;

public class Revealer extends ChameleonProcess {

    public Revealer(ChameleonConfiguration configuration) {
        super(configuration);
    }

    public InputStream process(Picture hideout) {
        // TODO To be implemented.
        throw new UnsupportedOperationException();
    }
}
