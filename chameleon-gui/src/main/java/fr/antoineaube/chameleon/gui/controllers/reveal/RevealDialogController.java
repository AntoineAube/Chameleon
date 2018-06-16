package fr.antoineaube.chameleon.gui.controllers.reveal;

import fr.antoineaube.chameleon.gui.controllers.ProcessDialogController;
import fr.antoineaube.chameleon.gui.models.RevealInformation;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;

public class RevealDialogController implements ProcessDialogController<RevealInformation> {

    private RevealInformation information;

    @FXML
    private Label imageLabel;

    @FXML
    private Label outputLabel;

    public void initialize() {
        information = new RevealInformation();
    }

    public void chooseImage() {
        FileChooser chooser = new FileChooser();

        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images files", "*.png", "*.bmp", "*.jpg"));

        File chosen = chooser.showOpenDialog(imageLabel.getScene().getWindow());

        information.setHideoutFile(chosen);

        if (chosen == null) {
            imageLabel.setText("Not chosen");
        } else {
            imageLabel.setText(chosen.getName());
        }
    }

    public void chooseOutput() {
        FileChooser chooser = new FileChooser();
        File chosen = chooser.showOpenDialog(outputLabel.getScene().getWindow());

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
