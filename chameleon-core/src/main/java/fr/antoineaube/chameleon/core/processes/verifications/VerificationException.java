package fr.antoineaube.chameleon.core.processes.verifications;

import fr.antoineaube.chameleon.core.processes.verifications.report.VerificationReport;

public class VerificationException extends Exception {

    private final VerificationReport report;

    public VerificationException(VerificationReport report) {
        this.report = report;
    }

    public VerificationReport getReport() {
        return report;
    }
}
