package fr.antoineaube.chameleon.core.processes.concealments;

import fr.antoineaube.chameleon.core.configurations.MagicNumber;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.SequenceInputStream;

public class MagicNumberAppender {

    private final MagicNumber magicNumber;

    public MagicNumberAppender(MagicNumber magicNumber) {
        this.magicNumber = magicNumber;
    }

    public InputStream appendMagicNumber(InputStream input) {
        return new SequenceInputStream(input, new ByteArrayInputStream(magicNumber.getContent()));
    }
}
