package fr.antoineaube.chameleon.core.pictures.exceptions;

public class UnavailableOutputFormatException extends Exception {

    private final String outputFormat;
    private final String[] availableOutputFormat;

    public UnavailableOutputFormatException(String outputFormat, String[] availableOutputFormat) {
        this.outputFormat = outputFormat;
        this.availableOutputFormat = availableOutputFormat;
    }

    public String getOutputFormat() {
        return outputFormat;
    }

    public String[] getAvailableOutputFormat() {
        return availableOutputFormat;
    }
}
