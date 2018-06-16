package fr.antoineaube.chameleon.gui.controllers.reveal;

import fr.antoineaube.chameleon.gui.controllers.ProcessDialog;
import fr.antoineaube.chameleon.gui.models.RevealInformation;

public class RevealDialog extends ProcessDialog<RevealInformation, RevealDialogController> {

    public RevealDialog() {
        super("fxml/RevealDialog.fxml");
    }
}
