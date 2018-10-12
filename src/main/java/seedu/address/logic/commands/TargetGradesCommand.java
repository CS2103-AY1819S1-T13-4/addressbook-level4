package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.module.Module;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author: jeremiah-ang
/**
 * Show CAP based on existing modules.
 */
public class TargetGradesCommand extends Command {
    public static final String COMMAND_WORD = "targetGrades";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Calculate grades you need to achieve "
            + "for your remaining ungraded modules "
            + "Parameters: NONE "
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_UNACHIEVABLE_GOAL = "Impossible to achieve target CAP, "
            + "consider withdrawing from NUS";
    public static final String MESSAGE_SUCCESS = "Your target grades are: %1$s";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        ObservableList<Module> targetGrades = model.getTargetGrades();
        if (targetGrades == null) {
            throw new CommandException(MESSAGE_UNACHIEVABLE_GOAL);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, "None Yet"));
    }
}
