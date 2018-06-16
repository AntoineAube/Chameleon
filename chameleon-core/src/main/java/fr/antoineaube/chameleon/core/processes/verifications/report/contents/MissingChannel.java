package fr.antoineaube.chameleon.core.processes.verifications.report.contents;

import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;

public class MissingChannel implements Verified {

    private final ChannelColour channel;

    public MissingChannel(ChannelColour channel) {
        this.channel = channel;
    }

    public ChannelColour getChannel() {
        return channel;
    }

    @Override
    public VerificationStatus getStatus() {
        return VerificationStatus.ERROR;
    }
}
