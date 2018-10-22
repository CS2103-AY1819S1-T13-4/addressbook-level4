package seedu.address.model.capgoal;

//@@author jeremiah-ang

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents Cap Goal
 *
 * Immutable. Value can be null.
 */
public class CapGoal {

    public static final String MESSAGE_CAP_GOAL_CONSTRAINTS = "CAP goal should between 0.0 and 5.0 inclusive.";
    private static final String MESSAGE_IS_NULL = "NIL";

    public final double capGoal;
    public final boolean isSet;
    public final boolean isImpossible;

    public CapGoal() {
        capGoal = 0;
        isSet = false;
        isImpossible = false;
    }

    public CapGoal(double capGoal) {
        this(capGoal, false);
    }

    public CapGoal(double capGoal, boolean isImpossible) {
        requireNonNull(capGoal);
        checkArgument(isValidCode(capGoal), MESSAGE_CAP_GOAL_CONSTRAINTS);
        isSet = true;
        this.capGoal = capGoal;
        this.isImpossible = isImpossible;
    }

    public static Boolean isValidCode(double capGoal) {
        return capGoal >= 0 && capGoal <= 5.0;
    }

    /**
     * Returns the cap goal
     * @return
     */
    public double getCapGoal() {
        return capGoal;
    }

    public boolean isSet() {
        return isSet;
    }

    public CapGoal isImpossible() {
        return new CapGoal(capGoal, true);
    }


    @Override
    public String toString() {
        if (isSet) {
            return "" + getCapGoal();
        }
        return MESSAGE_IS_NULL;

    }
}
