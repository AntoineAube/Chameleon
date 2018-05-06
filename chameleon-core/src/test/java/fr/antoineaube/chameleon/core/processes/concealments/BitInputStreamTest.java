package fr.antoineaube.chameleon.core.processes.concealments;

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
}