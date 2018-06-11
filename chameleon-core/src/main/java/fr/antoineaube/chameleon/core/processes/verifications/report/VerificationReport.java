package fr.antoineaube.chameleon.core.processes.verifications.report;

import fr.antoineaube.chameleon.core.processes.verifications.report.contents.Verified;

import java.util.ArrayList;
import java.util.List;

public class VerificationReport {

    private final List<Verified> reportedLogs;

    public VerificationReport() {
        reportedLogs = new ArrayList<>();
    }

    public List<Verified> getReportedLogs() {
        return reportedLogs;
    }

    public void log(Verified log) {
        reportedLogs.add(log);
    }
}
