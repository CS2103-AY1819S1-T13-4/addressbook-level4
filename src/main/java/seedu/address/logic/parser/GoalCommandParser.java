package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.GoalCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.capgoal.CapGoal;

//@@author jeremiah-ang
/**
 * Parses User Input
 */
public class GoalCommandParser implements Parser<GoalCommand> {
    @Override
    public GoalCommand parse(String userInput) throws ParseException {
        final String trimmedArgs = userInput.trim();
        final String format = String.format(MESSAGE_INVALID_COMMAND_FORMAT, GoalCommand.MESSAGE_USAGE);
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(format);
        }

        try {
            double newGoal = Double.parseDouble(trimmedArgs);
            if (CapGoal.isValidCode(newGoal)) {
                return new GoalCommand(newGoal);
            } else {
                throw new ParseException(CapGoal.MESSAGE_CAP_GOAL_CONSTRAINTS);
            }
        } catch (NumberFormatException nfe) {
            throw new ParseException(format);
        }
    }
}
