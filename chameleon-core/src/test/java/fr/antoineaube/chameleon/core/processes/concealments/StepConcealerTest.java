package fr.antoineaube.chameleon.core.processes.concealments;

import fr.antoineaube.chameleon.core.configurations.ConcealmentStep;
import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;
import fr.antoineaube.chameleon.core.pictures.structures.Pixel;
import fr.antoineaube.chameleon.core.pictures.structures.Position;
import fr.antoineaube.chameleon.core.processes.test.helpers.SimpleTestPicture;
import fr.antoineaube.chameleon.core.processes.test.helpers.SinglePositionPattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class StepConcealerTest {

    private Picture hideout;
    private StepConcealer concealer;

    @BeforeEach
    void initialize() {
        hideout = new SimpleTestPicture(3, 3);
        ConcealmentStep step = new ConcealmentStep(2, ChannelColour.RED, new SinglePositionPattern(new Position(0, 0)), false);

        byte[] values = {(byte) 0b11101011, (byte) 0b10010010};
        BitInputStream input = new BitInputStream(new ByteArrayInputStream(values));

        concealer = new StepConcealer(hideout, step, input);
    }

    @Test
    void shouldBuildTheCorrectMask() {
        assertEquals(0b00000011, concealer.computeMask());
    }

    @Test
    void shouldMergeTheValues() {
        assertEquals(0b00000011, concealer.mergeValues(0b00000000, 0b11));
    }

    @Test
    void shouldComputeTheNextValue() throws IOException {
        assertEquals(0b11, concealer.nextValueToMerge());
        assertEquals(0b01, concealer.nextValueToMerge());
        assertEquals(0b01, concealer.nextValueToMerge());
        assertEquals(0b11, concealer.nextValueToMerge());

        assertEquals(0b01, concealer.nextValueToMerge());
        assertEquals(0b10, concealer.nextValueToMerge());
        assertEquals(0b00, concealer.nextValueToMerge());
        assertEquals(0b01, concealer.nextValueToMerge());

        assertEquals(0b00, concealer.nextValueToMerge());
    }

    @Test
    void shouldCorrectlyModifyABlankPicture() throws IOException {
        concealer.process();

        Pixel modifiedPixel = hideout.getPixel(new Position(0, 0));

        assertEquals(0b11, modifiedPixel.getColourValue(ChannelColour.RED));
    }
}