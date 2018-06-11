package fr.antoineaube.chameleon.core.processes.concealments;

import fr.antoineaube.chameleon.core.configurations.ChameleonConfiguration;
import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.processes.ChameleonProcess;
import fr.antoineaube.chameleon.core.processes.verifications.ConcealVerifier;
import fr.antoineaube.chameleon.core.processes.verifications.VerificationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Concealer extends ChameleonProcess {

    private MagicNumberAppender appender;

    public Concealer(ChameleonConfiguration configuration) {
        super(configuration);

        appender = new MagicNumberAppender(configuration.getMagicNumber());
    }

    public Picture process(InputStream message, Picture hideout) throws IOException, VerificationException {
        new ConcealVerifier(getConfiguration()).verify(hideout, message);

        try (InputStream processedMessage = appender.appendMagicNumber(message); BitInputStream bitStream = new BitInputStream(processedMessage)) {
            for (StepConcealer step : createStepConcealers(hideout, bitStream)) {
                step.process();
            }
        }

        return hideout;
    }

    private List<StepConcealer> createStepConcealers(Picture hideout, BitInputStream message) {
        return Arrays.stream(getConfiguration().getSteps())
                .map(step -> new StepConcealer(hideout, step, message))
                .collect(Collectors.toList());
    }
}
