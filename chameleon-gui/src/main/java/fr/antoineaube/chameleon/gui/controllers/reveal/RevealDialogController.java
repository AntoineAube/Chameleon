package fr.antoineaube.chameleon.gui.controllers.reveal;

import fr.antoineaube.chameleon.gui.controllers.ProcessDialogController;
import fr.antoineaube.chameleon.gui.models.RevealInformation;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;

import java.io.File;

public class RevealDialogController extends ProcessDialogController<RevealInformation> {

    private RevealInformation information;

    @FXML
    private Label imageLabel;

    @FXML
    private Label outputLabel;

    public void initialize() {
        information = new RevealInformation();
    }

    public void chooseImage() {
        File chosen = getImage(imageLabel);

        information.setHideoutFile(chosen);

        if (chosen == null) {
            imageLabel.setText("Not chosen");
        } else {
            imageLabel.setText(chosen.getName());
        }
    }

    public void chooseOutput() {
        File chosen = getFile(outputLabel);

        information.setOutput(chosen);

        if (chosen == null) {
            outputLabel.setText("Not chosen");
        } else {
            outputLabel.setText(chosen.getName());
        }
    }

    public void setupApplyButton(Node proceedButton) {
        proceedButton.disableProperty().bind(
                Bindings.isNull(information.hideoutFileProperty()).or(
                        Bindings.isNull(information.outputProperty())
                ));
    }

    public RevealInformation getInformation() {
        return information;
    }
}
