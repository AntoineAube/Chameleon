package fr.antoineaube.chameleon.core.pictures;

import fr.antoineaube.chameleon.core.pictures.exceptions.UnavailableOutputFormatException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public abstract class PictureSaver<P extends Picture> {

    private final P picture;

    protected PictureSaver(P picture) {
        this.picture = picture;
    }

    protected P getPicture() {
        return picture;
    }

    /**
     * @return An array which contains the available output pictures formats. They are expected to be uppercase.
     */
    protected abstract String[] getAvailableOutputFormat();

    /**
     * Saves the picture at the given location.
     * The output format is safe so there is no need to throw an exception.
     * @param outputFormat The image format of the saved file. The string is uppercase.
     * @param location The location of the saved picture.
     * @throws IOException If this is not possible to save the picture in the given file.
     */
    protected abstract void safeSaveAs(String outputFormat, File location) throws IOException;

    /**
     * Saves the picture at the given location.
     * @param outputFormat The image format of the saved file.
     * @param location The location of the saved picture.
     * @throws IOException If this is not possible to save the picture in the given file.
     * @throws UnavailableOutputFormatException If the saver cannot save in the given output format.
     */
    public void saveAs(String outputFormat, File location) throws IOException, UnavailableOutputFormatException {
        if (!acceptsOutputFormat(outputFormat)) {
            throw new UnavailableOutputFormatException(outputFormat, getAvailableOutputFormat());
        }

        safeSaveAs(outputFormat.toUpperCase(), location);
    }

    public boolean acceptsOutputFormat(String outputFormat) {
        outputFormat = outputFormat.toUpperCase();

        return Arrays.asList(getAvailableOutputFormat()).contains(outputFormat);
    }
}
