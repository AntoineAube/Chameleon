package fr.antoineaube.chameleon.core.processes.concealments;

import fr.antoineaube.chameleon.core.configurations.ChameleonConfiguration;
import fr.antoineaube.chameleon.core.configurations.ConcealmentStep;
import fr.antoineaube.chameleon.core.configurations.MagicNumber;
import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;
import fr.antoineaube.chameleon.core.pictures.structures.Position;
import fr.antoineaube.chameleon.core.processes.test.helpers.SimpleTestPicture;
import fr.antoineaube.chameleon.core.processes.test.helpers.SinglePositionPattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConcealerTest {

    private Concealer concealer;

    @BeforeEach
    void initialize() {
        byte[] magicNumber = { 0b110 };

        // Is able to fully hide a 1 byte message.
        ConcealmentStep[] steps = {
                new ConcealmentStep(3, ChannelColour.RED, new SinglePositionPattern(new Position(0, 0)), false),
                new ConcealmentStep(5, ChannelColour.RED, new SinglePositionPattern(new Position(0, 1)), false),
                new ConcealmentStep(4, ChannelColour.BLUE, new SinglePositionPattern(new Position(1, 0)), false),
                new ConcealmentStep(4, ChannelColour.BLUE, new SinglePositionPattern(new Position(2, 0)), false)
        };

        ChameleonConfiguration configuration = new ChameleonConfiguration(new MagicNumber(magicNumber), steps);

        concealer = new Concealer(configuration);
    }

    @DisplayName("Should completely hide a short enough message")
    @Test
    void shouldCompletelyHideAShortEnoughMessage() throws IOException {
        Picture hideout = new SimpleTestPicture(3, 3);
        InputStream input = new ByteArrayInputStream(new byte[] { 0b1101 });

        concealer.process(input, hideout);

        // Full expected message: 00001101 00000110
        // In the pixels: 000 10110 0000 0110.
        // Let's check. We assert the equality because the initial value of these pixels is 0.

        assertEquals(0b000, hideout.getPixel(new Position(0, 0)).getColourValue(ChannelColour.RED));
        assertEquals(0b10110, hideout.getPixel(new Position(0, 1)).getColourValue(ChannelColour.RED), () -> Integer.toBinaryString(hideout.getPixel(new Position(0, 1)).getColourValue(ChannelColour.RED)));
        assertEquals(0b0000, hideout.getPixel(new Position(1, 0)).getColourValue(ChannelColour.BLUE));
        assertEquals(0b0110, hideout.getPixel(new Position(2, 0)).getColourValue(ChannelColour.BLUE));
    }
}