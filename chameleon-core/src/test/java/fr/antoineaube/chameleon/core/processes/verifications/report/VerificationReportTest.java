package fr.antoineaube.chameleon.core.processes.verifications.report;

import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;
import fr.antoineaube.chameleon.core.processes.verifications.report.contents.MissingChannel;
import fr.antoineaube.chameleon.core.processes.verifications.report.contents.NotEnoughSpace;
import fr.antoineaube.chameleon.core.processes.verifications.report.contents.VerificationStatus;
import fr.antoineaube.chameleon.core.processes.verifications.report.contents.Verified;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VerificationReportTest {

    @DisplayName("Should log the verifications errors/warnings")
    @Test
    void shouldLog() {
        VerificationReport report = new VerificationReport();

        report.log(new MissingChannel(ChannelColour.RED));
        report.log(new NotEnoughSpace(10, 8));

        List<Verified> verifiedLogs = report.getReportedLogs();

        assertTrue(verifiedLogs.get(0) instanceof MissingChannel);
        assertEquals(VerificationStatus.ERROR, verifiedLogs.get(0).getStatus());
        assertEquals(ChannelColour.RED, ((MissingChannel) verifiedLogs.get(0)).getChannel());

        assertTrue(verifiedLogs.get(1) instanceof NotEnoughSpace);
        assertEquals(VerificationStatus.ERROR, verifiedLogs.get(1).getStatus());
        assertEquals(10, ((NotEnoughSpace) verifiedLogs.get(1)).getRequiredBits());
        assertEquals(8, ((NotEnoughSpace) verifiedLogs.get(1)).getAvailableBits());
    }
}