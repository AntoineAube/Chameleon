package fr.antoineaube.chameleon.core.processes.revelations;

import fr.antoineaube.chameleon.core.configurations.ChameleonConfiguration;
import fr.antoineaube.chameleon.core.configurations.ConcealmentStep;
import fr.antoineaube.chameleon.core.configurations.MagicNumber;
import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;
import fr.antoineaube.chameleon.core.pictures.structures.Position;
import fr.antoineaube.chameleon.core.processes.test.helpers.PositionsPattern;
import fr.antoineaube.chameleon.core.processes.test.helpers.SimpleTestPicture;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class RevealerTest {

    @Test
    void shouldFindMessageInOneStep() throws IOException {
        String message = "a"; // 01100001
        MagicNumber magicNumber = new MagicNumber("b".getBytes()); // 01100010

        SimpleTestPicture picture = new SimpleTestPicture(3, 3);
        picture.getPixel(new Position(0, 0)).setColourValue(ChannelColour.RED, 0b0110);
        picture.getPixel(new Position(0, 1)).setColourValue(ChannelColour.RED, 0b1000);
        picture.getPixel(new Position(0, 2)).setColourValue(ChannelColour.RED, 0b0110);
        picture.getPixel(new Position(1, 0)).setColourValue(ChannelColour.RED, 0b0100);

        PositionsPattern pattern = new PositionsPattern(
                new Position(0, 0),
                new Position(0, 1),
                new Position(0, 2),
                new Position(1, 0)
        );

        ConcealmentStep step = new ConcealmentStep(4, ChannelColour.RED, pattern, false);

        ChameleonConfiguration configuration = new ChameleonConfiguration(magicNumber, new ConcealmentStep[]{step});

        Revealer revealer = new Revealer(configuration);

        InputStream is = revealer.process(picture);

        assertArrayEquals(message.getBytes(), IOUtils.toByteArray(is));
    }

    @Test
    void shouldFindMessageInSeveralSteps() throws IOException {
        String message = "ac"; // 01100001 01100011
        MagicNumber magicNumber = new MagicNumber("b".getBytes()); // 01100010

        SimpleTestPicture picture = new SimpleTestPicture(3, 3);
        picture.getPixel(new Position(0, 0)).setColourValue(ChannelColour.RED, 0b110);
        picture.getPixel(new Position(0, 1)).setColourValue(ChannelColour.RED, 0b000);
        picture.getPixel(new Position(0, 2)).setColourValue(ChannelColour.RED, 0b010);
        picture.getPixel(new Position(1, 0)).setColourValue(ChannelColour.RED, 0b011);

        picture.getPixel(new Position(0, 0)).setColourValue(ChannelColour.BLUE, 0b100);
        picture.getPixel(new Position(0, 1)).setColourValue(ChannelColour.BLUE, 0b101);
        picture.getPixel(new Position(0, 2)).setColourValue(ChannelColour.BLUE, 0b001);
        picture.getPixel(new Position(1, 0)).setColourValue(ChannelColour.BLUE, 0b010);

        PositionsPattern pattern = new PositionsPattern(
                new Position(0, 0),
                new Position(0, 1),
                new Position(0, 2),
                new Position(1, 0)
        );

        ChameleonConfiguration configuration = new ChameleonConfiguration(magicNumber, new ConcealmentStep[]{
                new ConcealmentStep(3, ChannelColour.RED, pattern, false),
                new ConcealmentStep(3, ChannelColour.BLUE, pattern, false)});

        Revealer revealer = new Revealer(configuration);

        InputStream is = revealer.process(picture);

        assertArrayEquals(message.getBytes(), IOUtils.toByteArray(is));
    }
}