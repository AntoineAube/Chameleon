package fr.antoineaube.chameleon.gui.controllers.conceal;

import fr.antoineaube.chameleon.gui.controllers.ProcessDialogController;
import fr.antoineaube.chameleon.gui.models.ConcealInformation;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.io.File;

public class ConcealDialogController extends ProcessDialogController<ConcealInformation> {

    private static final String NOT_CHOSEN = "Not chosen";

    private static final String[] AVAILABLE_FORMATS = {
            "bmp",
            "png"
    };

    private ConcealInformation information;

    @FXML
    private Label imageLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private Label outputLabel;

    @FXML
    private ComboBox<String> outputFormat;

    @FXML
    public void initialize() {
        information = new ConcealInformation();

        outputFormat.setItems(FXCollections.observableArrayList(AVAILABLE_FORMATS));
        outputFormat.valueProperty().bindBidirectional(information.outputFormatProperty());
    }

    public void chooseImage() {
        File chosen = getImage(imageLabel);

        information.setHideoutFile(chosen);

        if (chosen == null) {
            imageLabel.setText(NOT_CHOSEN);
        } else {
            imageLabel.setText(chosen.getName());
        }
    }

    public void chooseMessage() {
        File chosen = getFile(messageLabel);

        information.setMessage(chosen);

        if (chosen == null) {
            messageLabel.setText(NOT_CHOSEN);
        } else {
            messageLabel.setText(chosen.getName());
        }
    }

    public void chooseOutput() {
        File chosen = getFile(outputLabel);

        information.setOutput(chosen);

        if (chosen == null) {
            outputLabel.setText(NOT_CHOSEN);
        } else {
            outputLabel.setText(chosen.getName());
        }
    }

    public void setupApplyButton(Node proceedButton) {
        proceedButton.disableProperty().bind(
                Bindings.isNull(information.hideoutFileProperty()).or(
                        Bindings.isNull(information.messageProperty())).or(
                                Bindings.isNull(information.outputFormatProperty())).or(
                                        Bindings.isNull(information.outputProperty())
                ));
    }

    public ConcealInformation getInformation() {
        return information;
    }
}
