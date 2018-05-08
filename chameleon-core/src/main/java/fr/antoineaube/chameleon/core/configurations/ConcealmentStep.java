package fr.antoineaube.chameleon.core.configurations;

import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;

public class ConcealmentStep {

    private final int bitsNumber;
    private final ChannelColour channel;
    private final ConcealmentPattern followedPattern;
    private final boolean reversed;

    public ConcealmentStep(int bitsNumber, ChannelColour channel, ConcealmentPattern followedPattern, boolean reversed) {
        this.bitsNumber = bitsNumber;
        this.channel = channel;
        this.followedPattern = followedPattern;
        this.reversed = reversed;
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

    public boolean isReversed() {
        return reversed;
    }

    @Override
    public String toString() {
        return "ConcealmentStep{" +
                "bitsNumber=" + bitsNumber +
                ", channel=" + channel +
                ", followedPattern=" + followedPattern.getName() +
                ", reversed=" + reversed +
                '}';
    }
}
