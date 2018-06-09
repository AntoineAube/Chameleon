package fr.antoineaube.chameleon.core.configurations;

import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;

public class ConcealmentStep {

    private int bitsNumber;
    private ChannelColour channel;
    private ConcealmentPattern followedPattern;
    private boolean reversed;

    public ConcealmentStep(int bitsNumber, ChannelColour channel, ConcealmentPattern followedPattern, boolean reversed) {
        this.bitsNumber = bitsNumber;
        this.channel = channel;
        this.followedPattern = followedPattern;
        this.reversed = reversed;
    }

    public int getBitsNumber() {
        return bitsNumber;
    }

    public void setBitsNumber(int bitsNumber) {
        this.bitsNumber = bitsNumber;
    }

    public ChannelColour getChannel() {
        return channel;
    }

    public void setChannel(ChannelColour channel) {
        this.channel = channel;
    }

    public ConcealmentPattern getFollowedPattern() {
        return followedPattern;
    }

    public void setFollowedPattern(ConcealmentPattern followedPattern) {
        this.followedPattern = followedPattern;
    }

    public boolean isReversed() {
        return reversed;
    }

    public void setReversed(boolean reversed) {
        this.reversed = reversed;
    }

    @Override
    public String toString() {
        return "ConcealmentStep{" +
                "bitsNumber=" + bitsNumber +
                ", channel=" + channel +
                ", followedPattern=" + (followedPattern == null ? null : followedPattern.getName()) +
                ", reversed=" + reversed +
                '}';
    }
}
