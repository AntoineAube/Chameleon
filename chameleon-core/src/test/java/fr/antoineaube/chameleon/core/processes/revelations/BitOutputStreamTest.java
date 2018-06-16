package fr.antoineaube.chameleon.core.processes.revelations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Bit output stream")
class BitOutputStreamTest {

    private static final List<WritingCase> WRITING_CASES = Arrays.asList(
            new WritingCase().withWrittenValues().withExpectedWritten(),
            new WritingCase().withWrittenValues(1).withExpectedWritten((byte) 0b10000000),
            new WritingCase().withWrittenValues(1, 1, 0, 1, 0).withExpectedWritten((byte) 0b11010000),
            new WritingCase().withWrittenValues(1, 1, 0, 1, 0, 1, 1, 0, 1, 0).withExpectedWritten((byte) 0b11010110, (byte) 0b10000000),
            new WritingCase().withWrittenValues(1, 1, 0, 1, 0, 1, 1, 0).withExpectedWritten((byte) 0b11010110),
            new WritingCase().withWrittenValues(0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 0).withExpectedWritten((byte) 0b01100001, (byte) 0b01100010)
    );

    @DisplayName("Should write bits")
    @TestFactory
    Stream<DynamicTest> shouldWriteBits() {
        return WRITING_CASES.stream().map(writingCase -> {
            String caseName = "Writing " + Arrays.toString(writingCase.writtenValues);

            return DynamicTest.dynamicTest(caseName, () -> {
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

                BitOutputStream output = new BitOutputStream(byteStream);

                for (int value : writingCase.writtenValues) {
                    output.write(value);
                }

                output.close();

                byte[] written = byteStream.toByteArray();

                assertArrayEquals(writingCase.expectedWritten, written);
            });
        });
    }

    @DisplayName("Write several bits once")
    @Test
    void writeSeveralBitsOnce() throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

        BitOutputStream output = new BitOutputStream(byteStream);
        output.write(new byte[] {1, 1, 0, 1, 1, 1, 1, 0}, 0, 8);
        output.close();

        byte[] written = byteStream.toByteArray();
        assertArrayEquals(new byte[] {(byte) 0b11011110}, written);
    }

    private static class WritingCase {

        private int[] writtenValues;
        private byte[] expectedWritten;

        WritingCase withWrittenValues(int... writtenValues) {
            this.writtenValues = writtenValues;

            return this;
        }

        WritingCase withExpectedWritten(byte... expectedWritten) {
            this.expectedWritten = expectedWritten;

            return this;
        }
    }

    @DisplayName("Should fail if attempting to write neither 0 or 1")
    @Test
    void shouldFailIfWriteMoreThanBit() {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        BitOutputStream output = new BitOutputStream(byteStream);

        assertThrows(IOException.class, () -> output.write(5));
    }
}