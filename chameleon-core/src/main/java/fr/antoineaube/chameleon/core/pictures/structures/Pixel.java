package fr.antoineaube.chameleon.core.pictures.structures;

import java.util.EnumMap;
import java.util.Map;

public class Pixel {

    private final ChannelColour[] orderedChannels;
    private final Map<ChannelColour, Integer> coloursValues;

    public Pixel(ChannelColour[] orderedChannels) {
        this.orderedChannels = orderedChannels;
        coloursValues = new EnumMap<>(ChannelColour.class);
    }

    public ChannelColour[] getOrderedChannels() {
        return orderedChannels;
    }

    public Map<ChannelColour, Integer> getColoursValues() {
        return coloursValues;
    }
}
