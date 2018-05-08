package fr.antoineaube.chameleon.core.processes.revelations;

import java.io.IOException;
import java.io.OutputStream;

public class BitOutputStream extends OutputStream {

    private static final byte MASK = 0b00000001;

    protected final OutputStream initialStream;
    private byte currentByte;
    private int currentByteSize;

    public BitOutputStream(OutputStream initialStream) {
        this.initialStream = initialStream;
        currentByte = 0;
        currentByteSize = 0;
    }

    @Override
    public void write(int i) throws IOException {
        assert i == 0 || i == 1;

        byte masked = (byte) (i & MASK);

        currentByte = (byte) (currentByte << 1 | masked & 0x00FF);
        currentByteSize++;

        if (currentByteSize == 8) {
            initialStream.write(currentByte);

            currentByte = 0;
            currentByteSize = 0;
        }
    }

    @Override
    public void close() throws IOException {
        while (currentByteSize != 0) {
            write((byte) 0);
        }

        initialStream.close();
    }
}
