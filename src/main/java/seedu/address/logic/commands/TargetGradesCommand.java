package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Show CAP based on existing modules.
 */
public class TargetGradesCommand extends Command {
    public static final String COMMAND_WORD = "targetGrades";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Calculate grades you need to achieve "
            + "for your remaining ungraded modules "
            + "Parameters: NONE "
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "Your target grades are: %1$s";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        return new CommandResult(String.format(MESSAGE_SUCCESS, "None Yet"));
    }
}
