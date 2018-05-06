package fr.antoineaube.chameleon.core.processes.revelations;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class BitOutputStreamTest {

    private static final List<WritingCase> WRITING_CASES = Arrays.asList(
            new WritingCase().withWrittenValues().withExpectedWritten(),
            new WritingCase().withWrittenValues(1).withExpectedWritten((byte) 0b10000000),
            new WritingCase().withWrittenValues(1, 1, 0, 1, 0).withExpectedWritten((byte) 0b11010000),
            new WritingCase().withWrittenValues(1, 1, 0, 1, 0, 1, 1, 0, 1, 0).withExpectedWritten((byte) 0b11010110, (byte) 0b10000000),
            new WritingCase().withWrittenValues(1, 1, 0, 1, 0, 1, 1, 0).withExpectedWritten((byte) 0b11010110)
    );

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
}