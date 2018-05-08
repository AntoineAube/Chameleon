package fr.antoineaube.chameleon.patterns.tests.helpers;

import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.pictures.PictureSaver;
import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;
import fr.antoineaube.chameleon.core.pictures.structures.Pixel;
import fr.antoineaube.chameleon.core.pictures.structures.Position;

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

    @Override
    public Pixel getPixel(Position position) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PictureSaver getSaver() {
        return null;
    }
}
