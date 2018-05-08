package fr.antoineaube.chameleon.core.processes.concealments;

import fr.antoineaube.chameleon.core.configurations.MagicNumber;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Magic number appender")
class MagicNumberAppenderTest {

    private static final MagicNumber MAGIC_NUMBER = new MagicNumber("World!".getBytes());

    private MagicNumberAppender appender;

    @BeforeEach
    void initialize() {
        appender = new MagicNumberAppender(MAGIC_NUMBER);
    }

    @DisplayName("Should append the magic number")
    @Test
    void shouldAppendTheMagicNumber() throws IOException {
        InputStream input = new ByteArrayInputStream("Hello, ".getBytes());

        InputStream concatenated = appender.appendMagicNumber(input);

        assertArrayEquals("Hello, World!".getBytes(), IOUtils.toByteArray(concatenated));
    }
}