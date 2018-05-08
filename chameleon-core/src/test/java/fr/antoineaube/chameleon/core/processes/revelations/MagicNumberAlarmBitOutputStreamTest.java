package fr.antoineaube.chameleon.core.processes.revelations;

import fr.antoineaube.chameleon.core.configurations.MagicNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Magic number alarm stream")
class MagicNumberAlarmBitOutputStreamTest {

    private MagicNumber magicNumber;

    @BeforeEach
    void initialize() {
        byte[] bytes = {0b110}; // 00000110
        magicNumber = new MagicNumber(bytes);
    }

    @DisplayName("Should flush when the number of bits exceed the size of the magic number")
    @Test
    void shouldFlushWhenExceedingTheMagicNumberSize() throws IOException {
        CountingOutputStream mockedOutput = new CountingOutputStream();
        MagicNumberAlarmBitOutputStream stream = new MagicNumberAlarmBitOutputStream(mockedOutput, magicNumber);

        for (int i = 0; i < 16; i++) { // 16 == 8 for the size of the magic number + 8 for the mechanism of BitOutputStream to write in the stream.
            assertEquals(0, mockedOutput.count);
            stream.write(0);
        }

        assertEquals(1, mockedOutput.count);
    }

    @DisplayName("Should stop writing in the stream when the magic number has been found")
    @Test
    void shouldStopWritingWhenTheMagicNumberIsFound() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        MagicNumberAlarmBitOutputStream stream = new MagicNumberAlarmBitOutputStream(output, magicNumber);

        // Writing "a": 01100001
        stream.write(0);
        stream.write(1);
        stream.write(1);
        stream.write(0);
        stream.write(0);
        stream.write(0);
        stream.write(0);
        stream.write(1);

        // Writing the magic number: 00000110
        stream.write(0);
        stream.write(0);
        stream.write(0);
        stream.write(0);
        stream.write(0);
        stream.write(1);
        stream.write(1);
        stream.write(0);

        assertTrue(stream.hasFoundMagicNumber());
        assertArrayEquals("a".getBytes(), output.toByteArray());

        for (int i = 0; i < 100; i++) {
            stream.write(1);
        }

        // Nothing changed.
        assertArrayEquals("a".getBytes(), output.toByteArray());
    }


    private static class CountingOutputStream extends OutputStream {

        int count = 0;

        @Override
        public void write(int i) throws IOException {
            count++;
        }
    }
}