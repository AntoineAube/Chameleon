package fr.antoineaube.chameleon.core.processes.concealments;

import fr.antoineaube.chameleon.core.configurations.MagicNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Bit input stream")
class BitInputStreamTest {

    private static final List<String> STRING_CONTENTS = Arrays.asList("", "a", "abc", "abcd");

    @DisplayName("Should find the correct number of available bits")
    @TestFactory
    Stream<DynamicTest> shouldFindTheCorrectNumberOfAvailableBits() {
        return STRING_CONTENTS.stream().flatMap(content -> {
           List<DynamicTest> relatedTests = new ArrayList<>();

           int maximumSize = content.length() * Byte.SIZE;

           for (int i = 0; i <= maximumSize; i++) {
               final int readings = i;
               String caseName = "With content '" + content + "' and after " + readings + " readings";
               relatedTests.add(DynamicTest.dynamicTest(caseName, () -> {
                   InputStream byteStream = new ByteArrayInputStream(content.getBytes());

                   BitInputStream input = new BitInputStream(byteStream);

                   for (int j = 0; j < readings; j++) {
                       input.read();
                   }

                   assertEquals(maximumSize - readings, input.available());
               }));
           }

           return relatedTests.stream();
        });
    }

    @DisplayName("Should read the bits of two bytes")
    @Test
    void shouldReadTheBitsOfTwoBytes() throws IOException {
        byte[] bytes = {(byte) 0b11001100, (byte) 0b00110011};
        InputStream byteStream = new ByteArrayInputStream(bytes);

        BitInputStream input = new BitInputStream(byteStream);

        assertEquals(1, input.read());
        assertEquals(1, input.read());
        assertEquals(0, input.read());
        assertEquals(0, input.read());
        assertEquals(1, input.read());
        assertEquals(1, input.read());
        assertEquals(0, input.read());
        assertEquals(0, input.read());

        assertEquals(0, input.read());
        assertEquals(0, input.read());
        assertEquals(1, input.read());
        assertEquals(1, input.read());
        assertEquals(0, input.read());
        assertEquals(0, input.read());
        assertEquals(1, input.read());
        assertEquals(1, input.read());
    }

    /**
     * Bug reproduction:
     * - 1-byte message, 1-byte magic number.
     * - One read 3 bits, then 5 bits, then 4 bits, then 4 bits.
     * - It seems that after the 3 and 5 readings, the available() method returns 0.
     *
     * Update: the problem was due to SequenceInputStream (SequenceInputStream::available returns
     * the available bytes on the current stream).
     */
    @DisplayName("Should still have available bytes after a partial reading (stream with appended magic number)")
    @Test
    void shouldStillHaveAvailableAfterPartialReadingFromConcatenatedStream() throws IOException {
        byte[] message = { 0b1101 };

        byte[] magicNumber = { 0b110 };
        InputStream concatenated = new MagicNumberAppender(new MagicNumber(magicNumber)).appendMagicNumber(new ByteArrayInputStream(message));

        BitInputStream input = new BitInputStream(concatenated);

        assertEquals(16, input.available());

        assertEquals(0, input.read());
        assertEquals(0, input.read());
        assertEquals(0, input.read());

        assertEquals(13, input.available());

        assertEquals(0, input.read());
        assertEquals(1, input.read());
        assertEquals(1, input.read());
        assertEquals(0, input.read());
        assertEquals(1, input.read());

        assertEquals(8, input.available());

        assertEquals(0, input.read());
        assertEquals(0, input.read());
        assertEquals(0, input.read());
        assertEquals(0, input.read());

        assertEquals(0, input.read());
        assertEquals(1, input.read());
        assertEquals(1, input.read());
        assertEquals(0, input.read());

        assertEquals(0, input.available());
    }
}