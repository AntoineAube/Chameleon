package fr.antoineaube.chameleon.gui.models;

import fr.antoineaube.chameleon.core.configurations.ConcealmentPattern;
import fr.antoineaube.chameleon.core.configurations.ConcealmentStep;
import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;
import fr.antoineaube.chameleon.patterns.linear.horizontal.HorizontalLinearPattern;
import javafx.beans.property.*;

public class StepModel {

    private final IntegerProperty numberOfBits;
    private final ObjectProperty<ChannelColour> channelColour;
    private final ObjectProperty<ConcealmentPattern> pattern;
    private final BooleanProperty reversed;

    public StepModel() {
        numberOfBits = new SimpleIntegerProperty(2);
        channelColour = new SimpleObjectProperty<>(ChannelColour.RED);
        pattern = new SimpleObjectProperty<>(new HorizontalLinearPattern());
        reversed = new SimpleBooleanProperty(false);
    }

    public int getNumberOfBits() {
        return numberOfBits.get();
    }

    public IntegerProperty numberOfBitsProperty() {
        return numberOfBits;
    }

    public ChannelColour getChannelColour() {
        return channelColour.get();
    }

    public ObjectProperty<ChannelColour> channelColourProperty() {
        return channelColour;
    }

    public ConcealmentPattern getPattern() {
        return pattern.get();
    }

    public ObjectProperty<ConcealmentPattern> patternProperty() {
        return pattern;
    }

    public boolean isReversed() {
        return reversed.get();
    }

    public BooleanProperty reversedProperty() {
        return reversed;
    }

    public ConcealmentStep toStep() {
        return new ConcealmentStep(getNumberOfBits(), getChannelColour(), getPattern(), isReversed());
    }
}
