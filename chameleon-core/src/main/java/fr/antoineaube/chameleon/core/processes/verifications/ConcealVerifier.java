package fr.antoineaube.chameleon.core.processes.verifications;

import fr.antoineaube.chameleon.core.configurations.ChameleonConfiguration;
import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.processes.verifications.report.VerificationReport;
import fr.antoineaube.chameleon.core.processes.verifications.report.contents.NotEnoughSpace;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Optional;

public class ConcealVerifier extends ProcessVerifier {

    private static final Logger LOGGER = LogManager.getLogger(ConcealVerifier.class);

    public ConcealVerifier(ChameleonConfiguration configuration) {
        super(configuration);
    }

    public void verify(Picture hideout, InputStream message) throws VerificationException {
        VerificationReport report = new VerificationReport();

        checkChannels(hideout).ifPresent(report::log);
        try {
            checkSpace(hideout, message).ifPresent(report::log);
        } catch (IOException e) {
            LOGGER.error("Failed to fetch the number of available bytes in message", e);
        }

        if (!report.getReportedLogs().isEmpty()) {
            throw new VerificationException(report);
        }
    }

    private Optional<NotEnoughSpace> checkSpace(Picture hideout, InputStream message) throws IOException {
        int messageSizeInBits = message.available() * Byte.SIZE;

        int availableBits = Arrays.stream(getConfiguration().getSteps())
                .map(step -> step.getFollowedPattern().countUsedPixels(hideout) * step.getBitsNumber())
                .reduce(Integer::sum).orElse(0);

        if (messageSizeInBits > availableBits) {
            return Optional.of(new NotEnoughSpace(messageSizeInBits, availableBits));
        }

        return Optional.empty();
    }
}
