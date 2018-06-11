package fr.antoineaube.chameleon.integration.tests.framework;

import fr.antoineaube.chameleon.core.configurations.ChameleonConfiguration;
import fr.antoineaube.chameleon.core.configurations.ConcealmentPattern;
import fr.antoineaube.chameleon.core.configurations.ConcealmentStep;
import fr.antoineaube.chameleon.core.configurations.MagicNumber;
import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;
import fr.antoineaube.chameleon.core.processes.concealments.Concealer;
import fr.antoineaube.chameleon.core.processes.revelations.Revealer;
import fr.antoineaube.chameleon.core.processes.verifications.VerificationException;
import fr.antoineaube.chameleon.pictures.implementations.buffered.BufferedImagePicture;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public abstract class BaseIT {

    private static final Logger LOGGER = LogManager.getLogger(BaseIT.class);

    private MagicNumber magicNumber;
    private List<StepDescriber> stepDescribers;

    @BeforeEach
    void initialize() {
        stepDescribers = new ArrayList<>();
    }

    @DisplayName("Execute the concealment & revelation with the described configuration")
    @TestFactory
    final Stream<DynamicTest> executeTest() {
        describeConfiguration();

        return getImagesFiles().stream()
                .map(imageFile -> {
                    try {
                        return new PictureBundle(new BufferedImagePicture(ImageIO.read(imageFile)), imageFile);
                    } catch (IOException e) {
                        LOGGER.error("Error while reading the file into an image", e);
                        return null;
                    }
                })

                // TODO Remove the cases when the message is too big for the "image & configuration".

                .flatMap(pictureBundle -> getMessagesFiles().stream().map(messageFile -> {
                    String caseName = "Image: '" + pictureBundle.file.getName() + "' ; message: '" + messageFile.getName() + "'";

                    return DynamicTest.dynamicTest(caseName, () -> testCase(pictureBundle.file, messageFile));
                }));
    }

    protected abstract void describeConfiguration();

    private void testCase(File imageFile, File messageFile) throws IOException, VerificationException {
        ChameleonConfiguration configuration = createConfiguration();

        LOGGER.info("Running configuration:\n" + configuration);

        Picture picture = new BufferedImagePicture(ImageIO.read(imageFile));

        try (InputStream messageInFile = new FileInputStream(messageFile)) {
            Concealer concealer = new Concealer(configuration);
            concealer.process(messageInFile, picture);
        }

        Revealer revealer = new Revealer(configuration);

        try (InputStream readMessage = revealer.process(picture); InputStream messageInFile = new FileInputStream(messageFile)) {
            assertArrayEquals(IOUtils.toByteArray(messageInFile), IOUtils.toByteArray(readMessage));
        }
    }

    private ChameleonConfiguration createConfiguration() {
        ConcealmentStep[] steps = new ConcealmentStep[this.stepDescribers.size()];
        for (int i = 0; i < steps.length; i++) {
            steps[i] = this.stepDescribers.get(i).build();
        }

        return new ChameleonConfiguration(magicNumber, steps);
    }

    private List<File> getImagesFiles() {
        return getContentFiles("images-samples");
    }

    private List<File> getMessagesFiles() {
        return getContentFiles("messages-samples");
    }

    private List<File> getContentFiles(String folderPath) {
        URL imageFolderUrl = getClass().getClassLoader().getResource(folderPath);

        assert imageFolderUrl != null;

        File[] contentFiles = new File(imageFolderUrl.getFile()).listFiles();

        assert contentFiles != null;

        return Arrays.asList(contentFiles);
    }

    protected final void magicNumber(byte... bytes) {
        magicNumber = new MagicNumber(bytes);
    }

    protected final void magicNumber(int... ints) {
        byte[] bytes = new byte[ints.length];

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) ints[i];
        }

        magicNumber(bytes);
    }

    protected final StepDescriber step() {
        StepDescriber stepDescriber = new StepDescriber();

        stepDescribers.add(stepDescriber);

        return stepDescriber;
    }

    public class StepDescriber {

        private int bitsNumber;
        private ChannelColour channel;
        private ConcealmentPattern followedPattern;
        private boolean reversed;

        public StepDescriber bits(int bitsNumber) {
            this.bitsNumber = bitsNumber;

            return this;
        }

        public StepDescriber channel(ChannelColour channel) {
            this.channel = channel;

            return this;
        }

        public StepDescriber pattern(ConcealmentPattern followedPattern) {
            this.followedPattern = followedPattern;

            return this;
        }

        public StepDescriber reversed() {
            reversed = true;

            return this;
        }

        private ConcealmentStep build() {
            return new ConcealmentStep(bitsNumber, channel, followedPattern, reversed);
        }
    }

    private static class PictureBundle {
        private final Picture picture;
        private final File file;

        public PictureBundle(Picture picture, File file) {
            this.picture = picture;
            this.file = file;
        }
    }
}
