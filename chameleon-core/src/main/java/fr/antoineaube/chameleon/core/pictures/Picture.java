package fr.antoineaube.chameleon.core.pictures;

import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;
import fr.antoineaube.chameleon.core.pictures.structures.Pixel;
import fr.antoineaube.chameleon.core.pictures.structures.Position;

public interface Picture {

    int getHeight();
    int getWidth();
    ChannelColour[] getColourChannels();
    Pixel getPixel(Position position);
    PictureSaver getSaver();
}
