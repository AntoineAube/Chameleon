package fr.antoineaube.chameleon.core.configurations;

public class MagicNumber {

    private final byte[] content;

    public MagicNumber(byte[] content) {
        this.content = content;
    }

    public byte[] getContent() {
        return content;
    }
}
