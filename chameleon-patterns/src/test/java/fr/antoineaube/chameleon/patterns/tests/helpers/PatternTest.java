package fr.antoineaube.chameleon.patterns.tests.helpers;

import fr.antoineaube.chameleon.core.processes.ConcreteConcealmentPattern;

public abstract class PatternTest {

    public PatternTester pattern(ConcreteConcealmentPattern pattern) {
        return new PatternTester().pattern(pattern);
    }
}
