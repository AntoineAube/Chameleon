package fr.antoineaube.chameleon.pictures.implementations.buffered;

import fr.antoineaube.chameleon.core.pictures.PictureSaver;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class BufferedImagePictureSaver extends PictureSaver<BufferedImagePicture> {

    private static final String[] AVAILABLE_OUTPUT_FORMATS = {"PNG", "BMP"};

    protected BufferedImagePictureSaver(BufferedImagePicture picture) {
        super(picture);
    }

    @Override
    protected String[] getAvailableOutputFormat() {
        return AVAILABLE_OUTPUT_FORMATS;
    }

    @Override
    protected void safeSaveAs(String outputFormat, File location) throws IOException {
        ImageIO.write(getPicture().getImage(), outputFormat, location);
    }
}
