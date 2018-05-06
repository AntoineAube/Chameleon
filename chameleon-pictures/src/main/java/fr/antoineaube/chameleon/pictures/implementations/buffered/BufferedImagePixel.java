package fr.antoineaube.chameleon.pictures.implementations.buffered;

import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;
import fr.antoineaube.chameleon.core.pictures.structures.Pixel;

import java.awt.image.BufferedImage;

public class BufferedImagePixel implements Pixel {

    private final BufferedImage origin;

    public BufferedImagePixel(BufferedImage origin) {
        this.origin = origin;
    }

    @Override
    public ChannelColour[] getOrderedChannels() {
        return AWTColourSpaceTranslator.findByCode(origin.getType());
    }

    @Override
    public int getColourValue(ChannelColour colour) {
        // TODO Implement this.
        throw new UnsupportedOperationException("Need to be implemented.");
    }

    @Override
    public void setColourValue(ChannelColour colour, int newValue) {
        // TODO Implement this.
        throw new UnsupportedOperationException("Need to be implemented.");
    }
}
