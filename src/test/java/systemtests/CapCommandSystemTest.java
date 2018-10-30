package systemtests;

//import org.junit.Test;

import seedu.address.logic.commands.CapCommand;

//@@author jeremiah-ang
public class CapCommandSystemTest extends AddressBookSystemTest {

    /**
     * TODO: REMOVE
     */
    //@Test
    public void cap() {

        /**
         * Empty system should show cap = 0
         */
        executeCommand(CapCommand.COMMAND_WORD);
        double cap = 0.0;
        assertApplicationDisplaysExpected("", String.format(CapCommand.MESSAGE_SUCCESS, cap), getModel());
    }
}
