package fr.antoineaube.chameleon.core.pictures;

import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;

public interface Picture {

    int getHeight();
    int getWidth();
    ChannelColour[] getColourChannels();
}
