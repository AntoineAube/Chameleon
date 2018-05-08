package fr.antoineaube.chameleon.core.configurations;

public class MagicNumber {

    private final byte[] content;

    public MagicNumber(byte[] content) {
        this.content = content;
    }

    public byte[] getContent() {
        return content;
    }

    public int[] asBitsArray() {
        int[] bits = new int[Byte.SIZE * content.length];

        for (int i = 0; i < content.length; i++) {
            byte b = content[i];

            for (int j = 0; j < Byte.SIZE; j++) {
                bits[i * Byte.SIZE + j] = (b >> (Byte.SIZE - 1 - j)) & 1;
            }
        }

        return bits;
    }
}
