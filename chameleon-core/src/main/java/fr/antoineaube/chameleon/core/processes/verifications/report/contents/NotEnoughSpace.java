package fr.antoineaube.chameleon.core.processes.verifications.report.contents;

public class NotEnoughSpace implements Verified {

    private final int requiredBits;
    private final int availableBits;

    public NotEnoughSpace(int requiredBits, int availableBits) {
        this.requiredBits = requiredBits;
        this.availableBits = availableBits;
    }

    public int getRequiredBits() {
        return requiredBits;
    }

    public int getAvailableBits() {
        return availableBits;
    }

    @Override
    public VerificationStatus getStatus() {
        return VerificationStatus.ERROR;
    }
}
