package fr.antoineaube.chameleon.pictures.implementations.buffered;

import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.stream.Stream;

import static fr.antoineaube.chameleon.core.pictures.structures.ChannelColour.*;
import static java.awt.image.BufferedImage.*;
import static org.junit.jupiter.api.Assertions.*;

class AWTColourSpaceTranslatorTest {

    private static final ChannelColour[] RGB = {RED, GREEN, BLUE};
    private static final ChannelColour[] BGR = {BLUE, GREEN, RED};
    private static final ChannelColour[] ABGR = {ALPHA, BLUE, GREEN, RED};
    private static final ChannelColour[] ARGB = {ALPHA, RED, GREEN, BLUE};
    private static final ChannelColour[] GRAY_SPACE = {GRAY};
    private static final ChannelColour[] EMPTY_SPACE = {};

    private Stream<DynamicTest> buildDynamicTests(String spaceName, ChannelColour[] spaceContent, Integer[] codes) {
        return Arrays.stream(codes).map(code -> DynamicTest.dynamicTest(code + " is " + spaceName,
                () -> assertArrayEquals(spaceContent, AWTColourSpaceTranslator.findByCode(code))));
    }

    @DisplayName("Should translate RGB colour spaces")
    @TestFactory
    Stream<DynamicTest> shouldTranslateRGBColourSpaces() {
        return buildDynamicTests("RGB", RGB, new Integer[]{TYPE_INT_RGB, TYPE_USHORT_555_RGB, TYPE_USHORT_565_RGB});
    }

    @DisplayName("Should translate BGR colour spaces")
    @TestFactory
    Stream<DynamicTest> shouldTranslateBGRColourSpaces() {
        return buildDynamicTests("BGR", BGR, new Integer[]{TYPE_3BYTE_BGR, TYPE_INT_BGR});
    }

    @DisplayName("Should translate ABGR colour spaces")
    @TestFactory
    Stream<DynamicTest> shouldTranslateABGRColourSpaces() {
        return buildDynamicTests("ABGR", ABGR, new Integer[]{TYPE_4BYTE_ABGR, TYPE_4BYTE_ABGR_PRE});
    }

    @DisplayName("Should translate ARGB colour spaces")
    @TestFactory
    Stream<DynamicTest> shouldTranslateARGBColourSpaces() {
        return buildDynamicTests("ABGR", ARGB, new Integer[]{TYPE_INT_ARGB, TYPE_INT_ARGB_PRE});
    }

    @DisplayName("Should translate gray colour spaces")
    @TestFactory
    Stream<DynamicTest> shouldTranslateGrayColourSpaces() {
        return buildDynamicTests("GRAY", GRAY_SPACE, new Integer[]{TYPE_BYTE_GRAY, TYPE_USHORT_GRAY});
    }

    @DisplayName("Should translate special colour spaces")
    @TestFactory
    Stream<DynamicTest> shouldTranslateSpecialColourSpaces() {
        return buildDynamicTests("EMPTY", EMPTY_SPACE, new Integer[]{TYPE_BYTE_BINARY, TYPE_BYTE_INDEXED, TYPE_CUSTOM});
    }
}