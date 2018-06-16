package fr.antoineaube.chameleon.core.processes.concealments.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * We need {@code SequenceInputStream}
 */
public class EnhancedSequenceInputStream extends SequenceInputStream {

    private static final Logger LOGGER = LogManager.getLogger(EnhancedSequenceInputStream.class);

    private List<InputStream> streams;

    public EnhancedSequenceInputStream(InputStream first, InputStream second) {
        super(first, second);

        streams = new ArrayList<>();
        streams.add(first);
        streams.add(second);
    }

    @Override
    public int read() throws IOException {
        if (!streams.isEmpty() && streams.get(0).available() == 1) {
            streams.remove(0);
        }

        return super.read();
    }

    @Override
    public int available() {
        return streams.stream().map(stream -> {
            try {
                return stream.available();
            } catch (IOException e) {
                LOGGER.error("Error while reading available bits (" + stream.getClass() + ")", e);
                return 0;
            }
        }).reduce(Integer::sum).orElse(0);
    }
}
