package fr.antoineaube.chameleon.core.processes.concealments.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Arrays;
import java.util.List;

/**
 * We need {@code SequenceInputStream}
 */
public class EnhancedSequenceInputStream extends SequenceInputStream {

    private List<InputStream> streams;

    public EnhancedSequenceInputStream(InputStream first, InputStream second) {
        super(first, second);

        streams = Arrays.asList(first, second);
    }

    @Override
    public int available() throws IOException {
        return streams.stream().map(stream -> {
            try {
                return stream.available();
            } catch (IOException e) {
                // TODO Log this error.
                return 0;
            }
        }).reduce(Integer::sum).orElse(0);
    }
}
