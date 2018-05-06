package fr.antoineaube.chameleon.core.configurations;

import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;

public class ConcealmentStep {

    private final int bitsNumber;
    private final ChannelColour channel;
    private final ConcealmentPattern followedPattern;

    public ConcealmentStep(int bitsNumber, ChannelColour channel, ConcealmentPattern followedPattern) {
        this.bitsNumber = bitsNumber;
        this.channel = channel;
        this.followedPattern = followedPattern;
    }

    public int getBitsNumber() {
        return bitsNumber;
    }

    public ChannelColour getChannel() {
        return channel;
    }

    public ConcealmentPattern getFollowedPattern() {
        return followedPattern;
    }
}
