package fr.antoineaube.chameleon.core.pictures.structures;

public interface Pixel {

    ChannelColour[] getOrderedChannels();
    int getColourValue(ChannelColour colour);
    void setColourValue(ChannelColour colour, int newValue);
}
