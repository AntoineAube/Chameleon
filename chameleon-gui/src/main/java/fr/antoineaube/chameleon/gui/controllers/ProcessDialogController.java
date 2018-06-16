package fr.antoineaube.chameleon.gui.controllers;

import javafx.scene.Node;

public interface ProcessDialogController<I> {

    void setupApplyButton(Node node);
    I getInformation();
}
