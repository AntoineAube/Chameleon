package fr.antoineaube.chameleon.core.processes.test.helpers;

import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;
import fr.antoineaube.chameleon.core.pictures.structures.Pixel;
import fr.antoineaube.chameleon.core.pictures.structures.Position;

import java.util.HashMap;
import java.util.Map;

public class SimpleTestPicture implements Picture {

    private final int height;
    private final int width;
    private final Map<Position, Pixel> pixels;

    public SimpleTestPicture(int height, int width) {
        this.height = height;
        this.width = width;

        pixels = new HashMap<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels.put(new Position(x, y), new SimpleTestPixel(getColourChannels()));
            }
        }
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
        return new ChannelColour[] {ChannelColour.BLUE, ChannelColour.GREEN, ChannelColour.RED};
    }

    @Override
    public Pixel getPixel(Position position) {
        return pixels.get(position);
    }
}
