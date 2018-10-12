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
        String expectedMsg = "None Yet";
        assertTargetGradesSuccess(expectedMsg);
    }
    
    

    private void assertTargetGradesSuccess(String expectedMsg) {
        assertApplicationDisplaysExpected("",
                String.format(TargetGradesCommand.MESSAGE_SUCCESS, expectedMsg),
                getModel());
    }

    private void assertTargetGradesFailure(String expectedMsg) {
        assertApplicationDisplaysExpected(TargetGradesCommand.COMMAND_WORD,
                String.format(TargetGradesCommand.MESSAGE_SUCCESS, expectedMsg),
                getModel());
    }
}
