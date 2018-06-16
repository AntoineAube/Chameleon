package fr.antoineaube.chameleon.gui.controllers;

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
import fr.antoineaube.chameleon.gui.controllers.conceal.ConcealDialog;
import fr.antoineaube.chameleon.gui.controllers.reveal.RevealDialog;
import fr.antoineaube.chameleon.gui.models.StepModel;
import fr.antoineaube.chameleon.patterns.linear.horizontal.HorizontalLinearPattern;
import fr.antoineaube.chameleon.patterns.linear.vertical.VerticalLinearPattern;
import fr.antoineaube.chameleon.pictures.implementations.buffered.BufferedImagePicture;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class MainController {

    private static final int COLOUR_DEPTH = 8;

    private static final ConcealmentPattern[] AVAILABLE_PATTERNS = {
            new HorizontalLinearPattern(),
            new VerticalLinearPattern()
    };

    @FXML
    private TextField stopWord;

    @FXML
    private Button removeStepButton;

    @FXML
    private ListView<StepModel> stepsList;

    @FXML
    private GridPane stepEditionGrid;

    @FXML
    private Spinner<Integer> numberOfBits;

    @FXML
    private ComboBox<ChannelColour> colourChannel;

    @FXML
    private ComboBox<ConcealmentPattern> pattern;

    @FXML
    private CheckBox stepReversed;

    @FXML
    private Button concealButton;

    @FXML
    private Button revealButton;

    private ObservableList<StepModel> steps;
    private Property<StepModel> selectedStep;

    @FXML
    public void initialize() {
        stopWord.setText("Hello, World!");
        steps = FXCollections.observableArrayList();
        stepsList.setItems(steps);

        removeStepButton.disableProperty().bind(Bindings.isNull(stepsList.getSelectionModel().selectedItemProperty()));
        stepEditionGrid.disableProperty().bind(Bindings.isNull(stepsList.getSelectionModel().selectedItemProperty()));

        selectedStep = new SimpleObjectProperty<>();

        stepsList.setCellFactory(param -> new ListCell<StepModel>() {
                    @Override
                    protected void updateItem(StepModel item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setText("");
                        } else {
                            setText("Step #" + (steps.indexOf(item) + 1));
                        }
                    }
                }
        );

        stepsList.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> selectedStep.setValue(newValue)));

        selectedStep.addListener(((observable, oldValue, newValue) -> {
            if (newValue == null) {
                resetStepFields();
            } else {
                setupStepFields(newValue);
            }
        }));

        numberOfBits.valueProperty().addListener(((observable, oldValue, newValue) ->
                Optional.ofNullable(selectedStep.getValue()).ifPresent(step -> step.numberOfBitsProperty().setValue(newValue))));

        colourChannel.valueProperty().addListener(((observable, oldValue, newValue) ->
                Optional.ofNullable(selectedStep.getValue()).ifPresent(step -> step.channelColourProperty().setValue(newValue))));

        pattern.valueProperty().addListener(((observable, oldValue, newValue) ->
                Optional.ofNullable(selectedStep.getValue()).ifPresent(step -> step.patternProperty().setValue(newValue))));

        stepReversed.selectedProperty().addListener(((observable, oldValue, newValue) ->
                Optional.ofNullable(selectedStep.getValue()).ifPresent(step -> step.reversedProperty().setValue(newValue))));

        colourChannel.setItems(FXCollections.observableArrayList(ChannelColour.values()));

        pattern.setItems(FXCollections.observableArrayList(AVAILABLE_PATTERNS));
        pattern.setConverter(new StringConverter<ConcealmentPattern>() {
            @Override
            public String toString(ConcealmentPattern object) {
                return object.getName();
            }

            @Override
            public ConcealmentPattern fromString(String string) {
                for (ConcealmentPattern object : AVAILABLE_PATTERNS) {
                    if (object.getName().equals(string)) {
                        return object;
                    }
                }

                return null;
            }
        });

        concealButton.disableProperty().bind(Bindings.isEmpty(steps).or(Bindings.isEmpty(stopWord.textProperty())));
        revealButton.disableProperty().bind(Bindings.isEmpty(steps).or(Bindings.isEmpty(stopWord.textProperty())));
    }

    private void resetStepFields() {
        numberOfBits.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, COLOUR_DEPTH, 0));
        colourChannel.setValue(null);
        pattern.setValue(null);
        stepReversed.setSelected(false);
    }

    private void setupStepFields(StepModel step) {
        numberOfBits.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, COLOUR_DEPTH, step.getNumberOfBits()));
        colourChannel.setValue(step.getChannelColour());
        pattern.setValue(step.getPattern());
        stepReversed.setSelected(step.isReversed());
    }

    public void addStep() {
        steps.add(new StepModel());
    }

    public void removeStep() {
        assert stepsList.getSelectionModel() != null;

        steps.remove(stepsList.getSelectionModel().getSelectedIndex());
    }

    private void openSimpleAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    public void openConcealDialog() {
        createConfiguration().ifPresent(configuration -> new ConcealDialog().showAndWait().ifPresent(information -> {
            try {
                Picture picture = new BufferedImagePicture(ImageIO.read(information.getHideoutFile()));

                Concealer concealer = new Concealer(configuration);

                Picture concealed = concealer.process(new FileInputStream(information.getMessage()), picture);

                concealed.getSaver().saveAs(information.getOutputFormat(), information.getOutput());

                openSimpleAlert(Alert.AlertType.INFORMATION,
                        "Concealment succeeded! The output image is saved in '" + information.getOutput().getAbsolutePath() + "'.");
            } catch (IOException e) {
                openSimpleAlert(Alert.AlertType.ERROR,
                        "Failed to read image at '" + information.getHideoutFile().getAbsolutePath() + "'.");
            } catch (VerificationException e) {
                // TODO Show a more detailed message.
                openSimpleAlert(Alert.AlertType.ERROR,
                        "Some verifications failed.");
            } catch (UnavailableOutputFormatException e) {
                // TODO Show a more detailed message.
                openSimpleAlert(Alert.AlertType.ERROR,
                        "Cannot translate to the given output format.");
            }
        }));
    }

    public void openRevealDialog() {
        createConfiguration().ifPresent(configuration -> new RevealDialog().showAndWait().ifPresent(information -> {
            try {
                Picture picture = new BufferedImagePicture(ImageIO.read(information.getHideoutFile()));

                Revealer revealer = new Revealer(configuration);

                InputStream message = revealer.process(picture);

                IOUtils.copy(message, new FileOutputStream(information.getOutput()));

                openSimpleAlert(Alert.AlertType.INFORMATION,
                        "Concealment succeeded! The output image is saved in '" + information.getOutput().getAbsolutePath() + "'.");
            } catch (IOException e) {
                openSimpleAlert(Alert.AlertType.ERROR,
                        "Failed to read image at '" + information.getHideoutFile().getAbsolutePath() + "'.");
            } catch (VerificationException e) {
                // TODO Show a more detailed message.
                openSimpleAlert(Alert.AlertType.ERROR,
                        "Some verifications failed.");
            }
        }));
    }

    public void close() {
        Stage stage = (Stage) stepEditionGrid.getScene().getWindow();

        stage.close();
    }

    private Optional<ChameleonConfiguration> createConfiguration() {
        MagicNumber magicNumber = new MagicNumber(stopWord.getText().getBytes());

        ConcealmentStep[] describedSteps = new ConcealmentStep[steps.size()];
        for (int i = 0; i < describedSteps.length; i++) {
            describedSteps[i] = steps.get(i).toStep();
        }

        return Optional.of(new ChameleonConfiguration(magicNumber, describedSteps));
    }
}
