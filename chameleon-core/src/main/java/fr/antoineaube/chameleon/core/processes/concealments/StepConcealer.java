package fr.antoineaube.chameleon.core.processes.concealments;

import fr.antoineaube.chameleon.core.configurations.ConcealmentStep;
import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.pictures.structures.Pixel;
import fr.antoineaube.chameleon.core.processes.ConcreteConcealmentPattern;

import java.io.IOException;

public class StepConcealer {

    private final Picture hideout;
    private final ConcealmentStep step;
    private final BitInputStream message;
    private final int mask;

    public StepConcealer(Picture hideout, ConcealmentStep step, BitInputStream message) {
        this.hideout = hideout;
        this.step = step;
        this.message = message;

        mask = computeMask();
    }

    public void process() throws IOException {
        if (message.available() == 0) {
            return;
        }

        ConcreteConcealmentPattern pattern = step.getFollowedPattern().createPattern(hideout, step.isReversed());

        while (message.available() > 0 && pattern.hasNext()) {
            int valueToMerge = nextValueToMerge();
            Pixel currentPixel = hideout.getPixel(pattern.nextPosition());

            int currentValue = currentPixel.getColourValue(step.getChannel());

            currentPixel.setColourValue(step.getChannel(), mergeValues(currentValue, valueToMerge));
        }
    }

    int mergeValues(int initialValue, int valueToMerge) {
        return (initialValue & ~mask) + valueToMerge;
    }

    int nextValueToMerge() throws IOException {
        int neededBits = step.getBitsNumber();

        int value = 0;
        for (int i = 0; i < neededBits; i++) {
            int nextBit = message.available() == 0 ? 0 : message.read() << i;

            value += nextBit;
        }

        return value;
    }

    /**
     * For example with "number of bits = 3", the mask is "0b00000111".
     * @return The mask of the step configuration.
     */
    int computeMask() {
        return ~(~0 << step.getBitsNumber()) & 0xFF;
    }
}
