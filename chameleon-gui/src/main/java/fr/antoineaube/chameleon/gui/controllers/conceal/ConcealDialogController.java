package fr.antoineaube.chameleon.gui.controllers.conceal;

import fr.antoineaube.chameleon.gui.models.ConcealInformation;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;

public class ConcealDialogController {

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

    public void chooseMessage() {
        FileChooser chooser = new FileChooser();
        File chosen = chooser.showOpenDialog(messageLabel.getScene().getWindow());

        information.setMessage(chosen);

        if (chosen == null) {
            messageLabel.setText("Not chosen");
        } else {
            messageLabel.setText(chosen.getName());
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
                        Bindings.isNull(information.messageProperty())).or(
                                Bindings.isNull(information.outputFormatProperty())).or(
                                        Bindings.isNull(information.outputProperty())
                ));
    }

    public ConcealInformation getInformation() {
        return information;
    }
}
