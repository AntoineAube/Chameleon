package fr.antoineaube.chameleon.integration.benchmarks;

import fr.antoineaube.chameleon.core.configurations.ChameleonConfiguration;
import fr.antoineaube.chameleon.core.configurations.ConcealmentStep;
import fr.antoineaube.chameleon.core.configurations.MagicNumber;
import fr.antoineaube.chameleon.core.pictures.Picture;
import fr.antoineaube.chameleon.core.pictures.structures.ChannelColour;
import fr.antoineaube.chameleon.core.processes.concealments.Concealer;
import fr.antoineaube.chameleon.patterns.linear.horizontal.HorizontalLinearPattern;
import fr.antoineaube.chameleon.pictures.implementations.buffered.BufferedImagePicture;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@BenchmarkMode(Mode.AverageTime)
public class ConcealmentBenchmark {

    private ChameleonConfiguration simpleConfiguration() {
        byte[] magicNumberContent = {0b1101, 0b101};

        ConcealmentStep[] steps = {
                new ConcealmentStep(3, ChannelColour.RED, new HorizontalLinearPattern(), true),
                new ConcealmentStep(3, ChannelColour.BLUE, new HorizontalLinearPattern(), false),
                new ConcealmentStep(3, ChannelColour.GREEN, new HorizontalLinearPattern(), true)
        };

        return new ChameleonConfiguration(new MagicNumber(magicNumberContent), steps);
    }

    private InputStream fileInput(String name) throws IOException {
        URL fileUrl = getClass().getClassLoader().getResource("messages-samples/" + name);

        assert fileUrl != null;

        return fileUrl.openStream();
    }

    private Picture hideout(String name) throws IOException {
        URL imageUrl = getClass().getClassLoader().getResource("images-samples/" + name);

        assert imageUrl != null;

        return new BufferedImagePicture(ImageIO.read(imageUrl));
    }

    @Benchmark
    public void simpleConfigurationLittleMessageLittleImage() throws IOException {
        Concealer concealer = new Concealer(simpleConfiguration());
        InputStream input = fileInput("little-message.txt");
        Picture hideout = hideout("little-image.png");

        concealer.process(input, hideout);
    }

    @Benchmark
    public void simpleConfigurationBibleChameleon() throws IOException {
        Concealer concealer = new Concealer(simpleConfiguration());
        InputStream input = fileInput("bible.txt");
        Picture hideout = hideout("chameleon.bmp");

        concealer.process(input, hideout);
    }

    @Benchmark
    public void simpleConfigurationTimelapseOfMountainsWallpaper() throws IOException {
        Concealer concealer = new Concealer(simpleConfiguration());
        InputStream input = fileInput("timelapse-of-mountains.mp4");
        Picture hideout = hideout("wallpaper.png");

        concealer.process(input, hideout);
    }
}
