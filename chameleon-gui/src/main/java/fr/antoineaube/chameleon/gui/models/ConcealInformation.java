package fr.antoineaube.chameleon.gui.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;

public class ConcealInformation {

    private final ObjectProperty<File> hideoutFile;
    private final StringProperty outputFormat;
    private final ObjectProperty<File> message;
    private final ObjectProperty<File> output;

    public ConcealInformation() {
        hideoutFile = new SimpleObjectProperty<>();
        outputFormat = new SimpleStringProperty();
        message = new SimpleObjectProperty<>();
        output = new SimpleObjectProperty<>();
    }

    public File getOutput() {
        return output.get();
    }

    public ObjectProperty<File> outputProperty() {
        return output;
    }

    public void setOutput(File output) {
        this.output.set(output);
    }

    public File getHideoutFile() {
        return hideoutFile.get();
    }

    public ObjectProperty<File> hideoutFileProperty() {
        return hideoutFile;
    }

    public void setHideoutFile(File hideoutFile) {
        this.hideoutFile.set(hideoutFile);
    }

    public String getOutputFormat() {
        return outputFormat.get();
    }

    public StringProperty outputFormatProperty() {
        return outputFormat;
    }

    public void setOutputFormat(String outputFormat) {
        this.outputFormat.set(outputFormat);
    }

    public File getMessage() {
        return message.get();
    }

    public ObjectProperty<File> messageProperty() {
        return message;
    }

    public void setMessage(File message) {
        this.message.set(message);
    }
}
