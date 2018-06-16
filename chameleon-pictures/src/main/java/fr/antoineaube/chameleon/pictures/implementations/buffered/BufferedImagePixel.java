package fr.antoineaube.chameleon.pictures.implementations.buffered;

import fr.antoineaube.chameleon.core.pictures.exceptions.UnavailableColourException;
import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;
import fr.antoineaube.chameleon.core.pictures.structures.Pixel;
import fr.antoineaube.chameleon.core.pictures.structures.Position;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BufferedImagePixel extends Pixel {

    private final BufferedImage origin;
    private final Position positionInPicture;

    public BufferedImagePixel(BufferedImage origin, Position positionInPicture) {
        this.origin = origin;
        this.positionInPicture = positionInPicture;
    }

    @Override
    public ChannelColour[] getOrderedChannels() {
        return AWTColourSpaceTranslator.findByCode(origin.getType());
    }

    @Override
    public int safeGetColourValue(ChannelColour colour) {
        int rgb = origin.getRGB(positionInPicture.getX(), positionInPicture.getY());

        Color color = new Color(rgb);

        switch (colour) {
            case BLUE:
                return color.getBlue();
            case GREEN:
                return color.getGreen();
            case RED:
                return color.getRed();
            case ALPHA:
                return color.getAlpha();

            // TODO Handle GRAY color.
        }

        throw new UnavailableColourException(colour, getOrderedChannels());
    }

    @Override
    public void safeSetColourValue(ChannelColour colour, int newValue) {
        Color currentColor = new Color(origin.getRGB(positionInPicture.getX(), positionInPicture.getY()));

        Color actualizedColor = currentColor;
        switch (colour) {
            case BLUE:
                actualizedColor = new Color(currentColor.getRed(), currentColor.getGreen(), newValue, currentColor.getAlpha());
                break;
            case GREEN:
                actualizedColor = new Color(currentColor.getRed(), newValue, currentColor.getBlue(), currentColor.getAlpha());
                break;
            case RED:
                actualizedColor = new Color(newValue, currentColor.getGreen(), currentColor.getBlue(), currentColor.getAlpha());
                break;
            case ALPHA:
                actualizedColor = new Color(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), newValue);
                break;

            // TODO Handle GRAY color.
        }

        origin.setRGB(positionInPicture.getX(), positionInPicture.getY(), actualizedColor.getRGB());
    }
}
