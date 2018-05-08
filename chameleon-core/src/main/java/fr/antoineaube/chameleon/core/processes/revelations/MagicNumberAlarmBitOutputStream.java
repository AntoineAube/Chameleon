package fr.antoineaube.chameleon.core.processes.revelations;

import fr.antoineaube.chameleon.core.configurations.MagicNumber;

import java.io.IOException;
import java.io.OutputStream;

public class MagicNumberAlarmBitOutputStream extends BitOutputStream {

    private final MagicNumberAlarm alarm;
    private boolean hasFound;

    public MagicNumberAlarmBitOutputStream(OutputStream initialStream, MagicNumber magicNumber) {
        super(initialStream);
        this.alarm = new MagicNumberAlarm(magicNumber);

        hasFound = false;
    }

    @Override
    public void write(int i) throws IOException {
        if (hasFound) {
            return;
        }

        if (alarm.isFull()) {
            super.write(alarm.releaseBit());
        }

        alarm.acknowledgeBit(i);

        if (alarm.isMagicNumber()) {
            hasFound = true;
        }
    }

    public boolean hasFoundMagicNumber() {
        return hasFound;
    }

    @Override
    public void close() throws IOException {
        initialStream.close();
    }
}
