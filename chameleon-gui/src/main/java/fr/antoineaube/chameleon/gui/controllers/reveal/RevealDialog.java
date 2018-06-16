package fr.antoineaube.chameleon.gui.controllers.reveal;

import fr.antoineaube.chameleon.gui.models.RevealInformation;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class RevealDialog extends Dialog<RevealInformation> {

    private static final Logger LOGGER = LogManager.getLogger(RevealDialog.class);

    public RevealDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/RevealDialog.fxml"));
            Parent root = loader.load();

            RevealDialogController controller = loader.getController();

            getDialogPane().setContent(root);
            getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

            controller.setupApplyButton(getDialogPane().lookupButton(ButtonType.APPLY));

            setResultConverter(buttonType -> {
                if (buttonType == ButtonType.APPLY) {
                    return controller.getInformation();
                }

                return null;
            });

        } catch (IOException e) {
            LOGGER.error("Failed to initialize the reveal dialog.", e);
        }
    }
}
