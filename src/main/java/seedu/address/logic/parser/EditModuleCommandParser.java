package seedu.address.logic.parser;

import static seedu.address.logic.parser.ParserUtil.argsAreNameValuePair;
import static seedu.address.logic.parser.ParserUtil.argsWithBounds;
import static seedu.address.logic.parser.ParserUtil.parseException;
import static seedu.address.logic.parser.ParserUtil.targetCodeNotNull;
import static seedu.address.logic.parser.ParserUtil.targetYearNullIffTargetSemesterNull;
import static seedu.address.logic.parser.ParserUtil.tokenize;
import static seedu.address.logic.parser.ParserUtil.validateName;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import java.util.Objects;

import seedu.address.logic.commands.EditModuleCommand;
import seedu.address.logic.parser.arguments.EditArgument;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * {@code EditModuleCommandParser} parses input arguments for
 * {@code EditModuleCommand}.
 */
public class EditModuleCommandParser implements Parser<EditModuleCommand> {
    /**
     * Message that informs that the command is in a wrong format and
     * prints the usage for edit command.
     */
    public static final String MESSAGE_INVALID_FORMAT =
            ParserUtil.MESSAGE_INVALID_FORMAT
                    + "\n"
                    + EditModuleCommand.MESSAGE_USAGE;

    /**
     * Message that informs that the command does not lead to any changes.
     */
    public static final String MESSAGE_NO_NEW_VALUE = "No new value provided.\n"
            + EditModuleCommand.MESSAGE_USAGE;

    /**
     * Message that informs that the target code is required and prints the
     * usage for edit command.
     */
    public static final String MESSAGE_TARGET_CODE_REQUIRED =
            ParserUtil.MESSAGE_TARGET_CODE_REQUIRED
                    + "\n"
                    + EditModuleCommand.MESSAGE_USAGE;

    /**
     * Message that informs that target year has to be specified if and only if
     * semester is specified, and prints the usage for edit command.
     */
    public static final String MESSAGE_YEAR_AND_SEMESTER_XOR_NULL =
            ParserUtil.MESSAGE_YEAR_AND_SEMESTER_XOR_NULL
                    + "\n"
                    + EditModuleCommand.MESSAGE_USAGE;

    /**
     * Immutable map that maps string argument to edit argument enum.
     */
    private static final Map<String, EditArgument> NAME_TO_ARGUMENT_MAP;

    /**
     * Map the object of the parsed value to {@code EditArgument} instance.
     */
    private EnumMap<EditArgument, Object> argMap;

    /**
     * Populate {@code NAME_TO_ARGUMENT_MAP} with short name and long name as
     * key and the respective {@code EditArgument} instance as value.
     */
    static {
        Map<String, EditArgument> map = new HashMap<>();
        for (EditArgument instance : EditArgument.values()) {
            map.put(instance.getShortName(), instance);
            map.put(instance.getLongName(), instance);
        }
        NAME_TO_ARGUMENT_MAP = Collections.unmodifiableMap(map);
    }

    /**
     * Parses {@code argsInString} in the context of {@code EditModuleCommand}
     * and returns {@code EditModuleCommand} for execution.
     * <p>
     * Throws {@code ParseException} when:
     * <ul>
     *     <li>Number of argument is not between 4 and 16.</li>
     *     <li>Number of argument is not even.</li>
     *     <li>Argument is not in name-value pair format</li>
     *     <li>Argument contains illegal name</li>
     *     <li>Same name appeared more than once</li>
     *     <li>Target code is not provided.</li>
     *     <li>Target year is provided but target semester is not provided.</li>
     *     <li>Target semester is provided but target year is not provided.</li>
     *     <li>No new value provided.</li>
     *     <li>Unable to parse any field.</li>
     * </ul>
     *
     * @param argsInString String that contains all the argument
     * @return {@code EditModuleCommand} object for execution
     * @throws ParseException thrown when user input does not conform to the
     * expected format
     */
    public EditModuleCommand parse(String argsInString) throws ParseException {
        // Setup argument map.
        argMap = new EnumMap<>(EditArgument.class);
        argMap.put(EditArgument.TARGET_CODE, null);
        argMap.put(EditArgument.TARGET_YEAR, null);
        argMap.put(EditArgument.TARGET_SEMESTER, null);
        argMap.put(EditArgument.NEW_CODE, null);
        argMap.put(EditArgument.NEW_YEAR, null);
        argMap.put(EditArgument.NEW_SEMESTER, null);
        argMap.put(EditArgument.NEW_CREDIT, null);
        argMap.put(EditArgument.NEW_GRADE, null);

        // Converts argument string to tokenize argument array.
        String[] args = tokenize(argsInString);

        // Size of argument should be between 4 to 16.
        // Size of argument should be even.
        // Arguments should be in name-value pair.
        // Name should be legal.
        // No duplicate name.
        argsWithBounds(args, 4, 16);
        argsAreNameValuePair(args, MESSAGE_INVALID_FORMAT);
        validateName(args, NAME_TO_ARGUMENT_MAP, MESSAGE_INVALID_FORMAT);

        // Parse values.
        parseValues(args);

        // Target code should not be null.
        // Target year is null if and only if target semester is null.
        // At least one new value should be specified.
        targetCodeNotNull(
                argMap.get(EditArgument.TARGET_CODE),
                MESSAGE_TARGET_CODE_REQUIRED);
        targetYearNullIffTargetSemesterNull(
                argMap.get(EditArgument.TARGET_YEAR),
                argMap.get(EditArgument.TARGET_SEMESTER),
                MESSAGE_YEAR_AND_SEMESTER_XOR_NULL);
        atLeastOneNewValueSpecified();

        // Return edit module command for execution.
        return new EditModuleCommand(argMap);
    }

    /**
     * Checks if argument array does not contain the same name twice and all
     * names are legal.
     *
     * @param args array of name-value pair arguments
     * @throws ParseException
     */
    public static void validateName(String[] args) throws ParseException {
        List<EditArgument> nameArray = IntStream.range(0, args.length)
                .filter(index -> index % 2 == 0)
                .mapToObj(index -> NAME_TO_ARGUMENT_MAP.get(args[index]))
                .collect(Collectors.toList());

        boolean illegalNameExist = nameArray.stream()
                .anyMatch(Objects::isNull);

        if (illegalNameExist) {
            throw parseException(MESSAGE_INVALID_FORMAT);
        }

        Set<EditArgument> nameSet = new HashSet<>(nameArray);

        if (nameArray.size() != nameSet.size()) {
            throw parseException(MESSAGE_INVALID_FORMAT);
        }
    }

    /**
     * Parses the value into its relevant object.
     *
     * @param args array of name-value pair arguments
     * @throws ParseException thrown when the value cannot be parsed
     */
    private void parseValues(String[] args) throws ParseException {
        for (int index = 0; index < args.length; index = index + 2) {
            EditArgument name = NAME_TO_ARGUMENT_MAP.get(args[index]);
            Object value = name.getValue(args[index + 1]);
            argMap.put(name, value);
        }
    }

    /**
     * Checks that target code is not be null.
     *
     * @throws ParseException thrown when target code is null
     */
    private void targetCodeNotNull() throws ParseException {
        // Throw parse exception if target code is null.
        Object targetCode = argMap.get(EditArgument.TARGET_CODE);
        if (targetCode == null) {
            throw parseException(MESSAGE_TARGET_CODE_REQUIRED);
        }
    }

    /**
     * Checks that target year is null if and only if target semester is also
     * null.
     * <p>
     * Target year is null if and only if target semester is also null.
     *
     * @throws ParseException thrown when target year and target semester is
     * exclusively null
     */
    private void targetYearNullIffTargetSemesterNull() throws ParseException {
        Object targetYear = argMap.get(EditArgument.TARGET_YEAR);
        Object targetSemester = argMap.get(EditArgument.TARGET_SEMESTER);
        if (targetYear == null ^ targetSemester == null) {
            throw parseException(MESSAGE_YEAR_AND_SEMESTER_XOR_NULL);
        }
    }

    /**
     * Checks that one of code, year, semester, credit, or grade should have a
     * new value.
     *
     * @throws ParseException Thrown when code, year, semester, credit, and
     * grade are all null
     */
    private void atLeastOneNewValueSpecified() throws ParseException {
        boolean allNewValueIsNull = argMap.entrySet()
                .stream()
                .filter(entry -> entry.getKey().name().startsWith("NEW"))
                .map(entry -> entry.getValue())
                .allMatch(Objects::isNull);

        if (allNewValueIsNull) {
            throw parseException(MESSAGE_NO_NEW_VALUE);
        }
    }
}
