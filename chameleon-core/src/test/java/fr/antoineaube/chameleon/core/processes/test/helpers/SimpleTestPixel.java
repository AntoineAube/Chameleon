package fr.antoineaube.chameleon.core.processes.test.helpers;

import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;
import fr.antoineaube.chameleon.core.pictures.structures.Pixel;

import java.util.EnumMap;
import java.util.Map;

public class SimpleTestPixel implements Pixel {

    private final ChannelColour[] orderedChannels;
    private final Map<ChannelColour, Integer> coloursValues;

    public SimpleTestPixel(ChannelColour[] orderedChannels) {
        this.orderedChannels = orderedChannels;

        coloursValues = new EnumMap<>(ChannelColour.class);
        for (ChannelColour channel : orderedChannels) {
            coloursValues.put(channel, 0);
        }
    }

    @Override
    public ChannelColour[] getOrderedChannels() {
        return orderedChannels;
    }

    @Override
    public int getColourValue(ChannelColour colour) {
        return coloursValues.get(colour);
    }

    @Override
    public void setColourValue(ChannelColour colour, int newValue) {
        coloursValues.put(colour, newValue);
    }
}
