package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Code;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.util.ModuleBuilder;

import static java.util.Objects.requireNonNull;

public class AdjustCommand extends Command {
    public static final String COMMAND_WORD = "c_adjust";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adjust target grade of an incomplete module "
            + "Parameters: CODE YEAR SEM GRADE "
            + "Example: " + COMMAND_WORD + " CS2103 1 1 A+";
    public static final String MESSAGE_SUCCESS = "Module Adjusted: %1$s";

    private final Module moduleToFind;
    private final Grade grade;

    public AdjustCommand(Module moduleToFind, Grade grade) {
        requireNonNull(moduleToFind);
        requireNonNull(grade);
        this.moduleToFind = moduleToFind;
        this.grade = grade;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Module targetModule = model.findModule(moduleToFind);
        Module adjustedModule = targetModule.adjustGrade(grade);
        model.updateModule(targetModule, adjustedModule);
        return new CommandResult(String.format(MESSAGE_SUCCESS, adjustedModule));
    }
}
