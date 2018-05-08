package fr.antoineaube.chameleon.core.processes.concealments;

import fr.antoineaube.chameleon.core.configurations.ChameleonConfiguration;
import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.processes.ChameleonProcess;
import org.apache.commons.io.IOUtils;

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

    public Picture process(InputStream message, Picture hideout) throws IOException {
        InputStream processedMessage = appender.appendMagicNumber(message);

        for (StepConcealer step : createStepConcealers(hideout, new BitInputStream(processedMessage))) {
            step.process();
        }

        return hideout;
    }

    private List<StepConcealer> createStepConcealers(Picture hideout, BitInputStream message) {
        return Arrays.stream(getConfiguration().getSteps())
                .map(step -> new StepConcealer(hideout, step, message))
                .collect(Collectors.toList());
    }
}
