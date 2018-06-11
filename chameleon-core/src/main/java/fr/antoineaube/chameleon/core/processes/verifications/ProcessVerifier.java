package fr.antoineaube.chameleon.core.processes.verifications;

import fr.antoineaube.chameleon.core.configurations.ChameleonConfiguration;
import fr.antoineaube.chameleon.core.configurations.ConcealmentStep;
import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;
import fr.antoineaube.chameleon.core.processes.ChameleonProcess;
import fr.antoineaube.chameleon.core.processes.verifications.report.contents.MissingChannel;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProcessVerifier extends ChameleonProcess {

    public ProcessVerifier(ChameleonConfiguration configuration) {
        super(configuration);
    }

    protected Optional<MissingChannel> checkChannels(Picture hideout) {
        List<ChannelColour> configurationChannels = Arrays.stream(getConfiguration().getSteps())
                .map(ConcealmentStep::getChannel)
                .collect(Collectors.toList());

        List<ChannelColour> pictureChannels = Arrays.asList(hideout.getColourChannels());

        for (ChannelColour configurationChannel : configurationChannels) {
            if (!pictureChannels.contains(configurationChannel)) {
                return Optional.of(new MissingChannel(configurationChannel));
            }
        }

        return Optional.empty();
    }
}
