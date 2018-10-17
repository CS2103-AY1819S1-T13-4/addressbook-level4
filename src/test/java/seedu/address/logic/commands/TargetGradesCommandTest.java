package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.model.util.SampleDataUtil.getSampleTranscript;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


//@@author jeremiah-ang
public class TargetGradesCommandTest {

    private Model model = new ModelManager(getSampleTranscript(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void commandFailure() {
        TargetGradesCommand command = new TargetGradesCommand();
        assertCommandFailure(command, model, commandHistory, TargetGradesCommand.MESSAGE_UNACHIEVABLE_GOAL);
    }
}
