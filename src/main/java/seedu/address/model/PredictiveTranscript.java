package seedu.address.model;

/**
 * Predictive Transcript is an extension of Transcript but with the
 * capabilities of coming up with predictions with some extra information given by users
 */
public class PredictiveTranscript extends Transcript {

    public PredictiveTranscript() {
        super();
    }

    public PredictiveTranscript(ReadOnlyTranscript toBeCopied) {
        super(toBeCopied);
    }
}
