package fr.antoineaube.chameleon.gui;

import fr.antoineaube.chameleon.core.configurations.ChameleonConfiguration;
import fr.antoineaube.chameleon.core.configurations.ConcealmentPattern;
import fr.antoineaube.chameleon.core.configurations.ConcealmentStep;
import fr.antoineaube.chameleon.core.configurations.MagicNumber;
import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.pictures.exceptions.UnavailableOutputFormatException;
import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;
import fr.antoineaube.chameleon.core.processes.concealments.Concealer;
import fr.antoineaube.chameleon.core.processes.revelations.Revealer;
import fr.antoineaube.chameleon.core.processes.verifications.VerificationException;
import fr.antoineaube.chameleon.patterns.linear.horizontal.HorizontalLinearPattern;
import fr.antoineaube.chameleon.pictures.implementations.buffered.BufferedImagePicture;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.io.*;

public class MainViewController {

    private static final int COLOUR_DEPTH = 8;
    private static final ObservableList<ConcealmentPattern> CONCEALMENT_PATTERNS = FXCollections.observableArrayList(
            new HorizontalLinearPattern()
    );

    private static ConcealmentStep defaultStep() {
        return new ConcealmentStep(2, null, null, false) {
            @Override
            public String toString() {
                String channelString = getChannel() == null ? "NO_CHANNEL" : getChannel().name();
                String patternString = getFollowedPattern() == null ? "NO_PATTERN" : getFollowedPattern().getName();
                String reversedString = isReversed() ? "REVERSED" : "NOT_REVERSED";

                return getBitsNumber() + " - " + channelString + " - " + patternString + " - " + reversedString;
            }
        };
    }

    @FXML
    private TextField stopWordField;

    @FXML
    private ListView<ConcealmentStep> stepsList;

    @FXML
    private Button removeStepButton;

    @FXML
    private Spinner<Integer> numberOfBitsSpinner;

    @FXML
    private ComboBox<ChannelColour> colourChannelCombo;

    @FXML
    private ComboBox<ConcealmentPattern> patternCombo;

    @FXML
    private GridPane stepsGrid;

    @FXML
    private CheckBox reversedCheckBox;

    private final Property<ConcealmentStep> currentStep = new SimpleObjectProperty<>();
    private final ObservableList<ConcealmentStep> describedPatterns = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        initializeStep();
    }

    private void initializeStep() {
        removeStepButton.disableProperty().bind(Bindings.isNull(stepsList.getSelectionModel().selectedItemProperty()));
        stepsGrid.disableProperty().bind(Bindings.isNull(stepsList.getSelectionModel().selectedItemProperty()));

        stepsList.setItems(describedPatterns);

        stepsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                currentStep.setValue(newValue);
            }

        });

        currentStep.addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                return;
            }

            numberOfBitsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, COLOUR_DEPTH, newValue.getBitsNumber()));
            colourChannelCombo.setValue(newValue.getChannel());
            patternCombo.setValue(newValue.getFollowedPattern());
            reversedCheckBox.setSelected(newValue.isReversed());
        });

        numberOfBitsSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (currentStep.getValue() != null) {
                ConcealmentStep step = currentStep.getValue();

                step.setBitsNumber(newValue);
                stepsList.refresh();
            }
        });

        colourChannelCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (currentStep.getValue() != null) {
                ConcealmentStep step = currentStep.getValue();

                step.setChannel(newValue);
                stepsList.refresh();
            }
        });

        patternCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (currentStep.getValue() != null) {
                ConcealmentStep step = currentStep.getValue();

                step.setFollowedPattern(newValue);
                stepsList.refresh();
            }
        });

        reversedCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (currentStep.getValue() != null) {
                ConcealmentStep step = currentStep.getValue();

                step.setReversed(newValue);
                stepsList.refresh();
            }
        });

        numberOfBitsSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, COLOUR_DEPTH, 2));

        colourChannelCombo.getItems().addAll(ChannelColour.values());

        patternCombo.setItems(CONCEALMENT_PATTERNS);
        patternCombo.setConverter(new StringConverter<ConcealmentPattern>() {
            @Override
            public String toString(ConcealmentPattern pattern) {
                return pattern.getName();
            }

            @Override
            public ConcealmentPattern fromString(String patternName) {
                for (ConcealmentPattern pattern : CONCEALMENT_PATTERNS) {
                    if (pattern.getName().equals(patternName)) {
                        return pattern;
                    }
                }

                return null;
            }
        });
    }

    public void addStep() {
        describedPatterns.add(defaultStep());
    }

    public void removeStep() {
        ConcealmentStep selectedStep = stepsList.getSelectionModel().getSelectedItem();

        if (selectedStep == null) {
            return;
        }

        describedPatterns.removeAll(selectedStep);
    }

    private ChameleonConfiguration getDescribedConfiguration() {
        MagicNumber magicNumber = new MagicNumber(stopWordField.getText().getBytes());

        ConcealmentStep[] steps = new ConcealmentStep[stepsList.getItems().size()];
        for (int i = 0; i < steps.length; i++) {
            steps[i] = stepsList.getItems().get(i);
        }

        return new ChameleonConfiguration(magicNumber, steps);
    }

    private Picture getHideout() throws IOException {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Select your image");

        File file = fileChooser.showOpenDialog(new Stage());

        if (file == null) {
            return null;
        }

        return new BufferedImagePicture(ImageIO.read(file));
    }

    private File getMessage() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Select your message");

        return fileChooser.showOpenDialog(new Stage());
    }

    private File getOutput() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Select the output file");

        return fileChooser.showOpenDialog(new Stage());
    }

    public void onConceal() throws IOException, UnavailableOutputFormatException {
        Picture hideout = getHideout();

        if (hideout == null) {
            return;
        }

        File message = getMessage();

        if (message == null) {
            return;
        }

        File output = getOutput();

        if (output == null) {
            return;
        }

        Concealer concealer = new Concealer(getDescribedConfiguration());

        Picture concealed = null;
        try {
            concealed = concealer.process(new FileInputStream(message), hideout);
        } catch (VerificationException e) {
            e.printStackTrace();
        }

        concealed.getSaver().saveAs("bmp", output);
    }

    public void onReveal() throws IOException {
        Picture hideout = getHideout();

        if (hideout == null) {
            return;
        }

        File output = getOutput();

        if (output == null) {
            return;
        }

        Revealer revealer = new Revealer(getDescribedConfiguration());
        InputStream revealed = null;
        try {
            revealed = revealer.process(hideout);
        } catch (VerificationException e) {
            e.printStackTrace();
        }

        IOUtils.copy(revealed, new FileOutputStream(output));
    }
}
