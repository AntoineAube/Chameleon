package fr.antoineaube.chameleon.core.processes.revelations;

import fr.antoineaube.chameleon.core.configurations.ConcealmentStep;
import fr.antoineaube.chameleon.core.configurations.MagicNumber;
import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;
import fr.antoineaube.chameleon.core.pictures.structures.Position;
import fr.antoineaube.chameleon.core.processes.test.helpers.PositionsPattern;
import fr.antoineaube.chameleon.core.processes.test.helpers.SimpleTestPicture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class StepRevealerTest {

    @DisplayName("Should compute the mask fitting the step")
    @Test
    void shouldComputeTheMask() {
        StepRevealer revealer = new StepRevealer(null, new ConcealmentStep(3, null, null, false), null);
        
        assertEquals(0b00000111, revealer.computeMask());
    }

    @DisplayName("Should extract the value according to the number of used bits per color")
    @Test
    void shouldExtractTheValue() {
        StepRevealer revealer = new StepRevealer(null, new ConcealmentStep(3, null, null, false), null);

        assertEquals(0b00000010, revealer.extractValue(0b11011010));
    }

    @DisplayName("Should read the message in the picture")
    @Test
    void shouldFindTheMessageInThePicture() throws IOException {
        // Preparation.
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

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        MagicNumberAlarmBitOutputStream alarm = new MagicNumberAlarmBitOutputStream(output, magicNumber);

        StepRevealer revealer = new StepRevealer(picture, step, alarm);
        revealer.process();

        assertArrayEquals(message.getBytes(), output.toByteArray());
    }
}