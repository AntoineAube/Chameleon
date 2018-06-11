package fr.antoineaube.chameleon.core.processes.verifications;

import fr.antoineaube.chameleon.core.configurations.ChameleonConfiguration;
import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.processes.verifications.report.VerificationReport;

public class RevealVerifier extends ProcessVerifier {

    public RevealVerifier(ChameleonConfiguration configuration) {
        super(configuration);
    }

    public void verify(Picture hideout) throws VerificationException {
        VerificationReport report = new VerificationReport();

        checkChannels(hideout).ifPresent(report::log);

        if (!report.getReportedLogs().isEmpty()) {
            throw new VerificationException(report);
        }
    }
}
