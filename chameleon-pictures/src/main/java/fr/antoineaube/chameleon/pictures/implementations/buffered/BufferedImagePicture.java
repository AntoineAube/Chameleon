package fr.antoineaube.chameleon.pictures.implementations.buffered;

import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;
import fr.antoineaube.chameleon.core.pictures.structures.Pixel;
import fr.antoineaube.chameleon.core.pictures.structures.Position;

import java.awt.image.BufferedImage;

public class BufferedImagePicture implements Picture {

    private final BufferedImage image;

    public BufferedImagePicture(BufferedImage image) {
        this.image = image;
    }

    @Override
    public int getHeight() {
        return image.getHeight();
    }

    @Override
    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public ChannelColour[] getColourChannels() {
        return AWTColourSpaceTranslator.findByCode(image.getType());
    }

    @Override
    public Pixel getPixel(Position position) {
        return new BufferedImagePixel(this.image, position);
    }
}
