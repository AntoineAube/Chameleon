package fr.antoineaube.chameleon.core.processes;

import fr.antoineaube.chameleon.core.configurations.ChameleonConfiguration;

public abstract class ChameleonProcess {

    private final ChameleonConfiguration configuration;

    public ChameleonProcess(ChameleonConfiguration configuration) {
        this.configuration = configuration;
    }

    protected ChameleonConfiguration getConfiguration() {
        return configuration;
    }
}
