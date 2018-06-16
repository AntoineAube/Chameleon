package fr.antoineaube.chameleon.core.processes.revelations;

import fr.antoineaube.chameleon.core.configurations.MagicNumber;

import java.util.Arrays;
import java.util.LinkedList;

public class MagicNumberAlarm {

    private final int[] magicNumberBits;
    private final LinkedList<Integer> queue;

    public MagicNumberAlarm(MagicNumber magicNumber) {
        this.magicNumberBits = magicNumber.asBitsArray();
        queue = new LinkedList<>();
    }

    public void acknowledgeBit(int bit) {
        if ((bit != 0 && bit != 1) || !isFull()) {
            return; // TODO Throw an exception.
        }

        queue.add(bit);
    }

    public boolean isMagicNumber() {
        return isFull() && Arrays.equals(magicNumberBits, queue.stream().mapToInt(i -> i).toArray());
    }

    public boolean isFull() {
        return queue.size() == magicNumberBits.length;
    }

    public int releaseBit() {
        return queue.remove();
    }
}
