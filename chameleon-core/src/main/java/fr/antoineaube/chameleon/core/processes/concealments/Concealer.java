package fr.antoineaube.chameleon.core.processes.concealments;

import fr.antoineaube.chameleon.core.configurations.ChameleonConfiguration;
import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.processes.ChameleonProcess;

import java.io.InputStream;

public class Concealer extends ChameleonProcess {

    public Concealer(ChameleonConfiguration configuration) {
        super(configuration);
    }

    public Picture process(InputStream message, Picture hideout) {
        // TODO To be implemented.
        throw new UnsupportedOperationException();
    }
}
