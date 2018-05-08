package fr.antoineaube.chameleon.core.pictures.structures;

import fr.antoineaube.chameleon.core.pictures.exceptions.UnavailableColourException;

import java.util.Arrays;

public interface Pixel {

    default int getColourValue(ChannelColour colour) {
        ChannelColour[] availableColours = getOrderedChannels();
        if (!Arrays.asList(availableColours).contains(colour)) {
            throw new UnavailableColourException(colour, getOrderedChannels());
        }

        return safeGetColourValue(colour);
    }

    default void setColourValue(ChannelColour colour, int newValue) {
        ChannelColour[] availableColours = getOrderedChannels();
        if (!Arrays.asList(availableColours).contains(colour)) {
            throw new UnavailableColourException(colour, getOrderedChannels());
        }

        safeSetColourValue(colour, newValue);
    }


    ChannelColour[] getOrderedChannels();
    int safeGetColourValue(ChannelColour colour);
    void safeSetColourValue(ChannelColour colour, int newValue);
}
