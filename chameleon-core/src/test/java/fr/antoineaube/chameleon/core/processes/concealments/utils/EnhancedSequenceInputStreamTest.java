package fr.antoineaube.chameleon.core.processes.concealments.utils;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Enhanced sequence input stream")
class EnhancedSequenceInputStreamTest {

    private String completeSentence;
    private EnhancedSequenceInputStream concatenated;

    @BeforeEach
    void initialize() {
        completeSentence = "Hello, World!";

        InputStream first = new ByteArrayInputStream("Hello, ".getBytes());
        InputStream second = new ByteArrayInputStream("World!".getBytes());

        concatenated = new EnhancedSequenceInputStream(first, second);
    }

    @DisplayName("Should concatenate streams (just like SequenceInputStream)")
    @Test
    void shouldConcatenateStreams() throws IOException {
        assertArrayEquals(completeSentence.getBytes(), IOUtils.toByteArray(concatenated));
    }

    @DisplayName("Should sum the available bytes of all streams")
    @Test
    void shouldSumTheAvailableOfAllStreams() throws IOException {
        assertEquals(completeSentence.length(), concatenated.available());
    }
}