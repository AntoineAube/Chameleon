package fr.antoineaube.chameleon.pictures.implementations.buffered;

import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.pictures.PictureSaver;
import fr.antoineaube.chameleon.core.pictures.exceptions.UnavailableOutputFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Buffered image picture saver")
class BufferedImagePictureSaverTest {

    private File temporaryFile;

    @BeforeEach
    void initialize() throws IOException {
        temporaryFile = File.createTempFile("saved", ".tmp");
        temporaryFile.deleteOnExit();
    }

    @DisplayName("Should accept BMP and PNG formats, not matter the case")
    @Test
    void shouldAcceptOutputFormat() {
        PictureSaver saver = new BufferedImagePictureSaver(null);

        assertTrue(saver.acceptsOutputFormat("bmp"));
        assertTrue(saver.acceptsOutputFormat("pnG"));
        assertTrue(saver.acceptsOutputFormat("PNG"));
    }

    @DisplayName("Should not accept formats which are not BMP or PNG")
    @Test
    void shouldNotAcceptOutputFormat() {
        PictureSaver saver = new BufferedImagePictureSaver(null);

        assertFalse(saver.acceptsOutputFormat("jpeg"));
    }

    @Test
    void shouldSaveAPictureAsPNG() throws IOException, UnavailableOutputFormatException {
        URL imageUrl = getClass().getClassLoader().getResource("images-samples/chameleon.bmp");
        assert imageUrl != null;
        Picture chameleonPicture = new BufferedImagePicture(ImageIO.read(imageUrl));

        chameleonPicture.getSaver().saveAs("PNG", temporaryFile);

        checkFormat("png");
    }

    @Test
    void shouldSaveAPictureAsBMP() throws IOException, UnavailableOutputFormatException {
        URL imageUrl = getClass().getClassLoader().getResource("images-samples/chameleon.bmp");
        assert imageUrl != null;
        Picture chameleonPicture = new BufferedImagePicture(ImageIO.read(imageUrl));

        chameleonPicture.getSaver().saveAs("BMP", temporaryFile);

        checkFormat("bmp");
    }

    private void checkFormat(String expectedFormat) throws IOException {
        ImageInputStream iis = ImageIO.createImageInputStream(temporaryFile);
        ImageReader reader = ImageIO.getImageReaders(iis).next();

        assertEquals(expectedFormat, reader.getFormatName());
    }
}