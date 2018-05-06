package fr.antoineaube.chameleon.core.processes.concealments;

import java.io.IOException;
import java.io.InputStream;

public class BitInputStream extends InputStream {

    private final InputStream initialStream;
    private int currentByte;
    private int bitsOfCurrentByte;

    public BitInputStream(InputStream initialStream) {
        this.initialStream = initialStream;

        // Begin at 8 so that the first "read()" reads the first byte of the stream.
        bitsOfCurrentByte = Byte.SIZE;
    }

    @Override
    public int read() throws IOException {
        if (bitsOfCurrentByte == Byte.SIZE) {
            bitsOfCurrentByte = 0;
            currentByte = initialStream.read();
        }


        int shifted = currentByte >> (Byte.SIZE - 1 - bitsOfCurrentByte++);

        return shifted & 1;
    }

    @Override
    public int available() throws IOException {
        return Byte.SIZE * initialStream.available() + Byte.SIZE - bitsOfCurrentByte;
    }

    @Override
    public void close() throws IOException {
        initialStream.close();
    }
}
