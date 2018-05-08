package fr.antoineaube.chameleon.integration.tests.implementation;

import fr.antoineaube.chameleon.integration.tests.framework.BaseIT;
import fr.antoineaube.chameleon.patterns.linear.horizontal.HorizontalLinearPattern;

import static fr.antoineaube.chameleon.core.pictures.structures.ChannelColour.BLUE;
import static fr.antoineaube.chameleon.core.pictures.structures.ChannelColour.GREEN;
import static fr.antoineaube.chameleon.core.pictures.structures.ChannelColour.RED;

public class SimpleBGRConfiguration extends BaseIT {

    @Override
    public void describeConfiguration() {
        magicNumber(0b11011010, 0b10101011);

        step()
                .bits(1)
                .channel(BLUE)
                .pattern(new HorizontalLinearPattern());

        step()
                .bits(1)
                .channel(GREEN)
                .pattern(new HorizontalLinearPattern())
                .reversed();

        step()
                .bits(1)
                .channel(RED)
                .pattern(new HorizontalLinearPattern())
                .reversed();
    }
}
