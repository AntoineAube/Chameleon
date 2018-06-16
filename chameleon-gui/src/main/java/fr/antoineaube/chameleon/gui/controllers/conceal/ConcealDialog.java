package fr.antoineaube.chameleon.gui.controllers.conceal;

import fr.antoineaube.chameleon.gui.models.ConcealInformation;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ConcealDialog extends Dialog<ConcealInformation> {

    private static Logger LOGGER = LogManager.getLogger(ConcealDialog.class);

    public ConcealDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/ConcealDialog.fxml"));
            Parent root = loader.load();

            ConcealDialogController controller = loader.getController();

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
            LOGGER.error("Failed to initialize the conceal dialog.", e);
        }
    }
}
