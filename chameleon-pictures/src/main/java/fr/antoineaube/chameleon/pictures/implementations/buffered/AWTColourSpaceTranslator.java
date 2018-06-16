package fr.antoineaube.chameleon.pictures.implementations.buffered;

import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;

import static fr.antoineaube.chameleon.core.pictures.structures.ChannelColour.*;
import static java.awt.image.BufferedImage.*;
import static java.awt.image.BufferedImage.TYPE_CUSTOM;

public class AWTColourSpaceTranslator {

    private AWTColourSpaceTranslator() {
        // Not meant to be instantiated.
    }

    static ChannelColour[] findByCode(int code) {
        switch (code) {
            case TYPE_3BYTE_BGR:
            case TYPE_INT_BGR:
                return new ChannelColour[]{BLUE, GREEN, RED};

            case TYPE_4BYTE_ABGR:
            case TYPE_4BYTE_ABGR_PRE:
                return new ChannelColour[]{ALPHA, BLUE, GREEN, RED};

            case TYPE_INT_ARGB:
            case TYPE_INT_ARGB_PRE:
                return new ChannelColour[]{ALPHA, RED, GREEN, BLUE};


            case TYPE_INT_RGB:
            case TYPE_USHORT_555_RGB:
            case TYPE_USHORT_565_RGB:
                return new ChannelColour[]{RED, GREEN, BLUE};

            case TYPE_BYTE_GRAY:
            case TYPE_USHORT_GRAY:
                return new ChannelColour[]{GRAY};

            case TYPE_BYTE_BINARY:
            case TYPE_BYTE_INDEXED:
            case TYPE_CUSTOM:
            default:
                return new ChannelColour[]{};
        }
    }
}
