package fr.antoineaube.chameleon.core.pictures.structures;

import fr.antoineaube.chameleon.core.pictures.exceptions.UnavailableColourException;

import java.util.Arrays;

public abstract class Pixel {

    public int getColourValue(ChannelColour colour) {
        ChannelColour[] availableColours = getOrderedChannels();
        if (!Arrays.asList(availableColours).contains(colour)) {
            throw new UnavailableColourException(colour, getOrderedChannels());
        }

        return safeGetColourValue(colour);
    }

    public void setColourValue(ChannelColour colour, int newValue) {
        ChannelColour[] availableColours = getOrderedChannels();
        if (!Arrays.asList(availableColours).contains(colour)) {
            throw new UnavailableColourException(colour, getOrderedChannels());
        }

        safeSetColourValue(colour, newValue);
    }

    public abstract ChannelColour[] getOrderedChannels();
    protected abstract int safeGetColourValue(ChannelColour colour);
    protected abstract void safeSetColourValue(ChannelColour colour, int newValue);
}
