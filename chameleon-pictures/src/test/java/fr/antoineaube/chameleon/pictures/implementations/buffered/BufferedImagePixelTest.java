package fr.antoineaube.chameleon.pictures.implementations.buffered;

import fr.antoineaube.chameleon.core.pictures.exceptions.UnavailableColourException;
import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;
import fr.antoineaube.chameleon.core.pictures.structures.Pixel;
import fr.antoineaube.chameleon.core.pictures.structures.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class BufferedImagePixelTest {

    private BufferedImage simpleImage;

    @BeforeEach
    void initialize() throws IOException {
        URL imageUrl = getClass().getClassLoader().getResource("images-samples/simple-rgb.png");

        assert imageUrl != null;

        simpleImage = ImageIO.read(imageUrl);
    }

    @DisplayName("Should recognize the pixel's available colours")
    @Test
    void shouldRecognizePixelAvailableColours() {
        Pixel pixel = new BufferedImagePixel(simpleImage, new Position(0, 0));

        ChannelColour[] expected = { ChannelColour.ALPHA, ChannelColour.BLUE, ChannelColour.GREEN, ChannelColour.RED };

        assertArrayEquals(expected, pixel.getOrderedChannels());
    }

    @DisplayName("Should read one pixel's colours")
    @Test
    void shouldReadOnePixelsColours() {
        Pixel pixel = new BufferedImagePixel(simpleImage, new Position(0, 1));

        assertEquals(255, pixel.getColourValue(ChannelColour.ALPHA));
        assertEquals(0, pixel.getColourValue(ChannelColour.BLUE));
        assertEquals(0, pixel.getColourValue(ChannelColour.GREEN));
        assertEquals(255, pixel.getColourValue(ChannelColour.RED));
    }

    @DisplayName("Should modify one pixel's colours")
    @Test
    void shouldModifyOnePixelsColours() {
        Pixel pixel = new BufferedImagePixel(simpleImage, new Position(0, 1));

        pixel.setColourValue(ChannelColour.BLUE, 2);
        pixel.setColourValue(ChannelColour.GREEN, 3);
        pixel.setColourValue(ChannelColour.RED, 253);

        assertEquals(255, pixel.getColourValue(ChannelColour.ALPHA));
        assertEquals(2, pixel.getColourValue(ChannelColour.BLUE));
        assertEquals(3, pixel.getColourValue(ChannelColour.GREEN));
        assertEquals(253, pixel.getColourValue(ChannelColour.RED));
    }

    @DisplayName("Should fail to peek an unavailable colour")
    @Test
    void shouldFailToPeekAnUnavailableColour() {
        assertThrows(UnavailableColourException.class, () -> {
            Pixel pixel = new BufferedImagePixel(simpleImage, new Position(0, 1));

            pixel.getColourValue(null);
        });
    }

    @DisplayName("Should fail to save an unavailable colour")
    @Test
    void shouldFailToSaveAnUnavailableColour() {
        assertThrows(UnavailableColourException.class, () -> {
            Pixel pixel = new BufferedImagePixel(simpleImage, new Position(0, 1));

            pixel.setColourValue(null, 3);
        });
    }
}