package fr.antoineaube.chameleon.core.pictures.exceptions;

import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;

public class UnavailableColourException extends RuntimeException {

    private final ChannelColour colour;
    private final ChannelColour[] availableColours;

    public UnavailableColourException(ChannelColour colour, ChannelColour[] availableColours) {
        this.colour = colour;
        this.availableColours = availableColours;
    }

    public ChannelColour getColour() {
        return colour;
    }

    public ChannelColour[] getAvailableColours() {
        return availableColours;
    }
}
