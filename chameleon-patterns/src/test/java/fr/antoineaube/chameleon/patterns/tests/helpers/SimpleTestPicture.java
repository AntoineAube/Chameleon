package fr.antoineaube.chameleon.patterns.tests.helpers;

import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;

public class SimpleTestPicture implements Picture {

    private final int height;
    private final int width;

    public SimpleTestPicture(int height, int width) {
        this.height = height;
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public ChannelColour[] getColourChannels() {
        return new ChannelColour[0];
    }
}
