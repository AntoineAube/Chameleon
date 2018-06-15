package fr.antoineaube.chameleon.patterns.tests.helpers;

import fr.antoineaube.chameleon.core.pictures.structures.Position;
import fr.antoineaube.chameleon.core.processes.ConcreteConcealmentPattern;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PatternTester {

    private ConcreteConcealmentPattern pattern;
    private final List<Position> positions;

    public PatternTester() {
        positions = new ArrayList<>();
    }

    public PatternTester pattern(ConcreteConcealmentPattern pattern) {
        this.pattern = pattern;

        return this;
    }

    public PatternTester startsWith(int x, int y) {
        positions.clear();
        return then(x, y);
    }

    public PatternTester then(int x, int y) {
        positions.add(new Position(x, y));

        return this;
    }

    public void go() {
        for (Position position : positions) {
            assertEquals(position, pattern.nextPosition());
        }

        assertFalse(pattern.hasNext());
    }
}
