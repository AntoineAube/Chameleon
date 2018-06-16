package fr.antoineaube.chameleon.gui.models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.File;

public class RevealInformation {

    private final ObjectProperty<File> hideoutFile;
    private final ObjectProperty<File> output;

    public RevealInformation() {
        hideoutFile = new SimpleObjectProperty<>();
        output = new SimpleObjectProperty<>();
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

    public File getOutput() {
        return output.get();
    }

    public ObjectProperty<File> outputProperty() {
        return output;
    }

    public void setOutput(File output) {
        this.output.set(output);
    }
}
