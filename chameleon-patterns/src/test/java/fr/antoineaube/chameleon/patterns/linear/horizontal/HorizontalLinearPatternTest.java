package fr.antoineaube.chameleon.patterns.linear.horizontal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HorizontalLinearPatternTest {

    @Test
    @DisplayName("Has right name")
    void hasRightName() {
        assertEquals("HORIZONTAL_LINEAR", new HorizontalLinearPattern().getName());
    }
}