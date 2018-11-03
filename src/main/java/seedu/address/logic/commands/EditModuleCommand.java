package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Code;
import seedu.address.model.module.Credit;
import seedu.address.model.module.Grade;
import seedu.address.model.module.Module;
import seedu.address.model.module.Semester;
import seedu.address.model.module.Year;
import seedu.address.model.util.ModuleBuilder;

/**
 * {@code EditModuleCommand} edit fields of existing module.
 */
public class EditModuleCommand extends Command {

    /**
     * Command word for {@code EditModuleCommand}.
     */
    public static final String COMMAND_WORD = "edit";

    /**
     * Usage of <b>edit</b>.
     * <p>
     * Provides the description and syntax of <b>edit</b>.
     */
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the details of the module specified by the module code."
            + " Existing values will be overwritten by the input values."
            + " \nParameters:"
            + " -code MODULE_CODE"
            + " -year [YEAR]"
            + " -semester [SEMESTER]"
            + " -credit [CREDIT]"
            + " -grade [GRADE]";

    // Constants for CommandException.
    public static final String MESSAGE_EDIT_SUCCESS = "Edited module: %1$s";
    public static final String MESSAGE_NO_SUCH_MODULE = "No such module exist.";
    public static final String MESSAGE_MODULE_EXIST = "Edited module already"
            + " exist.";
    public static final String MESSAGE_MODULE_INCOMPLETE = "Cannot change grade"
            + " of incomplete modules.";
    public static final String MESSAGE_MULTIPLE_MODULE_EXIST = "Multiple module"
            + " entries with the same module code exist but year or semester is"
            + " not specified.";

    // Fields.
    private final Code targetCode;
    private final Year targetYear;
    private final Semester targetSemester;
    private final Code newCode;
    private final Year newYear;
    private final Semester newSemester;
    private final Credit newCredit;
    private final Grade newGrade;

    /**
     * Prevents instantiation of empty constructor.
     */
    private EditModuleCommand() {
        this.targetCode = null;
        this.targetYear = null;
        this.targetSemester = null;
        this.newCode = null;
        this.newYear = null;
        this.newSemester = null;
        this.newCredit = null;
        this.newGrade = null;
    }

    /**
     * Constructor that instantiates {@code EditModuleCommand} and sets the
     * immutable fields needed for editing the module.
     *
     * @param targetCode target code that identifies the targeted module
     * @param targetYear target year that identifies the targeted module
     * @param targetSemester target semester that identifies the targeted module
     * @param newCode replaces module code of the targeted module if not null
     * @param newYear replaces year of the targeted module if not null
     * @param newSemester replaces semester of the targeted module if not null
     * @param newCredit replaces credit of the targeted module if not null
     * @param newGrade replaces grade of targeted module if not null
     */
    public EditModuleCommand(Code targetCode, Year targetYear,
            Semester targetSemester, Code newCode, Year newYear,
            Semester newSemester, Credit newCredit, Grade newGrade) {
        assert targetCode != null;
        assert !(targetYear == null ^ targetSemester == null);

        // Target fields.
        this.targetCode = targetCode;
        this.targetYear = targetYear;
        this.targetSemester = targetSemester;

        // New fields.
        this.newCode = newCode;
        this.newYear = newYear;
        this.newSemester = newSemester;
        this.newCredit = newCredit;
        this.newGrade = newGrade;
    }

    /**
     * Edits the targeted module in the module list of transcript.
     *
     * @param model {@code Model} that the command operates on.
     * @param history {@code CommandHistory} that the command operates on.
     * @return result of the command
     * @throws CommandException thrown when command cannot be executed
     * successfully
     */
    @Override
    public CommandResult execute(Model model, CommandHistory history)
            throws CommandException {
        requireNonNull(model);

        // Get target module.
        // Throws CommandException if module does not exists.
        // Throws CommandException if module is incomplete and grade changed.
        Module target = getTargetModule(model);
        moduleCompletedIfGradeChange(target);

        // Get edited module.
        // Throws CommandException if edited module already exist.
        Module editedModule = createEditedModule(target);
        editedModuleExist(model, editedModule);

        // Update module and commit the transcript.
        model.updateModule(target, editedModule);
        model.commitTranscript();

        String successMsg = String.format(MESSAGE_EDIT_SUCCESS, editedModule);
        return new CommandResult(successMsg);
    }

    /**
     * Returns the targeted module.
     * <p>
     * Checks if module specified by {@code targetCode} exist. If multiple
     * module entries matches {@code targetCode}, check if {@code targetYear}
     * and {@code targetSemester} has been specified. If all check passes, the
     * targeted module is returned.
     *
     * @param model model containing the transcript
     * @return targeted module
     * @throws CommandException thrown when specified module does not exist or
     * there are multiple module entries matching the {@code targetCode} but
     * {@code targetYear} or {@code targetSemester} was not specified
     */
    private Module getTargetModule(Model model) throws CommandException {
        // Returns the number of modules with target code, year, and semester.
        List<Module> filteredModule = model.getFilteredModuleList()
                .stream()
                .filter(this::isTargetModule)
                .collect(Collectors.toList());

        // Throws exception when targeted module does not exist.
        if (filteredModule.size() == 0) {
            throw new CommandException(MESSAGE_NO_SUCH_MODULE);
        }

        // Returns the targeted module
        assert filteredModule.size() == 1;
        return filteredModule.get(0);
    }

    /**
     * Returns true if module matches all non-null target fields.
     *
     * @param module module {@code Module} checked
     * @return true if module matches all non-null target fields
     */
    private boolean isTargetModule(Module module) {
        if (targetYear == null && targetSemester == null) {
            return module.getCode().equals(targetCode);
        }

        return module.getCode().equals(targetCode)
                && module.getYear().equals(targetYear)
                && module.getSemester().equals(targetSemester);
    }

    /**
     * Returns the edited version of the target module.
     *
     * @param target the module to be edited
     * @return the edited version of {@code target}
     */
    private Module createEditedModule(Module target) {
        ModuleBuilder moduleBuilder = new ModuleBuilder(target);

        if (newCode != null) {
            moduleBuilder = moduleBuilder.withCode(newCode);
        }

        if (newYear != null) {
            moduleBuilder = moduleBuilder.withYear(newYear);
        }

        if (newSemester != null) {
            moduleBuilder = moduleBuilder.withSemester(newSemester);
        }

        if (newCredit != null) {
            moduleBuilder = moduleBuilder.withCredit(newCredit);
        }

        if (newGrade != null) {
            moduleBuilder = moduleBuilder.withGrade(newGrade);
        }

        return moduleBuilder.build();
    }

    /**
     * Throws {@code CommandException} if target is an incomplete module and
     * grade has been changed.
     *
     * @param target targeted module to be updated
     * @throws CommandException thrown when target is an incomplete module and
     * grade has been changed
     */
    private void moduleCompletedIfGradeChange(Module target)
            throws CommandException {
        boolean targetIncomplete = target.getGrade().isIncomplete();
        boolean newGradeNotNull = newGrade != null;

        if (targetIncomplete && newGradeNotNull) {
            throw new CommandException(MESSAGE_MODULE_INCOMPLETE);
        }
    }

    /**
     * Throws {@code CommandException} if code, year, or semester has been
     * changed, and there exist a module in module list of transcript that
     * shares the same module code, year, and semester as the
     * {@code editedModule}.
     *
     * @param model {@code Model} that the command operates on.
     * @param editedModule module with updated fields
     * @throws CommandException thrown if current module list already contain a
     * module sharing the same module code, year, and semester as the
     * {@code editedModule}
     */
    private void editedModuleExist(Model model, Module editedModule)
            throws CommandException {
        boolean identifierNotChanged = newCode == null
                && newYear == null
                && newSemester == null;

        if (identifierNotChanged) {
            return;
        }

        if (model.hasModule(editedModule)) {
            throw new CommandException(MESSAGE_MODULE_EXIST);
        }
    }

    /**
     * Returns true if all field matches.
     * <p>
     * Field matches when they are both null or equal to one another.
     *
     * @param other the other object compared against
     * @return true if all field matches
     */
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditModuleCommand)) {
            return false;
        }

        // state check
        EditModuleCommand e = (EditModuleCommand) other;

        boolean targetYearSame = (targetYear == null && e.targetYear == null)
                || targetYear.equals(e.targetYear);

        boolean targetSemesterSame = (targetSemester == null && e.targetSemester == null)
                || targetSemester.equals(e.targetSemester);

        boolean newCodeSame = (newCode == null && e.newCode == null)
                || newCode.equals(e.newCode);

        boolean newYearSame = (newYear == null && e.newYear == null)
                || newYear.equals(e.newYear);

        boolean newSemesterSame = (newSemester == null && e.newSemester == null)
                || newSemester.equals(e.newSemester);

        boolean newCreditSame = (newCredit == null && e.newCredit == null)
                || newCredit.equals(e.newCredit);

        boolean newGradeSame = (newGrade == null && e.newGrade == null)
                || newGrade.equals(e.newGrade);

        return targetCode.equals(e.targetCode)
                && targetYearSame
                && targetSemesterSame
                && newCodeSame
                && newYearSame
                && newSemesterSame
                && newCreditSame
                && newGradeSame;
    }
}
