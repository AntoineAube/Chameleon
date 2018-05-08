package fr.antoineaube.chameleon.core.configurations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@DisplayName("Magic number")
class MagicNumberTest {

    @DisplayName("Should translate into bits array")
    @Test
    void shouldTranslateIntoBitsArray() {
        byte[] bytes = {0b110, 0b1010}; // 00000110 00001010

        MagicNumber magicNumber = new MagicNumber(bytes);

        int[] bits = {0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0};

        assertArrayEquals(bits, magicNumber.asBitsArray());
    }
}