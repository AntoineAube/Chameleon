package fr.antoineaube.chameleon.core.processes.concealments;

import fr.antoineaube.chameleon.core.configurations.MagicNumber;
import fr.antoineaube.chameleon.core.processes.concealments.utils.EnhancedSequenceInputStream;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MagicNumberAppender {

    private final MagicNumber magicNumber;

    public MagicNumberAppender(MagicNumber magicNumber) {
        this.magicNumber = magicNumber;
    }

    public InputStream appendMagicNumber(InputStream input) {
        return new EnhancedSequenceInputStream(input, new ByteArrayInputStream(magicNumber.getContent()));
    }
}
