package fr.antoineaube.chameleon.core.configurations;

import java.util.Arrays;

public class ChameleonConfiguration {

    private final MagicNumber magicNumber;
    private final ConcealmentStep[] steps;

    public ChameleonConfiguration(MagicNumber magicNumber, ConcealmentStep[] steps) {
        this.magicNumber = magicNumber;
        this.steps = steps;
    }

    public MagicNumber getMagicNumber() {
        return magicNumber;
    }

    public ConcealmentStep[] getSteps() {
        return steps;
    }

    @Override
    public String toString() {
        return "ChameleonConfiguration{" +
                "magicNumber=" + magicNumber +
                ", steps=" + Arrays.toString(steps) +
                '}';
    }
}
