package fr.antoineaube.chameleon.core.processes.revelations;

import fr.antoineaube.chameleon.core.configurations.MagicNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Magic number alarm")
class MagicNumberAlarmTest {

    private MagicNumberAlarm alarm;

    @BeforeEach
    void initialize() {
        byte[] magicNumberContent = {0b110, 0b1101}; // 00000110 00001101

        alarm = new MagicNumberAlarm(new MagicNumber(magicNumberContent));
    }

    @DisplayName("Should be full when it contains as much bits as the magic number")
    @Test
    void shouldBecomeFull() {
        for (int i = 0; i < 16; i++) {
            assertFalse(alarm.isFull());

            alarm.acknowledgeBit(1);
        }

        assertTrue(alarm.isFull());
    }

    @DisplayName("Should release bits in FIFO order")
    @Test
    void shouldReleaseBitsInFifoOrder() {
        alarm.acknowledgeBit(1);
        alarm.acknowledgeBit(0);

        assertEquals(1, alarm.releaseBit());
        assertEquals(0, alarm.releaseBit());
    }

    @DisplayName("Should not recognize the magic number when this is not full")
    @Test
    void shouldNotRecognizeTheMagicNumberWhenNotFull() {
        alarm.acknowledgeBit(1);
        alarm.acknowledgeBit(0);

        assertFalse(alarm.isMagicNumber());
    }

    @DisplayName("Should not recognize the magic number when the given bits are different than those of the magic number")
    @Test
    void shouldNotRecognizeTheMagicNumberWhenNotEquals() {
        alarm.acknowledgeBit(1);
        alarm.acknowledgeBit(1);
        alarm.acknowledgeBit(1);
        alarm.acknowledgeBit(1);
        alarm.acknowledgeBit(1);
        alarm.acknowledgeBit(1);
        alarm.acknowledgeBit(1);
        alarm.acknowledgeBit(1);

        alarm.acknowledgeBit(1);
        alarm.acknowledgeBit(1);
        alarm.acknowledgeBit(1);
        alarm.acknowledgeBit(1);
        alarm.acknowledgeBit(1);
        alarm.acknowledgeBit(1);
        alarm.acknowledgeBit(1);
        alarm.acknowledgeBit(1);

        assertFalse(alarm.isMagicNumber());
    }

    @DisplayName("Should recognize the magic number when the given bits are the same as those of the magic number")
    @Test
    void shouldRecognizeTheMagicNumberWhenEquals() {
        alarm.acknowledgeBit(0);
        alarm.acknowledgeBit(0);
        alarm.acknowledgeBit(0);
        alarm.acknowledgeBit(0);
        alarm.acknowledgeBit(0);
        alarm.acknowledgeBit(1);
        alarm.acknowledgeBit(1);
        alarm.acknowledgeBit(0);

        alarm.acknowledgeBit(0);
        alarm.acknowledgeBit(0);
        alarm.acknowledgeBit(0);
        alarm.acknowledgeBit(0);
        alarm.acknowledgeBit(1);
        alarm.acknowledgeBit(1);
        alarm.acknowledgeBit(0);
        alarm.acknowledgeBit(1);

        assertTrue(alarm.isMagicNumber());
    }

    @DisplayName("Should fail to add neither 0 or 1")
    @Test
    void shouldFailToMisuseMagicNumberByAddingNotBit() {
        assertThrows(IllegalMagicNumberUse.class, () -> alarm.acknowledgeBit(5));
    }

    @DisplayName("Should fail to add more than authorized")
    @Test
    void shouldFailToMisuseMagicNumberByAddingMoreThanAuthorized() {
        for (int i = 0; i < 16; i++) {
            alarm.acknowledgeBit(1);
        }

        assertThrows(IllegalMagicNumberUse.class, () -> alarm.acknowledgeBit(1));
    }
}