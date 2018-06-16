package fr.antoineaube.chameleon.patterns.linear.vertical;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VerticalLinearPatternTest {

    @Test
    @DisplayName("Has right name")
    void hasRightName() {
        assertEquals("VERTICAL_LINEAR", new VerticalLinearPattern().getName());
    }
}