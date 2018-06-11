package fr.antoineaube.chameleon.core.processes.revelations;

import fr.antoineaube.chameleon.core.configurations.ChameleonConfiguration;
import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.processes.ChameleonProcess;
import fr.antoineaube.chameleon.core.processes.verifications.RevealVerifier;
import fr.antoineaube.chameleon.core.processes.verifications.VerificationException;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Revealer extends ChameleonProcess {

    public Revealer(ChameleonConfiguration configuration) {
        super(configuration);
    }

    public InputStream process(Picture hideout) throws IOException, VerificationException {
        new RevealVerifier(getConfiguration()).verify(hideout);

        File temporaryFile = File.createTempFile("revealed-message-" + UUID.randomUUID(), ".tmp");
        temporaryFile.deleteOnExit();

        try (MagicNumberAlarmBitOutputStream message = new MagicNumberAlarmBitOutputStream(new FileOutputStream(temporaryFile), getConfiguration().getMagicNumber())) {
            for (StepRevealer step : createStepRevealers(hideout, message)) {
                step.process();
            }
        }

        return new FileInputStream(temporaryFile);
    }

    private List<StepRevealer> createStepRevealers(Picture hideout, MagicNumberAlarmBitOutputStream message) {
        return Arrays.stream(getConfiguration().getSteps())
                .map(step -> new StepRevealer(hideout, step, message))
                .collect(Collectors.toList());
    }
}
