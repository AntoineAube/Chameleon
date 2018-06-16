package fr.antoineaube.chameleon.pictures.implementations.buffered;

import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;
import fr.antoineaube.chameleon.core.pictures.structures.Pixel;
import fr.antoineaube.chameleon.core.pictures.structures.Position;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.URL;

import static fr.antoineaube.chameleon.core.pictures.structures.ChannelColour.BLUE;
import static fr.antoineaube.chameleon.core.pictures.structures.ChannelColour.GREEN;
import static fr.antoineaube.chameleon.core.pictures.structures.ChannelColour.RED;
import static org.junit.jupiter.api.Assertions.*;

class BufferedImagePictureTest {

    @Test
    void shouldCorrectlyReadABMPPicture() throws IOException {
        URL imageUrl = getClass().getClassLoader().getResource("images-samples/chameleon.bmp");

        assert imageUrl != null;

        Picture chameleonPicture = new BufferedImagePicture(ImageIO.read(imageUrl));

        assertEquals(350, chameleonPicture.getHeight());
        assertEquals(700, chameleonPicture.getWidth());

        ChannelColour[] expectedChannels = {BLUE, GREEN, RED};
        assertArrayEquals(expectedChannels, chameleonPicture.getColourChannels());

        Pixel pixel = chameleonPicture.getPixel(new Position(0, 0));
        assertNotNull(pixel);
        assertTrue(pixel instanceof BufferedImagePixel);
    }
}