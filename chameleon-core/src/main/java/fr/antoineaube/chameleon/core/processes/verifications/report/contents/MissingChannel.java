package fr.antoineaube.chameleon.core.processes.verifications.report.contents;

import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;

public class MissingChannel implements Verified {

    private final ChannelColour missingChannel;

    public MissingChannel(ChannelColour missingChannel) {
        this.missingChannel = missingChannel;
    }

    public ChannelColour getMissingChannel() {
        return missingChannel;
    }

    @Override
    public VerificationStatus getStatus() {
        return VerificationStatus.ERROR;
    }
}
