package fr.antoineaube.chameleon.core.processes.revelations;

import fr.antoineaube.chameleon.core.configurations.ConcealmentStep;
import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.pictures.structures.Pixel;
import fr.antoineaube.chameleon.core.processes.ConcreteConcealmentPattern;

import java.io.IOException;

public class StepRevealer {

    private final Picture hideout;
    private final ConcealmentStep step;
    private final MagicNumberAlarmBitOutputStream message;
    private final int mask;

    public StepRevealer(Picture hideout, ConcealmentStep step, MagicNumberAlarmBitOutputStream message) {
        this.hideout = hideout;
        this.step = step;
        this.message = message;

        mask = computeMask();
    }

    public void process() throws IOException {
        ConcreteConcealmentPattern pattern = step.getFollowedPattern().createPattern(hideout, step.isReversed());

        while (pattern.hasNext() && !message.hasFoundMagicNumber()) {
            Pixel currentPixel = hideout.getPixel(pattern.nextPosition());

            int extracted = extractValue(currentPixel.getColourValue(step.getChannel()));

            for (int i = 0; i < step.getBitsNumber(); i++) {
                int bit = (extracted >> i) & 0b00000001;

                message.write(bit);

                if (message.hasFoundMagicNumber()) {
                    break;
                }
            }
        }
    }

    int extractValue(int initialValue) {
        return initialValue & mask;
    }

    /**
     * For example with "number of bits = 3", the mask is "0b00000111".
     *
     * @return The mask of the step configuration.
     */
    int computeMask() {
        return ~(~0 << step.getBitsNumber()) & 0xFF;
    }
}
