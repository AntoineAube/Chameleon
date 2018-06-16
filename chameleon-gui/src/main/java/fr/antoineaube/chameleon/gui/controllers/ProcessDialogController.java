package fr.antoineaube.chameleon.gui.controllers;

import javafx.scene.Node;
import javafx.stage.FileChooser;

import java.io.File;

public abstract class ProcessDialogController<I> {

    public abstract void setupApplyButton(Node node);
    public abstract I getInformation();

    protected File getImage(Node node) {
        FileChooser chooser = new FileChooser();

        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images files", "*.png", "*.bmp", "*.jpg"));

        return chooser.showOpenDialog(node.getScene().getWindow());
    }

    protected File getFile(Node node) {
        FileChooser chooser = new FileChooser();

        return chooser.showOpenDialog(node.getScene().getWindow());
    }
}
