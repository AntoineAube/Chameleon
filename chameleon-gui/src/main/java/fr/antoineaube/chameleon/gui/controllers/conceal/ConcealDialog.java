package fr.antoineaube.chameleon.gui.controllers.conceal;

import fr.antoineaube.chameleon.gui.controllers.ProcessDialog;
import fr.antoineaube.chameleon.gui.models.ConcealInformation;

public class ConcealDialog extends ProcessDialog<ConcealInformation, ConcealDialogController> {

    public ConcealDialog() {
        super("fxml/ConcealDialog.fxml");
    }
}
