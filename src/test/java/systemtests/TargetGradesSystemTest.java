package systemtests;

import org.junit.Test;

import seedu.address.logic.commands.TargetGradesCommand;

public class TargetGradesSystemTest extends AddressBookSystemTest {

    /**
     * Empty system should show cap = 0
     */
    @Test
    public void targetGrades() {
        executeCommand(TargetGradesCommand.COMMAND_WORD);
        String expectedMsg = TargetGradesCommand.MESSAGE_UNACHIEVABLE_GOAL;
        assertTargetGradesFailure(expectedMsg);
    }

    private void assertTargetGradesSuccess(String expectedMsg) {
        assertApplicationDisplaysExpected("",
                String.format(TargetGradesCommand.MESSAGE_SUCCESS, expectedMsg),
                getModel());
    }

    private void assertTargetGradesFailure(String failureMessage) {
        assertApplicationDisplaysExpected(TargetGradesCommand.COMMAND_WORD,
                failureMessage,
                getModel());
    }
}
